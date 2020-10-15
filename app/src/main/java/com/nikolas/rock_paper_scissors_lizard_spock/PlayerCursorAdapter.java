package com.nikolas.rock_paper_scissors_lizard_spock;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.nikolas.rock_paper_scissors_lizard_spock.data.PlayerContract.PlayerEntry;
/**
 * Created by Nikolas on 29/5/2016.
 */
public class PlayerCursorAdapter extends CursorAdapter {

    public PlayerCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View listItemView, Context context, Cursor cursor) {


        // Field of the listItemView
        TextView nameText = (TextView) listItemView.findViewById(R.id.name_text);
        TextView scoreText = (TextView)listItemView.findViewById(R.id.score_text);

        // Get pet properties from the database
        String playerName = cursor.getString(cursor.getColumnIndex(PlayerEntry.COLUMN_PLAYER_NAME));
        int highScore = cursor.getInt(cursor.getColumnIndex(PlayerEntry.COLUMN_HIGH_SCORE));
        int topLevel = cursor.getInt(cursor.getColumnIndex(PlayerEntry.COLUMN_TOP_LEVEL));
        int lastScore = cursor.getInt(cursor.getColumnIndex(PlayerEntry.COLUMN_LAST_GAME_SCORE));
        int lastLevel = cursor.getInt(cursor.getColumnIndex(PlayerEntry.COLUMN_LAST_GAME_LEVEL));

        // Fill the listItemView with the values
        nameText.setText(playerName);
        scoreText.setText(String.valueOf(highScore));
    }
}