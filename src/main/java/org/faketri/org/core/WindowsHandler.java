package org.faketri.org.core;


import org.faketri.org.events.EventDispatcher;
import org.faketri.org.events.KeyEvent;
import org.faketri.org.events.ResizeEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowsHandler {
    private long window;
    private int width = 1280;
    private int height = 720;
    private final EventDispatcher eventDispatcher;

    public WindowsHandler() {
        eventDispatcher = new EventDispatcher();

        init();
        updateViewPort();
        eventsRegister();
    }

    private void init(){
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwWindowHint(org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(width, height, "Sandbox!", NULL, NULL);

        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glViewport(0, 0, width, height);
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


    public void cleanup(){
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
    }

    private void eventsRegister(){
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            eventDispatcher.post(new KeyEvent(key, action));
        });
        glfwSetFramebufferSizeCallback(window, (win, width, height) -> {
            eventDispatcher.post(new ResizeEvent(width, height));
        });

        eventDispatcher.register(KeyEvent.class, event -> {
            if ( event.key() == GLFW_KEY_ESCAPE && event.action() == GLFW_RELEASE )
                close();
        });

        eventDispatcher.register(ResizeEvent.class, event -> {
            this.width = event.width;
            this.height = event.height;
            updateViewPort();
        });
    }

    private void updateViewPort(){
        glViewport(0, 0, width, height);
        glEnable(GL_DEPTH_TEST);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
