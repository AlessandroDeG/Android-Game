package com.example.game;

//Dati del Boss da inserire nel db: Boss ha solo campi e metodi statici perchè ne esiste solo uno per volta nel gioco, questo oggetto permette di salvarne i dati rilevanti e avere piu copie dello stesso boss
public class BossData {
    int posX;
    int posY;
    int direction;
    int health;
    public BossData(int posX,int posY,int direction,int health){
        this.posX=posX;
        this.posY=posY;
        this.direction=direction;
        this.health=health;
    }
}
