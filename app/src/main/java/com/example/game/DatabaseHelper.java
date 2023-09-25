package com.example.game;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
//Oggetto che permette di utilizzare il db locale:
//-salvare i punteggi piu alti(e la data)
//-salvare i dati di una partita: -giocatore,boss,shots,missili,ostacoli,cibo,ecc (con screenshot della partita nel momento del salvataggio,e la data)
//-caricare i dati dei punteggi, o di una partita;
public final class DatabaseHelper extends SQLiteOpenHelper {

    public static final int MAX_HIGHSCORES_NUMBER=10;

    private static final String TAG = "DatabaseHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Game.db";
    private static final String TABLE_GAME = "Game";
    private static final String TABLE_PLAYER = "Player";
    private static final String TABLE_OBSTACLE = "Obstacle";
    private static final String TABLE_SHOT = "Shot";
    private static final String TABLE_MISSILE = "Missile";
    private static final String TABLE_BOSS = "Boss";
    private static final String TABLE_HIGHSCORE = "Highscore";



    private static final String KEY_ID_GAME = "gameID";
    private static final String KEY_GAME_NAME = "gameName";
    private static final String KEY_ID_PLAYER = "PlayerID";
    private static final String KEY_ID_OBSTACLE = "ObstacleID";
    private static final String KEY_ID_SHOT = "ShotID";
    private static final String KEY_ID_MISSILE = "MissileID";
    private static final String KEY_ID_BOSS = "BossID";
    private static final String KEY_ID_HIGHSCORE = "HighscoreID";



    private static final String KEY_NUMBER_OF_OBSTACLES="ObsN";
    private static final String KEY_NUMBER_OF_BOSSES="BossN";
    private static final String KEY_NUMBER_OF_BONUSES="BonusN";
    private static final String KEY_NUMBER_OF_FOOD="FoodN";

    private static final String KEY_SCREENSHOT="Screenshot";

    private static final String KEY_POSX="PosX";
    private static final String KEY_POSY="PosY";

    private static final String KEY_DIRECTION_X="DirectionX";
    private static final String KEY_DIRECTION_Y="DirectionY";
    private static final String KEY_DIRECTION="Direction";

    private static final String KEY_SKIN ="Skin";  //if null == no skin?
    //private static final String KEY_SKIN_TYPE="SkinType";

    private static final String KEY_HP="HealthPoints";

    private static final String KEY_PLAYER_NAME="PlayerName";

    private static final String KEY_SCORE="Score";

    private static final String KEY_ISFOOD="isFood";
    private static final String KEY_ISBONUS="isBonus";

    private static final String KEY_MAX_SHOTS="MaxSHots";

    private static final String KEY_ALIVE="Alive";

    private static final String KEY_DATE = "creation_date";

    //SINLGETON/////////////
    private static DatabaseHelper instance;

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(Context context){
        if(instance==null){
            instance = new DatabaseHelper(context);
        }

        return instance;
    }
    /////////////////////


