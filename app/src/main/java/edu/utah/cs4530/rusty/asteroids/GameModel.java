package edu.utah.cs4530.rusty.asteroids;

import android.content.res.Resources;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Rusty on 12/14/2016.
 */
public class GameModel implements Serializable {
    private Set<Sprite> _asteroids = new HashSet<>();
    private Set<Sprite> _bullets = new HashSet<>();
    private Sprite _ship = new Sprite();
    private Set<Sprite> _allSprites = new HashSet<>(); //this is for the activity to draw all the sprites.

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
        Sprite cwButton = new Sprite();
        cwButton.setCenterX(-0.25f);
        cwButton.setCenterY(-0.8f);
        cwButton.setHeight(0.5f);
        cwButton.setWidth(0.25f);
        cwButton.setTexture(BitmapFactory.decodeResource(resources, R.drawable.rotate_clockwise));

        Sprite ccwButton = new Sprite();
        ccwButton.setCenterX(-0.75f);
        ccwButton.setCenterY(-0.8f);
        ccwButton.setHeight(0.5f);
        ccwButton.setWidth(0.25f);
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

        _allSprites.add(cwButton);
        _allSprites.add(ccwButton);
        _allSprites.add(fireButton);
        _allSprites.add(goButton);

        _ship.setWidth(0.1f);
        _ship.setHeight(0.2f);
        //_ship.setVelocityX(7.0f);
        _ship.setTexture(BitmapFactory.decodeResource(resources, R.drawable.basic_ship));
        _allSprites.add(_ship);

        Sprite asteroid1 = new Sprite();
        asteroid1.setHeight(0.5f);
        asteroid1.setWidth(0.25f);
        asteroid1.setCenterX(0.5f);
        asteroid1.setCenterY(-0.5f);
        asteroid1.setVelocityX(-3.0f);
        asteroid1.setVelocityY(-1.0f);
//        asteroid1.setRotation(1.0f);
        asteroid1.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));

        Sprite asteroid2 = new Sprite();
        asteroid2.setHeight(0.5f);
        asteroid2.setWidth(0.25f);
        asteroid2.setCenterX(0.5f);
        asteroid2.setCenterY(0.5f);
        asteroid2.setVelocityX(1.0f);
        asteroid2.setVelocityY(5.0f);
//        asteroid2.setRotation(-1.0f);
        asteroid2.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));

        Sprite asteroid3 = new Sprite();
        asteroid3.setHeight(0.5f);
        asteroid3.setWidth(0.25f);
        asteroid3.setCenterX(-0.5f);
        asteroid3.setCenterY(-0.5f);
        asteroid3.setVelocityX(1.5f);
        asteroid3.setVelocityY(-3.4f);
//        asteroid3.setRotation(5.0f);
        asteroid3.setTexture(BitmapFactory.decodeResource(resources, R.drawable.asteroid_sprite));

        _asteroids.add(asteroid1);
        _asteroids.add(asteroid2);
        _asteroids.add(asteroid3);

        _allSprites.addAll(_asteroids);
    }

    public void updateGame() {
        synchronized (_allSprites) {
            for (Sprite sprite : _allSprites) {
                sprite.setCenterX(sprite.getCenterX() + sprite.getVelocityX() * 0.001f);
                sprite.setCenterY(sprite.getCenterY() + sprite.getVelocityY() * 0.001f);
                checkWrap(sprite);
            }
        }
        /*
        for (Sprite asteroid : _asteroids) {
//            asteroid.setRotation(asteroid.getRotation() + 1.0f); //this might make velocity harder
//            asteroid.setCenterX(asteroid.getCenterX() + asteroid.getVelocityX() * 0.001f);
//            asteroid.setCenterY(asteroid.getCenterY() + asteroid.getVelocityX() * 0.001f);
            asteroid.setCenterX(asteroid.getCenterX() + asteroid.getVelocityX() * (float)Math.cos(asteroid.getRotation()) * 0.001f);
            asteroid.setCenterY(asteroid.getCenterY() + asteroid.getVelocityX() * (float)Math.sin(asteroid.getRotation()) * 0.001f);
            checkWrap(asteroid);
        }
        for (Sprite bullet : _bullets) {
            bullet.setCenterX(bullet.getCenterX() + bullet.getVelocityX() * (float)Math.cos(bullet.getRadians()));
            bullet.setCenterY(bullet.getCenterY() + bullet.getVelocityX() * (float)Math.sin(bullet.getRadians()));
        }
//        _ship.setCenterY(_ship.getCenterY() + _ship.getVelocityX() * 0.001f);
//        _ship.setCenterY(_ship.getCenterY() + _ship.getVelocityX() * 0.001f);
        // TODO: only change the velocity and stuff on ship thrust.... (still change center based on velocity.)
        _ship.setCenterX(_ship.getCenterX() + _ship.getVelocityX() * 0.001f);
        _ship.setCenterY(_ship.getCenterY() + _ship.getVelocityX() * 0.001f);
        checkWrap(_ship);
        //TODO: move all the sprites and check for collisions.
        */
    }

    public void applyThrust() {
        _ship.setCenterX(_ship.getCenterX() + _ship.getVelocityX() * (float)Math.cos(_ship.getRadians()) * 0.001f);
        _ship.setCenterY(_ship.getCenterY() + _ship.getVelocityY() * (float)Math.sin(_ship.getRadians()) * 0.001f);
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
        bullet.setWidth(0.01f);
        bullet.setHeight(0.005f);
        bullet.setVelocityX(10.0f);
        bullet.setTexture(BitmapFactory.decodeResource(resources, R.drawable.bullet));
        bullet.setCenterX(_ship.getCenterX());
        bullet.setCenterY(_ship.getCenterY());
        bullet.setRotation(_ship.getRotation());
        _bullets.add(bullet);
        // TODO: Lock this for multi threading
        synchronized (_allSprites) {
            _allSprites.add(bullet);
        }
    }
}
