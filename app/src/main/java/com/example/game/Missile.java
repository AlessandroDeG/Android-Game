package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


//I missili che vengono sparati dal Boss ;)
public class Missile {


    static List<Missile> missileList = Collections.synchronizedList(new ArrayList<Missile>());

    static final int MAX_DEFAULT_NUMBER=5;
    //static int maxShotsN=MAX_DEFAULT_NUMBER;

    static final int SIZE_X=30;
    static final int SIZE_Y=100;

    static Bitmap skin;


    int positionX;
    int positionY;

    Paint color;
    static Random random= new Random();

    static final int DIRECTION_RIGHT=0;
    static final int DIRECTION_LEFT=1;
    static final int DIRECTION_STOP=2;


    int direction=DIRECTION_STOP;


    public Missile(int positionX, int positionY,int direction){
        this.positionX=positionX;
        this.positionY=positionY;
        this.direction=direction;

        color = new Paint();
        color.setColor(Color.BLACK);

    }

    public Missile(int positionX, int positionY){
        this.positionX=positionX;
        this.positionY=positionY;

        color = new Paint();
        color.setColor(Color.BLACK);

    }
    public static synchronized void setSkin(Bitmap newSkin){
        skin=newSkin;
    }

    public synchronized void move(){
        this.positionY+=2;

        int rMove=random.nextInt(100);

        if(rMove==0){
            direction=random.nextInt(2); //random direction
        }
        if(direction==DIRECTION_RIGHT){
            if(positionX<MainActivity.screenWidth) {
                this.positionX += 2;
            }else{
                changeDirection();
            }
        }
        else if(direction==DIRECTION_LEFT){
            if(positionX>0) {
                this.positionX -= 2;
            }else{
                changeDirection();
            }
        }

    }

    public void changeDirection(){
        if(direction==DIRECTION_RIGHT){
           direction=DIRECTION_LEFT;
        }
        else if(direction==DIRECTION_LEFT){
            direction=DIRECTION_RIGHT;
        }
    }

    public static synchronized void addInMissileList(Missile missile){
        missileList.add(missile);

    }
    public static synchronized void removeFromMissileList(Missile missile){
        missileList.remove(missile);
    }
    public static synchronized void removeFromMissileList(ArrayList<Missile> missile){
        missileList.removeAll(missile);
    }
    public static synchronized int getMissileNumber(){
        return missileList.size();
    }

    public static synchronized void reset(){
        missileList.clear();
    }




}
