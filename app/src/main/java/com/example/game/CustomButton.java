package com.example.game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

//Button personalizzato con animazione di click coerente con la skin selezionata dall'utente
public class CustomButton extends androidx.appcompat.widget.AppCompatButton {


    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(Skins.currentSkin== Skins.SPACE_SKIN) {

            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                this.setBackgroundResource(R.drawable.buttonpressbackground);
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                this.setBackgroundResource(R.drawable.spacebuttonbackground);
            }
        }

        return super.onTouchEvent(event);
    }
}
