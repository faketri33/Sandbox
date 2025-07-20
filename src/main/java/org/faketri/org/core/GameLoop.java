package org.faketri.org.core;

import org.faketri.org.game.Scene;

public class GameLoop implements Runnable {
    private final WindowsHandler windowsHandler;

    private final Scene scene;

    public GameLoop() {
        this.windowsHandler = new WindowsHandler();
        this.scene = new Scene();
    }

    @Override
    public void run() {
        while (!windowsHandler.showClose()) update();
        windowsHandler.cleanup();
    }

    private void update(){
        scene.rendere();
        windowsHandler.update();
    }
}
