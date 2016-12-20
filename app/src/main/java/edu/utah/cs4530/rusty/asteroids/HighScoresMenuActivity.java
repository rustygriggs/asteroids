package edu.utah.cs4530.rusty.asteroids;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

//        HighScores.setInstance(loadFromFile("asteroids_high_score"));

        //This doesn't really work. I sure tried but oh well.
        List<Integer> highScores = HighScores.getInstance().getHighScores();
        if (highScores.size() > 0) {
            for (int i = highScores.size() - 1; i > highScores.size() - 11 && i >= 0; i--) {
                TextView highScoreView = new TextView(this);
                highScoreView.setText(highScores.get(i));
                rootLayout.addView(highScoreView, new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
            }
        }

        rootLayout.addView(titleView, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));

        setContentView(rootLayout);
    }

    /**
     * This method will save the Gallery to disk using a Serializable fileStream
     */
    public void saveToFile() {
        try {
            FileOutputStream fos = openFileOutput("asteroids_high_score", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(HighScores.getInstance());
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * This method extracts a Gallery from a file saved to disk.
     * @param paintPaletteFileName is the file name where the Gallery is saved.
     * @return the Gallery that was previously saved to disk.
     */
    private HighScores loadFromFile(String paintPaletteFileName) {
        HighScores highScores = null;
        try {
            FileInputStream fis = openFileInput(paintPaletteFileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            highScores = (HighScores) ois.readObject();
            ois.close();
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return highScores;
    }

}
