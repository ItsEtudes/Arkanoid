package me.etudes.arkanoid.math;

public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f() {
        this(0f, 0f, 0f);
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector3f vector) {
        x = x + vector.x;
        y = y + vector.y;
        z = z + vector.z;
    }

}
