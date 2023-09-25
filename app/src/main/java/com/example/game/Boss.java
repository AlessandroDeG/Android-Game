package com.example.game;

import android.graphics.Bitmap;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

//Oggetto che gestisce il boss: posizione,movimento,direzione,vita,reset,aggiunta di bitmap e animazione(Vedere Animator), e conversione in formato con i dati da inserire nel db(vedere BossData),ecc.

public class Boss {

    public static Boss boss=null;

   static boolean isAlive=false;
   static boolean spawnSequence=true;


    static final int MAX_HEALTH=200;

    static int bossHealth=MAX_HEALTH;

    static final int SIZE = 200;

    static final int INIT_POSX=(MainActivity.screenWidth/2)-(SIZE/2);
    static final int INIT_POSY=-SIZE*2;

    static int posX=INIT_POSX; //TEST
    static int posY=INIT_POSY; //TEST

    static final int DIRECTION_RIGHT=0;
    static final int DIRECTION_LEFT=1;
    static final int DIRECTION_STOP=2;
    static final int DIRECTION_UP=3;
    static final int DIRECTION_DOWN=4;

    static int direction=DIRECTION_STOP;


    static Paint color;

    static Bitmap[] skin;
    static Animator animation;
    static boolean wasHit=false;

    static Random random;

    public Boss(){
        color= new Paint();
        color.setColor(Color.RED);
        spawnSequence=true;
        wasHit=false;
    }

    public static BossData toBossData(){
        return new BossData(posX,posY,direction,bossHealth);
    }

    public static void changeDirection(){
        random = new Random();
        //Log.e("BOSS", "CHANGE DIRECTION");
            if (direction == DIRECTION_LEFT) {
                direction = DIRECTION_RIGHT;
            }
            else if(direction==DIRECTION_RIGHT){
                direction = DIRECTION_LEFT;
            }
            else if(direction==DIRECTION_STOP){
                int d= random.nextInt(10);

                Log.e("BOSS START MOVING? 0,1",Integer.toString(d));

                if(d==DIRECTION_RIGHT){
                    direction=DIRECTION_RIGHT;
                }
                if(d==DIRECTION_LEFT){
                    direction=DIRECTION_LEFT;
                }
            }


    }



    public static void move(){

        Log.d("BOSS MOVE","direction"+Integer.toString(direction));
        if(spawnSequence) {
            if (posY < 0) {
                direction = DIRECTION_DOWN;
            } else {
                Log.e("BOSS","END SPAWN SEQ");
                direction = DIRECTION_STOP;
                spawnSequence = false;
            }
        }

            if (direction == DIRECTION_RIGHT) {
                if(posX<MainActivity.screenWidth-(Boss.SIZE/2)) {
                    posX = posX + 2;
                }
                else{changeDirection();}
            }
            if (direction == DIRECTION_LEFT) {
                if(posX>0-(Boss.SIZE/2)) {
                    posX = posX - 2;
                }
                else{changeDirection();}
            }
            if (direction == DIRECTION_DOWN) {
                posY+=2;
            }
    }

    public static void setSkin(Bitmap[] newSkin){
         skin= newSkin;
    }

    public static void setAnimation(){
        //Log.d("SETTING ANIMATION", skin.toString());
        animation= new Animator(skin);
        Boss.isAlive=true;
        animation.start();
        ///OCCHIO DRAWVIEW PUO CHIEDERE ANIMATION PRIMA CHE VENGA INIZIALIZZATO
    }

    static void decrementHealth(int damage){
        bossHealth-=damage;
        wasHit=true;
        if(bossHealth<=0){
            Player.score+=101;
           reset();
           Missile.reset();
        }


        //PlayFragment.healthBar.setProgress(bossHealth);
        /*
        if(bossHealth<=LOW_HEALTH_THRESHOLD){
            PlayFragment.healthBar.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        */

    }
    static void resetMaxHealth(){
        bossHealth=MAX_HEALTH;
        //PlayFragment.healthBar.setProgress(bossHealth);

        //PlayFragment.healthBar.getProgressDrawable().setColorFilter(
                //Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
    }

    public static void reset(){
        isAlive=false;
        wasHit=false;
        spawnSequence=true;
        posX=(MainActivity.screenWidth/2)-(SIZE/2);
        posY=-SIZE*2;
        resetMaxHealth();
    }







}
