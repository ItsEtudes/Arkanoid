package me.etudes.arkanoid.graphics;

import me.etudes.arkanoid.math.Matrix4f;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int program;
    private boolean enabled;

    private Map<String, Integer> uniforms = new HashMap<>();

    public Shader(String vertPath, String fragPath) {
        String vertSource = loadSource(vertPath);
        String fragSource = loadSource(fragPath);
        program = create(vertSource, fragSource);
    }

    private int create(String vertSource, String fragSource) {
        int result = glCreateProgram();

        int vert = glCreateShader(GL_VERTEX_SHADER);
        int frag = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vert, vertSource);
        glShaderSource(frag, fragSource);

        glCompileShader(vert);
        if(glGetShaderi(vert, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Could not compile vertex shader");
            System.err.println(glGetShaderInfoLog(vert));
            return -1;
        }

        glCompileShader(frag);
        if(glGetShaderi(frag, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Could not compile fragment shader");
            System.err.println(glGetShaderInfoLog(frag));
            return -1;
        }

        glAttachShader(result, vert);
        glAttachShader(result, frag);

        glLinkProgram(result);
        glValidateProgram(result);

        return result;
    }

    private String loadSource(String path) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String buffer;
            while((buffer = reader.readLine()) != null) {
                result.append(buffer).append("\n");
            }
            reader.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private int getUniform(String name) {
        if(uniforms.containsKey(name)) {
            return uniforms.get(name);
        }
        int location = glGetUniformLocation(program, name);
        uniforms.put(name, location);
        return location;
    }

    public void setUniform1i(String name, int value) {
        if(!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniformMatrix4f(String name, Matrix4f matrix) {
        if(!enabled) enable();
        glUniformMatrix4fv(getUniform(name), false, matrix.asFloatBuffer());
    }

    public void enable() {
        enabled = true;
        glUseProgram(program);
    }

    public void disable() {
        enabled = false;
        glUseProgram(0);
    }

}
