package com.example.game;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.game.Highscore;
import com.example.game.R;

import java.util.ArrayList;


//porzione di activity che visualizza i punteggi piu alti
public class HighscoresFragment extends Fragment implements View.OnClickListener {

    ListView listView;
   ArrayAdapter<Highscore> adapter=null;
   ArrayList<Highscore>  highscores = null;

   Button mainmenuButton;
   TextView titleTextView;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.highscores_layout, container, false);

        listView = view.findViewById(R.id.highscoresListView);
        mainmenuButton = view.findViewById(R.id.mainMenuButton);
        mainmenuButton.setOnClickListener(this);

        titleTextView = view.findViewById(R.id.titleTextView);
/*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long itemId) {
                onLongClick(position);
                return true;
            }
        });
        */


        if (adapter != null){
            Log.e("HIGHSCOREFRAGMENT","SETTING ADAPTER NOT NULL");
            if(Skins.currentSkin==Skins.SPACE_SKIN) {
                adapter = new ArrayAdapter<Highscore>(MainActivity.context, R.layout.simple_list_item_1_greentext, highscores);
            }
            else{
                adapter = new ArrayAdapter<Highscore>(MainActivity.context, R.layout.simple_list_item_1_blacktext, highscores);
            }
            listView.setAdapter(adapter);
         }
         else{
            highscores= DatabaseHelper.getInstance(MainActivity.context).getAllHighscores();
            if(Skins.currentSkin==Skins.SPACE_SKIN) {
                adapter = new ArrayAdapter<Highscore>(MainActivity.context, R.layout.simple_list_item_1_greentext, highscores);
            }
            else{
                adapter = new ArrayAdapter<Highscore>(MainActivity.context, android.R.layout.simple_list_item_1, highscores);
            }
            listView.setAdapter(adapter);
        }

        if(Skins.currentSkin== Skins.SPACE_SKIN) {
            view.setBackgroundResource(R.drawable.spacebackground);
            titleTextView.setTextColor(Player.ALTERNATIVE_SCORE_COLOR);

            mainmenuButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            mainmenuButton.setTextColor(Color.GREEN);

        }


        return view;
    }

    public void setHighscores(ArrayList<Highscore> highscores){
        this.highscores=highscores;

    }
    public ArrayList<Highscore> getHighscores(){
        return highscores;
    }

    public void setAdapter(ArrayAdapter<Highscore> adapter){
        this.adapter = adapter;
        if (listView!= null) {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        if (v== v.findViewById(R.id.mainMenuButton)){
            MainActivity.setMainMenuFragment();
        }

    }

}
