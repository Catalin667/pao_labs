package com.company.repositories;

import com.company.dataHandlerDB.DataBaseConfiguration;
import com.company.users.Admin;

import java.sql.*;
import java.util.UUID;

public class AdminRepository {
    private static AdminRepository instance;
    private static final Connection connection = DataBaseConfiguration.getDatabaseConnection();

    private AdminRepository(){

    }

    public static AdminRepository createAdminRepository() {
        if (instance == null) {
            instance = new AdminRepository();
        }
        return instance;
    }

    public void createTableAdmin(){
        try{
            String createAdmin = "CREATE TABLE IF NOT EXISTS pao.user"+
                    "(id VARCHAR (50) PRIMARY KEY, "+
                    "password VARCHAR(50) not NULL)";

            Statement statement = connection.createStatement();
            statement.execute(createAdmin);
            if(statement.execute(createAdmin)==false){
                System.out.println("Table Admin was created!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private boolean checkIfNotExists(String id){
//        String idString = id.toString();
        try {
            String check = "SELECT count(id) FROM pao.user WHERE id = ?";
            PreparedStatement sql_statement = connection.prepareStatement(check);
            sql_statement.setString(1,id);
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

    public void insertIntoAdmin(Admin admin){
        if (checkIfNotExists(admin.getId())){
            try {
                String sql_insert = "INSERT INTO user (id,password) VALUES (?, ?)";
                PreparedStatement sql_statement = connection.prepareStatement(sql_insert);

                sql_statement.setString(1, admin.getId());
                sql_statement.setString(2, admin.getPassword());


                int rowsInserted = sql_statement.executeUpdate();
                if (rowsInserted == 0) {
                    System.out.println("Error! The admin couldn't be created!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void changePassword(String password){
        try{
            String updatePassword = "UPDATE pao.user SET password = ?";
            PreparedStatement sql_statement = connection.prepareStatement(updatePassword);
            sql_statement.setString(1,password);
            int rowsChanged = sql_statement.executeUpdate();
            if (rowsChanged == 0) {
                System.out.println("The password could not be changed!");
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteTableAdmin(){
        try{
            String deleteTableAdmin = "DROP TABLE pao.user";

            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteTableAdmin);
            System.out.println("Admin table was deleted!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}








