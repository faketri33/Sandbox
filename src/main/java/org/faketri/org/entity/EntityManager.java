package org.faketri.org.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {
    private final Map<Integer, Entity> entities = new HashMap<>();
    private Integer nextId = 0;

    public Entity createEntity(String name) {
        Entity e = new Entity(nextId++, name);
        entities.put(e.getId(), e);
        return e;
    }

    public Map<Integer, Entity> getEntities() {
        return entities;
    }
}
