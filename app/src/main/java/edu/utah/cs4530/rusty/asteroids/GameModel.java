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
    private static final float BIG_ASTEROID_RADIUS = 0.5f;
    private static final float MEDIUM_ASTEROID_RADIUS = 0.3f;
    private static final float SMALL_ASTEROID_RADIUS = 0.1f;

    private Set<Sprite> _asteroids = new HashSet<>();
    private Set<Sprite> _bullets = new HashSet<>();
    private Sprite _ship = new Sprite();
    private Set<Sprite> _allSprites = new HashSet<>(); //this is for the activity to draw all the sprites.
    private Random random = new Random();

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
//        Sprite testShip = new Sprite();
//        testShip.setCenterX(0.5f);
//        testShip.setCenterY(0);
//        testShip.setHeight(0.5f);
//        testShip.setWidth(0.25F);
//        testShip.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_shield));
//        _allSprites.add(testShip);

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

        synchronized (_allSprites) {
            _allSprites.add(cwButton);
            _allSprites.add(ccwButton);
            _allSprites.add(fireButton);
            _allSprites.add(goButton);
        }

        _ship.setWidth(0.08f);
        _ship.setHeight(0.16f);
        //_ship.setVelocity(7.0f);
        _ship.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_sideways));
        synchronized (_allSprites) {
            _allSprites.add(_ship);
        }

        for (int i = 0; i < 3; i++ ) {
            Sprite asteroid = new Sprite();
            asteroid.setHeight(BIG_ASTEROID_RADIUS);
            asteroid.setWidth(BIG_ASTEROID_RADIUS / 2);
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
        /*
        Sprite asteroid1 = new Sprite();
        asteroid1.setHeight(0.5f);
        asteroid1.setWidth(0.25f);
        asteroid1.setCenterX(0.5f);
        asteroid1.setCenterY(-0.5f);
        asteroid1.setVelocityX(-3.0f);
        asteroid1.setVelocityY(-1.0f);
        asteroid1.setRotation(1.0f);
        asteroid1.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));

        Sprite asteroid2 = new Sprite();
        asteroid2.setHeight(0.5f);
        asteroid2.setWidth(0.25f);
        asteroid2.setCenterX(0.5f);
        asteroid2.setCenterY(0.5f);
        asteroid2.setVelocityX(1.0f);
        asteroid2.setVelocityY(5.0f);
        asteroid2.setRotation(-190.0f);
        asteroid2.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));

        Sprite asteroid3 = new Sprite();
        asteroid3.setHeight(0.5f);
        asteroid3.setWidth(0.25f);
        asteroid3.setCenterX(-0.5f);
        asteroid3.setCenterY(-0.5f);
        asteroid3.setVelocityX(1.5f);
        asteroid3.setVelocityY(-3.4f);
        asteroid3.setRotation(100.0f);
        asteroid3.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));

        _asteroids.add(asteroid1);
        _asteroids.add(asteroid2);
        _asteroids.add(asteroid3);

        _allSprites.addAll(_asteroids);
        */
    }

    public void updateGame(Resources resources) {
        //move ship
        _ship.setCenterX(_ship.getCenterX() + _ship.getVelocityX() * 0.001f);
        _ship.setCenterY(_ship.getCenterY() + _ship.getVelocityY() * 0.001f);
        checkWrap(_ship);

        //move bullets
        synchronized (_bullets) {
            for (Sprite bullet : _bullets) {
                bullet.setCenterX(bullet.getCenterX() + bullet.getVelocityX() * 0.001f);
                bullet.setCenterY(bullet.getCenterY() + bullet.getVelocityY() * 0.001f);
            }
        }
        //move asteroids
        synchronized (_asteroids) {
            for (Sprite asteroid : _asteroids) {
                asteroid.setCenterX(asteroid.getCenterX() + asteroid.getVelocityX() * 0.001f);
                asteroid.setCenterY(asteroid.getCenterY() + asteroid.getVelocityY() * 0.001f);
                asteroid.setRotation(asteroid.getRotation() + 0.05f);
                checkWrap(asteroid);
            }
        }
        synchronized (_allSprites) {
            checkCollisions(resources);
        }
        //TODO: check for collisions.

    }

    private synchronized void checkCollisions(Resources resources) {
        Set<Sprite> toBeRemoved = new HashSet<>();
        Set<Sprite> toBeAdded = new HashSet<>();

        synchronized (_asteroids) {
            for (Sprite asteroid : _asteroids) {

                synchronized (_bullets) {
                    for (Sprite bullet : _bullets) {
                        float combinedRadius = asteroid.getRadius() + bullet.getRadius();
                        float distance = (float) Math.sqrt((Math.pow((asteroid.getCenterX() - bullet.getCenterX()), 2.0f)) -
                                (Math.pow((asteroid.getCenterY() - bullet.getCenterY()), 2.0f)));

                        if (distance < combinedRadius) { // collision occurs
                            if (asteroid.getRadius() == BIG_ASTEROID_RADIUS) {
                                for (int i = 0; i < 3; i++) {
                                    Log.i("Medium", "adding 1 medium asteroid");
                                    Sprite mediumAsteroid = new Sprite();
                                    mediumAsteroid.setHeight(MEDIUM_ASTEROID_RADIUS);
                                    mediumAsteroid.setWidth(MEDIUM_ASTEROID_RADIUS / 2);
                                    mediumAsteroid.setCenterX(asteroid.getCenterX());
                                    mediumAsteroid.setCenterY(asteroid.getCenterY());
                                    mediumAsteroid.setVelocityX((float) random.nextInt(10) - 5);
                                    mediumAsteroid.setVelocityY((float) random.nextInt(10) - 5);
                                    mediumAsteroid.setRotation((float) random.nextInt(360));
                                    mediumAsteroid.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));
                                    toBeAdded.add(mediumAsteroid);
                                }
                            }
                        /*
                        else if (asteroid.getRadius() == MEDIUM_ASTEROID_RADIUS) {
                            for (int i = 0; i < 3; i++) {
                                Sprite smallAsteroid = new Sprite();
                                smallAsteroid.setHeight(SMALL_ASTEROID_RADIUS);
                                smallAsteroid.setWidth(SMALL_ASTEROID_RADIUS / 2);
                                smallAsteroid.setCenterX(asteroid.getCenterX());
                                smallAsteroid.setCenterY(asteroid.getCenterY());
                                smallAsteroid.setVelocityX((float) random.nextInt(10) - 5);
                                smallAsteroid.setVelocityY((float) random.nextInt(10) - 5);
                                smallAsteroid.setRotation((float) random.nextInt(360));
                                smallAsteroid.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));
                                toBeAdded.add(smallAsteroid);
                            }
                        }
                        */
//                        toBeRemoved.add(asteroid);
                        }

                    }
                }


                float combinedRadiusShip = asteroid.getRadius() + _ship.getRadius();
                float distanceShip = (float) Math.sqrt((Math.pow((asteroid.getCenterX() - _ship.getCenterX()), 2.0f)) -
                        (Math.pow((asteroid.getCenterY() - _ship.getCenterY()), 2.0f)));
                if (distanceShip < combinedRadiusShip) {
                    // TODO: player loses lives
                }

            }

            for (Sprite s : toBeAdded) {
                _asteroids.add(s);
                _allSprites.add(s);
            }
            for (Sprite s : toBeRemoved) {
                _asteroids.remove(s);
                _allSprites.remove(s);
            }

        }

    }

    public void applyThrust(Resources resources) {
        _ship.setVelocityX((float)(_ship.getVelocityX() + 1.5 * (float)Math.cos(_ship.getRadians())));
        _ship.setVelocityY((float)(_ship.getVelocityY() + 1.5 * (float)Math.sin(_ship.getRadians())));
        _ship.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship_better_boost));
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
        Sprite bullet = new Sprite();
        bullet.setWidth(0.025f);
        bullet.setHeight(0.05f);
        float radians = _ship.getRadians();
        bullet.setVelocityX((float)((Math.cos(radians)) * 30.0f));
        bullet.setVelocityY((float)((Math.sin(radians)) * 30.0f));
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
