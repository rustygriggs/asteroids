package edu.utah.cs4530.rusty.asteroids;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        rootLayout.setBackgroundColor(Color.BLACK);

        TextView welcome = new TextView(this);
        welcome.setText("Welcome to Asteroids");
        welcome.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        welcome.setBackgroundColor(Color.DKGRAY);
        welcome.setTextColor(Color.RED);
        welcome.setTextSize(48);

        Button startGameButton = new Button(this);
        startGameButton.setText("Start Game");

        final Button highScoresButton = new Button(this);
        highScoresButton.setText("High Scores");

        rootLayout.addView(welcome,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(startGameButton,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScoresButton,
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        setContentView(rootLayout);

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startGameIntent = new Intent();
                startGameIntent.setClass(MainMenuActivity.this, GameScreenActivity.class);
                startActivity(startGameIntent);
            }
        });

        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent highScoresIntent = new Intent();
                highScoresIntent.setClass(MainMenuActivity.this, HighScoresMenuActivity.class);
                startActivity(highScoresIntent);
            }
        });
    }
}
