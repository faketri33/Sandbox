package org.faketri.org.core;


import org.faketri.org.events.EventDispatcher;
import org.faketri.org.events.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowsHandler {
    private long window;
    private final EventDispatcher eventDispatcher;

    public WindowsHandler() {
        eventDispatcher = new EventDispatcher();

        init();
        eventsRegister();
    }

    private void init(){
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwWindowHint(org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(720, 400, "Sandbox!", NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glViewport(0, 0, 720, 400);
        GLFW.glfwSwapInterval(1);
    }

    public void show(){
        glfwShowWindow(window);
    }

    public boolean showClose(){
        return glfwWindowShouldClose(window);
    }

    public void update() {
        eventDispatcher.dispatch();
        glfwPollEvents();
        glfwSwapBuffers(window);
    }

    public void close(){
        glfwSetWindowShouldClose(window, true);
    }

    public long getWindow() {
        return window;
    }

    public void cleanup(){
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

    private void eventsRegister(){
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            eventDispatcher.post(new KeyEvent(key, action));
        });

        eventDispatcher.register(KeyEvent.class, event -> {
            if ( event.key() == GLFW_KEY_ESCAPE && event.action() == GLFW_RELEASE )
                close();
        });
    }
}
