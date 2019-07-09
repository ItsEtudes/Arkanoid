package me.etudes.arkanoid.models;

import me.etudes.arkanoid.graphics.Mesh;
import me.etudes.arkanoid.graphics.Shader;
import me.etudes.arkanoid.graphics.Texture;

public class Background {

    private Shader shader;
    private Texture texture;
    private Mesh mesh;

    public Background() {
        shader = new Shader("shaders/background.vert", "shaders/background.frag");
        shader.setUniform1i("tex", 0);
        shader.disable();
        texture = new Texture("background.png");
        float[] vertices = new float[] {
                -1f, -1f, 1f,
                -1f, 1f, 1f,
                1f, 1f, 1f,
                1f, -1f, 1f
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

    public void render() {
        texture.bind();
        shader.enable();
        mesh.render();
        shader.disable();
        texture.unbind();
    }

}
