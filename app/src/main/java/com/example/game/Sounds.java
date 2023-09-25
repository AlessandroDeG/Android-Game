package com.example.game;

import android.media.MediaPlayer;


//effetti sonori e musica di gioco
public class Sounds {

    static MediaPlayer music;
    static MediaPlayer alienBossSounds;
    static MediaPlayer impactSound;
    static MediaPlayer laserSound;
    static MediaPlayer missileImpactSound;

    static boolean musicOn = true;
    static boolean soundEffectsOn = true;


    static void startMusic(){
        if(musicOn) {
            music = MediaPlayer.create(PlayFragment.context, R.raw.tunztunz);
            music.start();
            music.setLooping(true);
        }
    }

    static void startBossSounds(){
        if(soundEffectsOn) {
            if (Skins.currentSkin == Skins.SPACE_SKIN) {
                alienBossSounds = MediaPlayer.create(PlayFragment.context, R.raw.aliensound);
                alienBossSounds.start();
                alienBossSounds.setLooping(true);
            }
        }
    }

     static void releaseBossSounds() {
         if(soundEffectsOn) {
             if (Skins.currentSkin == Skins.SPACE_SKIN) {
                 alienBossSounds.release();
             }
         }
     }

    static void pauseAllSounds(){
        if(musicOn) {
            music.pause();
        }
        if(soundEffectsOn) {
            if (Skins.currentSkin == Skins.SPACE_SKIN && Boss.isAlive) {
                alienBossSounds.pause();
            }
        }
    }
    static void resumeAllSounds() {
        if(musicOn) {
            music.start();
        }
        if(soundEffectsOn) {
            if (Skins.currentSkin == Skins.SPACE_SKIN && Boss.isAlive) {

                    alienBossSounds = MediaPlayer.create(PlayFragment.context, R.raw.aliensound);
                    alienBossSounds.start();
                    alienBossSounds.setLooping(true);

            }
        }
    }

    static void releaseAllSounds(){
        if(musicOn) {
            music.stop();
            music.release();
        }
        if(soundEffectsOn) {
            if (Skins.currentSkin == Skins.SPACE_SKIN && Boss.isAlive) {
                alienBossSounds.stop();
                alienBossSounds.release();
            }
            if (impactSound != null) impactSound.release();
            if (missileImpactSound != null) missileImpactSound.release();
            if (laserSound != null) laserSound.release();
        }

    }

    static void playImpactSound(){
        if(soundEffectsOn) {
            impactSound = MediaPlayer.create(MainActivity.context, R.raw.impact);
            impactSound.start();
            impactSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    impactSound.release();
                }
            });
        }
    }

    static void playMissileImpactSound(){
        if(soundEffectsOn) {
            missileImpactSound = MediaPlayer.create(MainActivity.context, R.raw.missileimpact);
            missileImpactSound.start();
            missileImpactSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    missileImpactSound.release();
                }
            });
        }
    }



    static void playLaserSound(){
        if(soundEffectsOn) {
            laserSound = MediaPlayer.create(MainActivity.context, R.raw.laser);
            laserSound.start();
            laserSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    laserSound.release();
                }
            });
        }


    }



}
