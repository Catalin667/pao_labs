package com.company.repositories;

import com.company.dataHandlerDB.DataBaseConfiguration;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InstrumentRepository {

    private static InstrumentRepository instance;
    private static final Connection connection = DataBaseConfiguration.getDatabaseConnection();

    private InstrumentRepository(){

    }

    public static InstrumentRepository createInstrumentRepository() {
        if (instance == null) {
            instance = new InstrumentRepository();
        }
        return instance;
    }

    public void createTableInstrument(){
        try{
            String createInstrument = "CREATE TABLE IF NOT EXISTS pao.instrument"+
                    "(id VARCHAR (50) PRIMARY KEY, "+
                    "instrument_name VARCHAR(50) not NULL,"+
                    "price DOUBLE,"+
                    "manufacturer INTEGER ,"+
                    "category VARCHAR(50)," +
                    "description VARCHAR(50),"+
                    "yearCreated INTEGER,"+
                    "warranty INTEGER," +
                    "store_id VARCHAR (50)," +
                    "FOREIGN KEY (store_id) REFERENCES pao.store(id))";

            Statement statement = connection.createStatement();
            statement.execute(createInstrument);
            if(statement.execute(createInstrument)==false){
                System.out.println("Table instrument was created!");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteTableInstrument(){
        try{
            String deleteTableInstrument = "DROP TABLE pao.instrument";
            Statement statement = connection.createStatement();
            statement.executeUpdate(deleteTableInstrument);
            System.out.println("Instrument table was deleted!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
