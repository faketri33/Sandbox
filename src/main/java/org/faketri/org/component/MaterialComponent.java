package org.faketri.org.component;

import org.faketri.org.engine.Shader;

public class MaterialComponent implements Component {
    private Shader shader;

    public MaterialComponent(Shader shader) {
        this.shader = shader;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }
}
