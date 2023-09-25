package com.example.game;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

//oggetto che serve per salvare i punteggi piu alti nel db
public class Highscore {

        private int highscore;
        private String playerName;

        private GregorianCalendar date;

        private long id;
        public Highscore() {}
        public Highscore(int highscore, String playerName) {
            this.highscore = highscore;
            this.playerName=playerName;
            this.date = new GregorianCalendar();
        }

        @Override    public String toString() {
            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date.getTime());
            return /*MainActivity.highscoresFragment.highscores.indexOf(this)+") "+*/highscore +" "+ playerName+" "+currentDate;
        }

        public GregorianCalendar getDate() {
            return date;
        }
        public void setDate(GregorianCalendar d) {
            this.date = d;
        }

        public int getHighscore(){
            return highscore;
        }
        public void setHighscore(int highscore){
            this.highscore=highscore;
        }
        public long getId(){
            return id;
        }
        public void setId(long id){
            this.id=id;
        }
    public String getPlayerName(){
        return playerName;
    }
    public void setPlayerName(String playerName){
        this.playerName=playerName;
    }


    }

