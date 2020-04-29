package SQLCompiler.SQLEngine;
import SQLCompiler.DBQuery;
import SQLCompiler.SQLExceptions.InvalidQueryException;
import java.io.*;
import java.util.ArrayList;

// class to do the main engine work of the database
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
        checkDatabaseExists(database);
        query.setDatabase(DBName);
        query.setOutput("OK");
    }

    private void checkDatabaseExists(File checkDB) throws InvalidQueryException {
        if(checkDB.exists()) throw new InvalidQueryException("ERROR: Database does not exist.");
    }

    public void createTable(String TBLName, DBQuery query, ArrayList<String> columnValues) throws IOException {
        String DBName = query.getDatabase();
        Table newTable = new Table();
        newTable.addColumns(columnValues);
        String newFileName = DBName + File.separator + TBLName;
        saveTableToFile(newFileName, newTable);
    }

    private Boolean checkTableExists(File[] listOfTables, String TBLName){
        for(File file : listOfTables){
            String currTableName = file.getName();
            if(currTableName.equals(TBLName)){
                return true;
            }
        }
        return false;
    }

    // to serialize a table to the appropriate file
    private void saveTableToFile(String TBLFileName, Table newTable) throws IOException {
        FileOutputStream fileOut = null;
        ObjectOutputStream objOut = null;
        try {
            fileOut = new FileOutputStream(TBLFileName);
            objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(newTable);
            objOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // to de-serialize a table from the appropriate file
    private Table getTableFromFile(String TBLFileName) throws IOException {
        FileInputStream fileIn = null;
        ObjectInputStream objIn = null;
        Table table = new Table();
        try{
            fileIn = new FileInputStream(TBLFileName);
            objIn = new ObjectInputStream(fileIn);
            table = (Table) objIn.readObject();
            objIn.close();
        } catch (FileNotFoundException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return table;
    }
}
