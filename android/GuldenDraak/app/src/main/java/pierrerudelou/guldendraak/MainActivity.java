package pierrerudelou.guldendraak;


import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
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
        this.initButtonDirectionListeners();
        // Initialize SeekBar for power management
        this.initSeekBar();
        // Initialize mediaPlayer for streaming
        this.initMediaPlayer();
        // Initialize socket connection
        this.initConnection();
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
        //findViewById(R.id.buttonPhoto).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.photo,msnMng));
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
                // nothing
            }
        });
    }

    /** MainActivity : Initialize MediaPlayer for video stream. */
    private void initMediaPlayer(){
        this.mediaController = new MediaController(this);

        findViewById(R.id.buttonStream).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                String url = "http://192.168.43.43:5000";
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

        findViewById(R.id.buttonConnection).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (msnMng!=null){
                    msnMng.closeSocket();
                }
                msnMng = new MsgMng();
            }
        });
    }

}
