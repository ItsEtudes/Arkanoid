package me.etudes.arkanoid;

import me.etudes.arkanoid.math.Matrix4f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Main implements Runnable {

    private static int width = 1280;
    private static int height = 720;
    private static final String TITLE = "Arkanoid";

    private static Matrix4f projectionMatrix;

    private Window window;

    private boolean running;
    private Thread thread;

    private Game game;

    private void init() {
        projectionMatrix = Matrix4f.orthographic(0, 100, 100, 0, -1, 1);
        window = new Window(width, height, TITLE);

        GL.createCapabilities();
        GLUtil.setupDebugMessageCallback();
        glClearColor(0f, 0f, 0f, 0f);

        glActiveTexture(GL_TEXTURE0);

        game = new Game();
    }

    public void run() {
        init();
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;
        double ns = 1000000000.0 / 60.0;
        int frames = 0;
        while(running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            while(delta >= 1.0) {
                update();
                delta--;
            }

            if(running) {
                render();
                frames++;
            }

            if(System.currentTimeMillis() - timer >= 1000) {
                window.setTitle(TITLE + " | " + frames + " fps");
                frames = 0;
                timer += 1000;
            }
        }
    }

    private void start() {
        running = true;
        thread = new Thread(this, TITLE);
        thread.start();
    }

    private void update() {
        window.update();
        game.update();
        if(window.isCloseRequested()) {
            running = false;
        }
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT);

        // Draw graphics here
        game.render();

        window.render();
    }

    public static Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public static void main(String[] args) {
        new Main().start();
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setWidth(int width) {
        Main.width = width;
    }

    public static void setHeight(int height) {
        Main.height = height;
    }

}
