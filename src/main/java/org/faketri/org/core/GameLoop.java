package org.faketri.org.core;

import org.faketri.org.component.SquareRenderComponent;
import org.faketri.org.engine.Renderer;
import org.faketri.org.entity.Entity;
import org.faketri.org.entity.EntityManager;

public class GameLoop implements Runnable {
    private final WindowsHandler windowsHandler;

    private final Renderer renderer;
    private final EntityManager entityManager;

    public GameLoop() {
        this.windowsHandler = new WindowsHandler();
        this.renderer = new Renderer(720, 400);

        entityManager = new EntityManager();
        Entity entity = entityManager.createEntity();
        entity.addComponent(new SquareRenderComponent());
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
        renderer.renderer(entityManager);
        windowsHandler.update();
    }

}
