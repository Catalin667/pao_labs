package com.company.repositories;

import com.company.dataHandlerDB.DataBaseConfiguration;
import com.company.stores.Address;


import java.sql.*;
import java.util.UUID;

public class AddressRepository {
    private static AddressRepository instance;
    private static final Connection connection = DataBaseConfiguration.getDatabaseConnection();

    private AddressRepository(){

    }

    public static AddressRepository createAddressRepository() {
        if (instance == null) {
            instance = new AddressRepository();
        }
        return instance;
    }

    public void createTableAddress(){
        try{
            String createAddress = "CREATE TABLE IF NOT EXISTS pao.address"+
                    "(id VARCHAR (50) PRIMARY KEY, "+
                    "city VARCHAR(50) not NULL,"+
                    "street VARCHAR(50) not NULL,"+
                    "street_number INTEGER not NULL,"+
                    "county VARCHAR(50) not NULL) ";


            Statement statement = connection.createStatement();
            statement.execute(createAddress);
            if(statement.execute(createAddress)==false){
                System.out.println("Table card was created!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private boolean checkIfNotExists(UUID id){
        String idString = id.toString();
        try {
            String check = "SELECT count(id) FROM pao.address WHERE id = ?";
            PreparedStatement sql_statement = connection.prepareStatement(check);
            sql_statement.setString(1,idString);
            ResultSet result = sql_statement.executeQuery();
            ResultSetMetaData rsmd= result.getMetaData();
            if (rsmd.getColumnCount() == 0) {
                return true;
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void insertIntoAddress(Address address){

            try {
                String sql_insert = "INSERT INTO address ( id,city,street,street_number,county) VALUES (?,?,?,?,?)";
                PreparedStatement sql_statement = connection.prepareStatement(sql_insert);

                sql_statement.setString(1, address.getId().toString());
                sql_statement.setString(2, address.getCity());
                sql_statement.setString(3, address.getStreet());
                sql_statement.setInt(4, address.getNumber());
                sql_statement.setString(5, address.getCounty());


                int rowsInserted = sql_statement.executeUpdate();
                if (rowsInserted == 0) {
                    System.out.println("Error! The address couldn't be created!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }



    public void deleteTableAddress(){
        try{
            String deleteTableAddress = "DROP TABLE pao.address";
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteTableAddress);
            System.out.println("Address table was deleted!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
