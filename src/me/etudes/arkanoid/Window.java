package me.etudes.arkanoid;

import me.etudes.arkanoid.input.Keyboard;
import org.lwjgl.glfw.GLFWVidMode;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

    long window;

    public Window(int width, int height, String title) {

        if(!glfwInit()) {
            System.err.println("Could not initialize GLFW");
        }

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        glfwSetKeyCallback(window, new Keyboard());
        glfwSetWindowSizeCallback(window, (window, dwidth, dheight) -> {
            Main.setWidth(dwidth);
            Main.setHeight(dheight);
            glViewport(0, 0, dwidth, dheight);
        });

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        int xpos = (vidMode.width() - width) / 2;
        int ypos = (vidMode.height() - height) / 2;
        glfwSetWindowPos(window, xpos, ypos);

        glfwMakeContextCurrent(window);
        glfwShowWindow(window);

    }

    public void update() {
        glfwPollEvents();
    }

    public void render() {
        glfwSwapBuffers(window);
    }

    public void setTitle(String title) {
        glfwSetWindowTitle(window, title);
    }

    public boolean isCloseRequested() {
        return glfwWindowShouldClose(window);
    }

}
