package me.etudes.arkanoid.models;

import me.etudes.arkanoid.Main;
import me.etudes.arkanoid.graphics.Mesh;
import me.etudes.arkanoid.graphics.Shader;
import me.etudes.arkanoid.graphics.Texture;
import me.etudes.arkanoid.math.Matrix4f;
import me.etudes.arkanoid.math.Vector3f;

public class Brick {

    private static final float WIDTH = 100f / 12f;
    private static final float HEIGHT = 6f;
    private static final float OFFSET = 10f;

    private Shader shader;
    private Texture texture;
    private Mesh mesh;

    private Vector3f position;

    private boolean destroyed;

    public Brick(String color, float x, float y) {
        position = new Vector3f(x * WIDTH, y * HEIGHT + OFFSET, 0f);
        destroyed = false;
        shader = new Shader("shaders/brick.vert", "shaders/brick.frag");
        shader.setUniform1i("tex", 0);
        shader.setUniformMatrix4f("projectionMatrix", Main.getProjectionMatrix());
        shader.disable();
        texture = new Texture(color + "Brick.png");

        float[] vertices = new float[] {
                0f, 0f, 0f,
                0f, HEIGHT, 0f,
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

    public boolean ballCollision(Ball ball) {
        if(!destroyed) {
            Vector3f ballPos = ball.getPosition();
            boolean collided = ballPos.x <= position.x + WIDTH &&
                    ballPos.x + ball.getWidth() >= position.x &&
                    ballPos.y <= position.y + HEIGHT &&
                    ballPos.y + ball.getHeight() >= position.y;
            if (collided) {
                float pCenterX = position.x + (WIDTH) / 2;
                float centerX = ballPos.x + (ball.getWidth() / 2);
                float centerY = ballPos.y + (ball.getHeight() / 2);

                if(centerX - pCenterX < 0 && ball.getVxSign() < 0) {
                    ball.reboundY();
                } else if(centerX - pCenterX > 0 && ball.getVxSign() > 0) {
                    ball.reboundY();
                } else if (centerX >= position.x && centerX <= position.x + WIDTH) {
                    ball.reboundY();
                    if (centerY < position.y) {
                        ball.setY(position.y - ball.getHeight());
                    } else {
                        ball.setY(position.y + HEIGHT);
                    }
                } else {
                    ball.reboundX();
                    if (centerX < position.x) {
                        ball.setX(position.x - ball.getWidth());
                    } else {
                        ball.setX(position.x + WIDTH);
                    }
                }
            }
            return collided;
        }
        return false;
    }

    public void render() {
        if(!destroyed) {
            shader.setUniformMatrix4f("transformationMatrix",
                    Matrix4f.translate(new Vector3f(position.x, position.y, 0f)));
            shader.disable();

            texture.bind();
            shader.enable();
            mesh.render();
            shader.disable();
            texture.unbind();
        }
    }

    public void destroy() {
        destroyed = true;
    }

}
