package pierrerudelou.guldendraak;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**Enum of action names */
    public enum ButtonAction {forward, backward, left, right, stop, photo}
    /** Manage messages */
    public MsgMng msnMng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.msnMng = new MsgMng();
        ((TextView)findViewById(R.id.textViewSocket)).setText("Socket : "+msnMng.socketStatus.toString());
        this.initButtonListeners();
    }

    @Override
    protected void onStop() {
        msnMng.closeSocket();
        super.onStop();
    }

    /** Initialize buttons listeners*/
    private void initButtonListeners(){
        findViewById(R.id.buttonUp).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.forward,msnMng));
        findViewById(R.id.buttonDown).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.backward,msnMng));
        findViewById(R.id.buttonLeft).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.left,msnMng));
        findViewById(R.id.buttonRight).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.right,msnMng));
        //findViewById(R.id.buttonPhoto).setOnTouchListener(new OnClickListenerButtonAction(ButtonAction.photo,msnMng));
    }

}
