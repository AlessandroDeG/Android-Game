package com.example.game;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;


//giocatore: movimento, direzione,vita,grafica e conversione in formato dati da inserire nel db
public class Player {

    static String name="Player";

    static final int SIZE =100;

    static final int LOW_HEALTH_THRESHOLD = 30;
    static final int MAX_HEALTH = 100;


    //static final int INIT_POSX =100;//TEST
    //static final int INIT_POSY=500;//TEST

    static final int INIT_POSY =(MainActivity.screenHeight/5)*4;
    static final int INIT_POSX=MainActivity.screenWidth/2-(SIZE/2);

    static final int UP = 1;
    static final int DOWN = 2;
    static final int LEFT = 3;
    static final int RIGHT = 4;
    static final int STOP = 0;

    static int directionX = STOP;
    static int directionY =STOP;

    static int speedX=0;
    static int speedY=0;

    static int posX = INIT_POSX;
    static int posY =INIT_POSY;


    static int score=0;


    Paint color;
    static Paint scoreColor;
    static int DEFAULT_SCORE_COLOR= Color.BLACK;
    static int ALTERNATIVE_SCORE_COLOR= Color.GREEN;

    static Random random;

    static Bitmap playerSkin;

    static int playerHealth=MAX_HEALTH;

    static boolean wasHit=false;

    public Player() {
        color = new Paint();
        color.setColor(Color.GREEN);
        if(Skins.currentSkin!=Skins.SPACE_SKIN) {
           setScoreColor(DEFAULT_SCORE_COLOR);
        }
        scoreColor.setTextSize(100);
        scoreColor.setTypeface(Typeface.DEFAULT_BOLD);
        random = new Random();

        //playerHealth=MAX_HEALTH;
    }

    public static PlayerData toPlayerData(){
        return new PlayerData( name, posX, posY, playerHealth, directionX, directionY, score);
    }

    public static void directionUp(){ directionX=UP; }
    public static void directionDown(){ directionX=DOWN; }
    public static void directionRight(){ directionY=RIGHT; }
    public static void directionLeft(){directionY=LEFT; }
    public static void directionStopX(){directionX=STOP;}
    public static void directionStopY(){directionY=STOP;}

    public static synchronized void setSkin(Bitmap skin){
        playerSkin=skin;
    }
    public static synchronized void setScoreColor(int color){
        scoreColor= new Paint();
        scoreColor.setColor(color);
        scoreColor.setTextSize(100);
        scoreColor.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public static synchronized Bitmap getSkin(){
        return playerSkin;
    }


    public static void move(){
        if (directionX == UP) {
            moveUp();
            // Log.e("DIRECTION", "U");
        } else if (directionX == DOWN) {
            moveDown();
            //Log.e("DIRECTION", "D");
        } else {
            //Log.e("DIRECTION", "STOP");
        }

        if (directionY == LEFT) {
            moveLeft();
            // Log.e("DIRECTION", "L");
        } else if (directionY == RIGHT) {
            moveRight();
            // Log.e("DIRECTION", "R");
        } else {
            // Log.e("DIRECTION", "STOP");
        }

    }

    public static void moveLeft() {
        if (Player.posX > 0) {
            Player.posX -=2;

        }
    }

    public static void moveRight() {
        if (Player.posX+SIZE < MainActivity.screenWidth) {
            Player.posX += 2;

        }

    }

    public static void moveUp() {
        if (Player.posY+SIZE < MainActivity.screenHeight-PlayFragment.buttonsLayout.getMeasuredHeight()) {
            Player.posY += 2;
        }
    }

    public static void moveDown() {
        if (Player.posY > 0) {
            Player.posY -= 2;
        }
    }

    public static void setRandomScoreColor(boolean bonus){
        if(bonus) {
            scoreColor.setColor(Color.argb(200, random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        }
        else{
            if(Skins.currentSkin!= Skins.SPACE_SKIN) {
                scoreColor.setColor(DEFAULT_SCORE_COLOR);
            }
            else{
                setScoreColor(ALTERNATIVE_SCORE_COLOR);
            }
        }
    }

    static void incrementHealth(){
        playerHealth+=10;
        PlayFragment.healthBar.setProgress(playerHealth);
    }
    static void decrementHealth(int damage){
        playerHealth-=damage;
        PlayFragment.healthBar.setProgress(playerHealth);
        wasHit=true;


        if(damage<Timer.MISSILE_DAMAGE) {
            Vibratore.shakeIt();
            Sounds.playImpactSound();
        }
        else{
            Vibratore.shakeIt(Vibratore.LONG_VIBRATION);
            Sounds.playMissileImpactSound();
        }


        if(playerHealth<=0){
            PlayFragment.gameover=true;
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(PlayFragment.context,"GAMEOVER",Toast.LENGTH_LONG).show();
                    //postInvalidate();
                }
            });

            PlayFragment.checkForHighScore();
            PlayFragment.pause();

        }
    }

    static void resetMaxHealth(){
        Log.e("PLAYER","RESET MAX HEALTH");
        playerHealth=MAX_HEALTH;
        if(PlayFragment.healthBar!=null){
            PlayFragment.healthBar.setProgress(playerHealth);
        }
    }

    static synchronized void reset(){
         posX = INIT_POSX;
        posY =INIT_POSY;
        score=0;
        resetMaxHealth();
        directionStopX();
        directionStopY();
    }


}