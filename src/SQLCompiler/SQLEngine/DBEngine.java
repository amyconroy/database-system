package SQLCompiler.SQLEngine;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.io.*;

public class DBEngine {

    public void createDatabase(String DBName, DBQuery query) throws InvalidQueryException {
        File newDB = new File(DBName);
        if(newDB.exists()) throw new InvalidQueryException("ERROR: Database already exists.");
        if(!newDB.mkdir()) throw new InvalidQueryException("ERROR: Unable to create database.");
        query.setDatabase(DBName);
        query.setOutput("OK");
    }

    public void useDatabase(String DBName, DBQuery query) throws InvalidQueryException {
        File database = new File(DBName);
        if(!database.exists()) throw new InvalidQueryException("ERROR: Database does not exist.");
        query.setDatabase(DBName);
        query.setOutput("OK");
    }

    
    // todo make a db class
    // when use, load everything to db class - then set the Query to be what is in
    // the current db
}
