package org.faketri.org.engine;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    private int vaoId;
    private int vboId;
    private int tboId;
    private int eboId;
    private int vertexCount;

    private float[] vertices;
    private float[] texCoords;
    private int[] indices;

    public Mesh(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
        this.vertexCount = indices.length;
    }

    public Mesh(float[] vertices, float[] texCoords, int[] indices) {
        this.vertices = vertices;
        this.texCoords = texCoords;
        this.indices = indices;
        this.vertexCount = indices.length;
    }

    public void uploadToGPU() {
        vaoId = glGenVertexArrays();
        glBindVertexArray(vaoId);

        // Vertex positions
        vboId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);

        // Texture coordinates
        if (texCoords != null) {
            tboId = glGenBuffers();
            glBindBuffer(GL_ARRAY_BUFFER, tboId);
            glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
            glEnableVertexAttribArray(1);
        }

        // Indices
        eboId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        // Unbind
        glBindVertexArray(0);

        // Освобождение данных CPU-стороны
        this.vertices = null;
        this.texCoords = null;
        this.indices = null;
    }

    public void render() {
        glBindVertexArray(vaoId);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void cleanup() {
        glDeleteBuffers(vboId);
        glDeleteBuffers(tboId);
        glDeleteBuffers(eboId);
        glDeleteVertexArrays(vaoId);
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }

    public float[] getVertices() {
        return vertices;
    }

    public void setVertices(float[] vertices) {
        this.vertices = vertices;
    }

    public float[] getTexCoords() {
        return texCoords;
    }

    public void setTexCoords(float[] texCoords) {
        this.texCoords = texCoords;
    }

    public int[] getIndices() {
        return indices;
    }

    public void setIndices(int[] indices) {
        this.indices = indices;
    }
}
