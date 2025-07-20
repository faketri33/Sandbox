package org.faketri.org.component;

import org.faketri.org.engine.Mesh;

public class MeshComponent implements Component{
    private Mesh mesh;

    public MeshComponent(Mesh mesh) {
        this.mesh = mesh;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}
