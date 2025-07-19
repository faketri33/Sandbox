package org.faketri.org.engine;

import org.faketri.org.component.Component;
import org.faketri.org.component.SquareRenderComponent;
import org.faketri.org.entity.EntityManager;

import static org.lwjgl.opengl.ARBVertexArrayObject.glBindVertexArray;
import static org.lwjgl.opengl.GL11.*;

public class Renderer{
    private final int width, height;
    public Renderer(int width, int height) {
        this.width = width; this.height = height;
        glViewport(0,0,width,height);
        glEnable(GL_DEPTH_TEST);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void renderMesh(int vao, int vertexCount) {
        glBindVertexArray(vao);
        glDrawElements(GL_TRIANGLES, vertexCount, GL_UNSIGNED_INT, 0);
        glBindVertexArray(0);
    }

    public void renderer(EntityManager em){
        em.getEntities().values().forEach(e -> e.getComponent(SquareRenderComponent.class).render());
    }
}
