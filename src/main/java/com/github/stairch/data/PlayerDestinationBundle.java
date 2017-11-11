package com.github.stairch.data;

import java.awt.*;

public class PlayerDestinationBundle {
    private final Point currentPosition;
    private final Point destination;

    public PlayerDestinationBundle(Point player, Point destination) {
        this.currentPosition = player;
        this.destination = destination;
    }

    public Point getCurrentPosition() {
        return currentPosition;
    }

    public Point getDestination() {
        return destination;
    }
}
