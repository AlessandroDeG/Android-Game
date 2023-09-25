package com.example.game;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
// colpi sparati dal giocatore(come consequenza del click sullo schermo)
public class Shot {


    static List<Shot> shotsList = Collections.synchronizedList(new ArrayList<Shot>());

    static final int MAX_DEFAULT_NUMBER=5;
    static int maxShotsN=MAX_DEFAULT_NUMBER;

    static final int SIZE=10;

    int positionX;
    int positionY;

    Paint color;



    public Shot(int positionX, int positionY){
        this.positionX=positionX;
        this.positionY=positionY;

        color = new Paint();
        if(Skins.currentSkin!=Skins.SPACE_SKIN) {
            color.setColor(Color.BLACK);
        }
        else{
            color.setColor(Color.GREEN);
        }


    }

    public synchronized void move(){
        this.positionY-=2;
    }

    public static synchronized void addInShotsList(Shot shot){
        shotsList.add(shot);

    }
    public static synchronized void removeFromShotsList(Shot shot){
        shotsList.remove(shot);
    }
    public static synchronized void removeFromShotsList(ArrayList<Shot> shot){
        shotsList.removeAll(shot);
    }
    public static synchronized int getShotsNumber(){
        return shotsList.size();
    }

    public static synchronized void reset(){
        shotsList.clear();
    }




}
