package org.faketri.org.component;

import org.joml.Matrix4f;

public class TransformComponent implements Component{

    private float x, y, z, rotation, scale = 1.0f;

    public Matrix4f getModelMatrix() {
        return new Matrix4f()
                .translate(x, y, z)
                .rotateZ(rotation)
                .scale(scale);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
