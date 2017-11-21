package pierrerudelou.guldendraak;

import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pierre on 21/11/17.
 */

public class OnClickListenerButtonAction implements View.OnTouchListener {

    MainActivity.ButtonAction buttonAction;
    MsgMng msgMng;

    OnClickListenerButtonAction(MainActivity.ButtonAction buttonAction, MsgMng msgMng){
        super();
        this.buttonAction = buttonAction;
        this.msgMng = msgMng;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // PRESSED
                msgMng.sendMessage(buttonAction);
                return true; // if you want to handle the touch event
            case MotionEvent.ACTION_UP:
                msgMng.sendMessage(MainActivity.ButtonAction.stop);
                // RELEASED
                return true; // if you want to handle the touch event
        }
        return false;
    }
}
