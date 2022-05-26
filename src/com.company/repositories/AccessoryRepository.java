package com.company.repositories;

import com.company.dataHandlerDB.DataBaseConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AccessoryRepository {

    private static AccessoryRepository instance;
    private static final Connection connection = DataBaseConfiguration.getDatabaseConnection();

    private AccessoryRepository(){

    }

    public static AccessoryRepository createAccessoryRepository() {
        if (instance == null) {
            instance = new AccessoryRepository();
        }
        return instance;
    }

    public void createTableAccessory(){
        try{
            String createAccessory= "CREATE TABLE IF NOT EXISTS pao.accessory"+
                    "(id VARCHAR (50) PRIMARY KEY, "+
                    "instrument_name VARCHAR(50) not NULL,"+
                    "price DOUBLE,"+
                    "manufacturer INTEGER ,"+
                    "category VARCHAR(50)," +
                    "description VARCHAR(50),"+
                    "gift BOOLEAN," +
                    "store_id VARCHAR (50)," +
                    "FOREIGN KEY (store_id) REFERENCES pao.store(id))";

            Statement statement = connection.createStatement();
            statement.execute(createAccessory);
            if(statement.execute(createAccessory)==false){
                System.out.println("Table accessory was created!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteTableAccessory(){
        try{
            String deleteTableAccessory = "DROP TABLE pao.accessory";
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteTableAccessory);
            System.out.println("Accessory table was deleted!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
