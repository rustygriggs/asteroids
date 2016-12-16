package edu.utah.cs4530.rusty.asteroids;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rusty on 12/7/2016.
 */
public class HighScoresMenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);

        TextView titleView = new TextView(this);
        titleView.setText("High Scores");
        titleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        TextView highScore1 = new TextView(this);
        TextView highScore2 = new TextView(this);
        TextView highScore3 = new TextView(this);
        TextView highScore4 = new TextView(this);
        TextView highScore5 = new TextView(this);
        TextView highScore6 = new TextView(this);
        TextView highScore7 = new TextView(this);
        TextView highScore8 = new TextView(this);
        TextView highScore9 = new TextView(this);
        TextView highScore10 = new TextView(this);

        highScore1.setText("High Score 1");
        highScore2.setText("High Score 2");
        highScore3.setText("High Score 3");
        highScore4.setText("High Score 4");
        highScore5.setText("High Score 5");
        highScore6.setText("High Score 6");
        highScore7.setText("High Score 7");
        highScore8.setText("High Score 8");
        highScore9.setText("High Score 9");
        highScore10.setText("High Score 10");

        rootLayout.addView(titleView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore1, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore2, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore3, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore4, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore5, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore6, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore7, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore8, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore9, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
        rootLayout.addView(highScore10, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        setContentView(rootLayout);
    }
}
