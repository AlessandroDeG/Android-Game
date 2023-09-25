package com.example.game;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Point;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;


//Activity principale usata per cambiare fragments tra: menu,gioco,punteggi,impostazioni,salvataggi

/*to do:
-screen size
-gameover
-main menu view
-accellerometer
-high score: db , save ecc
-icons
-incremental speed? no
-lateral shots? No
-settings: difficulty, skins(trollface) , modalità spawn da tutti i lati,
-condividi app? INTENT

//TEST su un telefono vero? mmmmmmhhhhh

v2
fix bug pausa
more skins
fix obstacle sovrapposti
animazione movimento(spawn trail random bottom)
bOSS!
*/

public class MainActivity extends AppCompatActivity{

    static MainMenuFragment mainMenuFragment;
    static PlayFragment playFragment;
    static SettingsFragment settingsFragment;
    static HighscoresFragment highscoresFragment;
    static SavedGamesFragment savedGamesFragment;

    static FragmentManager fm;
    static FragmentTransaction fragmentTransaction;
    static int fragmentContainerID;

    static int screenWidth;
    static int screenHeight;

    static Resources resources;

    static Context context;

    //ArrayAdapter<Highscore> adapter;
    //ArrayList<Highscore> highscores;

    //ArrayAdapter<SavedGame> savedGamesAdapter;
    //ArrayList<SavedGame> savedgames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        resources=getResources();
        context = getApplicationContext();

        Skins.initSkins();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        fragmentContainerID=R.id.fragmentContainer;

        mainMenuFragment= new MainMenuFragment();
        playFragment= new PlayFragment();
        settingsFragment = new SettingsFragment();
        highscoresFragment = new HighscoresFragment();
        savedGamesFragment = new SavedGamesFragment();

        //savedGamesFragment.savedGames= DatabaseHelper.getInstance(PlayFragment.context).getAllSavedGames();

        //DatabaseHelper.getInstance(this).deleteAllHighscores();
       // DatabaseHelper.getInstance(this).deleteAllSavedGames();

       // DatabaseHelper.getInstance(this).forceUpgrade();

 /*
        highscores= DatabaseHelper.getInstance(this).getAllHighscores();
        adapter = new ArrayAdapter<Highscore>(this , android.R.layout.simple_list_item_1, highscores);
        highscoresFragment.setAdapter(adapter);



        //DatabaseHelper.getInstance(this).deleteAllSavedGames();
        savedgames= DatabaseHelper.getInstance(this).getAllSavedGames();
        savedGamesAdapter = new ArrayAdapter<SavedGame>(this , android.R.layout.simple_list_item_1, savedgames);
        savedGamesFragment.setAdapter(savedGamesAdapter);
        */

        fm = getFragmentManager();

        //addFragment(playFragment);
        addFragment(mainMenuFragment);

        Skins.setSpaceSkins();


    }
    public static void addFragment(Fragment fragment){
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(fragmentContainerID, fragment);
        fragmentTransaction.commit();

    }

    public static void replaceFragment(Fragment fragment){
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(fragmentContainerID, fragment);
        fragmentTransaction.commit();
    }

    public static void refreshFragment(Fragment fragment){
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.detach(fragment).attach(fragment);
        fragmentTransaction.commit();
    }
    public static void refreshPlayFragment(){
       refreshFragment(playFragment);
    }

    public static void setMainMenuFragment(){
        replaceFragment(mainMenuFragment);
        if(playFragment.isCreated && !PlayFragment.gameover){
            mainMenuFragment.setResumeButtonVisibility(true);
        }
        else{mainMenuFragment.setResumeButtonVisibility(false);}
        refreshFragment(mainMenuFragment);
    }

    public static void setPlayFragment(){
        replaceFragment(playFragment);
    }
    public static void setSettingsFragment(){
        replaceFragment(settingsFragment);
    }
    public static void setHighscoresFragment(){
        replaceFragment(highscoresFragment);
    }
    public static void setSavedGamesFragment(){
        replaceFragment(savedGamesFragment);
    }

}
