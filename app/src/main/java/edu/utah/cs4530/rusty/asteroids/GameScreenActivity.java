package edu.utah.cs4530.rusty.asteroids;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rusty on 12/7/2016.
 */
public class GameScreenActivity extends Activity implements GLSurfaceView.Renderer{

    private GLSurfaceView _surfaceView;

    GameModel _gameModel = new GameModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        _surfaceView = new GLSurfaceView(this);
        _surfaceView.setEGLContextClientVersion(2);
        _surfaceView.setEGLConfigChooser(8, 8, 8, 8, 0, 0);
        _surfaceView.setRenderer(this);
        _surfaceView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(_surfaceView);

        _gameModel.setupGame(getResources());
    }

    @Override
    protected void onResume() {
        super.onResume();

        _surfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        _surfaceView.onPause();
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int width, int height) {
        // TODO: glViewport
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        GLES20.glClearColor(0.2f, 0.2f, 0.2f, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        _gameModel.updateGame(getResources());

        Set<Sprite> allSprites = _gameModel.getAllSprites();

        //This is to ensure the background gets drawn before any of the other sprites
        _gameModel.getBackground().draw();

        synchronized (allSprites) {
            for (Sprite sprite : allSprites) {
                sprite.draw();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (_gameModel.getGameOverFlag()) {
                _gameModel.setupGame(getResources());
            }
            else {
                if (touchX > _surfaceView.getWidth() / 2 && touchX < _surfaceView.getWidth() * 0.75f) {
                    _gameModel.setIsBoosting(true);
                } else if (touchX > _surfaceView.getWidth() * 0.75f) {
                    _gameModel.shoot(getResources());
                } else if (touchX < _surfaceView.getWidth() / 2 && touchX > _surfaceView.getWidth() / 4) {
                    _gameModel.setIsRotatingCW(true);
                } else {
                    _gameModel.setIsRotatingCCW(true);
                }
            }
        }
        else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            if (touchX > _surfaceView.getWidth() / 2 && touchX < _surfaceView.getWidth() * 0.75f) {
                _gameModel.setIsBoosting(false);
            }
            else if (touchX > _surfaceView.getWidth() * 0.75f) {
                //do nothing
            }
            else if (touchX < _surfaceView.getWidth() / 2 && touchX > _surfaceView.getWidth() / 4) {
                _gameModel.setIsRotatingCW(false);
            }
            else {
                _gameModel.setIsRotatingCCW(false);
            }
        }
        return true;
    }
}
