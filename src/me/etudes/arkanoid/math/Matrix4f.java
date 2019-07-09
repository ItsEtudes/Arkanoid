package me.etudes.arkanoid.math;

import me.etudes.arkanoid.util.BufferUtils;

import java.nio.FloatBuffer;

public class Matrix4f {

    private static final int SIZE = 4 * 4;
    private float[] data = new float[SIZE];

    public Matrix4f() {
        for(int i = 0; i < SIZE; i++) {
            data[i] = 0f;
        }
    }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        result.data[0 + 0 * 4] = 1f;
        result.data[1 + 1 * 4] = 1f;
        result.data[2 + 2 * 4] = 1f;
        result.data[3 + 3 * 4] = 1f;
        return result;
    }

    public static Matrix4f translate(Vector3f vector) {
        Matrix4f result = identity();
        result.data[0 + 3 * 4] = vector.x;
        result.data[1 + 3 * 4] = vector.y;
        result.data[2 + 3 * 4] = vector.z;
        return result;
    }

    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f result = identity();
        result.data[0 + 0 * 4] = 2 / (right - left);
        result.data[1 + 1 * 4] = 2 / (top - bottom);
        result.data[2 + 2 * 4] = 2 / (near - far);

        result.data[0 + 3 * 4] = -(right + left) / (right - left);
        result.data[1 + 3 * 4] = -(top + bottom) / (top - bottom);
        result.data[2 + 3 * 4] = -(far + near) / (far - near);

        return result;
    }

    public FloatBuffer asFloatBuffer() {
        return BufferUtils.createFloatBuffer(data);
    }

}
