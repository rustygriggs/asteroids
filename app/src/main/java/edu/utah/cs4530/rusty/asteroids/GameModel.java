package edu.utah.cs4530.rusty.asteroids;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by Rusty on 12/14/2016.
 */
public class GameModel implements Serializable {
    private static final float BIG_ASTEROID_DIAMETER = 0.5f;
    private static final float MEDIUM_ASTEROID_DIAMETER = 0.3f;
    private static final float SMALL_ASTEROID_RADIUS = 0.1f;
    private int asteroidCount;
    private int lifeCount;

    private Set<Sprite> _asteroids = new HashSet<>();
    private Set<Sprite> _bullets = new HashSet<>();
    private Sprite _ship = new Sprite();
    private Set<Sprite> _allSprites = new HashSet<>(); //this is for the activity to draw all the sprites.
    private Random random = new Random();
    private Sprite _life1;
    private Sprite _life2;
    private Sprite _life3;
    private Sprite _gameOver = new Sprite();
    private boolean _gameOverFlag = false;
    private boolean _isBoosting = false;
    private boolean _isRotatingCW = false;
    private boolean _isRotatingCCW = false;
    private boolean _setShipThrusters = false;

    public void setIsRotatingCW(boolean isRotatingCW) {
        _isRotatingCW = isRotatingCW;
    }
    public boolean getIsRotatingCW(boolean isRotatingCW) {
        return _isRotatingCW;
    }
    public void setIsRotatingCCW(boolean isRotatingCCW) {
        _isRotatingCCW = isRotatingCCW;
    }
    public boolean getIsRotatingCCW(boolean isRotatingCCW) {
        return _isRotatingCCW;
    }

    public void setIsBoosting(boolean isBoosting) {
        _isBoosting = isBoosting;
    }

    public boolean getIsBoosting() {
        return _isBoosting;
    }

    public void setGameOverFlag(boolean flag) {
        _gameOverFlag = flag;
    }

    public boolean getGameOverFlag() {
        return _gameOverFlag;
    }

    public void setAsteroids (Set<Sprite> asteroids) {
        _asteroids = asteroids;
    }

    public Set<Sprite> getAsteroids() {
        return _asteroids;
    }

    public void setBullets(Set<Sprite> bullets) {
        _bullets = bullets;
    }

    public Set<Sprite> getBullets() {
        return _bullets;
    }

    public void setShip(Sprite ship) {
        _ship = ship;
    }

    public Sprite getShip() {
        return _ship;
    }

    public void setAllSprites(Set<Sprite> allSprites) {
        _allSprites = allSprites;
    }

    public Set<Sprite> getAllSprites() {
        return _allSprites;
    }

