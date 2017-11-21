package pierrerudelou.guldendraak;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by pierre on 21/11/17.
 */

public class MsgMng {

    private static final int SERVERPORT = 12800; // Server port number
    private static final String SERVER_IP = "192.168.43.43"; // IP of server
    private Socket socket; // Socket use to communicate with server
    private PrintWriter out;
    private BufferedReader in;

    /** Send a message to Server and return response of server */
    public void sendMessage(final MainActivity.ButtonAction action) {
        Thread sendThread = new Thread(){
            @Override
            public void run() {
                try {
                    openSocket();
                    out = new PrintWriter(socket.getOutputStream());
                    out.println(action.toString());
                    out.flush();
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message_distant = null;
                    message_distant = in.readLine();
                    if (message_distant!=null){
                        Log.e("RESPONSE", ""+message_distant);
                    }
                    else{
                        Log.e("RESPONSE", "Error : no message sent by Server.");
                    }
                    closeSocket();

                } catch (IOException e) {
                    closeSocket();
                    e.printStackTrace();
                }
            }
        };
        sendThread.setDaemon(true);
        sendThread.start();
    }


    /** Open socket with SEVER_IP and SERVERPORT */
    public void openSocket(){
        try {
            this.socket = new Socket(InetAddress.getByName(SERVER_IP), SERVERPORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Close current socket */
    public void closeSocket(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
