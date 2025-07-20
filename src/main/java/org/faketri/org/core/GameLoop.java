package org.faketri.org.core;

import org.faketri.org.component.MaterialComponent;
import org.faketri.org.component.MeshComponent;
import org.faketri.org.component.TransformComponent;
import org.faketri.org.engine.AssetsLoader;
import org.faketri.org.engine.Mesh;
import org.faketri.org.entity.Entity;
import org.faketri.org.entity.EntityManager;
import org.faketri.org.entity.Shader;
import org.faketri.org.entity.Texture;
import org.faketri.org.system.RenderSystem;

import static org.lwjgl.opengl.GL11.*;

public class GameLoop implements Runnable {
    private final WindowsHandler windowsHandler;

    private final AssetsLoader assetsLoader;
    private final RenderSystem renderer;
    private final EntityManager entityManager;

    public GameLoop() {
        this.windowsHandler = new WindowsHandler();
        this.renderer = new RenderSystem(1280, 720);
        this.assetsLoader = new AssetsLoader();
        entityManager = new EntityManager();

        glDisable(GL_CULL_FACE);

        createFox();
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

    private void createFox(){
        entityManager.createEntity("fox");
        Mesh mesh = assetsLoader.loadMesh("C:\\Users\\faket\\Downloads\\Fox-bl.obj");

        String vert = """
            #version 330 core
                
            layout(location = 0) in vec3 aPos;
            layout(location = 1) in vec2 aTexCoord;
        
            uniform mat4 fox;
            uniform mat4 view;
            uniform mat4 projection;
        
            out vec2 TexCoord;
        
            void main() {
                gl_Position = projection * view * fox * vec4(aPos, 1.0);
                TexCoord = aTexCoord;
            }
                
        """;

        String frag = """
            #version 330 core
            in vec2 TexCoord;
            out vec4 FragColor;
            
            uniform sampler2D fox_tex;
            
            void main() {
                FragColor = texture(fox_tex, TexCoord);
            }
        """;

        Shader shader = new Shader(vert, frag);
        Texture texture = assetsLoader.loadTexture("C:\\Users\\faket\\Downloads\\Fox_texture.png");

        mesh.uploadToGPU();

        Entity entity = entityManager.createEntity("fox");

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.setZ(-3.5f);
        transformComponent.setY(-2.5f);
        /*        transformComponent.setScale(0.1f);*/

        entity.addComponent(transformComponent);
        entity.addComponent(new MeshComponent(mesh));
        entity.addComponent(new MaterialComponent(texture, shader));
    }
}
