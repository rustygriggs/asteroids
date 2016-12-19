package edu.utah.cs4530.rusty.asteroids;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Rusty on 12/7/2016.
 */
public class GameScreenActivity extends Activity implements GLSurfaceView.Renderer{

    List<Sprite> _sprites = new ArrayList<>();
    private GLSurfaceView _surfaceView;

    GameModel _gameModel = new GameModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        _surfaceView = new GLSurfaceView(this);
        _surfaceView.setEGLContextClientVersion(2);
        _surfaceView.setEGLConfigChooser(8, 8, 8, 8, 0, 0);
//        super.setEGLConfigChooser(8,8,8,8,16,0);
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

        // TODO: Do this in an animator or timer threaded
        synchronized (_gameModel.getAllSprites()) {
            _gameModel.updateGame(getResources());
        }
        Set<Sprite> allSprites = _gameModel.getAllSprites();

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
        Sprite ship = _gameModel.getShip();

        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            if (touchX > _surfaceView.getWidth() / 2 && touchX < _surfaceView.getWidth() * 0.75f) {
                Log.i("Boost", "The ship is being boosted");
                _gameModel.applyThrust(getResources());
                //TODO: set a flag that will be set when pressed down and unset when lifted

            }
            else if (touchX > _surfaceView.getWidth() * 0.75f) {
                Log.i("Shoot", "The ship's cannons are being fired");
                _gameModel.shoot(getResources());
            }
            else if (touchX < _surfaceView.getWidth() / 2 && touchX > _surfaceView.getWidth() / 4) {
                ship.setRotation(ship.getRotation() - 30);
                Log.i("Rotate CW", "The ship is being rotated clockwise. Angle is: " + ship.getRotation());
            }
            else {
                ship.setRotation(ship.getRotation() + 30);
                Log.i("Rotate CCW", "The ship is being rotated counterclockwise. Angle is: " + ship.getRotation());
            }
        }
        return true;
    }
}
