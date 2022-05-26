package com.company.repositories;

import com.company.dataHandlerDB.DataBaseConfiguration;
import com.company.stores.Address;
import com.company.stores.orders.Card;

import java.sql.*;
import java.util.UUID;

public class CardRepository {
    private static CardRepository instance;
    private static final Connection connection = DataBaseConfiguration.getDatabaseConnection();

    private CardRepository(){

    }

    public static CardRepository createCardRepository() {
        if (instance == null) {
            instance = new CardRepository();
        }
        return instance;
    }

    public void createTableCard(){
        try{
            String createCard = "CREATE TABLE IF NOT EXISTS pao.card"+
                    "(id VARCHAR (50) PRIMARY KEY, "+
                    "number_card VARCHAR(50) not NULL,"+
                    "validThru DATE not NULL,"+
                    "type_card VARCHAR(50) not NULL,"+
                    "authorisedSignature INTEGER not NULL) ";


            Statement statement = connection.createStatement();
            statement.execute(createCard);
            if(statement.execute(createCard)==false){
                System.out.println("Table card was created!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    private boolean checkIfNotExists(UUID id){
        String idString = id.toString();
        try {
            String check = "SELECT count(id) FROM pao.card WHERE id = ?";
            PreparedStatement sql_statement = connection.prepareStatement(check);
            sql_statement.setString(1,idString);
            ResultSet  result = sql_statement.executeQuery();
            ResultSetMetaData rsmd= result.getMetaData();
            if (rsmd.getColumnCount() == 0) {
                return true;
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void insertIntoCard(Card card) {
        if (checkIfNotExists(card.getId())) {
            try {
                String sql_insert = "INSERT INTO card ( id,number_card,validThru,type_card,authorisedSignature) VALUES (?,?,?,?,?)";
                PreparedStatement sql_statement = connection.prepareStatement(sql_insert);

                sql_statement.setString(1, card.getId().toString());
                sql_statement.setString(2, card.getNumber());
                sql_statement.setDate(3, new java.sql.Date(card.getValidThru().getTime()));
                sql_statement.setString(4, card.getType());
                sql_statement.setInt(5, card.getAuthorisedSignature());


                int rowsInserted = sql_statement.executeUpdate();
                if (rowsInserted == 0) {
                    System.out.println("Error! The card couldn't be created!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }


    public void deleteTableCard(){
        try{
            String deleteTableCard = "DROP TABLE pao.card";

            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteTableCard);
            System.out.println("Card table was deleted!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
