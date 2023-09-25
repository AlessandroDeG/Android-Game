package com.example.game;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Context.SENSOR_SERVICE;
import static com.example.game.SavedGamesFragment.savedGames;

//fragment di gioco:
// avvia la musica di gioco
//gestisce i click sullo schermo creando shots(con il suono)
//contiene:
// -la view di gioco(vedere DrawView)
//-bottoni: per salvataggio partita(e permette di fare un screenshot), per mettere in pausa e per tornare al fragment del menu principale
//-la custom progressbar per visualizzare la vita del giocatore(vedere HealthBar)

public class PlayFragment extends Fragment implements View.OnClickListener {

    static LinearLayout mainLayout;

    static boolean isCreated=false;

    static DrawView drawView;

    static boolean skins= false;
    static boolean measureFps = false;
    static boolean bossBattle=true;


    static SensorManager sensorManager;
    static Sensors sensors;

    static CustomButton mainMenuButton;
    static CustomButton pauseButton;
    static CustomButton saveButton;


    static Resources resources;
    static Context context;

    static HealthBar healthBar;

    static LinearLayout v;
    static LinearLayout buttonsLayout;

    static int healthBarColor=Color.GREEN;
    //static boolean changeColor=true;

    static boolean gameover=false;
    static boolean healthBarReset=false;

    static boolean savingGame=false;

    static boolean gamePaused=false;

