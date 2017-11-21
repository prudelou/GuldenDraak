package pierrerudelou.guldendraak;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

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

    public enum SocketStatus {connected, disconnected, unknown}
    private static final int SERVERPORT = 12800; // Server port number
    private static final String SERVER_IP = "192.168.43.43"; // IP of server
    private Socket socket; // Socket use to communicate with server
    private PrintWriter out; // Used to send
    private BufferedReader in; // Used to receive
    public SocketStatus socketStatus;
    /** Construct a message manager */
    MsgMng(){
        // Open socket with SERVERPORT and SERVER_IP
        socketStatus = SocketStatus.unknown;
        openSocket();
        startCheckConnection();
    }

    /** Send a message to Server and return response of server */
    public void sendMessage(final MainActivity.ButtonAction action) {
        Thread sendThread = new Thread(){
            @Override
            public void run() {
                try {
                    if (socket!=null && socket.isConnected()){
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
                    }
                    else{
                        Log.e("SEND" + action.toString(), "Socket not connected.");
                    }


                } catch (IOException e) {
                    closeSocket();
                    Log.e("SEND" + action.toString(), "Error during send.");                }
            }
        };
        sendThread.setDaemon(true);
        sendThread.start();
    }

    /** Start loop thread daemon which detect socket connection and update socketStatus */
    public void startCheckConnection(){
        Thread sendThread = new Thread(){
            @Override
            public void run(){

                while (true){

                    if (socket != null && socket.isConnected()){
                        socketStatus = SocketStatus.connected;
                    }
                    else{
                        socketStatus = SocketStatus.disconnected;
                    }
                    Log.e("SOCKET_STATUS", "Socket "+socketStatus.toString()+".");
                    // Sleep 1000ms
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
        } catch (IOException | android.os.NetworkOnMainThreadException e) {
            Log.e("SOCKET_OPEN", "Error during open socket.");
        }
    }

    /** Close current socket */
    public void closeSocket(){
        try {
            if (socket!=null && !socket.isClosed()){
                this.socket.close();
                Log.d("SOCKET_CLOSED", "Socket closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
