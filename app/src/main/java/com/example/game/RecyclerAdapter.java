package com.example.game;

import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.example.game.SavedGamesFragment.savedGames;


//adapter che serve a visualizzare i salvataggi nel fragment SavedGamesFragment;
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.SavedGameHolder> {
    private static ArrayList<SavedGame> mSavedGames;

    public RecyclerAdapter(ArrayList<SavedGame> items) {
        mSavedGames = items;
    }

    @NonNull
    @Override
    public SavedGameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        SavedGameHolder itemHolder = new SavedGameHolder(inflatedView);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SavedGameHolder SavedGameHolder, int position) {
        SavedGame SavedGame = mSavedGames.get(position);
        if(SavedGame.getDeleteOption()){
            SavedGameHolder.showDeleteButton();
        }
        else{
            SavedGameHolder.hideDeleteButton();
        }
        SavedGameHolder.bindSavedGame(SavedGame);
    }

    public int getItemCount() {
        return mSavedGames.size();
    }

    // 1 - Made the class extend RecyclerView.ViewHolder, allowing it to be used as a ViewHolder for the adapter.
    public static class SavedGameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // 2 - Add a list of references to the lifecycle of the object to allow the ViewHolder
        // to hang on to your ImageView and TextView, so it doesn’t have to repeatedly query the same information.
        private ImageView mItemImage;
        private TextView mItemDate;
        private TextView mItemDescription;
        private SavedGame mSavedGame;
        private ImageButton deleteButton;
        public boolean deleteVisible=false;

        // 3 - Set up a constructor to handle grabbing references to various subviews of the layout.
        public SavedGameHolder(View v) {
            super(v);
            mItemImage = (ImageView) v.findViewById(R.id.item_image);
            mItemDate = (TextView) v.findViewById(R.id.item_date);
            mItemDescription = (TextView) v.findViewById(R.id.item_description);
            deleteButton = v.findViewById(R.id.deleteButton);
            v.setOnClickListener(this);
        }
        // ...
        // 5 - This binds the SavedGame to the SavedGameHolder,
        // giving your item the data it needs to work out what it should show.
        public void bindSavedGame(SavedGame item) {
            mSavedGame = item;
            mItemImage.setImageBitmap(item.getBitmap());
            String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(item.getDate().getTime());
            mItemDate.setText(currentDate);
            mItemDescription.setText(item.getPlayer().name+"\n"+item.getPlayer().score);
            if(Skins.currentSkin==Skins.SPACE_SKIN){
                mItemDate.setTextColor(Color.GREEN);
                mItemDescription.setTextColor(Color.GREEN);
            }
        }

        //quando swipe sx
        public void showDeleteButton(){
            Log.e("SHOW DELETE BUTTON",Integer.toString(getAdapterPosition()));
            // delete the item from the DB
            //dbHelper.deleteItem(item);
            deleteButton.setVisibility(View.VISIBLE);
        }


        //quando swipe a dx o delete
        public void hideDeleteButton(){
            Log.e("HIDE DELETE BUTTON",Integer.toString(getAdapterPosition()));
            deleteButton.setVisibility(View.INVISIBLE);
        }
        public ImageButton getDeleteButton(){
            return deleteButton;
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
            Log.e("LONG CLICK","LOADING GAME"+Integer.toString(position));
            PlayFragment.loadGame(savedGames.get(position));
            Log.e("click itemholder : ",Integer.toString(position));
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(SavedGamesFragment.context,"LOADING GAME:\n"+savedGames.get(position).toString(),Toast.LENGTH_LONG).show();
                    //postInvalidate();
                }
            });
        }

        /*
            public boolean onLongClick(View v) {
                int position = ((RecyclerView.ViewHolder)v.getParent()).getAdapterPosition();
                Log.e("LONG CLICK","LOADING GAME"+Integer.toString(position));
                PlayFragment.loadGame(savedGames.get(position));
                return true;
            }
        */
    }
}