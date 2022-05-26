package com.company.repositories;

import com.company.dataHandlerDB.DataBaseConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreRepository {

    private static StoreRepository instance;
    private static final Connection connection = DataBaseConfiguration.getDatabaseConnection();

    private StoreRepository(){

    }

    public static StoreRepository createStoreRepository() {
        if (instance == null) {
            instance = new StoreRepository();
        }
        return instance;
    }

    public void createTableStore(){
        try{
            String createCustomer = "CREATE TABLE IF NOT EXISTS pao.store"+
                    "(id VARCHAR (50) PRIMARY KEY, "+
                    "name VARCHAR(50) not NULL,"+
                    "address VARCHAR(50),"+
                    "numberProducts INTEGER ,"+
                    "status VARCHAR(50)," +
                    "program VARCHAR(50),"+
                    " FOREIGN  KEY (address) REFERENCES pao.address(id)) ";

            Statement statement = connection.createStatement();
            statement.execute(createCustomer);
            if(statement.execute(createCustomer)==false){
                System.out.println("Table store was created!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteTableStore(){
        try{
            String deleteTableStore = "DROP TABLE pao.store";
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteTableStore);
            System.out.println("Store table was deleted!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