    Toast savingGameToast;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.play_layout, container, false);

        context = getActivity();
        resources= getResources();

        mainLayout = view.findViewById(R.id.playLinearLayout);

       // Skins skins = new Skins(); // init bitmaps[]

        sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        sensors = new Sensors();

        buttonsLayout=view.findViewById(R.id.topLayout);
        buttonsLayout.setBackgroundColor(DrawView.backgroundColor);

        mainMenuButton=view.findViewById(R.id.mainMenuButton);
        pauseButton=view.findViewById(R.id.pauseButton);
        saveButton=view.findViewById(R.id.saveButton);
        healthBar = view.findViewById(R.id.healthBar);

        if(healthBarReset) {
            healthBar.post(new Runnable() {
                @Override
                public void run() {
                    healthBar.setProgress(Player.MAX_HEALTH);
                }
            });
             healthBarReset=false;
        }

        mainMenuButton.setOnClickListener(this);
        pauseButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);


        // new Timer(drawView);

        Log.e("PLAYFRAGMENT", "STARTGAME");

        v = (LinearLayout) view.findViewById(R.id.playLinearLayout);
        drawView = new DrawView(context);
        v.addView(drawView);
        v.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {

                if(!gamePaused) {

                    if (Shot.getShotsNumber() < Shot.maxShotsN) {
                        Sounds.playLaserSound();
                        Shot.addInShotsList(new Shot(Player.posX + Player.SIZE - (Player.SIZE / 2), Player.posY));
                    }
                }

            }
        });

        if(Skins.currentSkin== Skins.SPACE_SKIN) {

            mainMenuButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            mainMenuButton.setTextColor(Color.GREEN);

            pauseButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            pauseButton.setTextColor(Color.GREEN);

            saveButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            saveButton.setTextColor(Color.GREEN);

        }

        Sounds.startMusic();



        isCreated=true;
       return view;
    }

    @Override
    public void onClick(View clickedView) {
        if (clickedView == clickedView.findViewById(R.id.mainMenuButton)){
            //resetGame();
            MainActivity.setMainMenuFragment();
        }
        if (clickedView == clickedView.findViewById(R.id.pauseButton)){
            if(!gameover) {
                if (Timer.start) {
                    pauseButton.setText("UNPAUSE");
                    PlayFragment.pause();
                    Toast.makeText(getActivity(), "GAME PAUSED", Toast.LENGTH_SHORT).show();
                } else {
                    pauseButton.setText("PAUSE");
                    PlayFragment.unPause();
                    Toast.makeText(getActivity(), "GAME RESUMED", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (clickedView == clickedView.findViewById(R.id.saveButton)){
            if(!gameover) {
                //savingGameToast=new Toast(getActivity());
               // savingGameToast.makeText(getActivity(), "SAVING GAME", Toast.LENGTH_SHORT).show();
                pause();
                //while(savingGameToast==null || !(savingGameToast.getView().isShown())){Log.e("TOAST","notshowing");}
                if(!savingGame) {
                    //if (savingGameToast == null || !savingGameToast.getView().isShown()) {
                        /*
                      new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                           public void run() {

                                savingGameToast.makeText(getActivity(), "SAVING GAME", Toast.LENGTH_SHORT).show();
                                //postInvalidate();
                            }
                        });
                        */
                      //  savingGameToast.makeText(getActivity(), "SAVING GAME", Toast.LENGTH_SHORT).show();
                        if (DatabaseHelper.getInstance(context).getSavedGamesNumber() <= 15) {
                            savingGame = true;

                            saveGame();

                            savingGame = false;
                            savingGameToast.makeText(getActivity(), "GAME SAVED", Toast.LENGTH_SHORT).show();
                        } else {
                            savingGameToast.makeText(getActivity(), "TOO MANY GAMES SAVED", Toast.LENGTH_LONG).show();
                        }
                   // }
                }
            }
            unPause();
        }
    }

    @Override
    public  void onResume() {
        super.onResume();
        unPause();
    }

    @Override
    public void onPause() {
        super.onPause();
        pause();
    }

    public static void pause(){
       Sounds.pauseAllSounds();
        sensorManager.unregisterListener(sensors);
        Timer.stopTimer();
        gamePaused=true;
    }
    public static void unPause(){
        if(!gameover) {
            Sounds.resumeAllSounds();
            sensorManager.registerListener(sensors, Sensors.gyroscope, SensorManager.SENSOR_DELAY_GAME);
            Timer.startTimer();
            gamePaused=false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Sounds.releaseAllSounds();
        sensorManager.unregisterListener(sensors);
        Timer.stopTimer();
        Timer.destroyTimer();


    }

    public static void resetGame(){
        //healthBar.setProgress(Player.MAX_HEALTH);
        gameover=false;
        healthBarReset=true;
       drawView.resetGame();


    }

    public static void checkForHighScore(){

        ArrayList<Highscore> highscores= DatabaseHelper.getInstance(context).getAllHighscores();
        boolean newHighscore=false;
        for(Highscore highscore : highscores) {
            if(Player.score>highscore.getHighscore()){
                newHighscore=true;
            }
        }
        if(newHighscore || highscores.size()<DatabaseHelper.getInstance(context).MAX_HIGHSCORES_NUMBER) {
            DatabaseHelper.getInstance(context).insertItem(new Highscore(Player.score, Player.name));
            MainActivity.highscoresFragment.highscores=DatabaseHelper.getInstance(context).getAllHighscores();
            if(Skins.currentSkin==Skins.SPACE_SKIN){
                MainActivity.highscoresFragment.setAdapter(new ArrayAdapter<Highscore>(context, R.layout.simple_list_item_1_greentext, MainActivity.highscoresFragment.highscores));
            }
            else {
                MainActivity.highscoresFragment.setAdapter(new ArrayAdapter<Highscore>(context, R.layout.simple_list_item_1_blacktext, MainActivity.highscoresFragment.highscores));
            }

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(PlayFragment.context,"NEW HIGHSCORE!",Toast.LENGTH_SHORT).show();
                    //postInvalidate();
                }
            });
        }

    }

    public static void saveGame(){

           // SavedGame newSavedGame = new SavedGame(/*"PROVA,"*/DrawView.player.toPlayerData(), Obstacle.obstaclesList, Shot.shotsList, Missile.missileList, Boss.boss.toBossData());
           /* DatabaseHelper.getInstance(context).insertItem(newSavedGame);
            MainActivity.savedGamesFragment.savedGames = DatabaseHelper.getInstance(context).getAllSavedGames();
            MainActivity.savedGamesFragment.setAdapter(new ArrayAdapter<SavedGame>(context, android.R.layout.simple_list_item_1, MainActivity.savedGamesFragment.savedGames));

            //new Handler(Looper.getMainLooper()).post(new Runnable() {
                //@Override
               // public void run() {
                    Toast.makeText(PlayFragment.context, "GAME SAVED", Toast.LENGTH_SHORT).show();
                    //postInvalidate();
               // }
            //});
            */

           SavedGamesFragment.addNewItem(Player.toPlayerData(), Obstacle.obstaclesList, Shot.shotsList, Missile.missileList, Boss.toBossData());



    }

    public static void loadGame(SavedGame savedGame){
        resetGame();

        Skins.setSkins(savedGame.getSkin());
        //Log.e("LOADSKIN",savedGame.getSkin());

        Player.name=savedGame.getPlayer().name;
        Player.posX =savedGame.getPlayer().posX;
        Player.posY =savedGame.getPlayer().posY;
        Player.playerHealth=savedGame.getPlayer().health;
        //PlayFragment.healthBar.setProgress(savedGame.getPlayer().health);
        Player.directionX=savedGame.getPlayer().directionX;
        Player.directionY=savedGame.getPlayer().directionY;
        Player.score=savedGame.getPlayer().score;

        Obstacle.obstaclesList= savedGame.getObstacles();

        Shot.shotsList = savedGame.getShots();


        new Boss();
        Boss.posX= savedGame.getBoss().posX;
        Boss.posY=savedGame.getBoss().posY;
        Boss.direction=savedGame.getBoss().direction;
        Boss.bossHealth=savedGame.getBoss().health;

        //////TEST
        if(Boss.posX != Boss.INIT_POSX && Boss.posY!=Boss.INIT_POSY){
            if(PlayFragment.skins){
                Boss.setAnimation();
            }
            else{
                Boss.isAlive=true;
            }
        }
        else{
            Boss.isAlive=false;
        }

        Missile.missileList= savedGame.getMissiles();
        if(PlayFragment.isCreated) {
            healthBar.post(new Runnable() {
                @Override
                public void run() {
                    healthBar.setProgress(Player.playerHealth);
                }
            });
            healthBarReset=false;
        }

        MainActivity.setPlayFragment();




    }

    public static Bitmap screenShot() {
        Bitmap bitmap = Bitmap.createBitmap(mainLayout.getWidth(),
                mainLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        mainLayout.draw(canvas);
        return bitmap;
    }



}

