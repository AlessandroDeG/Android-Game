package com.example.game;

import android.graphics.Bitmap;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
//dati di salvataggio di una partita
public class SavedGame {
    //String gameName;
    PlayerData playerData;
   List<Obstacle> obstacles;
     List<Shot> shots;
    List<Missile> missiles;
    BossData bossData;
    GregorianCalendar date;
    String currentDate;
    long id;
    Bitmap bitmap;
    boolean deleteOption;
    String skin;

    public SavedGame() {}

    public SavedGame(/*String gameName,*/ PlayerData playerData, List<Obstacle> obstacles, List<Shot> shots, List<Missile> missiles, BossData bossData, String skin){
       // this.gameName=gameName;
        this.playerData=playerData;
        this.obstacles=new ArrayList<Obstacle>(obstacles);
        this.shots=new ArrayList<Shot>(shots);
        this.missiles=new ArrayList<Missile>(missiles);
        this.bossData=bossData;
        this.deleteOption=false;
        date= new GregorianCalendar();
        bitmap = PlayFragment.screenShot();
        this.skin=skin;
        //Log.e("SAVEDGAME",date.toString());
    }

    public String toString() {
        String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date.getTime());
        return playerData.name+"\n "+currentDate+"\n score: "+ playerData.score;
    }

    public GregorianCalendar getDate() {
        return date;
    }
    public void setDate(GregorianCalendar d) {
        this.date = d;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id=id;
    }

    public PlayerData getPlayer(){
        return playerData;
    }
    public void setPlayer(PlayerData playerData){
        this.playerData=playerData;
    }

    public List<Obstacle> getObstacles(){
        return obstacles;
    }
    public void setObstacles(List<Obstacle> obstacles){
        this.obstacles=obstacles;
    }

    public BossData getBoss(){ return this.bossData; }
    public void setBoss(BossData bossData){
        this.bossData=bossData;
    }

    public List<Shot> getShots(){ return shots; }
    public void setShots(List<Shot> shots){
        this.shots=shots;
    }

    public List<Missile> getMissiles(){ return missiles; }
    public void setMissiles(List<Missile> missiles){
        this.missiles=missiles;
    }

    public void setDeleteOption(boolean visibility){
        this.deleteOption=visibility;
    }
    public boolean getDeleteOption(){
        return this.deleteOption;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap=bitmap;
    }
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }





    //SAVE







}
