package org.faketri.org.game;

import org.faketri.org.component.MaterialComponent;
import org.faketri.org.component.MeshComponent;
import org.faketri.org.component.TransformComponent;
import org.faketri.org.core.AssetsLoader;
import org.faketri.org.engine.Mesh;
import org.faketri.org.engine.Shader;
import org.faketri.org.engine.Texture;
import org.faketri.org.entity.Entity;
import org.faketri.org.entity.EntityManager;
import org.faketri.org.system.RenderSystem;

public class Scene {
    private final AssetsLoader assetsLoader;
    private final RenderSystem renderer;
    private final EntityManager entityManager;

    public Scene() {
        this.assetsLoader = new AssetsLoader();
        this.renderer = new RenderSystem();
        this.entityManager = new EntityManager();

        entityCreate();
    }

    public void rendere(){
        renderer.clear();
        renderer.render(entityManager);
    }

    private void entityCreate(){
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

        entity.addComponent(transformComponent);
        entity.addComponent(new MeshComponent(mesh));
        entity.addComponent(new MaterialComponent(texture, shader));
    }
}
