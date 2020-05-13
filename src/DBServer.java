import sqlCompiler.sqlExceptions.IncorrectSQLException;
import sqlCompiler.sqlExceptions.InvalidQueryException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DBServer {
    final static char EOT = 4;
    long totalTime;

    public static void main(String[] args) throws IOException {
        new DBServer(8888);
    }

    public DBServer(int portNumber) {
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
                    processQuery(line, out, dbController);
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

    private void processQuery(String input, BufferedWriter out, DBController dbController) throws Exception {
        long startTime = System.nanoTime();
        try{
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
           e.printStackTrace();
           System.err.println(e);
       }
        long endTime = System.nanoTime() - startTime;
        System.out.println("each time : " + endTime/1000000);
        totalTime += endTime;
        System.out.println("total time : " + totalTime/1000000);
    }
}

