package org.faketri.org.system;

import org.faketri.org.component.MaterialComponent;
import org.faketri.org.component.MeshComponent;
import org.faketri.org.component.TransformComponent;
import org.faketri.org.engine.Shader;
import org.faketri.org.entity.Entity;
import org.faketri.org.entity.EntityManager;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

public class RenderSystem implements System {

    private final int width, height;

    public RenderSystem(int width, int height) {
        this.width = width; this.height = height;
        glViewport(0,0,width,height);
        glEnable(GL_DEPTH_TEST);
    }

    public void clear() {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    }

    public void render(EntityManager em) {
        for (Entity e : em.getEntities().values()) {
            MeshComponent mesh = e.getComponent(MeshComponent.class);
            TransformComponent transform = e.getComponent(TransformComponent.class);
            MaterialComponent material = e.getComponent(MaterialComponent.class);

            if (mesh == null || transform == null || material == null) continue;

            Shader shader = material.getShader();
            shader.bind();
            shader.setUniform("uModel", transform.getModelMatrix());
            mesh.getMesh().render();
            shader.unbind();
        }
    }
}
