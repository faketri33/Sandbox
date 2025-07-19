package org.faketri.org.events;

import java.util.*;

public class EventDispatcher {
    private final Map<Class<?>, List<EventListener<?>>> listeners = new HashMap<>();
    private final Queue<Event> eventQueue = new LinkedList<>();

    public <T extends Event> void register(Class<T> type, EventListener<T> listener) {
        listeners.computeIfAbsent(type, k -> new ArrayList<>()).add(listener);
    }

    public void post(Event event) {
        eventQueue.add(event);
    }

    @SuppressWarnings("unchecked")
    public void dispatch() {
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            List<EventListener<?>> list = listeners.getOrDefault(event.getClass(), List.of());
            for (EventListener listener : list) {
                listener.onEvent(event);
            }
        }
    }

}
