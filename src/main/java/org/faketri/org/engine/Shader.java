package org.faketri.org.engine;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private final int id;

    public Shader(String vertexSource, String fragmentSource) {
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

    public void bind() { glUseProgram(id); }
    public void unbind() { glUseProgram(0); }

    public void setUniform(String name, Matrix4f value) {
        int loc = glGetUniformLocation(id, name);
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buffer = stack.mallocFloat(16);
            value.get(buffer);
            glUniformMatrix4fv(loc, false, buffer);
        }
    }
}
