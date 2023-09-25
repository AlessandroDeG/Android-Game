package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.MediaPlayer;
import android.util.Log;


//thread che gestisce l'animazione del boss impostando ciclicamente bitmap prese dall'array di bitmap con cui viene inizializzato
public class Animator extends Thread {

   final int ANIMATION_DELAY=250;
   // final int ANIMATION_DELAY=10000; TEST

    int animationCounter=0;
    Bitmap[] bitmaps;
    Bitmap currentSkin;

    public Animator(Bitmap[] bitmaps){
        this.bitmaps=bitmaps;
        currentSkin = getNextBitmap();
    }

    public void run(){
            //while(true) {
       Sounds.startBossSounds();
                while(Boss.isAlive) {
                    Log.d("ANIMATOR", " TIMERSTART LOOP");
                    if (PlayFragment.skins) {
                        sleep(ANIMATION_DELAY);
                        currentSkin = getNextBitmap();
                    }
                }

            //}
       Sounds.releaseBossSounds();

    }
    public Bitmap getCurrentSkin(){
        return currentSkin;
    }

    public Bitmap getNextBitmap(){
        animationCounter=(animationCounter+1)% bitmaps.length;
        return bitmaps[animationCounter];
    }
    /*
    public Bitmap getNextHitBitmap(){
        Bitmap hit = Boss.animation.getCurrentSkin();
        Paint hitPaint = new Paint();
        hitPaint.setColor(Color.RED);
        hitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        canvas.drawBitmap(Boss.animation.getCurrentSkin(), null, bossRect, hitPaint);
        //Boss.wasHit = false;
    }
    */

    public void sleep(int ANIMATION_DELAY){
        try{
            Thread.sleep(ANIMATION_DELAY);
        }catch(InterruptedException e){}
    }
}