    public void setupGame(Resources resources) {

        lifeCount = 3;
        asteroidCount = 1;
        _gameOverFlag = false;
        synchronized (_allSprites) {
            _allSprites.clear();
            _asteroids.clear();
            _bullets.clear();
        }

        Sprite cwButton = new Sprite();
        cwButton.setCenterX(-0.25f);
        cwButton.setCenterY(-0.6f);
        cwButton.setHeight(0.7f);
        cwButton.setWidth(0.35f);
        cwButton.setTexture(BitmapFactory.decodeResource(resources, R.drawable.rotate_clockwise));

        Sprite ccwButton = new Sprite();
        ccwButton.setCenterX(-0.75f);
        ccwButton.setCenterY(-0.6f);
        ccwButton.setHeight(0.7f);
        ccwButton.setWidth(0.35f);
        ccwButton.setTexture(BitmapFactory.decodeResource(resources, R.drawable.rotate_counterclockwise));


        Sprite fireButton = new Sprite();
        fireButton.setCenterX(0.75f);
        fireButton.setCenterY(-0.8f);
        fireButton.setHeight(0.5f);
        fireButton.setWidth(0.25f);
        fireButton.setTexture(BitmapFactory.decodeResource(resources, R.drawable.fire_button));

        Sprite goButton = new Sprite();
        goButton.setCenterX(0.25f);
        goButton.setCenterY(-0.8f);
        goButton.setHeight(0.5f);
        goButton.setWidth(0.25f);
        goButton.setTexture(BitmapFactory.decodeResource(resources, R.drawable.go_button));

        _life1 = new Sprite();
        _life1.setCenterX(0.9f);
        _life1.setCenterY(0.95f);
        _life1.setHeight(0.16f);
        _life1.setWidth(0.08f);
        _life1.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_sideways));

        _life2 = new Sprite();
        _life2.setCenterX(0.8f);
        _life2.setCenterY(0.95f);
        _life2.setHeight(0.16f);
        _life2.setWidth(0.08f);
        _life2.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_sideways));

        _life3 = new Sprite();
        _life3.setCenterX(0.7f);
        _life3.setCenterY(0.95f);
        _life3.setHeight(0.16f);
        _life3.setWidth(0.08f);
        _life3.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_sideways));

        _gameOver.setCenterX(0.0f);
        _gameOver.setCenterY(0.0f);
        _gameOver.setWidth(1.0f);
        _gameOver.setHeight(1.0f);
        _gameOver.setTexture(BitmapFactory.decodeResource(resources, R.drawable.game_over));

        synchronized (_allSprites) {
            _allSprites.add(cwButton);
            _allSprites.add(ccwButton);
            _allSprites.add(fireButton);
            _allSprites.add(goButton);
            _allSprites.add(_life1);
            _allSprites.add(_life2);
            _allSprites.add(_life3);
        }

        _ship.setWidth(0.08f);
        _ship.setHeight(0.16f);
        //_ship.setVelocity(7.0f);
        _ship.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_sideways));
        synchronized (_allSprites) {
            _allSprites.add(_ship);
        }

        for (int i = 0; i < asteroidCount; i++ ) {
            Sprite asteroid = new Sprite();
            asteroid.setHeight(BIG_ASTEROID_DIAMETER);
            asteroid.setWidth(BIG_ASTEROID_DIAMETER / 2);
            asteroid.setCenterX(random.nextFloat() * (random.nextBoolean() ? -1 : 1));
            asteroid.setCenterY(random.nextFloat() * (random.nextBoolean() ? -1 : 1));
            asteroid.setVelocityX((float)random.nextInt(10) - 5);
            asteroid.setVelocityY((float)random.nextInt(10) - 5);
            asteroid.setRotation((float)random.nextInt(360));
            asteroid.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));
            synchronized (_asteroids) {
                _asteroids.add(asteroid);
            }
            synchronized (_allSprites) {
                _allSprites.add(asteroid);
            }
        }

    }

    public void updateGame(Resources resources) {
        rotateShip();
        showShipThrusters(resources);

        //move ship
        _ship.setCenterX(_ship.getCenterX() + _ship.getVelocityX() * 0.001f);
        _ship.setCenterY(_ship.getCenterY() + _ship.getVelocityY() * 0.001f);
        checkWrap(_ship);

        //move bullets
        synchronized (_bullets) {
            Sprite bulletOutofScreen = null;
            for (Sprite bullet : _bullets) {
                if (bullet.getCenterX() > 1.0f || bullet.getCenterX() < -1.0f
                        || bullet.getCenterY() > 1.0f || bullet.getCenterY() < -1.0f) {
                    bulletOutofScreen = bullet;
                }
                bullet.setCenterX(bullet.getCenterX() + bullet.getVelocityX() * 0.001f);
                bullet.setCenterY(bullet.getCenterY() + bullet.getVelocityY() * 0.001f);
            }
            if (bulletOutofScreen != null) {
                _bullets.remove(bulletOutofScreen);
            }
        }
        //move asteroids
        synchronized (_asteroids) {
            for (Sprite asteroid : _asteroids) {
                asteroid.setCenterX(asteroid.getCenterX() + asteroid.getVelocityX() * 0.001f);
                asteroid.setCenterY(asteroid.getCenterY() + asteroid.getVelocityY() * 0.001f);
                asteroid.setRotation(asteroid.getRotation() + 1);
                checkWrap(asteroid);
            }
        }
        if (!_gameOverFlag) {
            synchronized (_allSprites) {
                checkCollisions(resources);
            }
        }

    }

    private void showShipThrusters(Resources resources) {
        if (_isBoosting) {
            _ship.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_better_boost));
            applyThrust(resources);
        }
        else {
            _ship.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_sideways));
        }
    }

    private void rotateShip() {
        if (_isRotatingCW) {
            _ship.setRotation(_ship.getRotation() - 7);
        }
        if (_isRotatingCCW) {
            _ship.setRotation(_ship.getRotation() + 7);
        }
    }

    private synchronized void checkCollisions(Resources resources) {
        Set<Sprite> toBeRemoved = new HashSet<>();
        Set<Sprite> toBeAdded = new HashSet<>();
        Set<Sprite> bulletsToBeRemoved = new HashSet<>();
        for (Sprite asteroid : _asteroids) {
            for (Sprite bullet : _bullets) {

            // TODO:  fix the collisions problem!
                float combinedRadius = asteroid.getRadius() + bullet.getRadius();
                float distance = (float) Math.sqrt((Math.pow((asteroid.getCenterX() - bullet.getCenterX()), 2.0f)) +
                        (Math.pow((asteroid.getCenterY() - bullet.getCenterY()), 2.0f)));
                if (distance < combinedRadius) { // collision occurs
                    if (asteroid.getRadius() == BIG_ASTEROID_DIAMETER / 4) {
                        for (int i = 0; i < 3; i++) {
                            Log.i("Medium", "adding 1 medium asteroid");
                            Sprite mediumAsteroid = new Sprite();
                            mediumAsteroid.setHeight(MEDIUM_ASTEROID_DIAMETER);
                            mediumAsteroid.setWidth(MEDIUM_ASTEROID_DIAMETER / 2);
                            mediumAsteroid.setCenterX(asteroid.getCenterX());
                            mediumAsteroid.setCenterY(asteroid.getCenterY());
                            mediumAsteroid.setVelocityX((float) random.nextInt(10) - 5);
                            mediumAsteroid.setVelocityY((float) random.nextInt(10) - 5);
                            mediumAsteroid.setRotation((float) random.nextInt(360));
                            mediumAsteroid.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));
                            toBeAdded.add(mediumAsteroid);
                        }
                    }
                    toBeRemoved.add(asteroid);
                    bulletsToBeRemoved.add(bullet);
                }

            }

                float combinedRadiusShip = asteroid.getRadius() + _ship.getRadius();
                float distanceShip = (float) Math.sqrt((Math.pow((asteroid.getCenterX() - _ship.getCenterX()), 2.0f)) +
                        (Math.pow((asteroid.getCenterY() - _ship.getCenterY()), 2.0f)));
                if (distanceShip < combinedRadiusShip) {
                    _ship.setCenterX(0.0f);
                    _ship.setCenterY(0.0f);
                    _ship.setVelocityY(0.0f);
                    _ship.setVelocityX(0.0f);
                    _ship.setRotation(0.0f);
                    // TODO: player loses lives
                    if (lifeCount == 3) {
                        _allSprites.remove(_life3);
                        lifeCount--;
                    }
                    else if (lifeCount == 2) {
                        _allSprites.remove(_life2);
                        lifeCount--;
                    }
                    else if (lifeCount == 1) {
                        _allSprites.remove(_life1);
                        _allSprites.add(_gameOver);
                        _allSprites.remove(_ship);
                        _gameOverFlag = true;
                        // TODO: GAME OVER sequence (show game over on screen, options for restart)
                    }
                }

            }
            for (Sprite bullet : bulletsToBeRemoved) {
                _bullets.remove(bullet);
                _allSprites.remove(bullet);
            }
            for (Sprite s : toBeAdded) {
                _asteroids.add(s);
                _allSprites.add(s);
            }
            for (Sprite s : toBeRemoved) {
                _asteroids.remove(s);
                _allSprites.remove(s);
            }

            if (_asteroids.size() == 0) {
                asteroidCount++;
                for (int i = 0; i < asteroidCount; i++) {
                    Sprite asteroid = new Sprite();
                    asteroid.setHeight(BIG_ASTEROID_DIAMETER);
                    asteroid.setWidth(BIG_ASTEROID_DIAMETER / 2);
                    asteroid.setCenterX((random.nextBoolean() ? -1 : 1));
                    asteroid.setCenterY(random.nextFloat() * (random.nextBoolean() ? -1 : 1));
                    asteroid.setVelocityX((float)random.nextInt(10) - 5);
                    asteroid.setVelocityY((float)random.nextInt(10) - 5);
                    asteroid.setRotation((float)random.nextInt(360));
                    asteroid.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));
                    _allSprites.add(asteroid);
                    _asteroids.add(asteroid);
                }
            }


    }

    public void applyThrust(Resources resources) {
        _ship.setVelocityX((float)(_ship.getVelocityX() + 1.1 * (float)Math.cos(_ship.getRadians())));
        _ship.setVelocityY((float)(_ship.getVelocityY() + 1.1 * (float)Math.sin(_ship.getRadians())));
//        _ship.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_better_boost));
    }

    private void checkWrap(Sprite sprite) {
        if (sprite.getCenterX() > 1.0f) {
            sprite.setCenterX(-1.0f);
        }
        else if (sprite.getCenterX() < -1.0f) {
            sprite.setCenterX(1.0f);
        }
        if (sprite.getCenterY() > 1.0f) {
            sprite.setCenterY(-1.0f);
        }
        else if (sprite.getCenterY() < -1.0f) {
            sprite.setCenterY(1.0f);
        }

    }

    public void shoot(Resources resources) {
        if (!_gameOverFlag) {
            Sprite bullet = new Sprite();
            bullet.setWidth(0.025f);
            bullet.setHeight(0.05f);
            float radians = _ship.getRadians();
            bullet.setVelocityX((float) ((Math.cos(radians)) * 30.0f));
            bullet.setVelocityY((float) ((Math.sin(radians)) * 30.0f));
            bullet.setTexture(BitmapFactory.decodeResource(resources, R.drawable.bullet));
            bullet.setCenterX(_ship.getCenterX());
            bullet.setCenterY(_ship.getCenterY());
            bullet.setRotation(_ship.getRotation());
            synchronized (_bullets) {
                _bullets.add(bullet);
            }
            // TODO: Lock this for multi threading - may not be necessary
            synchronized (_allSprites) {
                _allSprites.add(bullet);
            }
        }
    }
}
