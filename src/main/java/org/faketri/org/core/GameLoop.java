package org.faketri.org.core;

import org.faketri.org.component.MaterialComponent;
import org.faketri.org.component.MeshComponent;
import org.faketri.org.component.TransformComponent;
import org.faketri.org.engine.Mesh;
import org.faketri.org.engine.Shader;
import org.faketri.org.entity.Entity;
import org.faketri.org.entity.EntityManager;
import org.faketri.org.system.RenderSystem;

public class GameLoop implements Runnable {
    private final WindowsHandler windowsHandler;

    private final RenderSystem renderer;
    private final EntityManager entityManager;

    public GameLoop() {
        this.windowsHandler = new WindowsHandler();
        this.renderer = new RenderSystem(720, 400);

        entityManager = new EntityManager();

        float[] vertices = {
                -0.5f,  0.5f,
                0.5f,  0.5f,
                0.5f, -0.5f,
                -0.5f, -0.5f
        };
        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        Mesh mesh = new Mesh(vertices, indices);

        String vertSrc = """
            #version 330 core
            layout (location = 0) in vec2 aPos;
            uniform mat4 uModel;
            void main() {
                gl_Position = uModel * vec4(aPos, 0.0, 1.0);
            }
        """;
        String fragSrc = """
            #version 330 core
            out vec4 FragColor;
            void main() {
                FragColor = vec4(1.0, 0.0, 0.0, 1.0); // Красный
            }
        """;

        Shader shader = new Shader(vertSrc, fragSrc);

        Entity entity = entityManager.createEntity("Square");
        entity.addComponent(new TransformComponent());
        entity.addComponent(new MeshComponent(mesh));
        entity.addComponent(new MaterialComponent(shader));

    }

    @Override
    public void run() {
        while (!windowsHandler.showClose()){
            update();
        }
        windowsHandler.cleanup();
    }

    private void update(){
        renderer.clear();
        renderer.render(entityManager);
        windowsHandler.update();
    }

}
