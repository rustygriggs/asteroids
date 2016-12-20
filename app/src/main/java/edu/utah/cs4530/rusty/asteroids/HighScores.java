package edu.utah.cs4530.rusty.asteroids;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rusty on 12/19/2016.
 */
public class HighScores implements Serializable {
    private static HighScores _instance;

    /**
     * Used to get the one instance of this class.
     * @return the single instance of HighScores that has been instantiated.
     */
    public static HighScores getInstance() {
        if (_instance == null) {
            _instance = new HighScores();
        }
        return _instance;
    }

    /**
     * setter for the HighScores.
     * @param highScores
     */
    public static void setInstance(HighScores highScores) {
        _instance = highScores;
    }

    List<Integer> highScores = null;

    HighScores() {
        highScores = new ArrayList<>();
    }

    public void addScore(int score) {
        highScores.add(score);
        Collections.sort(highScores);
    }

    public List<Integer> getHighScores() {
        return highScores;
    }
}
