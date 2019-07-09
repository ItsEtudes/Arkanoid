package me.etudes.arkanoid.graphics;

import me.etudes.arkanoid.util.BufferUtils;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private int vao;
    private int count;

    public Mesh(float[] vertices, byte[] indices, float[] textureCoords) {
        count = indices.length;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        int vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(textureCoords), GL_STATIC_DRAW);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        vbo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);
    }

    public void render() {
        glBindVertexArray(vao);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

}
