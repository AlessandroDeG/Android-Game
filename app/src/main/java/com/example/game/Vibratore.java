package com.example.game;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import androidx.core.content.ContextCompat;

import static android.content.Context.VIBRATOR_SERVICE;


//vibrazione(consequente a una collisione tra giocatore e ostacoli)
public class Vibratore {

    static boolean vibrationOn=true;

    static int SHORT_VIBRATION = 50;
    static int LONG_VIBRATION = 300;


    public static void shakeIt() {
        if(vibrationOn) {
            if (ContextCompat.checkSelfPermission(MainActivity.context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= 26) {
                    ((Vibrator) MainActivity.context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(150, 10));
                } else {
                    ((Vibrator) MainActivity.context.getSystemService(VIBRATOR_SERVICE)).vibrate(SHORT_VIBRATION);
                }
            }
        }
    }


    public static void shakeIt(int duration) {
        if(vibrationOn) {
            if (ContextCompat.checkSelfPermission(MainActivity.context, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= 26) {
                    ((Vibrator) MainActivity.context.getSystemService(VIBRATOR_SERVICE)).vibrate(VibrationEffect.createOneShot(duration, 10));
                } else {
                    ((Vibrator) MainActivity.context.getSystemService(VIBRATOR_SERVICE)).vibrate(duration);
                }
            }
        }
    }
}
