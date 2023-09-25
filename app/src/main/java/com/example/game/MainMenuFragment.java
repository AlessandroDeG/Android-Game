package com.example.game;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

//Fragment del menu principale con bottoni per cambiare tra i fragment di : gioco,punteggi,salvataggi,impostazione
public class MainMenuFragment extends Fragment implements View.OnClickListener {
    View view;

    CustomButton resumeButton;
    CustomButton playButton;
    CustomButton highScoreButton;
    CustomButton profileButton;
    CustomButton settingsButton;
    CustomButton savedGamesButton;
    TextView titleTextView;

    static Boolean resumeButtonVisible=false;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_menu_layout, container, false);

       playButton = view.findViewById( R.id.playButton);
       highScoreButton = view.findViewById(R.id.highscoreButton );
       profileButton = view.findViewById(R.id.profileButton);
       settingsButton = view.findViewById( R.id.settingsButton);
       resumeButton = view.findViewById( R.id.resumeButton);
        savedGamesButton = view.findViewById( R.id.savedGameButton);
        titleTextView= view.findViewById(R.id.titleTextView);

        if(resumeButtonVisible){
            resumeButton.setVisibility(View.VISIBLE);
        }
        else{
            resumeButton.setVisibility(View.INVISIBLE);
        }

       playButton.setOnClickListener(this);
       highScoreButton.setOnClickListener(this);
       profileButton.setOnClickListener(this);
       settingsButton.setOnClickListener(this);
        resumeButton.setOnClickListener(this);
        savedGamesButton.setOnClickListener(this);


        if(Skins.currentSkin== Skins.SPACE_SKIN) {
            view.setBackgroundResource(R.drawable.spacebackground);
            titleTextView.setTextColor(Player.ALTERNATIVE_SCORE_COLOR);



            playButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            highScoreButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            profileButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            settingsButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            resumeButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            savedGamesButton.setBackgroundResource(R.drawable.spacebuttonbackground);



            playButton.setTextColor(Color.GREEN);
            highScoreButton.setTextColor(Color.GREEN);
            profileButton.setTextColor(Color.GREEN);
            settingsButton.setTextColor(Color.GREEN);
            resumeButton.setTextColor(Color.GREEN);
            savedGamesButton.setTextColor(Color.GREEN);
        }


        return view;
    }



    public void onClick(View view) {
        if (view == view.findViewById(R.id.playButton)) {
            MainActivity.setPlayFragment();
            if(PlayFragment.isCreated) {
                PlayFragment.resetGame();
            }
        }
        if (view == view.findViewById(R.id.highscoreButton)) {
            MainActivity.highscoresFragment.setHighscores(DatabaseHelper.getInstance(getActivity()).getAllHighscores());
            Log.e("MAINMENU","GETTING HIGHSCORES!"+DatabaseHelper.getInstance(getActivity()).getAllHighscores());
            //HighscoresFragment.adapter.notifyDataSetChanged();;
            MainActivity.setHighscoresFragment();
        }
        if (view == view.findViewById(R.id.profileButton)) {

        }
        if (view == view.findViewById(R.id.settingsButton)) {
            MainActivity.setSettingsFragment();
        }
        if (view == view.findViewById(R.id.resumeButton)) {
            MainActivity.setPlayFragment();
        }
        if (view == view.findViewById(R.id.savedGameButton)) {
            MainActivity.savedGamesFragment.setSavedGames(DatabaseHelper.getInstance(getActivity()).getAllSavedGames());
            Log.e("MAINMENU","GETTING SAVEDGAMES!"+DatabaseHelper.getInstance(getActivity()).getAllSavedGames());
            MainActivity.setSavedGamesFragment();
        }




    }

    public static void setResumeButtonVisibility(boolean visibility){
        resumeButtonVisible=visibility;
       // MainActivity.refreshFragment(MainActivity.mainMenuFragment);

        Log.e("RESUMEBUTTON","VISIBILITY"+visibility);
    }

}
