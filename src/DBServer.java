import SQLCompiler.SQLExceptions.IncorrectSQLException;
import SQLCompiler.SQLExceptions.InvalidQueryException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DBServer {
    final static char EOT = 4;

    public static void main(String[] args) throws IOException {
        new DBServer(8888);
    }

    public DBServer(int portNumber) throws IOException {
        while(true) {
            try {
                System.out.println("Server Listening");
                ServerSocket ss = new ServerSocket(portNumber);
                Socket socket = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                String line;
                DBController dbController = new DBController();
                while ((line = in.readLine()) != null) {
                    processQuery(line, out, in, dbController);
                }
                out.close();
                in.close();
                socket.close();
                ss.close();
            }
            catch (IOException ioe) {
                System.err.println(ioe);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processQuery(String input, BufferedWriter out, BufferedReader in, DBController dbController) throws Exception {
       try{
           System.out.println("SQL : " + input);
           String output = dbController.preformQuery(input);
           out.write(output + "\n" + EOT + "\n");
           out.flush();
       }
        catch (InvalidQueryException | IncorrectSQLException e){
           out.write("ERROR:" + e + "\n" + EOT + "\n");
           out.flush();
           e.printStackTrace();
           System.err.println(e);
        }
       catch(Exception e){
           System.err.println(e);
       }
    }
}

