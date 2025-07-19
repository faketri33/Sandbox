package org.faketri.org.events;

public interface EventListener<T extends Event> {
    void onEvent(T event);
}
