package org.faketri.org.entity;

import org.faketri.org.component.Component;

import java.util.HashMap;
import java.util.Map;

public class Entity {
    private final int id;
    private final String name;

    public Entity(int id, String name) { this.id = id;
        this.name = name;
    }

    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    public <T extends Component> void addComponent(T comp) { components.put(comp.getClass(), comp); }
    public <T extends Component> T getComponent(Class<T> c) { return c.cast(components.get(c)); }
    public boolean hasComponent(Class<? extends Component> c) { return components.containsKey(c); }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
