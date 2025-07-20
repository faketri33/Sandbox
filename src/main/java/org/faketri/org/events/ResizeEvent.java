package org.faketri.org.events;

public class ResizeEvent implements Event{
    public int width, height;

    public ResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
