package pierrerudelou.guldendraak;

import android.app.ProgressDialog;
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
    private String ipServer ;
    private Integer portServer;
    protected Socket socket; // Socket use to communicate with server
    private PrintWriter out; // Used to send
    private BufferedReader in; // Used to receive
    public SocketStatus socketStatus;
    public boolean checkStatus = false;
    private boolean endOfActionThread = false;
    private String responsePhoto;
    /** Construct a message manager */
    MsgMng() {
        // Open socket with SERVERPORT and SERVER_IP
        socketStatus = SocketStatus.unknown;
        checkStatus = true;
        this.ipServer = SERVER_IP;
        this.portServer = new Integer(SERVERPORT);
        try {
            startCheckConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Send a message to Server and return response of server */
    public void sendMessage(final String action) {
        Thread sendThread = new Thread(){
            @Override
            public void run() {
                String message_distant = "";

                try {
                    if (socket!=null && socket.isConnected() && !socket.isClosed()) {
                        out.println(action.toString());
                        out.flush();
                        message_distant = in.readLine();
                        Log.e("RESPONSE " + action.toString(), "" + message_distant);

                    } else {
                        Log.e("SEND" + action.toString(), "Socket not connected.");
                    }
                } catch (java.net.SocketTimeoutException e) {
                    Log.e("TIMEOUT", "Socket time out.");
                    try {
                        openSocket();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch(java.net.SocketException e){
                    Log.e("CLOSE", "Socket close.");
                    try {
                        openSocket();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("SEND" + action.toString(), "Error during send.");
                }
                if (action.equals("photo")){
                    responsePhoto = message_distant;
                    endOfActionThread =true;
                }
            }
        };
        sendThread.setDaemon(true);
        sendThread.start();
    }

    /** Start loop thread daemon which detect socket connection and update socketStatus */
    public void startCheckConnection() throws IOException{
        Thread sendThread = new Thread(){
            @Override
            public void run(){

                try {
                    openSocket();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                while (true){
                    if (socket != null && socket.isConnected() && !socket.isClosed()){
                        socketStatus = SocketStatus.connected;
                    }
                    else{
                        socketStatus = SocketStatus.disconnected;
                        Log.e("SOCKET_STATUS", "Socket "+socketStatus.toString()+".");
                        try {
                            openSocket();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    // Sleep 1000ms
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (!checkStatus){
                        break;
                    }
                }
            }
        };
        sendThread.setDaemon(true);
        sendThread.start();
    }

    /** MsgMng : Send take photo request and return string response. */
    public String takePhoto(){
        if (socket!=null && socket.isConnected()){
            sendMessage(MainActivity.ButtonAction.photo.toString());

// To dismiss the dialog
            while(!endOfActionThread){
                Log.e("PHOTO", ""+endOfActionThread);
            }

            endOfActionThread = false;
            return responsePhoto;

        }
        return "Error";
    }

    /** Open socket with SEVER_IP and SERVERPORT */
    public void openSocket() throws IOException{

            this.socket = new Socket(InetAddress.getByName(ipServer), this.portServer);
            this.socket.setSoTimeout(60000);
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Log.e("SOCKET_OPEN", "Socket opened.");

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
            checkStatus = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setIpServer(String ip){
        this.ipServer=ip;
    }

    public String getIpServer(){
        return this.ipServer;
    }

    public void setPortServer(Integer port){
        this.portServer=port;
    }

    public Integer getPortServer(){
        return this.portServer;
    }
}
