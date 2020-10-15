package com.nikolas.rock_paper_scissors_lizard_spock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.nikolas.rock_paper_scissors_lizard_spock.data.PlayerContract.PlayerEntry;

public class GameOverActivity extends AppCompatActivity implements View.OnClickListener {

    String playerName;
    int playerHighScore = GameActivity.HIGH_SCORE;
    int playerTopLevel = GameActivity.TOP_LEVEL;

    TextView textScore;
    TextView text2;
    TextView gameOverTitle;
    EditText playerNameEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_over);

        textScore = (TextView)findViewById(R.id.score);
        text2 = (TextView)findViewById(R.id.textView2);
        gameOverTitle = (TextView)findViewById(R.id.game_over_title);
        playerNameEdit = (EditText)findViewById(R.id.PlayerName);
        textScore.setText("Score: " + GameActivity.HIGH_SCORE);

        playerNameEdit.setTextColor(Color.LTGRAY);

        final Button RestartButton = (Button)findViewById(R.id.restart);
        final Button MainMenuButton = (Button)findViewById(R.id.mainMenu);
        final Button OKButton = (Button)findViewById(R.id.okButton);
        RestartButton.setOnClickListener(this);
        MainMenuButton.setOnClickListener(this);
        OKButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent i;
        Intent j;
        Intent k;
        i = new Intent(this, GameActivity.class);
        j = new Intent(this, MainActivity.class);
        k = new Intent(this, HighScoresActivity.class);

        switch (view.getId()) {
            case R.id.restart:
                finish();
                startActivity(i);
                break;
            case R.id.mainMenu:
                finish();
                startActivity(j);
                break;
            case R.id.okButton:
                try {
                    saveScore();
                    finish();
                    startActivity(k);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    private void saveScore(){
        playerName = playerNameEdit.getText().toString().trim();
        // Create a ContentValues object, where the column names are the keys and the player result the attributes
        ContentValues values = new ContentValues();
        values.put(PlayerEntry.COLUMN_PLAYER_NAME, playerName);
        values.put(PlayerEntry.COLUMN_HIGH_SCORE, playerHighScore);
        values.put(PlayerEntry.COLUMN_TOP_LEVEL, playerTopLevel);

        /***************************************************************/
        values.put(PlayerEntry.COLUMN_LAST_GAME_SCORE, playerHighScore);    // NOT READY YET
        values.put(PlayerEntry.COLUMN_LAST_GAME_LEVEL, playerTopLevel);     // NOT READY YET
        /***************************************************************/


        // Enter player details to the database by calling the {@link PlayerProvider#insert()} method
        Uri newUri = getContentResolver().insert(PlayerEntry.CONTENT_URI, values);

        // Check if the player successfully inserted to the database
        if (newUri != null) {
            // Insertion was successful
            Toast.makeText(getApplicationContext(), "Thanks, " + playerNameEdit.getText().toString() + "!", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Error player details cannot be saved...", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed(){
    }
}