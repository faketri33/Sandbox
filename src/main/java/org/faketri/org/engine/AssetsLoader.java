package org.faketri.org.engine;

import org.faketri.org.entity.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBImage;

import java.io.IOException;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;

public class AssetsLoader {
    private final Map<String, Mesh> meshCache = new HashMap<>();
    private final Map<String, Texture> textureCache = new HashMap<>();

    public Mesh loadMesh(String path) {
        if (meshCache.containsKey(path)) return meshCache.get(path);
        try {
            Mesh mesh = OBJModelLoader.loadOBJ(path);
            meshCache.put(path, mesh);
            return mesh;
        } catch (IOException e) {
            throw new RuntimeException("Failed to load mesh: " + path, e);
        }
    }

    public Texture loadTexture(String path) {
        if (textureCache.containsKey(path)) return textureCache.get(path);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);

        STBImage.stbi_set_flip_vertically_on_load(true);
        var data = STBImage.stbi_load(path, width, height, channels, 4);
        if (data == null) throw new RuntimeException("Failed to load texture: " + path);

        int textureId = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textureId);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0,
                GL_RGBA, GL_UNSIGNED_BYTE, data);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        STBImage.stbi_image_free(data);
        Texture texture = new Texture(textureId);
        textureCache.put(path, texture);
        return texture;
    }


}
