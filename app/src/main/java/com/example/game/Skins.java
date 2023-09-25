package com.example.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.content.ContextCompat;
import android.util.Log;

// inizializza le skin caricando i file dalle risorse in bitmap(o array di bitmap per le animazioni)
//contiene i metodi per cambiare da una skin all'altra
public class Skins{
    static Bitmap[] alieno;
    static Bitmap[] dancingTroll;
    static Bitmap[] jet;

    static Bitmap sir;
    static Bitmap trollface ;
    static Bitmap wineglass;
    static Bitmap whiskey;

    static Bitmap invader;

    static Bitmap navetta;
    static Bitmap asteroid;
    static Bitmap pizza;
    static Bitmap missile;

    static Bitmap biplane;
    static Bitmap cloud;
    static Bitmap fuel;

    static Bitmap helicopter;

    static Bitmap spaceBackground;

    static String currentSkin;
    static final String SPACE_SKIN = "Space";
    static final String BIPLANE_SKIN = "Biplane";
    static final String TROLL_SKIN = "Troll";



    public static void initSkins(){

        ////LOAD SKINS INTO DB???? avoid long heap size errore


        Log.d("INITSKINS","");
        alieno = new Bitmap[8];
        alieno[0]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno1);
        alieno[1]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno2);
        alieno[2]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno3);
        alieno[3]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno4);
        alieno[4]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno5);
        alieno[5]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno6);
        alieno[6]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno7);
        alieno[7]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.alieno8);

        dancingTroll = new Bitmap[8];
        dancingTroll[0]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll1);
        dancingTroll[1]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll2);
        dancingTroll[2]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll3);
        dancingTroll[3]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll4);
        dancingTroll[4]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll5);
        dancingTroll[5]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll6);
        dancingTroll[6]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll7);
        dancingTroll[7]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.dancingtroll8);

        jet = new Bitmap[8];
        jet[0]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet1);
        jet[1]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet2);
        jet[2]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet3);
        jet[3]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet4);
        jet[4]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet5);
        jet[5]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet6);
        jet[6]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet7);
        jet[7]= BitmapFactory.decodeResource(MainActivity.resources, R.drawable.jet8);

        sir = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.sir);
        trollface = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.trollface);
        wineglass = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.wineglass);
        whiskey = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.whiskey);

        navetta = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.spaceship);
        asteroid = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.asteroid);
        pizza = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.pizza);

        missile = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.missile);

        invader = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.invader);

        biplane = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.biplane);
        helicopter = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.biplaneenemy);
        fuel = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.fuel);
        cloud = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.cloud);

        spaceBackground = BitmapFactory.decodeResource(MainActivity.resources, R.drawable.spacebackground);

    }






    static void setTrollSkins(){
        Log.d("SETTTING SKINS","TROLL");
        DrawView.backgroundColor= Color.WHITE;
        Player.setSkin(sir);
        Obstacle.obstacleSkin=trollface;
        Obstacle.foodSkin=wineglass;
        Obstacle.bonusFoodSkin=whiskey;

        Boss.setSkin(dancingTroll);

        Missile.setSkin(missile);

        PlayFragment.skins = true;
        currentSkin=TROLL_SKIN;

    }

    static void setSpaceSkins(){
        Log.d("SETTTING SKINS","SPACE");
        DrawView.backgroundColor=Color.BLACK;
        Player.setSkin(navetta);
        Player.setScoreColor(Player.ALTERNATIVE_SCORE_COLOR);
        Obstacle.obstacleSkin=asteroid;
        Obstacle.foodSkin=invader;
        Obstacle.bonusFoodSkin=pizza;

        Boss.setSkin(alieno);

        Missile.setSkin(missile);


        PlayFragment.skins = true;
        currentSkin=SPACE_SKIN;
    }

    static void setBiplaneSkins(){
        Log.d("SETTTING SKINS","SPACE");
        DrawView.backgroundColor= ContextCompat.getColor(MainActivity.context,R.color.biplaneBackground);
        Player.setSkin(biplane);
        Obstacle.obstacleSkin=helicopter;
        Obstacle.foodSkin=cloud;
        Obstacle.bonusFoodSkin=fuel;

        Boss.setSkin(jet);

        Missile.setSkin(missile);


        PlayFragment.skins = true;
        currentSkin=BIPLANE_SKIN;
    }

    static void setSkins(String skin){
        if(skin==null){currentSkin=null;PlayFragment.skins=false;}
        else if(skin.equals(SPACE_SKIN)){
            setSpaceSkins();
        }
        else if(skin.equals(BIPLANE_SKIN)){
            setBiplaneSkins();
        }
        else if(skin.equals(TROLL_SKIN)){
            setTrollSkins();
        }

    }
}
