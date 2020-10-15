package com.nikolas.rock_paper_scissors_lizard_spock;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener, Animation.AnimationListener {

    private enum GameResult {WIN, LOSE, DRAW}
    private enum PlayerAction {ROCK, PAPER, SCISSORS, LIZARD, SPOCK}

    private int lives;
    private int max;
    private int score;
    private int winsInRow;

    public static int HIGH_SCORE = 0;
    public static int TOP_LEVEL = 1;

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

    private ImageView playerMove;
    private ImageView cpuMove;

    private TextView resultTextView;
    private TextView resultActionView;

    private TextView livesText;
    private TextView maxText;

    private ViewFlipper viewFlipper;

    private Animation rotation;
    private Animation zoom;
    private Animation fadeOut;
    private Animation fadeIn;
    private Animation getReady;
    private Animation nextMove;
    private boolean clickEnable;

    private String animationRunning = "";

    private View computerChoiceBoard;
    private View gameView;
    private View resultBoardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialize buttons animations and views
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

        computerChoiceBoard = findViewById(R.id.computer_choice_board);

        gameView = findViewById(R.id.game_view);

        livesText = findViewById(R.id.lives_text);
        maxText = findViewById(R.id.max_text);

        resultBoardView = findViewById(R.id.result_view);

        resultTextView = findViewById(R.id.result_text_view);
        resultActionView = findViewById(R.id.action_text_view);

        playerMove = findViewById(R.id.player_move);
        cpuMove = findViewById(R.id.cpu_move);

        rotation = AnimationUtils.loadAnimation(this, R.anim.rotation);
        zoom = AnimationUtils.loadAnimation(this, R.anim.zoom_in_out);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        getReady = AnimationUtils.loadAnimation(this, R.anim.get_ready);
        nextMove = AnimationUtils.loadAnimation(this, R.anim.next_move);

        // Animation listener attached to the animations those needs monitoring
        rotation.setAnimationListener(this);
        fadeOut.setAnimationListener(this);
        fadeIn.setAnimationListener(this);

        rockViewHuman.setOnClickListener(this);
        paperViewHuman.setOnClickListener(this);
        scissorsViewHuman.setOnClickListener(this);
        lizardViewHuman.setOnClickListener(this);
        spockViewHuman.setOnClickListener(this);

        prevView.setOnClickListener(this);
        nextView.setOnClickListener(this);

        reset();
    }

    private void reset(){
        lives = 3;
        max = 0;
        winsInRow = 0;
        clickEnable = true;
        init();
    }
    // The next 2 methods are the action board
    @Override
    public void onAnimationStart(Animation animation) {
        if (animation.equals(rotation)){
            animationRunning = "start";
        } else if (animation.equals(fadeIn)){
            animationRunning = "end";
        } else if (animation.equals(fadeOut)){
            animationRunning = "continue";
        }
        Log.i(GameActivity.class.getSimpleName(), animationRunning);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onAnimationEnd(Animation animation) {
        Intent g_o = new Intent(this, GameOverActivity.class);
        switch (animationRunning){
            case "start":
                resumeBattle();
                break;
            case "continue":
                showResult();
                break;
            case "end":
                if (winsInRow == 3){
                    winsInRow = 0;
                    lives += 1;
                }
                if (lives == 0){    // GAME OVER
                    HIGH_SCORE = score;
                    TOP_LEVEL = max;
                    startActivity(g_o);
                    finish();
                }
                init();
                gameView.startAnimation(getReady);
                clickEnable = true;
                break;
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {}

    private void init(){
        livesText.setText("lives:" + lives);
        maxText.setText("max:" + max);

        resultBoardView.setVisibility(View.GONE);
        gameView.setVisibility(View.VISIBLE);

        rockViewComputer.setBackgroundResource(0);
        paperViewComputer.setBackgroundResource(0);
        scissorsViewComputer.setBackgroundResource(0);
        lizardViewComputer.setBackgroundResource(0);
        spockViewComputer.setBackgroundResource(0);

        rockViewHuman.setBackgroundResource(0);
        paperViewHuman.setBackgroundResource(0);
        scissorsViewHuman.setBackgroundResource(0);
        lizardViewHuman.setBackgroundResource(0);
        spockViewHuman.setBackgroundResource(0);

    }
    // When the player chose an action and click it the game starts
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        if (!clickEnable){
            return;
        }
        init();
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
                startBattle();
                break;
            case R.id.paper_img_2:
                humanAction = PlayerAction.PAPER;
                paperViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                startBattle();
                break;
            case R.id.scissors_img_2:
                humanAction = PlayerAction.SCISSORS;
                scissorsViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                startBattle();
                break;
            case R.id.lizard_img_2:
                humanAction = PlayerAction.LIZARD;
                lizardViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                startBattle();
                break;
            case R.id.spock_img_2:
                humanAction = PlayerAction.SPOCK;
                spockViewHuman.setBackground(getDrawable(R.drawable.circle_cyan));
                startBattle();
                break;
        }
    }
    // When player chose an action animation starting...
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startBattle(){
        clickEnable = false;
        computerChoiceBoard.startAnimation(rotation);
        rockViewComputer.startAnimation(zoom);
        paperViewComputer.startAnimation(zoom);
        scissorsViewComputer.startAnimation(zoom);
        lizardViewComputer.startAnimation(zoom);
        spockViewComputer.startAnimation(zoom);
    }
    // Getting things ready on the result screen
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void resumeBattle() {
        computerChoice();
        setResult();
        resultTextView.setText(result.toString());
        resultActionView.setText(toastInfo);
        setImages();
        gameView.startAnimation(fadeOut);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showResult() {
        gameView.setVisibility(View.GONE);
        resultBoardView.setVisibility(View.VISIBLE);
        resultBoardView.startAnimation(fadeIn);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void computerChoice() {
        Random random = new Random();
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
    // Get the result of each turn
    private void setResult(){
        if (humanAction.equals(computerAction)){
            toastInfo = " . . . ";
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
                        toastInfo = "Spock smashes Scissors!!!";
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

    // Set the images on the result screen
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setImages(){
        switch (result){
            case WIN:
                playerMove.setBackground(getDrawable(R.drawable.circle_green));
                cpuMove.setBackground(getDrawable(R.drawable.circle_red));
                max += 1;
                score = score + max + 10;
                winsInRow += 1;
                break;
            case LOSE:
                playerMove.setBackground(getDrawable(R.drawable.circle_red));
                cpuMove.setBackground(getDrawable(R.drawable.circle_green));
                max += 1;
                lives -= 1;
                break;
            case DRAW:
                playerMove.setBackground(getDrawable(R.drawable.circle_no_color));
                cpuMove.setBackground(getDrawable(R.drawable.circle_no_color));
                max += 1;
                break;
        }
        switch (humanAction){
            case ROCK:
                playerMove.setImageDrawable(getDrawable(R.drawable.rock_img));
                break;
            case PAPER:
                playerMove.setImageDrawable(getDrawable(R.drawable.paper_img));
                break;
            case SCISSORS:
                playerMove.setImageDrawable(getDrawable(R.drawable.scissors_img));
                break;
            case LIZARD:
                playerMove.setImageDrawable(getDrawable(R.drawable.lizard_img));
                break;
            case SPOCK:
                playerMove.setImageDrawable(getDrawable(R.drawable.spock_img));
                break;
        }
        switch (computerAction){
            case ROCK:
                cpuMove.setImageDrawable(getDrawable(R.drawable.rock_img));
                break;
            case PAPER:
                cpuMove.setImageDrawable(getDrawable(R.drawable.paper_img));
                break;
            case SCISSORS:
                cpuMove.setImageDrawable(getDrawable(R.drawable.scissors_img));
                break;
            case LIZARD:
                cpuMove.setImageDrawable(getDrawable(R.drawable.lizard_img));
                break;
            case SPOCK:
                cpuMove.setImageDrawable(getDrawable(R.drawable.spock_img));
                break;
        }
    }

    @Override
    public void onBackPressed(){
    }
}