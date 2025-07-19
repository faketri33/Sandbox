package org.faketri.org.component;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import org.lwjgl.system.MemoryUtil;

public class SquareRenderComponent implements Component{

    private int vaoId;
    private int vboId;
    private int shaderProgram;

    private boolean initialized = false;

    public void init() {
        // Вершины квадрата (2D)
        float[] vertices = {
                -0.5f,  0.5f,  // верхний левый
                0.5f,  0.5f,  // верхний правый
                0.5f, -0.5f,  // нижний правый
                -0.5f, -0.5f   // нижний левый
        };

        // Создаём VAO
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Создаём VBO
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        FloatBuffer vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
        vertexBuffer.put(vertices).flip();
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
        MemoryUtil.memFree(vertexBuffer);

        // Указываем формат вершин
        glVertexAttribPointer(0, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);
        glEnableVertexAttribArray(0);

        // Отвязка
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindVertexArray(0);

        // Создаём шейдеры
        shaderProgram = createShaderProgram();

        initialized = true;
    }

    private int createShaderProgram() {
        String vertexShaderSource = """
            #version 330 core
            layout(location = 0) in vec2 position;
            void main() {
                gl_Position = vec4(position, 0.0, 1.0);
            }
            """;

        String fragmentShaderSource = """
            #version 330 core
            out vec4 FragColor;
            void main() {
                FragColor = vec4(1.0, 0.0, 0.0, 1.0); // красный цвет
            }
            """;

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, vertexShaderSource);
        glCompileShader(vertexShader);
        checkCompileErrors(vertexShader, "VERTEX");

        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShader, fragmentShaderSource);
        glCompileShader(fragmentShader);
        checkCompileErrors(fragmentShader, "FRAGMENT");

        int program = glCreateProgram();
        glAttachShader(program, vertexShader);
        glAttachShader(program, fragmentShader);
        glLinkProgram(program);
        checkCompileErrors(program, "PROGRAM");

        glDeleteShader(vertexShader);
        glDeleteShader(fragmentShader);

        return program;
    }

    private void checkCompileErrors(int shader, String type) {
        int success;
        if ("PROGRAM".equals(type)) {
            success = glGetProgrami(shader, GL_LINK_STATUS);
            if (success == GL_FALSE) {
                System.err.println("ERROR::PROGRAM_LINKING_ERROR of type: " + type);
                System.err.println(glGetProgramInfoLog(shader));
            }
        } else {
            success = glGetShaderi(shader, GL_COMPILE_STATUS);
            if (success == GL_FALSE) {
                System.err.println("ERROR::SHADER_COMPILATION_ERROR of type: " + type);
                System.err.println(glGetShaderInfoLog(shader));
            }
        }
    }

    public void render() {
        if (!initialized) {
            init();
        }
        glUseProgram(shaderProgram);
        glBindVertexArray(vaoId);
        glDrawArrays(GL_TRIANGLE_FAN, 0, 4);
        glBindVertexArray(0);
        glUseProgram(0);
    }
}
