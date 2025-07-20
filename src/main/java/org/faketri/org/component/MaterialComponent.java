package org.faketri.org.component;

import org.faketri.org.engine.Shader;
import org.faketri.org.engine.Texture;

public class MaterialComponent implements Component {
    private Texture texture;
    private Shader shader;

    public MaterialComponent() {
    }

    public MaterialComponent(Shader shader) {
        this.shader = shader;
    }

    public MaterialComponent(Texture texture) {
        this.texture = texture;
    }

    public MaterialComponent(Texture texture, Shader shader) {
        this.texture = texture;
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}
