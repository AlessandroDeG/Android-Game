package com.example.game;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
//fragment per visualizzare i salvataggi
// gestisce l'interazione tra l'utente e i salvataggi:
//click = carica gioco;
//swipe left= mostra cestino per cancellare;
public class SavedGamesFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    static RecyclerAdapter adapter;
    static ArrayList<SavedGame> savedGames;
    static Context context;

   /*ListView listView;
    ArrayAdapter<SavedGame> adapter=null;
    ArrayList<SavedGame> savedGames = null;
    */

    Button mainmenuButton;
    TextView titleTextView;


    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.saved_games_layout, container, false);

        context = getActivity();

        //listView = view.findViewById(R.id.savedGamesListView);

        recyclerView = view.findViewById(R.id.savedGamesRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        //savedGames = new ArrayList<SavedGame>();
        savedGames= DatabaseHelper.getInstance(PlayFragment.context).getAllSavedGames();
        adapter = new RecyclerAdapter(savedGames);
        recyclerView.setAdapter(adapter);
        setRecyclerViewItemTouchListener();

        mainmenuButton = view.findViewById(R.id.mainMenuButton);
        mainmenuButton.setOnClickListener(this);

        titleTextView = view.findViewById(R.id.titleTextView);


    /*
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, final long itemId) {
                onLongClick(position);
                return true;
            }
        });



        if (adapter != null){
            Log.e("SAVEDGAMESFRAGMENT","SETTING ADAPTER NOT NULL");
            listView.setAdapter(adapter);
        }
        */


        if(Skins.currentSkin== Skins.SPACE_SKIN) {
            view.setBackgroundResource(R.drawable.spacebackground);
            titleTextView.setTextColor(Player.ALTERNATIVE_SCORE_COLOR);

            mainmenuButton.setBackgroundResource(R.drawable.spacebuttonbackground);
            mainmenuButton.setTextColor(Color.GREEN);

        }


        return view;
    }

    public static void addNewItem(PlayerData playerData, List<Obstacle> obstacles, List<Shot> shots, List<Missile> missiles, BossData bossData) {
       if(savedGames==null){
           savedGames= DatabaseHelper.getInstance(PlayFragment.context).getAllSavedGames();
           adapter = new RecyclerAdapter(savedGames);
       }

        SavedGame item = new SavedGame(playerData,obstacles,shots,missiles,bossData,PlayFragment.skins ? Skins.currentSkin : null);
        // add to the ArrayList and notify the adapter
        savedGames.add(item);
        adapter.notifyItemInserted(savedGames.size());
        // add to the DB and set the item idx
        long idx = DatabaseHelper.getInstance(PlayFragment.context).insertItem(item);
         item.setId(idx);
    }

    public void setSavedGames(ArrayList<SavedGame> newSavedGames){
        savedGames=newSavedGames;

    }
    public ArrayList<SavedGame> getSavedGames(){
        return savedGames;
    }
/*
    public void setAdapter(ArrayAdapter<SavedGame> adapter){
        this.adapter = adapter;
        if (listView!= null) {
            listView.setAdapter(adapter);
        }
    */

    @Override
    public void onClick(View v) {
        if (v== v.findViewById(R.id.mainMenuButton)){
            MainActivity.setMainMenuFragment();
        }

    }
    /*
        private void onLongClick(final int position) {
            // get the item delected for delation
            SavedGame item = (SavedGame) listView.getItemAtPosition(position);
            Toast.makeText(getActivity(), "LOADING SAVED GAME " + position + " " + item.toString(), Toast.LENGTH_LONG).show();
            // delete the item from the DB
           // DatabaseHelper.getInstance(getActivity()).deleteItem(item);
            // delete the item from the listview data structure
            //savedGames.remove(position);
            // notify the data set has changed to the listview adapter
            //adapter.notifyDataSetChanged();
        }
        */
    private void setRecyclerViewItemTouchListener() {
        // 1 - You create the callback and tell it what events to listen for.
        // It takes two parameters, one for drag directions and one for swipe directions, but
        // you’re only interested in swipe, so you pass 0 to inform the callback not to respond to drag events.
        ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {


            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder viewHolder1) {
                // 2 - You return false in onMove because you don’t want to perform any special behavior here.
                return false;
            }
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // 3 - onSwiped is called when you swipe an item in the direction specified in the
                // ItemTouchHelper. Here, you request the viewHolder parameter passed for the
                // position of the item view, then you remove that item from your list of photos.
                // Finally, you inform the RecyclerView adapter that an item has been removed at a
                // specific position.



                if(swipeDir==ItemTouchHelper.LEFT) {

                    ((RecyclerAdapter.SavedGameHolder) viewHolder).getDeleteButton().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // savedGames.get(position).setDeleteOption(false); //in caso venga recycled
                            // recyclerView.getAdapter().notifyItemChanged(position);
                            int position = viewHolder.getAdapterPosition();
                            DatabaseHelper.getInstance(PlayFragment.context).deleteSavedGame(savedGames.get(position));
                            savedGames.remove(position);
                            recyclerView.getAdapter().notifyItemRemoved(position);
                            Log.e("DELETE BUTTON CLICK",Integer.toString(position));
                        }
                    });
                    int position = viewHolder.getAdapterPosition();
                    savedGames.get(position).setDeleteOption(true);
                    recyclerView.getAdapter().notifyDataSetChanged();

                }
                if(swipeDir==ItemTouchHelper.RIGHT) {
                    int position = viewHolder.getAdapterPosition();
                    savedGames.get(position).setDeleteOption(false);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }

                /*
                SavedGame item = savedGames.remove(position);
                // delete the item from the DB
                dbHelper.deleteItem(item);
                recyclerView.getAdapter().notifyItemRemoved(position);
                */
            }
        };
        // 4 - You initialize the ItemTouchHelper with the callback behavior you defined, and
        // then attach it to the RecyclerView.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

}