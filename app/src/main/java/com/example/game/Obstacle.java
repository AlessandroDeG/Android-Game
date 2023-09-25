package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//Ostacoli generati in modo casuale con rate diversi, possono essere: ostacoli,cibo,cibo bonus
//in posizione casuale
//
class Obstacle {

    static List<Obstacle> obstaclesList = Collections.synchronizedList(new ArrayList<Obstacle>());
    static List<Obstacle> bonusFoodList = Collections.synchronizedList(new ArrayList<Obstacle>());

    //public static final int SIZE=50;
    public static final int SIZE=75;

    int positionX;
    int positionY=0;


    Random random = new Random();
    boolean isFood;
    boolean isBonusFood=false;

    Paint color;

    static boolean skins= true;
    static Bitmap obstacleSkin ;
    static Bitmap foodSkin;
    static Bitmap bonusFoodSkin;

    static final int OBSTACLES_TYPES_RATE=101;

    //static final int BONUS_FOOD_RATE = 25;  //4su100 circa
    static final int BONUS_FOOD_RATE = 100;  //1su100 circa
    static final int FOOD_RATE = 10;  //10su100 - BONUSRATE circa





    public Obstacle(int positionX){

        this.positionX=positionX;


        int randType=random.nextInt(OBSTACLES_TYPES_RATE);

        if(randType%FOOD_RATE==0) {
            if(randType%BONUS_FOOD_RATE==0 && bonusFoodList.isEmpty()) {
                isBonusFood = true;
                bonusFoodList.add(this);
            }

            isFood = true;
            color = new Paint();
            color.setColor(Color.GREEN);

        }
        else{
            isFood=false;
            color = new Paint();
            color.setColor(Color.RED);
        }

    }

    public Obstacle(int posX,int posY,boolean isFood,boolean isBonus){

        this.positionX=posX;
        this.positionY=posY;

        this.isFood=isFood;
        this.isBonusFood=isBonus;


        int randType=random.nextInt(OBSTACLES_TYPES_RATE);

             if(this.isFood){
                 if(this.isBonusFood) {
                     isBonusFood = true;
                     bonusFoodList.add(this);
                 }
                 color = new Paint();
                 color.setColor(Color.GREEN);
            }
            else{
            color = new Paint();
            color.setColor(Color.RED);
            }

    }


    public synchronized void move(){
        this.positionY+=2;
    }

    public static synchronized void addInObstaclesList(Obstacle obstacle){
        obstaclesList.add(obstacle);

    }
    public static synchronized void removeFromObstaclesList(Obstacle obstacle){
        obstaclesList.remove(obstacle);
    }
    public static synchronized void removeFromObstaclesList(ArrayList<Obstacle> obstacle){
        obstaclesList.removeAll(obstacle);
    }

    public synchronized static void reset(){
        obstaclesList.clear();
        bonusFoodList.clear();
    }

    public static void setSkin(Bitmap newObstacleSkin, Bitmap newFoodSkin, Bitmap newBonusFoodSkin){
        obstacleSkin=newObstacleSkin;
        foodSkin=newFoodSkin;
        bonusFoodSkin=newBonusFoodSkin;
    }

}

