package pierrerudelou.guldendraak;

import android.util.Log;
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
                    // Pressed
                    msgMng.sendMessage(buttonAction.toString());
                    return true;

                case MotionEvent.ACTION_UP:
                    // Released
                    msgMng.sendMessage(MainActivity.ButtonAction.stop.toString());
                    return true;
            }
        return false;
    }
}
