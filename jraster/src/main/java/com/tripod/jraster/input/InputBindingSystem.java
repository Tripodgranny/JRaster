package com.tripod.jraster.input;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class InputBindingSystem {
    
    // Maps an Action Name (e.g., "Attack") to a Set of arbitrary inputs
    private final HashMap<String, Set<InputEvent>> bindings = new HashMap<>();
    
    private final KeyboardHandler keyHandler;
    private final MouseHandler mouseHandler;

    public InputBindingSystem(KeyboardHandler kh, MouseHandler mh) {
        this.keyHandler = kh;
        this.mouseHandler = mh;
    }

    /**
     * Binds a physical input event to an abstract action string name.
     */
    public void bind(String actionName, InputEvent event) {
        bindings.computeIfAbsent(actionName, k -> new HashSet<>()).add(event);
    }

    /**
     * Checks if the bound action is being continuously held down.
     */
    public boolean isActionPressed(String actionName) {
        Set<InputEvent> events = bindings.get(actionName);
        if (events == null) return false;

        for (InputEvent event : events) {
            if (event.isPressed(keyHandler, mouseHandler)) {
                return true; // Return true immediately if any bound input matches
            }
        }
        return false;
    }

    /**
     * Checks if the bound action was triggered on this exact frame tick.
     */
    public boolean isActionJustPressed(String actionName) {
        Set<InputEvent> events = bindings.get(actionName);
        if (events == null) return false;

        for (InputEvent event : events) {
            if (event.isJustPressed(keyHandler, mouseHandler)) {
                return true;
            }
        }
        return false;
    }
}
