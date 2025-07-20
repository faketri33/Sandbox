package org.faketri.org.engine;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int id;
    private final String vertexSource, fragmentSource;
    public Shader(String vertexSource, String fragmentSource) {
        this.fragmentSource = fragmentSource;
        this.vertexSource = vertexSource;
    }

    private void init(){
        int vertex = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertex, vertexSource);
        glCompileShader(vertex);

        int fragment = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragment, fragmentSource);
        glCompileShader(fragment);

        id = glCreateProgram();
        glAttachShader(id, vertex);
        glAttachShader(id, fragment);
        glLinkProgram(id);

        glDeleteShader(vertex);
        glDeleteShader(fragment);
    }

    public void bind() {
        if (id == 0) init();
        glUseProgram(id);
    }
    public void unbind() { glUseProgram(0); }

    public void setUniform(String name, Matrix4f value) {
        if (id == 0) init();
        int loc = glGetUniformLocation(id, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            glUniformMatrix4fv(loc, false, buffer);
        }
    }
    public void setUniform(String name, int value) {
        if (id == 0) init();
        int location = glGetUniformLocation(id, name);
        if (location != -1) {
            glUniform1i(location, value);
        } else {
            System.err.println("Uniform " + name + " not found!");
        }
    }

}
