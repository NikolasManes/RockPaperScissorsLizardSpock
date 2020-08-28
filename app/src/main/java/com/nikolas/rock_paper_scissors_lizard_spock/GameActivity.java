package com.nikolas.rock_paper_scissors_lizard_spock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.transition.Scene;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    private enum GameResult {WIN, LOSE, DRAW}
    private enum PlayerAction {ROCK, PAPER, SCISSORS, LIZARD, SPOCK}

    private PlayerAction humanAction;
    private PlayerAction computerAction;
    private GameResult result;
    private String toastInfo;

    private ImageView rockViewComputer;
    private ImageView paperViewComputer;
    private ImageView scissorsViewComputer;
    private ImageView lizardViewComputer;
    private ImageView spockViewComputer;

    private ImageView rockViewHuman;
    private ImageView paperViewHuman;
    private ImageView scissorsViewHuman;
    private ImageView lizardViewHuman;
    private ImageView spockViewHuman;

    private ImageView prevView;
    private ImageView nextView;

    private ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize "buttons" etc
        viewFlipper = findViewById(R.id.view_flipper);

        rockViewComputer = findViewById(R.id.rock_img_1);
        paperViewComputer = findViewById(R.id.paper_img_1);
        scissorsViewComputer = findViewById(R.id.scissors_img_1);
        lizardViewComputer = findViewById(R.id.lizard_img_1);
        spockViewComputer = findViewById(R.id.spock_img_1);

        rockViewHuman = findViewById(R.id.rock_img_2);
        paperViewHuman = findViewById(R.id.paper_img_2);
        scissorsViewHuman = findViewById(R.id.scissors_img_2);
        lizardViewHuman = findViewById(R.id.lizard_img_2);
        spockViewHuman = findViewById(R.id.spock_img_2);

        prevView = findViewById(R.id.prev_button);
        nextView = findViewById(R.id.next_button);

        rockViewHuman.setOnClickListener(this);
        paperViewHuman.setOnClickListener(this);
        scissorsViewHuman.setOnClickListener(this);
        lizardViewHuman.setOnClickListener(this);
        spockViewHuman.setOnClickListener(this);

        prevView.setOnClickListener(this);
        nextView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.prev_button:
                viewFlipper.showPrevious();
                break;
            case R.id.next_button:
                viewFlipper.showNext();
                break;
            case R.id.rock_img_2:
                humanAction = PlayerAction.ROCK;
                rockViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                computerChoice();
                break;
            case R.id.paper_img_2:
                humanAction = PlayerAction.PAPER;
                paperViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                computerChoice();
                break;
            case R.id.scissors_img_2:
                humanAction = PlayerAction.SCISSORS;
                scissorsViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                computerChoice();
                break;
            case R.id.lizard_img_2:
                humanAction = PlayerAction.LIZARD;
                lizardViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                computerChoice();
                break;
            case R.id.spock_img_2:
                humanAction = PlayerAction.SPOCK;
                spockViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                computerChoice();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void runAnimation(){

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void computerChoice() {
        Random random = new Random();
        runAnimation();
        switch (random.nextInt(5)+1){
            case 1:
                rockViewComputer.setBackground(getDrawable(R.drawable.circle_cyan));
                computerAction = PlayerAction.ROCK;
                break;
            case 2:
                paperViewComputer.setBackground(getDrawable(R.drawable.circle_cyan));
                computerAction = PlayerAction.PAPER;
                break;
            case 3:
                scissorsViewComputer.setBackground(getDrawable(R.drawable.circle_cyan));
                computerAction = PlayerAction.SCISSORS;
                break;
            case 4:
                lizardViewComputer.setBackground(getDrawable(R.drawable.circle_cyan));
                computerAction = PlayerAction.LIZARD;
                break;
            case 5:
                spockViewComputer.setBackground(getDrawable(R.drawable.circle_cyan));
                computerAction = PlayerAction.SPOCK;
                break;
        }

    }

    private void setResult(){
        if (humanAction.equals(computerAction)){
            result = GameResult.DRAW;
            return;
        }
        switch (humanAction){
            case ROCK:
                switch (computerAction){
                    case PAPER:
                        toastInfo = "Paper covers Rock!!!";
                        result = GameResult.LOSE;
                        break;
                    case SCISSORS:
                        toastInfo = "Rock crushes Scissors!!!";
                        result = GameResult.WIN;
                        break;
                    case LIZARD:
                        toastInfo = "Rock crushes Lizard!!!";
                        result = GameResult.WIN;
                        break;
                    case SPOCK:
                        toastInfo = "Spock vaporizes Rock!!!";
                        result = GameResult.LOSE;
                        break;
                }
                break;
            case PAPER:
                switch (computerAction){
                    case ROCK:
                        toastInfo = "Paper covers Rock!!!";
                        result = GameResult.WIN;
                        break;
                    case SCISSORS:
                        toastInfo = "Scissors cuts Paper!!!";
                        result = GameResult.LOSE;
                        break;
                    case LIZARD:
                        toastInfo = "Lizard eats Paper!!!";
                        result = GameResult.LOSE;
                        break;
                    case SPOCK:
                        toastInfo = "Paper disproves Spock!!!";
                        result = GameResult.WIN;
                        break;
                }
                break;
            case SCISSORS:
                switch (computerAction){
                    case ROCK:
                        toastInfo = "Rock crushes scissors!!!";
                        result = GameResult.LOSE;
                        break;
                    case PAPER:
                        toastInfo = "Scissors cuts Paper!!!";
                        result = GameResult.WIN;
                        break;
                    case LIZARD:
                        toastInfo = "Scissors decapitates Lizard!!!";
                        result = GameResult.WIN;
                        break;
                    case SPOCK:
                        toastInfo = "Spock smashes Scissors!!!";
                        result = GameResult.LOSE;
                        break;
                }
                break;
            case LIZARD:
                switch (computerAction){
                    case ROCK:
                        toastInfo = "Rock crushes lizard!!!";
                        result = GameResult.LOSE;
                        break;
                    case PAPER:
                        toastInfo = "Lizard eats Paper!!!";
                        result = GameResult.WIN;
                        break;
                    case SCISSORS:
                        toastInfo = "Scissors decapitates Lizard!!!";
                        result = GameResult.LOSE;
                        break;
                    case SPOCK:
                        toastInfo = "Lizard poisons Spock!!!";
                        result = GameResult.WIN;
                        break;
                }
                break;
            case SPOCK:
                switch (computerAction){
                    case ROCK:
                        toastInfo = "Spock vaporizes rock!!!";
                        result = GameResult.WIN;
                        break;
                    case PAPER:
                        toastInfo = "Paper disproves Spock!!!";
                        result = GameResult.LOSE;
                        break;
                    case SCISSORS:
                        result = GameResult.WIN;
                        break;
                    case LIZARD:
                        toastInfo = "Lizard poisons Spock!!!";
                        result = GameResult.LOSE;
                        break;
                }
                break;
        }
    }
}