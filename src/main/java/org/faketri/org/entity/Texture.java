package org.faketri.org.entity;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

public class Texture {
    private final int id;

    public Texture(int id) {
        this.id = id;
    }

    public void bind() {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public int getId() {
        return id;
    }
}
