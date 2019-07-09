package me.etudes.arkanoid.input;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard extends GLFWKeyCallback {

    private static int[] keys = new int[349];

    public void invoke(long window, int key, int scanCode, int action, int mods) {
        keys[key] = action;
    }

    public static boolean isKeyDown(int key) {
        return keys[key] != GLFW_RELEASE;
    }

    public static boolean isKeyPressed(int key) {
        return keys[key] == GLFW_PRESS;
    }

    public static boolean isKeyReleased(int key) {
        return keys[key] == GLFW_RELEASE;
    }

}