    @Override    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table "+ TABLE_GAME + " " +
                "(" + KEY_ID_GAME + " integer primary key autoincrement," +
               // " "+ KEY_GAME_NAME + " String," +
               // " "+ KEY_ID_PLAYER + " integer not null ," +
                //" "+ KEY_ID_OBSTACLE + " integer not null," +
                //" "+ KEY_ID_BOSS + " integer not null," +
                " "+ KEY_SKIN + " String," +
                " "+ KEY_SCREENSHOT + " BLOB," +
                " "+ KEY_DATE + " INTEGER" + ");" + ""
        );

        ////TABLE SKINS???


        db.execSQL("create table "+ TABLE_PLAYER + " " +
                "(" + KEY_ID_PLAYER + " integer primary key autoincrement," +
                " " + KEY_ID_GAME + " integer not null  REFERENCES "+TABLE_GAME+"("+KEY_ID_GAME+")," +
                " "+ KEY_PLAYER_NAME + " TEXT not null," +
                " "+ KEY_SCORE + " integer not null,"+
                " "+ KEY_POSX + " integer not null," +
                " "+ KEY_POSY + " integer not null," +
                " "+ KEY_HP + " integer not null," +
                " "+ KEY_DIRECTION_X + " integer not null," +
                " "+ KEY_DIRECTION_Y + " integer not null" + ");" + ""
        );
        db.execSQL("create table "+ TABLE_OBSTACLE + " " +
                "(" + KEY_ID_OBSTACLE + " integer primary key autoincrement," +
                " " + KEY_ID_GAME + " integer not null  REFERENCES "+TABLE_GAME+"("+KEY_ID_GAME+")," +
                " "+ KEY_POSX + " integer not null," +
                " "+ KEY_POSY+ " integer not null," +
                " "+ KEY_ISFOOD + " BOOLEAN not null CHECK ("+KEY_ISFOOD +" IN (0,1))," +
                " "+ KEY_ISBONUS + " BOOLEAN not null CHECK ("+KEY_ISBONUS +" IN (0,1)));" + ""
        );
        db.execSQL("create table "+ TABLE_BOSS + " " +
                "(" + KEY_ID_BOSS + " integer primary key autoincrement," +
                " " + KEY_ID_GAME + " integer not null  REFERENCES "+TABLE_GAME+"("+KEY_ID_GAME+")," +
                " "+ KEY_POSX + " integer not null," +
                " "+ KEY_POSY + " integer not null," +
                " "+ KEY_HP + " integer not null," +
                //" "+ KEY_ALIVE + "boolean not null" +
                " "+ KEY_DIRECTION + " integer not null" +");" + ""
        );

        db.execSQL("create table "+ TABLE_SHOT + " " +
                "(" + KEY_ID_SHOT + " integer primary key autoincrement," +
                " " + KEY_ID_GAME + " integer not null  REFERENCES "+TABLE_GAME+"("+KEY_ID_GAME+")," +
               // " "+ KEY_MAX_SHOTS + "integer not null" +
                " "+ KEY_POSX + " integer not null," +
                " "+ KEY_POSY + " integer not null" + ");" + ""
        );
        db.execSQL("create table "+ TABLE_MISSILE + " " +
                "(" + KEY_ID_MISSILE + " integer primary key autoincrement," +
                " " + KEY_ID_GAME + " integer not null  REFERENCES "+TABLE_GAME+"("+KEY_ID_GAME+")," +
                " "+ KEY_POSX + " integer not null," +
                " "+ KEY_POSY + " integer not null," +
                //" "+ KEY_DIRECTION_X + "integer not null," + mhhhhhh
                " "+ KEY_DIRECTION + " integer not null" + ");" + ""
        );

        db.execSQL("create table "+ TABLE_HIGHSCORE + " " +
                "(" + KEY_ID_HIGHSCORE + " integer primary key autoincrement," +
              //  " "+ KEY_ID_PLAYER + " integer not null REFERENCES "+TABLE_PLAYER+"("+KEY_ID_PLAYER+")," + ///////MHHHHHHHHHHHHHHHHHHHHHHHH booo
                " "+ KEY_PLAYER_NAME + " String not null," +
                " "+ KEY_SCORE + " integer not null," +
               // " "+ KEY_NUMBER_OF_OBSTACLES + " integer not null," +
               // " "+ KEY_NUMBER_OF_BOSSES + " integer not null," +
               // " "+ KEY_NUMBER_OF_BONUSES + " integer not null," +
                //" "+ KEY_NUMBER_OF_FOOD + " integer not null," +
                " "+ KEY_DATE + " INTEGER" + ");" + ""
        );

        Log.e(TAG, "onCreate(): create tables");
    }



    public synchronized long insertItem(Object object){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. create ContentValues to add key "column"/value

        long id = 0;


        ContentValues values = new ContentValues();

        if(object instanceof SavedGame) {

            values= new ContentValues();
            //GAMEDATA
           // values.put(KEY_GAME_NAME,((SavedGame) object).gameName);
            values.put(KEY_DATE,((SavedGame) object).getDate().getTimeInMillis());
            values.put(KEY_SKIN,((SavedGame) object).getSkin());
            ByteArrayOutputStream blob  = new ByteArrayOutputStream();
            Bitmap bitmap =((SavedGame) object).getBitmap();
            bitmap.compress(Bitmap.CompressFormat.JPEG,15,blob);
            byte[] bytes = blob.toByteArray();
            try {
                blob.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            values.put(KEY_SCREENSHOT,bytes);

            long gameID= db.insert(TABLE_GAME, null,values);

            //PLAYER

            try
            {

            values = new ContentValues();
            values.put(KEY_ID_GAME,gameID);
            values.put(KEY_PLAYER_NAME,((SavedGame) object).playerData.name);
            values.put(KEY_SCORE,((SavedGame) object).playerData.score);
            values.put(KEY_POSX,((SavedGame) object).playerData.posX);
            values.put(KEY_POSY,((SavedGame) object).playerData.posY);
            values.put(KEY_HP,((SavedGame) object).playerData.health);
            values.put(KEY_DIRECTION_X,((SavedGame) object).playerData.directionX);
            values.put(KEY_DIRECTION_Y,((SavedGame) object).playerData.directionY);



            db.insert(TABLE_PLAYER, null,values);

            }
            catch (Exception e)
            {
                Log.e("ERROR DB PLAYER INSERT", e.toString());
            }

            //Boss
            values = new ContentValues();
            values.put(KEY_ID_GAME,gameID);
            values.put(KEY_POSX,((SavedGame) object).bossData.posX);
            values.put(KEY_POSY,((SavedGame) object).bossData.posY);
            values.put(KEY_DIRECTION,((SavedGame) object).bossData.direction);
            values.put(KEY_HP,((SavedGame) object).bossData.health);
            //values.put(KEY_ALIVE,((SavedGame) object).boss.isAlive);
            db.insert(TABLE_BOSS, null,values);

            //OBSTACLE
            synchronized (Obstacle.obstaclesList) {
                for (Obstacle obstacle : ((SavedGame) object).obstacles) {
                    values = new ContentValues();
                    values.put(KEY_ID_GAME, gameID);
                    values.put(KEY_POSX, obstacle.positionX);
                    values.put(KEY_POSY, obstacle.positionY);
                    values.put(KEY_ISFOOD, obstacle.isFood ? 1 : 0);
                    values.put(KEY_ISBONUS, obstacle.isBonusFood ? 1 : 0);
                    db.insert(TABLE_OBSTACLE, null, values);
                }
            }

            //Shot
            synchronized (Shot.shotsList) {
                for (Shot shot : ((SavedGame) object).shots) {
                    values = new ContentValues();
                    values.put(KEY_ID_GAME, gameID);
                    values.put(KEY_POSX, shot.positionX);
                    values.put(KEY_POSY, shot.positionY);
                    //values.put(KEY_MAX_SHOTS,Shot.maxShotsN); ///////////////GAMEDATA?
                    db.insert(TABLE_SHOT, null, values);
                }
            }

            //Missile
            synchronized (Missile.missileList) {
                for (Missile missile : ((SavedGame) object).missiles) {
                    values = new ContentValues();
                    values.put(KEY_ID_GAME, gameID);
                    values.put(KEY_POSX, missile.positionX);
                    values.put(KEY_POSY, missile.positionY);
                    // values.put(KEY_DIRECTION_X,(missile.directionX);
                    values.put(KEY_DIRECTION, missile.direction);

                    db.insert(TABLE_MISSILE, null, values);
                }
            }
           id=gameID;
        }
        else if(object instanceof Highscore ){

            values=new ContentValues();
            values.put(KEY_SCORE,((Highscore) object).getHighscore());
            values.put(KEY_PLAYER_NAME,((Highscore) object).getPlayerName());
            values.put(KEY_DATE,((Highscore) object).getDate().getTimeInMillis());
            id = db.insert(TABLE_HIGHSCORE, null,values);

            Log.e("DB","INSERT NEW HIGHSCORE");

            if(getAllHighscores().size()>MAX_HIGHSCORES_NUMBER){
                deleteHighscore((getAllHighscores().get(MAX_HIGHSCORES_NUMBER)));
                Log.e("DB","REMOVING LAST HIGHSCORE");
            }
        }

        db.close();
        return id;
    }

    public synchronized ArrayList<Highscore> getAllHighscores() {
        Log.e("DB","GETTING HIGHSCORES");
        ArrayList<Highscore> items = new ArrayList<Highscore>();
        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_HIGHSCORE +" ORDER BY "+KEY_SCORE+" DESC ";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        // 3. go over each row, build book and add it to list
        Highscore highscore = null;
        if (cursor.moveToFirst()) {
            do {
                highscore = new Highscore();
                highscore.setId(Integer.parseInt(cursor.getString(0)));
                highscore.setPlayerName(cursor.getString(1));
                highscore.setHighscore(cursor.getInt(2));
                GregorianCalendar createOn = new GregorianCalendar();
                createOn.setTimeInMillis(cursor.getLong(3));
                highscore.setDate(createOn);

                // Add item to items
                items.add(highscore);

            } while (cursor.moveToNext());
        }
        // 4. close
        cursor.close();
        db.close();
        //Log.d(TAG, "getAllItems(): "+ items.toString());
        return items;
        // return items
    }

    public synchronized void deleteHighscore(Highscore item) {
        // 1. get reference to writable DB
        // Create and/or open a database that will be used for reading and writing.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_HIGHSCORE, //table name
                KEY_ID_HIGHSCORE+" = ?",
                // selections
                new String[] { String.valueOf(item.getId()) }); //selections args

        // 3. close
        db.close();
        Log.d(TAG, "deleted item "+item.toString());
    }

    public synchronized void deleteAllHighscores() {
        // 1. get reference to writable DB
        // Create and/or open a database that will be used for reading and writing.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_HIGHSCORE,null,null);

        // 3. close
        db.close();
        Log.d(TAG, "deleted all highscores ");
    }

    ///////////SAVEDGAMES




    public synchronized ArrayList<SavedGame> getAllSavedGames() {
        Log.e("DB","GETTING SAVEDGAMES");
        ArrayList<SavedGame> items = new ArrayList<SavedGame>();
        // 1. build the query
        String games = "SELECT  * FROM " + TABLE_GAME +" ORDER BY "+KEY_DATE+" DESC ";
        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursorGames = db.rawQuery(games, null);
        // 3. go over each row, build book and add it to list
        SavedGame savedGame = null;

        if (cursorGames.moveToFirst()) {
            do {
                savedGame = new SavedGame();

                savedGame.setId(Integer.parseInt(cursorGames.getString(0)));

                savedGame.setSkin(cursorGames.getString(1));

                byte[] bytes= cursorGames.getBlob(2);
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
               savedGame.setBitmap(bitmap);

                GregorianCalendar createOn = new GregorianCalendar();
                createOn.setTimeInMillis(cursorGames.getLong(3));
                savedGame.setDate(createOn);
                ///MAXSHOTS?

                //query Player

                String playerQuery = "SELECT  * FROM " + TABLE_PLAYER +" WHERE "+KEY_ID_GAME+" = "+savedGame.getId();
                Cursor cursorPlayer = db.rawQuery(playerQuery, null);
                PlayerData playerData=null;
                if (cursorPlayer.moveToFirst()) {
                    do {
                        String playerName=cursorPlayer.getString(2);
                        int playerScore=cursorPlayer.getInt(3);
                        int playerPosX=cursorPlayer.getInt(4);
                        int playerPosY=cursorPlayer.getInt(5);
                        int playerHP=cursorPlayer.getInt(6);
                        int playerDirectionX=cursorPlayer.getInt(7);
                        int playerDirectionY=cursorPlayer.getInt(8);

                        playerData = new PlayerData(playerName,playerPosX,playerPosY,playerHP,playerDirectionX,playerDirectionY,playerScore);

                    } while (cursorPlayer.moveToNext());
                }
                cursorPlayer.close();

                //query boss
                String bossQuery = "SELECT  * FROM " + TABLE_BOSS +" WHERE "+KEY_ID_GAME+" = "+savedGame.getId();
                Cursor cursorBoss = db.rawQuery(bossQuery, null);
                BossData bossData = null;
                if (cursorBoss.moveToFirst()) {
                    do {
                        int posX=cursorBoss.getInt(2);
                        int posY=cursorBoss.getInt(3);
                        int health=cursorBoss.getInt(4);
                        int direction=cursorBoss.getInt(5);


                        //Log.e("GETTING BOSS HP",Integer.toString(health));

                        //int alive=cursorBoss.getInt(5);
                        bossData=new BossData(posX,posY,direction,health);

                    } while (cursorBoss.moveToNext());
                }
                cursorBoss.close();

                //query Shots
                String shotsQuery = "SELECT  * FROM " + TABLE_SHOT +" WHERE "+KEY_ID_GAME+" = "+savedGame.getId();
                Cursor cursorShots = db.rawQuery(shotsQuery, null);
                List<Shot> shots = Collections.synchronizedList(new ArrayList<Shot>());
                if (cursorShots.moveToFirst()) {
                    do {

                        int posX=cursorShots.getInt(2);
                        int posY=cursorShots.getInt(3);
                        //int maxShots=cursorShots.getInt(3); //////GAMEDATA
                       shots.add(new Shot(posX,posY));
                    } while (cursorShots.moveToNext());
                }
                cursorShots.close();

                //query Missiles
                String missilesQuery = "SELECT  * FROM " + TABLE_MISSILE +" WHERE "+KEY_ID_GAME+" = "+savedGame.getId();
                Cursor cursorMissiles = db.rawQuery(missilesQuery, null);
                List<Missile> missiles = Collections.synchronizedList((new ArrayList<Missile>()));
                if (cursorMissiles.moveToFirst()) {
                    do {

                        int posX=cursorMissiles.getInt(2);
                        int posY=cursorMissiles.getInt(3);
                        int direction=cursorMissiles.getInt(4);
                        missiles.add(new Missile(posX,posY,direction));
                        //SET MISSILES STUFF
                    }
                    while (cursorMissiles.moveToNext());
                }
                cursorMissiles.close();

                //query Obstacles
                String obstaclesQuery = "SELECT  * FROM " + TABLE_OBSTACLE +" WHERE "+KEY_ID_GAME+" = "+savedGame.getId();
                Cursor cursorObstacles = db.rawQuery(obstaclesQuery, null);
                List<Obstacle> obstacles = Collections.synchronizedList(new ArrayList<Obstacle>());
                if (cursorObstacles.moveToFirst()) {
                    do {
                        int posX=cursorObstacles.getInt(2);
                        int posY=cursorObstacles.getInt(3);
                        boolean isFood= cursorObstacles.getInt(4) == 1 ? true:false;
                        boolean isBonus=cursorObstacles.getInt(5)==1 ? true:false;
                        obstacles.add(new Obstacle(posX,posY,isFood,isBonus));
                        //SET Obstacles STUFF

                    }
                    while (cursorObstacles.moveToNext());
                }
                cursorObstacles.close();



                savedGame.setPlayer(playerData);
                savedGame.setBoss(bossData);
                savedGame.setShots(shots);
                savedGame.setMissiles(missiles);
                savedGame.setObstacles(obstacles);


                // Add item to items
                items.add(savedGame);

            } while (cursorGames.moveToNext());
        }
        // 4. close
        cursorGames.close();
        db.close();
        //Log.d(TAG, "getAllItems(): "+ items.toString());
        return items;
        // return items
    }

    public void deleteSavedGame(SavedGame item) {
        // 1. get reference to writable DB
        // Create and/or open a database that will be used for reading and writing.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_GAME, //table name
                KEY_ID_GAME+" = ?",
                // selections
                new String[] { String.valueOf(item.getId()) }); //selections args

        // 3. close
        db.close();
        Log.d(TAG, "deleted item "+item.toString());
    }


    public synchronized void deleteAllSavedGames() {
        // 1. get reference to writable DB
        // Create and/or open a database that will be used for reading and writing.
        SQLiteDatabase db = this.getWritableDatabase();
        // 2. delete
        db.delete(TABLE_GAME,null,null);

        // 3. close
        db.close();
        Log.e(TAG, "deleted all saved games ");
    }

    public int getSavedGamesNumber(){
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteStatement query = db.compileStatement("SELECT COUNT (*) FROM "+TABLE_GAME+ ";");
        int savedGamesNumber = (int)query.simpleQueryForLong();
        return savedGamesNumber;
    }

    public synchronized void forceUpgrade(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSTACLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOSS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MISSILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);
        // create fresh tables
        this.onCreate(db);
        Log.e(TAG, "forceUpgrade(): created fresh tables");
    }



    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBSTACLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOSS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MISSILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);
        // create fresh tables
        this.onCreate(db);
        Log.e(TAG, "onUpgrade(): created fresh tables");
    }

}
