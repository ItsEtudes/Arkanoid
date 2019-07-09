package me.etudes.arkanoid.models;

import me.etudes.arkanoid.Main;
import me.etudes.arkanoid.graphics.Mesh;
import me.etudes.arkanoid.graphics.Shader;
import me.etudes.arkanoid.graphics.Texture;
import me.etudes.arkanoid.math.Matrix4f;
import me.etudes.arkanoid.math.Vector3f;

public class Ball {

    private static final float WIDTH = 2f;
    private static final float HEIGHT = 3f;

    private Shader shader;
    private Texture texture;
    private Mesh mesh;

    private Vector3f position;
    private Vector3f velocity;

    public Ball() {
        position = new Vector3f((70f - WIDTH) / 2, (100f - HEIGHT) / 2, 0f);
        velocity = new Vector3f(0.3f, 0.3f, 0f);

        shader = new Shader("shaders/ball.vert", "shaders/ball.frag");
        shader.setUniform1i("tex", 0);
        shader.setUniformMatrix4f("projectionMatrix", Main.getProjectionMatrix());
        shader.disable();
        texture = new Texture("ball.png");

        float[] vertices = new float[] {
                0f, 0f, 0f,
                0, HEIGHT, 0f,
                WIDTH, HEIGHT, 0f,
                WIDTH, 0f, 0f
        };

        byte[] indices = new byte[] {
                0, 1, 2,
                2, 3, 0
        };

        float[] textureCoords = new float[] {
                0f, 0f,
                0f, 1f,
                1f, 1f,
                1f, 0f
        };

        mesh = new Mesh(vertices, indices, textureCoords);
    }

    private void doWallCollision() {
        if(position.x < 0f) {
            position.x = 0f;
            reboundX();
        }
        if(position.x + WIDTH > 100f) {
            position.x = 100f - WIDTH;
            reboundX();
        }
        if(position.y < 0f) {
            position.y = 0f;
            reboundY();
        }
        if(position.y + HEIGHT > 100f) {
            position.y = 100f - HEIGHT;
            reboundY();
        }
    }

    public void update() {
        doWallCollision();
        position.add(velocity);
    }

    public void render() {
        Matrix4f transformationMatrix = Matrix4f.translate(position);
        shader.setUniformMatrix4f("transformationMatrix", transformationMatrix);

        texture.bind();
        shader.enable();
        mesh.render();
        shader.disable();
        texture.unbind();
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setX(float x) {
        position.x = x;
    }

    public void setY(float y) {
        position.y = y;
    }

    public void reboundX() {
        velocity.x = -velocity.x;
    }

    public void reboundY() {
        velocity.y = -velocity.y;
    }

    public float getWidth() {
        return WIDTH;
    }

    public float getHeight() {
        return HEIGHT;
    }

    public int getVxSign() {
        return velocity.x > 0 ? 1 : -1;
    }

    public void setVelocityX(float vx) {
        velocity.x = vx;
    }

}
