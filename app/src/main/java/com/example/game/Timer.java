package com.example.game;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


//Thread che gestice le dinamiche di gioco:
//-tempo di gioco
//-spawn/creazione di oggetti(ostacoli,cibo,boss,ecc)
//-movimenti
//-collisioni
//-misura i frame per second
//ecc.
public class Timer extends Thread {
    static final int DELAY = 5;
    static long fps=0;
    static long frames=0;
    static long startTime;
    static long totalTime=0;
    static final long ONE_SEC = 1000000000;
    static final long ONE_MILLI = 1000000;
    //static Paint fpsPaint;

    static final int OBSTACLE_DAMAGE=10;
    static final int MISSILE_DAMAGE=50;
    static final int PLAYER_DAMAGE=10;

    static final int FOOD_VALUE=1;
    static final int BONUS_FOOD_VALUE=11;
    static final int BOSS_VALUE=101;

    static final int MISSILE_SPAWN_RATE=1000;//TEST
  //  static final int MISSILE_SPAWN_RATE=1000;//TEST

    static final int BOSS_CHANGING_DIRECTION_RATE=200;
    static final int BOSS_SPAWN_RATE=10;

    static final int OBSTACLE_SPAWN_RATE=100;  //FOOD//BONUS//OBSTACLES RATES IN OBSTACLE CLASS




    DrawView drawView;

    Random random;

    static boolean start=false;
    static boolean destroy=true;
    static List<Obstacle> obstaclesHitList = Collections.synchronizedList(new ArrayList<Obstacle>());
    static List<Shot> shotsHitList = Collections.synchronizedList(new ArrayList<Shot>());
    static List<Missile> missileHitList = Collections.synchronizedList(new ArrayList<Missile>());

    public Timer(DrawView drawView) {

        Log.e("TIMER","START");


        //fpsPaint.setTypeface(Typeface.DEFAULT_BOLD);


        this.drawView = drawView;
        this.random=new Random();

        destroy=false;
        start=true;
        this.start();

    }


    public void run() {

        if(PlayFragment.measureFps){startTime = System.nanoTime();}

        while(!destroy) {
            while (start) {

                sleep(DELAY);
                movePlayer();
                checkBonusFood();
                checkCollisionsAndMoveObstacle();
                checkCollisionsAndMoveShot();
                checkCollisionsAndMoveMissile();
                spawnObstacle();
                checkBoss();
                drawView.postInvalidate();

                if(PlayFragment.measureFps){checkFps();}



                 /*
             Display display = ((WindowManager) drawView.context
                     .getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
             //int screenOrientation = display.getRotation();
                   //Log.e("ROTATION!", Integer.toString(display.getRotation()));
*/

            }
        }
    }

