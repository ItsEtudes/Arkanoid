package me.etudes.arkanoid.models;

import me.etudes.arkanoid.Main;
import me.etudes.arkanoid.graphics.Mesh;
import me.etudes.arkanoid.graphics.Shader;
import me.etudes.arkanoid.graphics.Texture;
import me.etudes.arkanoid.input.Keyboard;
import me.etudes.arkanoid.math.Matrix4f;
import me.etudes.arkanoid.math.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Paddle {

    private static final float WIDTH = 9f;
    private static final float HEIGHT = 5f;
    private static final float CONST_Y = 90f;

    private Shader shader;
    private Texture texture;
    private Mesh mesh;

    private float x;
    private float vx;

    public Paddle() {
        x = (100 - WIDTH) / 2;
        vx = 0f;

        shader = new Shader("shaders/paddle.vert", "shaders/paddle.frag");
        shader.setUniform1i("tex", 0);
        shader.setUniformMatrix4f("projectionMatrix", Main.getProjectionMatrix());
        shader.disable();
        texture = new Texture("paddle.png");

        float[] vertices = new float[] {
                0f, 0f, 0f,
                0f, HEIGHT, 0f,
                WIDTH + 1f, HEIGHT, 0f,
                WIDTH + 1f, 0f, 0f
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

    public void update() {
        if(Keyboard.isKeyPressed(GLFW_KEY_LEFT)) vx = -0.3f;
        if(Keyboard.isKeyPressed(GLFW_KEY_RIGHT)) vx = 0.3f;
        if(Keyboard.isKeyReleased(GLFW_KEY_LEFT) && Keyboard.isKeyReleased(GLFW_KEY_RIGHT)) {
            vx = 0f;
        }

        if(!wallCollision()) {
            x += vx;
        }
    }

    private boolean wallCollision() {
        if(x < 0) {
            x = 0;
            return true;
        }
        if(x + WIDTH - 1f > 100) {
            x = 100 - WIDTH + 1f;
            return true;
        }
        return false;
    }

    public void ballCollision(Ball ball) {
        Vector3f ballPos = ball.getPosition();
        boolean collided =  ballPos.x <= x + WIDTH &&
                ballPos.x + ball.getWidth() >= x &&
                ballPos.y <= CONST_Y + HEIGHT &&
                ballPos.y + ball.getHeight() >= CONST_Y;
        if(collided) {
            float centerX = ballPos.x + (ball.getWidth() / 2);
            float centerY = ballPos.y + (ball.getHeight() / 2);
            if(centerX >= x && centerX <= x + WIDTH) {
                if(centerY < CONST_Y) {
                    ball.setY(CONST_Y - ball.getHeight());
                } else {
                    ball.setY(CONST_Y + HEIGHT);
                }
                ball.reboundY();

                float pCenterX = x + (WIDTH / 2);
                float delta = Math.abs((centerX - pCenterX)) / 10f;
                int direction = ball.getVxSign();
                ball.setVelocityX(delta * direction + 0.1f);
            } else {
                if(centerX < x) {
                    ball.setX(x - ball.getWidth());
                } else {
                    ball.setX(x + WIDTH);
                }
                ball.reboundX();
            }
        }
    }

    public void render() {
        Matrix4f transformationMatrix = Matrix4f.translate(new Vector3f(x, CONST_Y, 0f));
        shader.setUniformMatrix4f("transformationMatrix", transformationMatrix);

        texture.bind();
        shader.enable();
        mesh.render();
        shader.disable();
        texture.unbind();
    }

}
