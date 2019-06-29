package com.ritik.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {ONE, TWO, NO}
    private Player currentPlayer = Player.ONE;

    private Player[] playerChoices = new Player[9];
    private int [][] winCombos = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    private boolean gameOver = false;

    private Button btnReset;
    private GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridLayout = findViewById(R.id.gridLayout);
        btnReset = findViewById(R.id.btnReset);

        for(int i = 0; i < 9; ++i) {
            playerChoices[i] = Player.NO;
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    public void imageViewIsTapped(View imageView) {
        ImageView tappedImageView = (ImageView) imageView;
        int tiTag = Integer.parseInt(tappedImageView.getTag().toString());

        if(playerChoices[tiTag] == Player.NO && !gameOver) {
            playerChoices[tiTag] = currentPlayer;
            if(currentPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currentPlayer = Player.TWO;
            }
            else {
                tappedImageView.setImageResource(R.drawable.tiger);
                currentPlayer = Player.ONE;
            }

            tappedImageView.animate().alpha(1).rotationBy(3600).setDuration(1000);

            checkGameOver();
        }
    }

    private void checkGameOver() {
        for(int[] winColumns : winCombos) {
            if(playerChoices[winColumns[0]] != Player.NO && playerChoices[winColumns[0]] == playerChoices[winColumns[1]] && playerChoices[winColumns[0]] == playerChoices[winColumns[2]]) {
                String winner = "Player ";
                gameOver = true;
                if(currentPlayer == Player.ONE) {
                    winner += "Two";
                }
                else {
                    winner += "One";
                }
                Toast.makeText(MainActivity.this, winner + " has won", Toast.LENGTH_SHORT).show();
                btnReset.setVisibility(View.VISIBLE);
            }
        }

        for(int i = 0; i < playerChoices.length; ++i) {
            if(playerChoices[i] == Player.NO)
                return;
        }
        gameOver = true;
        Toast.makeText(MainActivity.this,  "The Game is Tied", Toast.LENGTH_SHORT).show();
        btnReset.setVisibility(View.VISIBLE);
    }

    private void resetGame() {
        for(int i = 0; i < gridLayout.getChildCount(); ++i) {
            playerChoices[i] = Player.NO;

            ImageView image = (ImageView) gridLayout.getChildAt(i);
            image.setAlpha(0.2f);
            image.setImageDrawable(null);
        }
        gameOver = false;
        currentPlayer = Player.ONE;
        btnReset.setVisibility(View.GONE);
    }
}