    public void sleep(int delay){
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
        }

    }

    public static void startTimer(){
        start=true;
    }
    public static void stopTimer(){
        start=false;
    }
    public static void destroyTimer(){
        destroy=true;
    }


    public void movePlayer(){
        Player.move();
    }

    public void checkCollisionsAndMoveObstacle(){
        synchronized (Obstacle.obstaclesList) {
            for (Obstacle obstacle : Obstacle.obstaclesList) {

                obstacle.move();
                //collision

                int obstaclePosX = obstacle.positionX;
                int obstaclePosY = obstacle.positionY;

                boolean collisionSidesY =(Player.posX <= obstaclePosX  &&  obstaclePosX <= Player.posX+Player.SIZE) ||(Player.posX <= obstaclePosX+obstacle.SIZE  &&  obstaclePosX+obstacle.SIZE <= Player.posX+Player.SIZE);
                boolean collisionSidesX =(Player.posY <= obstaclePosY  &&  obstaclePosY <= Player.posY+Player.SIZE) ||(Player.posY <= obstaclePosY+obstacle.SIZE  &&  obstaclePosY+obstacle.SIZE <= Player.posY+Player.SIZE);

                //boolean collisionSidesY =(obstaclePosX <= shotPosX  &&  shotPosX <= obstaclePosX+Obstacle.SIZE) ||(obstaclePosX <= shotPosX+Shot.SIZE  &&  shotPosX+Shot.SIZE <=obstaclePosX+Obstacle.SIZE);
               // boolean collisionSidesX =(obstaclePosY <= shotPosY  &&  shotPosY <= obstaclePosY+Obstacle.SIZE) ||(obstaclePosY <= shotPosY+shot.SIZE  &&  shotPosY+shot.SIZE <= obstaclePosY+Obstacle.SIZE);


                boolean collision = collisionSidesX && collisionSidesY;

                if (collision) {
                    if (!obstaclesHitList.contains(obstacle)) {
                        if (obstacle.isFood) {
                            if (obstacle.isBonusFood) {
                                Player.score += BONUS_FOOD_VALUE;
                                Obstacle.bonusFoodList.remove(obstacle);
                                Player.setRandomScoreColor(false);
                                Player.resetMaxHealth();
                            } else {
                                Player.score += FOOD_VALUE;
                            }
                        } else {
                            Player.decrementHealth(OBSTACLE_DAMAGE);
                            ///////////////////////////////////////////////GAME OVER CONDITION

                        }

                        Log.e("COLLISION", "player/obstacle");
                        synchronized (obstaclesHitList) {
                            obstaclesHitList.add(obstacle);
                        }
                    }
                }
            }
        }
    }

    public void checkCollisionsAndMoveShot(){
        synchronized (Shot.shotsList) {
            for (Shot shot : Shot.shotsList) {

                shot.move();

                int bossPosX = Boss.posX;
                int bossPosY=Boss.posY;

                int shotPosX = shot.positionX;
                int shotPosY= shot.positionY;

                synchronized (Obstacle.obstaclesList) {
                    for (Obstacle obstacle : Obstacle.obstaclesList) {

                        int obstaclePosX = obstacle.positionX;
                        int obstaclePosY = obstacle.positionY;

                        // obstaclePosX+Obstacle.SIZE;
                        //obstaclePosY+Obstacle.SIZE;



                        boolean collisionSidesY =(obstaclePosX <= shotPosX  &&  shotPosX <= obstaclePosX+Obstacle.SIZE) ||(obstaclePosX <= shotPosX+Shot.SIZE  &&  shotPosX+Shot.SIZE <=obstaclePosX+Obstacle.SIZE);
                        boolean collisionSidesX =(obstaclePosY <= shotPosY  &&  shotPosY <= obstaclePosY+Obstacle.SIZE) ||(obstaclePosY <= shotPosY+shot.SIZE  &&  shotPosY+shot.SIZE <= obstaclePosY+Obstacle.SIZE);
                        boolean collision = collisionSidesX && collisionSidesY;


                        //  boolean collision = ;

                        if (collision) {
                            Log.e("COLLISION", "SHOT/OBSTACLE");
                            synchronized (obstaclesHitList) {
                                obstaclesHitList.add(obstacle);
                            }
                            synchronized (shotsHitList) {
                                shotsHitList.add(shot);
                            }
                        }

                    }
                }

                boolean collisionSidesY =(bossPosX <= shotPosX  &&  shotPosX <= bossPosX+Boss.SIZE) ||(bossPosX <= shotPosX+Shot.SIZE  &&  shotPosX+Shot.SIZE <=bossPosX+Boss.SIZE);
                boolean collisionSidesX =(bossPosY <= shotPosY  &&  shotPosY <= bossPosY+Boss.SIZE) ||(bossPosY <= shotPosY+shot.SIZE  &&  shotPosY+shot.SIZE <= bossPosY+Boss.SIZE);
                boolean collision = collisionSidesX && collisionSidesY;


                //  boolean collision = ;

                if (collision) {
                    Log.e("COLLISION", "SHOT/BOSS");
                    if(!shotsHitList.contains(shot)) {
                        Boss.decrementHealth(PLAYER_DAMAGE);
                    }
                    synchronized (shotsHitList) {
                        shotsHitList.add(shot);
                    }
                }
            }
        }
    }

    public void checkCollisionsAndMoveMissile(){
        synchronized (Missile.missileList) {
            for (Missile missile : Missile.missileList) {

                missile.move();

                int missilePosX = missile.positionX;
                int missilePosY= missile.positionY;

                synchronized (Obstacle.obstaclesList) {
                    for (Obstacle obstacle : Obstacle.obstaclesList) {



                        int obstaclePosX = obstacle.positionX;
                        int obstaclePosY = obstacle.positionY;

                        // obstaclePosX+Obstacle.SIZE;
                        //obstaclePosY+Obstacle.SIZE;



                        boolean collisionSidesY =(obstaclePosX <= missilePosX  && missilePosX <= obstaclePosX+Obstacle.SIZE) ||(obstaclePosX <= missilePosX+missile.SIZE_X &&  missilePosX+missile.SIZE_X<=obstaclePosX+Obstacle.SIZE);
                        boolean collisionSidesX =(obstaclePosY <= missilePosY  &&  missilePosY <= obstaclePosY+Obstacle.SIZE) ||(obstaclePosY <= missilePosY+missile.SIZE_Y  &&  missilePosY+missile.SIZE_Y <= obstaclePosY+Obstacle.SIZE);

                        boolean collision = collisionSidesX && collisionSidesY;


                        if (collision) {
                            Log.e("COLLISION", "MISSILE OBSTACLE");
                            synchronized (obstaclesHitList) {
                                obstaclesHitList.add(obstacle);
                            }
                            synchronized (missileHitList) {
                                missileHitList.add(missile);
                            }
                        }

                    }
                }
                if(!missileHitList.contains(missile)) {
                    boolean collisionPlayerSidesY = (Player.posX <= missilePosX && missilePosX <= Player.posX+Player.SIZE) || (Player.posX <= missilePosX + missile.SIZE_X && missilePosX + missile.SIZE_X <= Player.posX+Player.SIZE);
                    boolean collisionPlayerSidesX = (Player.posY <= missilePosY && missilePosY <= Player.posY+Player.SIZE) || (Player.posY <= missilePosY + missile.SIZE_Y && missilePosY + missile.SIZE_Y <= Player.posY+Player.SIZE);
                    boolean collisionPlayer = collisionPlayerSidesX && collisionPlayerSidesY;
                    if (collisionPlayer) {
                        Log.e("COLLISION", "MISSILE/PLAYER");
                        Player.decrementHealth(MISSILE_DAMAGE);
                        ///////////////////////////////////////////////GAME OVER CONDITION
                        synchronized (missileHitList) {
                            missileHitList.add(missile);
                        }
                    }
                }
            }
        }
    }

    public void spawnObstacle(){
        //int spawn = random.nextInt(400);  ok
        int spawn = random.nextInt(OBSTACLE_SPAWN_RATE);  //test
        //int spawn = random.nextInt(10);
        if (spawn == 0) {
            int positionY = 1;
            while (positionY % 50 != 0) {
                positionY = random.nextInt(MainActivity.screenWidth-Obstacle.SIZE);
                //positionY = random.nextInt(1050) + 50;//test
            }
            //Log.e("INFO", "SPAWN");
            Obstacle.addInObstaclesList(new Obstacle(positionY));
        }

    }

    public void checkBonusFood(){
        if(!Obstacle.bonusFoodList.isEmpty()){
            Obstacle.bonusFoodList.get(0).color.setColor(Color.argb(200,random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            Player.setRandomScoreColor(true);
        }else{

        }
    }
 //TEST
    public void checkBoss(){

        if(Boss.isAlive){
            Boss.move();
            int changeD=random.nextInt(BOSS_CHANGING_DIRECTION_RATE);
            if(changeD==0){
                Boss.changeDirection();
            }
            spawnBossMissile();
        }

        if((Player.score!=0) && (Player.score%BOSS_SPAWN_RATE==0) && !Boss.isAlive){
            spawnBoss();
        }
    }

    public void spawnBoss(){

        Boss boss = new Boss();
        Boss.boss=boss;
        if(PlayFragment.skins){
            Boss.setAnimation();
        }
        else{
            Boss.isAlive=true;
        }
    }

    public void spawnBossMissile() {
       // int rShoot=random.nextInt(1500); TEST
         int rShoot=random.nextInt(MISSILE_SPAWN_RATE);//TEST
        if(rShoot==0) {
            Missile.addInMissileList(new Missile(Boss.posX + (Boss.SIZE / 2), Boss.posY + Boss.SIZE)); //DOWN
        }
        if(rShoot==2) {
            Missile.addInMissileList(new Missile(Boss.posX + Boss.SIZE+Missile.SIZE_X, Boss.posY + Boss.SIZE/2)); //RIGHT
        }
        if(rShoot==3) {
            Missile.addInMissileList(new Missile(Boss.posX -Missile.SIZE_X, Boss.posY + Boss.SIZE/2)); //LEFT
        }
    }

    public static void checkFps(){
        totalTime += System.nanoTime()-startTime;
        startTime=System.nanoTime();
        if(totalTime>=1*ONE_SEC){
            fps=frames/1;
            totalTime=0;
            frames=0;
        }
    }

}



