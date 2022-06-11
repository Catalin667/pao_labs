package com.company.repositories;

import com.company.dataHandlerDB.DataBaseConfiguration;
import com.company.users.Admin;
import com.company.users.Customer;

import java.sql.*;
import java.util.UUID;

public class CustomerRepository {
    private static CustomerRepository instance;
    private static final Connection connection = DataBaseConfiguration.getDatabaseConnection();

    private CustomerRepository(){

    }

    public static CustomerRepository createCustomerRepository() {
        if (instance == null) {
            instance = new CustomerRepository();
        }
        return instance;
    }

    public void createTableCustomer(){
        try{
            String createCustomer = "CREATE TABLE IF NOT EXISTS pao.customer"+
                    "(id VARCHAR (50) PRIMARY KEY, "+
                    "first_name VARCHAR(50) not NULL,"+
                    "last_name VARCHAR(50) not NULL,"+
                    "address VARCHAR(50),"+
                    "card_id VARCHAR(50)," +
                    " FOREIGN  KEY (card_id) REFERENCES pao.card(id)) ";

            Statement statement = connection.createStatement();
            statement.execute(createCustomer);
            if(statement.execute(createCustomer)==false){
                System.out.println("Table of customers was created!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void insertIntoCustomer(Customer customer){

        try {
            String sql_insert = "INSERT INTO customer (id,first_name,last_name,address,card_id) VALUES (?, ?,?,?,?)";
            PreparedStatement sql_statement = connection.prepareStatement(sql_insert);

            sql_statement.setString(1, customer.getId().toString());
            sql_statement.setString(2, customer.getFirstName());
            sql_statement.setString(3, customer.getLastName());
            sql_statement.setString(4, customer.getAdress().getId().toString());
            sql_statement.setString(5, customer.getCard().getId().toString());


            int rowsInserted = sql_statement.executeUpdate();
            if (rowsInserted == 0) {
                System.out.println("Error! The customer couldn't be created!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void updateLastName(Customer customer){
        try{
            String updatePassword = "UPDATE pao.customer SET last_name = ? WHERE id = ?";
            PreparedStatement sql_statement = connection.prepareStatement(updatePassword);
            sql_statement.setString(1,customer.getLastName());
            sql_statement.setString(2,customer.getId().toString());
            int rowsChanged = sql_statement.executeUpdate();
            if (rowsChanged == 0) {
                System.out.println("The customer could not be changed!");
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCustomer(Customer customer){
        try{
            String deleteCustomer = "DELETE FROM pao.customer WHERE id = ?";
            PreparedStatement sql_statement = connection.prepareStatement(deleteCustomer);
            sql_statement.setString(1,customer.getId().toString());
            int rowsChanged = sql_statement.executeUpdate();
            if (rowsChanged == 0) {
                System.out.println("The customer could not be deleted!");
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTableCustomer(){
        try{
            String deleteTableCustomer = "DROP TABLE pao.customer";

            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteTableCustomer);
            System.out.println("Customer table was deleted!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
