package pierrerudelou.guldendraak;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by pierre on 21/11/17.
 */

public class MsgMng {

    private static final int SERVERPORT = 12800; // Server port number
    private static final String SERVER_IP = "192.168.43.43"; // IP of server
    private Socket socket; // Socket use to communicate with server
    private PrintWriter out; // Used to send
    private BufferedReader in; // Used to receive

    /** Construct a message manager */
    MsgMng(){
        // Open socket with SERVERPORT and SERVER_IP
        openSocket();
    }

    /** Send a message to Server and return response of server */
    public void sendMessage(final MainActivity.ButtonAction action) {
        Thread sendThread = new Thread(){
            @Override
            public void run() {
                try {
                    out = new PrintWriter(socket.getOutputStream());
                    out.println(action.toString());
                    out.flush();

                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message_distant = in.readLine();

                    if (message_distant!=null){
                        Log.d("RESPONSE " + action.toString(), ""+message_distant);
                    }
                    else{
                        Log.e("RESPONSE" + action.toString(), "Error : no message sent by Server after "+action.toString()+" action.");
                    }
                } catch (IOException e) {
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
            if (socket != null || !SERVER_IP.isEmpty() ||  socket.isClosed()){
                this.socket = new Socket(InetAddress.getByName(SERVER_IP), SERVERPORT);
                Log.d("SOCKET_OPEN", "Socket opened.");
            }
        } catch (IOException e) {
            closeSocket();
            e.printStackTrace();
        }
    }

    /** Close current socket */
    public void closeSocket(){
        try {
            if (!socket.isClosed()){
                this.socket.close();
                Log.d("SOCKET_CLOSED", "Socket closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
