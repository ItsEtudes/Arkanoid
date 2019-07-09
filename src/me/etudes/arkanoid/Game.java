package me.etudes.arkanoid;

import me.etudes.arkanoid.models.*;

public class Game {

    private Background background;
    private Paddle paddle;
    private Ball ball;
    private Brick[] bricks;

    public Game() {
        bricks = new Brick[12 * 4];
        for(int y = 0; y < 4; y++) {
            for(int x = 0; x < 12; x++) {
                switch(y) {
                    case 0:
                        bricks[x + y * 12] = new Brick("Red", x, y);
                        break;
                    case 1:
                        bricks[x + y * 12] = new Brick("Yellow", x, y);
                        break;
                    case 2:
                        bricks[x + y * 12] = new Brick("Blue", x, y);
                        break;
                    case 3:
                        bricks[x + y * 12] = new Brick("Green", x, y);
                }
            }
        }
        background = new Background();
        paddle = new Paddle();
        ball = new Ball();
    }

    public void update() {
        paddle.update();
        ball.update();
        paddle.ballCollision(ball);

        for(Brick brick : bricks) {
            if(brick.ballCollision(ball)) {
                brick.destroy();
                break; // only destroy 1 brick per tick
            }
        }
    }

    public void render() {
        background.render();
        paddle.render();
        ball.render();

        for(Brick brick : bricks) {
            brick.render();
        }
    }

}
