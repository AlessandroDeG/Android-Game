package com.example.game;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


// fragment che permette all'utente di modificare le impostazioni di gioco:
//-nome con cui il giocatore che verrà salvato tra le partite o tra i punteggi piu alti
//-numero di colpi contemporanei(difficoltà del gioco: default 5 colpi)
//skin: si o no, selezione della skin ( default:space , altre skin non complete perchè space era piu bella)
//contatore di frame per second
//musica
//effetti sonori
//vibrazione
public class SettingsFragment extends Fragment implements View.OnClickListener {

    Button mainMenuButton;
    Switch skinSwitch;
    Switch fpsSwitch;
    Switch musicSwitch;
    Switch soundEffectsSwitch;
    Switch vibrationSwitch;
    Button confermaShotsButton;
    Button confermaPlayerNameButton;
    TextView nShotsText;
    TextView playerNameText;
    View view;
    CheckBox spaceCheckBox;
    CheckBox trollCheckBox;
    CheckBox biplaneCheckBox;
    LinearLayout skinLayout;



    static Context context;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_layout, container, false);
        context= getActivity();

        mainMenuButton = view.findViewById(R.id.mainMenuButton);
        skinSwitch = view.findViewById(R.id.skinSwitch);
        musicSwitch= view.findViewById(R.id.musicSwitch);
        soundEffectsSwitch= view.findViewById(R.id.effectsSwitch);
        fpsSwitch = view.findViewById(R.id.fpsSwitch);
        vibrationSwitch = view.findViewById(R.id.vibrationSwitch);
        confermaShotsButton = view.findViewById(R.id.confermaShotsButton);
        confermaPlayerNameButton = view.findViewById(R.id.confermaPlayerNameButton);
        nShotsText =view.findViewById(R.id.nShotsText);
        playerNameText =view.findViewById(R.id.playerNameText);
        spaceCheckBox = view.findViewById(R.id.spaceCheckBox);
        trollCheckBox = view.findViewById(R.id.trollCheckBox);
        biplaneCheckBox = view.findViewById(R.id.biplaneCheckBox);
        skinLayout = view.findViewById(R.id.skinsLayout);

        mainMenuButton.setOnClickListener(this);
        skinSwitch.setOnClickListener(this);
        fpsSwitch.setOnClickListener(this);
        musicSwitch.setOnClickListener(this);
        soundEffectsSwitch.setOnClickListener(this);
        confermaShotsButton.setOnClickListener(this);
        confermaPlayerNameButton.setOnClickListener(this);
        trollCheckBox.setOnClickListener(this);
        spaceCheckBox.setOnClickListener(this);
        biplaneCheckBox.setOnClickListener(this);
        vibrationSwitch.setOnClickListener(this);

        if(skinSwitch.isChecked()){
            skinLayout.setVisibility(View.VISIBLE);
        }
        else{
            skinLayout.setVisibility(View.INVISIBLE);
        }

        skinSwitch.setChecked(true);
        musicSwitch.setChecked(true);
        soundEffectsSwitch.setChecked(true);
        vibrationSwitch.setChecked(true);



        return view;
    }


    public void onClick(View clickedView) {
        if (clickedView == clickedView.findViewById(R.id.mainMenuButton)) {
            MainActivity.setMainMenuFragment();
        }
        if (clickedView == clickedView.findViewById(R.id.skinSwitch)) {

            if (skinSwitch.isChecked()) {
                spaceCheckBox.setChecked(false);
                biplaneCheckBox.setChecked(false);
                trollCheckBox.setChecked(false);
                skinLayout.setVisibility(View.VISIBLE);
            } else {
                PlayFragment.skins = false;
                skinLayout.setVisibility(View.INVISIBLE);
                Skins.currentSkin=null;
                DrawView.backgroundColor= Color.WHITE;
            }
        }
        if (clickedView == clickedView.findViewById(R.id.fpsSwitch)) {

            if (fpsSwitch.isChecked()) {
                PlayFragment.measureFps = true;
            } else {
                PlayFragment.measureFps = false;
            }
        }
        if (clickedView == clickedView.findViewById(R.id.musicSwitch)) {

            if (musicSwitch.isChecked()) {
                Sounds.musicOn = true;
            } else {
                Sounds.musicOn = false;
            }
        }
        if (clickedView == clickedView.findViewById(R.id.effectsSwitch)) {

            if (soundEffectsSwitch.isChecked()) {
                Sounds.soundEffectsOn = true;
            } else {
                Sounds.releaseAllSounds();
                Sounds.soundEffectsOn = false;
            }
        }
        if (clickedView == clickedView.findViewById(R.id.vibrationSwitch)) {

            if (vibrationSwitch.isChecked()) {
                Vibratore.vibrationOn = true;
            } else {
                Vibratore.vibrationOn = false;
            }
        }
        if (clickedView == clickedView.findViewById(R.id.confermaShotsButton)) {
            try {
                if (nShotsText.getText() != null && !(nShotsText.getText() == "") && Integer.parseInt(nShotsText.getText().toString()) >= 0) {
                    Shot.maxShotsN = Integer.parseInt(nShotsText.getText().toString());
                } else {
                    Shot.maxShotsN = Shot.MAX_DEFAULT_NUMBER;
                }
            }
            catch(NumberFormatException e){Shot.maxShotsN = Shot.MAX_DEFAULT_NUMBER;}
            Toast.makeText(SettingsFragment.context,"MAX SHOTS: "+Shot.maxShotsN,Toast.LENGTH_SHORT).show();
            Log.e("SET MAXSHOTS: ", Integer.toString(Shot.maxShotsN));

        }
        if (clickedView == clickedView.findViewById(R.id.confermaPlayerNameButton)) {

                if (playerNameText.getText() != null) {
                    Player.name=playerNameText.getText().toString();
                }
            Toast.makeText(SettingsFragment.context,"PLAYER NAME: "+Player.name,Toast.LENGTH_SHORT).show();
            Log.e("SET PLAYER NAME: ",Player.name);
        }
        if (clickedView == clickedView.findViewById(R.id.trollCheckBox)) {
           // Log.d("SETTTING SKINS","TROLL");
            if(((CheckBox)clickedView).isChecked()) {
                Log.d("SETTTING SKINS"," TROLL TRUE");
                Skins.setTrollSkins();
                //spaceCheckBox.toggle();
                spaceCheckBox.setChecked(false);
                biplaneCheckBox.setChecked(false);
                if(Boss.isAlive){
                    Boss.isAlive=false;
                    Boss.setAnimation();
                }
            }
        }
        if (clickedView == clickedView.findViewById(R.id.spaceCheckBox)) {
            //Log.d("SETTTING SKINS","SPACE");
            if(((CheckBox)clickedView).isChecked()) {
                Log.d("SETTTING SKINS","SPACE TRUE");
                Skins.setSpaceSkins();
                //trollCheckBox.toggle();
                trollCheckBox.setChecked(false);
                biplaneCheckBox.setChecked(false);
                if(Boss.isAlive){
                    Boss.isAlive=false;
                    Boss.setAnimation();
                }
            }
        }
        if (clickedView == clickedView.findViewById(R.id.biplaneCheckBox)) {
            //Log.d("SETTTING SKINS","BIPLANE");
            if(((CheckBox)clickedView).isChecked()) {
                Log.d("SETTTING SKINS","BIPLANE TRUE");
                Skins.setBiplaneSkins();
                //trollCheckBox.toggle();
                trollCheckBox.setChecked(false);
                spaceCheckBox.setChecked(false);
                if(Boss.isAlive){
                    Boss.isAlive=false;
                    Boss.setAnimation();
                }
            }
        }



    }
}
