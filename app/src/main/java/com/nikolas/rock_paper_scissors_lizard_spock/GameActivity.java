package com.nikolas.rock_paper_scissors_lizard_spock;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private enum GameResult {WIN, LOSE, DRAW}
    private enum PlayerAction {ROCK, PAPER, SCISSORS, LIZARD, SPOCK}

    private PlayerAction humanAction;
    private PlayerAction computerAction;
    private GameResult result;
    private String toastResult;
    private String toastInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.prev_button:
                break;
            case R.id.next_button:
                break;
            case R.id.rock_img_2:
                humanAction = PlayerAction.ROCK;
                break;
            case R.id.paper_img_2:
                humanAction = PlayerAction.PAPER;
                break;
            case R.id.scissors_img_2:
                humanAction = PlayerAction.SCISSORS;
                break;
            case R.id.lizard_img_2:
                humanAction = PlayerAction.LIZARD;
                break;
            case R.id.spock_img_2:
                humanAction = PlayerAction.SPOCK;
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