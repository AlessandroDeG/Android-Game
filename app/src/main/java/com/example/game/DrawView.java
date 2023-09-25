package com.example.game;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import android.graphics.RectF;
import android.util.Log;

import android.view.View;


import java.util.ArrayList;

//oggetto che disegna gli elementi di gioco sul Canvas( con la relativa skin): giocatore,ostacoli/cibo,boss,missili,shots,punteggio,fps,ecc
public class DrawView extends View {


    static int backgroundColor;
    static Paint fpsPaint;

    Context context;
    Timer timer;

   static Player player;

    static ArrayList<Obstacle> obstaclesRemovedList = new ArrayList<Obstacle>();
    static ArrayList<Shot> shotsRemovedList = new ArrayList<Shot>();
    static ArrayList<Missile> missileRemovedList = new ArrayList<Missile>();

    public DrawView(Context context){

        super(context);

        this.context=context;

        this.setWillNotDraw(false);

         if(timer==null) {
             this.timer = new Timer(this);
         }

        player = new Player();

        fpsPaint=fpsPaint= new Paint();
        fpsPaint.setColor(Player.scoreColor.getColor());
        fpsPaint.setTextSize(50);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /////BACKGROUND

        if(Skins.currentSkin!= Skins.SPACE_SKIN) {
            Paint background = new Paint();
            background.setColor(backgroundColor);
            canvas.drawPaint(background);
        }
        else{
            Rect screen = new Rect(0,0,MainActivity.screenWidth,MainActivity.screenHeight);
            canvas.drawBitmap(Skins.spaceBackground,null,screen,null);
        }

        //SCORE
        canvas.drawText(Integer.toString(Player.score),50,100,Player.scoreColor);
        //Log.e("INFO","draw");
        Rect playerRect = new Rect(Player.posX,Player.posY,Player.posX+Player.SIZE ,Player.posY+Player.SIZE);


        ///////PLAYER
        if(PlayFragment.skins && Player.getSkin()!=null) {
            if(Player.wasHit){
                Paint hitPaint= new Paint();
                ColorFilter filter = new LightingColorFilter(Color.RED, 0);
                hitPaint.setColorFilter(filter);
                canvas.drawBitmap(Player.getSkin(), null, playerRect, hitPaint);
                Player.wasHit=false;
            }
            else {
                canvas.drawBitmap(Player.getSkin(), null, playerRect, null);
            }
        }
        else{
            canvas.drawRect(playerRect, player.color);
        }

/////OBSTACLES
        synchronized(Obstacle.obstaclesList) {
            for (Obstacle obstacle : Obstacle.obstaclesList) {
                if (obstacle.positionY > 1720) {
                    obstaclesRemovedList.add(obstacle);
                } else {
                    if (!Timer.obstaclesHitList.contains(obstacle)) {
                        Rect obstacleRect = new Rect(obstacle.positionX, obstacle.positionY, obstacle.positionX + Obstacle.SIZE, obstacle.positionY + Obstacle.SIZE);
                        if(PlayFragment.skins){
                            if(!obstacle.isFood) {
                                canvas.drawBitmap(Obstacle.obstacleSkin, null, obstacleRect, null);
                            }else {
                                if(obstacle.isBonusFood){canvas.drawBitmap(Obstacle.bonusFoodSkin, null, obstacleRect, null);}
                                else{ canvas.drawBitmap(Obstacle.foodSkin, null, obstacleRect, null);}

                            }
                        }else {

                            canvas.drawRect(obstacleRect, obstacle.color);
                        }
                    } else {
                        obstaclesRemovedList.add(obstacle);
                    }
                }
            }
            if (!obstaclesRemovedList.isEmpty()) {

                if(!Obstacle.bonusFoodList.isEmpty() && obstaclesRemovedList.contains(Obstacle.bonusFoodList.get(0))) {
                    Obstacle.bonusFoodList.remove(0);
                    Player.setRandomScoreColor(false);
                }
                Obstacle.removeFromObstaclesList(obstaclesRemovedList);
                obstaclesRemovedList.clear();
            }
        }
        //Timer.obstaclesHitList.clear();

////////SHOTS
        synchronized(Shot.shotsList) {
            for (Shot shot : Shot.shotsList) {
                if (shot.positionY < 0) {
                    shotsRemovedList.add(shot);
                } else {
                    if (!Timer.shotsHitList.contains(shot)) {
                        RectF obstacleRect = new RectF(shot.positionX, shot.positionY, shot.positionX + shot.SIZE, shot.positionY + shot.SIZE);
                        canvas.drawOval(obstacleRect, shot.color);
                    } else {
                        shotsRemovedList.add(shot);
                    }
                }
            }
            if (!shotsRemovedList.isEmpty()) {
                Shot.removeFromShotsList(shotsRemovedList);
                shotsRemovedList.clear();
            }
        }
////////DRAWBOSS
        if(Boss.isAlive == true){
            Rect bossRect = new Rect(Boss.posX, Boss.posY, Boss.posX+Boss.SIZE, Boss.posY+Boss.SIZE);

            if(PlayFragment.skins) {
                Log.d("BOSS", "BATTLE");
                try {
                    if (!Boss.wasHit) {
                        canvas.drawBitmap(Boss.animation.getCurrentSkin(), null, bossRect, null);
                    } else {
                        Bitmap hit = Boss.animation.getCurrentSkin();
                        Paint hitPaint = new Paint();
                       // hitPaint.setColor(Color.RED);
                       // hitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
                        ColorFilter filter = new LightingColorFilter(Color.RED, 0);
                        hitPaint.setColorFilter(filter);
                        canvas.drawBitmap(Boss.animation.getCurrentSkin(), null, bossRect, hitPaint);
                        Boss.wasHit = false;
                        //canvas.drawBitmap(Color.argb(200,random.nextInt(255),random.nextInt(255),random.nextInt(255)), PorterDuff.Mode.ADD);

                    }
                }catch(Exception e){Log.d("DRAWVIEW","DRAWVIEW è partito prima di animator:"+e);}

            }
            else{
                canvas.drawRect(bossRect,Boss.color);
            }
            //DRAW BOSS MISSILE
            synchronized(Missile.missileList) {
                for (Missile missile : Missile.missileList) {
                    Rect obstacleRect = new Rect(missile.positionX, missile.positionY, missile.positionX + missile.SIZE_X, missile.positionY + missile.SIZE_Y);
                    if (missile.positionY < 0) {
                        missileRemovedList.add(missile);
                    } else {
                        if (!Timer.missileHitList.contains(missile)) {
                            if(!PlayFragment.skins) {
                                canvas.drawRect(obstacleRect, missile.color);
                            }
                            else {
                                canvas.drawBitmap(Missile.skin, null, obstacleRect, null);
                            }
                        } else {
                            missileRemovedList.add(missile);
                        }
                    }
                }
                if (!missileRemovedList.isEmpty()) {
                    Missile.removeFromMissileList(missileRemovedList);
                    missileRemovedList.clear();
                }
            }
        }



        if(PlayFragment.measureFps) {
            Timer.frames++;
            canvas.drawText(Long.toString(Timer.fps)+"fps", 900, 50, fpsPaint);
            //canvas.drawText(Long.toString(Timer.totalTime/Timer.ONE_SEC),900,100,Player.scoreColor);
        }

    }

    public static void resetGame(){
        Shot.reset();
        Missile.reset();
        Obstacle.reset();
        Boss.reset();
        Player.reset();
    }



    public void refresh(){
        this.invalidate();
    }




}
