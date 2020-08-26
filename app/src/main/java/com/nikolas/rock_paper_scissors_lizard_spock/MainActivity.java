package com.nikolas.rock_paper_scissors_lizard_spock;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize buttons
        Button startGameButton = findViewById(R.id.StartGameButton);
        Button highScoresButton = findViewById(R.id.HighScoresButton);
        Button quitButton = findViewById(R.id.QuitButton);

        // Attach the click listener to the buttons
        startGameButton.setOnClickListener(this);
        highScoresButton.setOnClickListener(this);
        quitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        // Intents to start Game and HighScores Activities
        Intent g_a = new Intent(this, GameActivity.class);
        Intent h_a = new Intent(this, HighScoresActivity.class);
        // Manage clicks
        switch (view.getId()){
            case R.id.StartGameButton:
                startActivity(g_a);     // start next activity
                finish();               // end current activity
                break;
            case R.id.HighScoresButton:
                startActivity(h_a);
                finish();
                break;
            case R.id.QuitButton:
                finish();
                break;
        }
    }
    // No action when back button is pressed
    @Override
    public void onBackPressed(){
    }
}