package com.example.game;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

// Oggetto che ottiene i dati dai sensori e modifica la direzione del giocatore ( ho utilizzato solo il giroscopio nel modo piu semplice con cui sono riuscito a farlo funzionare decentemente)
public class Sensors implements SensorEventListener {
    static Sensor gyroscope;
    static Sensor accellerometer;
    static Sensor magnetometer;
    float azimuth;
    float pitch;
    float roll;
    double z;
    double x;
    double y;

    public Sensors() {
        gyroscope = PlayFragment.sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        accellerometer = PlayFragment.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = PlayFragment.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {

            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            // z=Math.toDegrees(azimuth);
            // y=Math.toDegrees(pitch);
            // x=Math.toDegrees(roll);


            // Log.e("ANGLE", "x=" + x);
            // Log.e("ANGLE", "y=" + y);
            // Log.e("ANGLE", "z=" + z);

            if (y < -0.420f) {
                Player.directionLeft();
                //drawView.invalidate();
            }
            else if (y > 0.420f) {
                Player.directionRight();
                //drawView.invalidate();
            }
            /*
            if (x <-0.2f) {
                Player.directionUp();
                //drawView.invalidate();

            }
            else if (x > 0.2f) {
                Player.directionDown();
                //drawView.invalidate();
            }
            */
                    /*
                    else if(y>0f && y<=1f){
                        drawView.moveStopY();
                    }
                    else if(y<0f && y>=-1f){
                        drawView.moveStopY();
                    }

                    else if(x>0f && x<=1f){
                        drawView.moveStopX();
                    }
                    else if(x<0f && x>=-1f){
                        drawView.moveStopX();
                    }
                    */

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
};
