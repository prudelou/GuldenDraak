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
        startCheckConnection();
    }

    /** Send a message to Server and return response of server */
    public void sendMessage(final MainActivity.ButtonAction action) {
        Thread sendThread = new Thread(){
            @Override
            public void run() {
                try {
                    if (socket.isConnected() && !socket.isClosed()) {
                        out.println(action.toString());
                        out.flush();

                        String message_distant = in.readLine();
                        Log.e("RESPONSE " + action.toString(), "" + message_distant);
                    } else {
                        Log.e("SEND" + action.toString(), "Socket not connected.");
                    }
                } catch (java.net.SocketTimeoutException e) {
                    Log.e("TIMEOUT", "Socket time out.");
                    openSocket();
                } catch(java.net.SocketException e){
                    Log.e("CLOSE", "Socket close.");
                    openSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("SEND" + action.toString(), "Error during send.");
                }
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

                openSocket();

                while (true){
                    if (socket.isConnected() && !socket.isClosed()){
                        socketStatus = SocketStatus.connected;
                    }
                    else{
                        socketStatus = SocketStatus.disconnected;
                        Log.e("SOCKET_STATUS", "Socket "+socketStatus.toString()+".");
                        openSocket();
                    }

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
            this.socket = new Socket(InetAddress.getByName(SERVER_IP), SERVERPORT);
            this.socket.setSoTimeout(5000);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Log.e("SOCKET_OPEN", "Socket opened.");
        } catch (IOException | android.os.NetworkOnMainThreadException e) {
            Log.e("ERROR", e.getMessage());
        }
    }

    /** Close current socket */
    public void closeSocket(){
        try {
            if (socket!=null && !socket.isClosed()){
                this.socket.close();
                out.close();
                in.close();
                Log.e("SOCKET_CLOSED", "Socket closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
