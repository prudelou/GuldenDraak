package pierrerudelou.guldendraak;


import android.app.ActionBar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    /**Enum of action names */
    public enum ButtonAction {forward, backward, left, right, stop, photo}
    /** Manage messages */
    public MsgMng msnMng;
    public  VideoView videoView;
    public  MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //((TextView)findViewById(R.id.textViewSocket)).setText("Socket : "+msnMng.socketStatus.toString());

        // Initialize direction buttons listeners
        //this.initButtonDirectionListeners();
        // Initialize SeekBar for power management
        this.initSeekBar();
        // Initialize mediaPlayer for streaming
        this.initMediaPlayer();
        // Initialize socket connection
        this.initConnection();
        // Initialize photo buttons
        this.initButtonPhoto();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:

                final EditText ipTxt = new EditText(this);
                final EditText portTxt = new EditText(this);
                final TextView ipView = new TextView(this);
                final TextView portView = new TextView(this);


                ipTxt.setText(msnMng.getIpServer());
                portTxt.setText(msnMng.getPortServer().toString());
                ipView.setText("IP : ");
                portView.setText("Port : ");

                TableLayout tl = new TableLayout(this);
                tl.addView(ipView);
                tl.addView(ipTxt);
                tl.addView(portView);
                tl.addView(portTxt);

                tl.setPadding(10,0,0,0);

                new AlertDialog.Builder(this)
                        .setTitle("Server preferences")
                        .setView(tl)
                        .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                msnMng.setIpServer(ipTxt.getText().toString());
                                msnMng.setPortServer(Integer.parseInt(portTxt.getText().toString()));
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        })
                        .show();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onStop() {
        if (msnMng!=null){
            msnMng.closeSocket();
        }
        super.onStop();
    }

    /** Initialize buttons listeners*/
    private void initButtonDirectionListeners(){
        findViewById(R.id.buttonUp).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.forward,msnMng));
        findViewById(R.id.buttonDown).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.backward,msnMng));
        findViewById(R.id.buttonLeft).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.left,msnMng));
        findViewById(R.id.buttonRight).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.right,msnMng));
    }

    /** MainActivity : Initialize photo button */
    private void initButtonPhoto(){
        findViewById(R.id.buttonPhoto).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (msnMng!=null){
                    String response = msnMng.takePhoto();
                    openResponseDIalog(response);
                }
                else{
                    openResponseDIalog("Error. Please check server connection.");
                }
            }
        });
    }

    private void openResponseDIalog(String response){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(response).setTitle("Response");
        builder.create().show();
    }

    /** MainActivity : Initialize SeekBar for power management. */
    private void initSeekBar(){
        ((SeekBar)findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView)findViewById(R.id.textViewPowerValue)).setText(progress+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                msnMng.sendMessage("puissance:"+seekBar.getProgress());

            }
        });
    }

    /** MainActivity : Initialize MediaPlayer for video stream. */
    private void initMediaPlayer(){
        this.mediaController = new MediaController(this);

        findViewById(R.id.buttonStream).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String url = "http://"+msnMng.getIpServer()+":5000";
                videoView = (VideoView)findViewById(R.id.videoView);
                mediaController.setAnchorView(videoView);
                Uri video = Uri.parse(url);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(video);
                videoView.requestFocus();
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
                {
                    public void onPrepared(MediaPlayer mp){
                        videoView.start();
                    }
                });
            }
        });
    }

    /** MainActivity : Initialize socket connection. */
    private void initConnection(){
        this.mediaController = new MediaController(this);
        msnMng = new MsgMng();
        findViewById(R.id.buttonConnection).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (msnMng!=null){
                    msnMng.closeSocket();
                    msnMng.openSocket();
                }
                else{
                    msnMng = new MsgMng();
                    initButtonDirectionListeners();
                }
            }
        });
    }

}
