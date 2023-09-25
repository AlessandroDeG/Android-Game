package com.example.game;


//Dati del giocatore da inserire nel db: Giocatore ha solo campi e metodi statici perchè ne esiste solo uno per volta nel gioco, questo oggetto permette di salvarne i dati rilevanti e avere piu copie dello stesso giocatore
public class PlayerData {
    String name;
    int posY;
   int posX;
    int health;
   int directionX;
    int directionY;
    int score;


    public PlayerData(String name,int posX,int posY,int health,int directionX,int directionY,int score){
      this.name=name;
        this.posX=posX;
        this.posY=posY;
        this.health=health;
        this.directionX=directionX;
        this.directionY=directionY;
        this.score=score;
    }

}
