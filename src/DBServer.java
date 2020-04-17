import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class DBServer {
    final static char EOT = 4;
    static boolean client;

    public static void main(String[] args) {
        new DBServer(8888);
    }

    public DBServer(int portNumber) {
        try {
            ServerSocket ss = new ServerSocket(portNumber);
            System.out.println("Server Listening");
            Socket socket = ss.accept();
            client = true;
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while(client) processQuery(out, in);
            // save the work to file (?)
            System.out.println("test\n");
            out.close();
            in.close();
            socket.close();
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }

    private void processQuery(BufferedWriter out, BufferedReader in) throws IOException {
        String line = in.readLine();
        if(line == null){
            System.out.println("check\n");
            client = false;
        }
        else{
            System.out.println("SQL : " + line);
            out.write(line + "\n" + EOT + "\n");
            out.flush();
        }
    }
}

