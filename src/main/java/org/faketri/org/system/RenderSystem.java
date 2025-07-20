package org.faketri.org.system;

import org.faketri.org.component.MaterialComponent;
import org.faketri.org.component.MeshComponent;
import org.faketri.org.component.TransformComponent;
import org.faketri.org.engine.Shader;
import org.faketri.org.entity.Entity;
import org.faketri.org.entity.EntityManager;
import org.faketri.org.engine.Texture;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class RenderSystem implements System {

    private int width = 1280, height = 720;

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

            Matrix4f view = new Matrix4f().lookAt(new Vector3f(0,0,3), new Vector3f(0,0,0), new Vector3f(0,1,0));
            Matrix4f projection = new Matrix4f().perspective((float)Math.toRadians(60), width/(float)height, 0.1f, 100f);

            shader.setUniform(e.getName(), transform.getModelMatrix());
            shader.setUniform("view", view);
            shader.setUniform("projection", projection);


            Texture texture = material.getTexture();
            if (texture != null)
                texture.bind();

            mesh.getMesh().render();
            shader.unbind();
        }
    }
}
