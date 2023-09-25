package com.example.game;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.ProgressBar;



//custom progressbar per visualizzare al giocatore la vita corrente del suo personaggio(in verde, o in rosso se ha poca vita), viene aggiornata ogni volta che il giocatore viene colpito o si cura usando i bonus.
public final class HealthBar extends ProgressBar {

    final int HIGH_HEALTH_COLOR = Color.GREEN;
    final int LOW_HEALTH_COLOR = Color.RED;

    Context context;

    public HealthBar(Context context, AttributeSet attributes) {
        super(context, attributes);
        setProgress((Player.MAX_HEALTH));
    }

    @Override
    public void setProgress(int progress) {

        super.setProgress(progress);

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                if (getProgress() > Player.LOW_HEALTH_THRESHOLD) {
                    getProgressDrawable().setColorFilter(
                            HIGH_HEALTH_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
                } else {
                    getProgressDrawable().setColorFilter(
                            LOW_HEALTH_COLOR, android.graphics.PorterDuff.Mode.SRC_IN);
                }
                //postInvalidate();

            }
        });


    }


    public void resetProgress() {
        setProgress(Player.MAX_HEALTH);
    }
}

