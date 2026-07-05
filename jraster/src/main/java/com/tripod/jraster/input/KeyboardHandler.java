package com.tripod.jraster.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

    private final boolean[] keys = new boolean[256];
    
    // Arrays to track single-press actions (e.g., opening a menu)
    private final boolean[] justPressed = new boolean[256];
    private final boolean[] cantPress = new boolean[256];

    public void update() {
        for (int i = 0; i < keys.length; i++) {
            if (!keys[i]) {
                cantPress[i] = false;
            } else if (justPressed[i]) {
                justPressed[i] = false;
                cantPress[i] = true;
            }
        }
    }

    public boolean isKeyPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) return false;
        return keys[keyCode];
    }

    public boolean isKeyJustPressed(int keyCode) {
        if (keyCode < 0 || keyCode >= keys.length) return false;
        return justPressed[keyCode];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = true;
            
            // If the key wasn't held down previously, register it as a fresh press
            if (!cantPress[keyCode]) {
                justPressed[keyCode] = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode >= 0 && keyCode < keys.length) {
            keys[keyCode] = false;
            cantPress[keyCode] = false;
            justPressed[keyCode] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Unused for game movement, but required by KeyListener interface
    }
}