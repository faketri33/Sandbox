package org.faketri.org.core;

import org.faketri.org.engine.Mesh;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class OBJModelLoader {
    public static Mesh loadOBJ(String path) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(path));

        List<float[]> positions = new ArrayList<>();
        List<float[]> texCoords = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<float[]> finalVertices = new ArrayList<>();
        List<float[]> finalTex = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.trim().split("\\s+");
            switch (tokens[0]) {
                case "v" -> positions.add(new float[]{Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])});
                case "vt" -> texCoords.add(new float[]{Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])});
                case "f" -> {
                    for (int i = 1; i <= 3; i++) {
                        String[] parts = tokens[i].split("[/ ]");
                        int vi = Integer.parseInt(parts[0]) - 1;
                        int ti = Integer.parseInt(parts[1]) - 1;
                        finalVertices.add(positions.get(vi));
                        finalTex.add(texCoords.get(ti));
                        indices.add(indices.size());
                    }
                }
            }
        }

        return new Mesh(
            flatten(finalVertices), flatten(finalTex), indices.stream().mapToInt(i -> i).toArray()
        );
    }

    private static float[] flatten(List<float[]> list) {
        float[] out = new float[list.size() * list.get(0).length];
        int idx = 0;
        for (float[] arr : list)
            for (float f : arr) out[idx++] = f;
        return out;
    }

}
