package com.tech104.isreal.connect3;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // player 1 = yellow, 2 = red
    int player = 1;

    // 0 mean the counter has not been clicked
    int[] gameState = {0, 0, 0, 0, 0, 0, 0, 0, 0};

    // Winning position
    int[][] winningPositions = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8}, {0,4,8}, {2,4,6}};

    // Won message display Linear Layout
    LinearLayout layoutLinear;

    // Wom message display Text
    TextView wonMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dropInFunction(View view) {
        ImageView counter = (ImageView) view;
        int counterTag = Integer.parseInt(view.getTag().toString());

        if( gameState[counterTag] == 0 ) {
            counter.setTranslationY(-1000f);
            gameState[counterTag] = player;
            this.checkPlayer(counter);
            counter.animate().translationYBy(1000f).setDuration(200);
        } else {
            Toast.makeText(this, "Played before!", Toast.LENGTH_SHORT).show();
        }

        for(int[] winningPosition : winningPositions) {
            if( gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 0) {
                String playerName = (gameState[winningPosition[0]] == 1 ? "Player Yellow" : "Player Red");
                this.displayWonMessage(playerName);
                this.disableAllCounter();
            } else {
                // Check if game is over
                boolean isGameOver = true;
                for(int countGameState : gameState) {
                    if(countGameState == 0) { isGameOver = false; }
                }
                if( isGameOver ) {
                    this.displayGameOverMessage();
                    this.disableAllCounter();
                }
            }
        }
    }

    private void displayGameOverMessage() {
        layoutLinear = (LinearLayout) findViewById(R.id.wonMessageLinearLayout);
        layoutLinear.setVisibility(View.VISIBLE);
        wonMessageTextView = (TextView) findViewById(R.id.wonMessageTextView);
        String message = "Game was drawn, no winner";
        wonMessageTextView.setText(message);
    }

    public void checkPlayer(ImageView c) {
        if( player == 1 ) {
            c.setImageResource(R.drawable.yellow);
            player = 2;
        } else {
            c.setImageResource(R.drawable.red);
            player = 1;
        }
    }

    public void disableAllCounter() {
        GridLayout counterGridLayout = (GridLayout) findViewById(R.id.counterGridLayout);
        for(int i=0; i<counterGridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) counterGridLayout.getChildAt(i);
            counter.animate().alpha(0.2f);
            counter.setEnabled(false);
        }
    }

    public void displayWonMessage(String p) {
        layoutLinear = (LinearLayout) findViewById(R.id.wonMessageLinearLayout);
        layoutLinear.setVisibility(View.VISIBLE);
        wonMessageTextView = (TextView) findViewById(R.id.wonMessageTextView);
        String message = p + " Won the game";
        wonMessageTextView.setText(message);
    }

    public void playAgainFunction(View view) {
        // Reset the game states
        for(int i=0; i<gameState.length; i++) {
            gameState[i] = 0;
        }
        // Reset the image counter
        GridLayout counterGridLayout = (GridLayout) findViewById(R.id.counterGridLayout);
        for(int i=0; i<counterGridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) counterGridLayout.getChildAt(i);
            counter.animate().alpha(1);
            counter.setEnabled(true);
            counter.setImageResource(0);
        }
        // Hide the won Message Linear Layout
        layoutLinear.setVisibility(View.GONE);
        wonMessageTextView.setText("");
    }
}
