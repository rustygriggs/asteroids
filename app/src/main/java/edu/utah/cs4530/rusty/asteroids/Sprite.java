package edu.utah.cs4530.rusty.asteroids;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Rusty on 12/6/2016.
 */
public class Sprite {
    private static final int POSITION_ARRAY = 0;
    private static final int TEXTURE_COORDINATE_ARRAY = 1;

    private static final float[] _quadGeometry = new float[]{
            -0.5f,  -0.5f,
            0.5f,  -0.5f,
            -0.5f,  0.5f,
            0.5f,  0.5f,
    };

    private static final float[] _quadTextureCoordinates = new float[]{
            0.0f, 1.0f, //Bottom left
            1.0f, 1.0f, // Bottom right
            0.0f, 0.0f, // Top left
            1.0f, 0.0f, // Top right
    };

    private static final float[] _transform = new float[] {
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f,
    };

    private float _translateX = 0.0f;
    private float _translateY = 0.0f;
    private float _scaleX = 1.0f;
    private float _scaleY = 1.0f;
    private float _angle = 0.0f;
    private float _radians = 0.0f;
    private float _radius = 0.0f;
    private Bitmap _texture = null;

    private static boolean _setup = false;

    private static int _program = -1;
    private static int _transformUniformLocation = -1;
    private static int _textureCoordinateUniformLocation = -1;
    private int _textureName = -1;
    private float _velocityX = 0.0f;
    private float _velocityY = 0.0f;


    float getCenterX() {
        return _translateX;
    }

    void setCenterX(float centerX) {
        _translateX = centerX;
    }

   float getCenterY() {
       return _translateY;
   }

    void setCenterY(float centerY) {
        _translateY = centerY;
    }

    float getHeight() {
        return _scaleY;
    }

    void setHeight(float height) {
        _scaleY = height;
    }

    float getWidth() {
        return _scaleX;
    }

    void setWidth(float width) {
        _scaleX = width;
    }

    public Bitmap getTexture() {
        return _texture;
    }

    public void setTexture(Bitmap texture) {
        _texture = texture;
    }

    public float getRotation() {
        return _angle;
    }

    public void setRotation(float theta) {
        _angle = theta;
    }

    public float getVelocityX() {
        return _velocityX;
    }

    public void setVelocityX(float velX) {
        _velocityX = velX;
    }

    public float getVelocityY() {
        return _velocityY;
    }

    public void setVelocityY(float velY) {
        _velocityY = velY;
    }

    public float getRadians() {
        return (float) ((_angle / 360.0f) * (2.0f * Math.PI));
    }

    public void setRadians(float radians) {
        _radians = radians;
    }

    public float getRadius() {
        return Math.max(_scaleX, _scaleY);
    }


    // TODO: Animation get/set

    static private void setup() {
        String vertexShaderSource = "" +
                " \n" +
                " \n" +
                "attribute vec2 position;\n" +
                "attribute vec2 textureCoordinate; \n" +
                "uniform mat4 transform; \n" +
                " \n" +
                "varying vec2 textureCoordinateInterpolated; \n" +
                " \n" +
                "void main() { \n" +
                "   gl_Position = transform * vec4(position.x, position.y, 0.0, 1.0);\n" +
                "   textureCoordinateInterpolated = textureCoordinate;" +
                "} \n" +
                " \n";

        String fragmentShaderSource = "" +
                " \n" +
                " \n" +
                "varying highp vec2 textureCoordinateInterpolated; \n" +
                "uniform sampler2D textureUnit; \n" +
                " \n" +
                "void main() { \n" +
                "   gl_FragColor = texture2D(textureUnit, textureCoordinateInterpolated);\n" +
                "} \n" +
                " \n";

        // Create and compile vertex shader
        int vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
        GLES20.glShaderSource(vertexShader, vertexShaderSource);
        GLES20.glCompileShader(vertexShader);
        String vertexShaderInfoLog = GLES20.glGetShaderInfoLog(vertexShader);
        Log.i("Vertex Shader", "Output: " + vertexShaderInfoLog);

        // Create and compile fragment shader
        int fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
        GLES20.glShaderSource(fragmentShader, fragmentShaderSource);
        GLES20.glCompileShader(fragmentShader);
        String fragmentShaderInfoLog = GLES20.glGetShaderInfoLog(fragmentShader);
        Log.i("Fragment Shader", "Output: " + fragmentShaderInfoLog);

        // Link program
        _program = GLES20.glCreateProgram();
        GLES20.glAttachShader(_program, vertexShader);
        GLES20.glAttachShader(_program, fragmentShader);
        GLES20.glBindAttribLocation(_program, POSITION_ARRAY, "position");
        GLES20.glBindAttribLocation(_program, TEXTURE_COORDINATE_ARRAY, "textureCoordinate");
        GLES20.glLinkProgram(_program);
        String programInfoLog = GLES20.glGetProgramInfoLog(_program);

        Log.i("Linker", "Output: " + programInfoLog);

        GLES20.glUseProgram(_program);
        GLES20.glEnableVertexAttribArray(0);
        GLES20.glEnableVertexAttribArray(1);

        ByteBuffer quadGeometryByteBuffer = ByteBuffer.allocateDirect(4 * _quadGeometry.length);
        quadGeometryByteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer quadGeometryBuffer = quadGeometryByteBuffer.asFloatBuffer();
        quadGeometryBuffer.put(_quadGeometry);
        quadGeometryBuffer.rewind();

        ByteBuffer quadTextureCoordinateByteBuffer = ByteBuffer.allocateDirect(4 * _quadTextureCoordinates.length);
        quadTextureCoordinateByteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer quadTextureCoordinateBuffer = quadTextureCoordinateByteBuffer.asFloatBuffer();
        quadTextureCoordinateBuffer.put(_quadTextureCoordinates);
        quadTextureCoordinateBuffer.rewind();

        GLES20.glVertexAttribPointer(POSITION_ARRAY, 2, GLES20.GL_FLOAT, false, 0, quadGeometryBuffer);
        GLES20.glVertexAttribPointer(TEXTURE_COORDINATE_ARRAY, 2, GLES20.GL_FLOAT, false, 0, quadTextureCoordinateBuffer);

        _transformUniformLocation = GLES20.glGetUniformLocation(_program, "transform");
        _textureCoordinateUniformLocation = GLES20.glGetUniformLocation(_program, "textureCoordinate");

        GLES20.glEnable(GLES20.GL_TEXTURE_2D);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        _setup = true;
    }

    /**
     * draws the sprite that has already been initialized.
     */
    public void draw() {
        if (!_setup) {
            setup();
        }

        if (_textureName <= 0) {
            //Generate a single texture name identifier.
            int[] textureNames = new int[1];
            textureNames[0] = -1;
            GLES20.glGenTextures(1, textureNames, 0);
            _textureName = textureNames[0];

            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _textureName);
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, _texture, 0);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _textureName);

        Matrix.setIdentityM(_transform, 0);
        Matrix.translateM(_transform, 0, _translateX, _translateY, 0.0f);
        Matrix.scaleM(_transform, 0, _scaleX, _scaleY, 1.0f);
        Matrix.rotateM(_transform, 0, _angle, 0.0f, 0.0f, 1.0f);

        GLES20.glUniformMatrix4fv(_transformUniformLocation, 1, false, _transform, 0);
        GLES20.glUniform1i(_textureCoordinateUniformLocation, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4); //0,1,2 2,1,3
    }
}
