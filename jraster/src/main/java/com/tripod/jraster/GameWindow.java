package com.tripod.jraster;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import com.tripod.jraster.input.KeyboardHandler;
import com.tripod.jraster.input.MouseHandler;

public class GameWindow {

  private JFrame frame;
  private GameCanvas canvas;

  protected GameWindow(String title, int width, int height, int scale) {
    this.frame = new JFrame(title);
    this.frame.setAlwaysOnTop(true);
    this.frame.setAlwaysOnTop(true);
    this.frame.setDefaultCloseOperation(
        javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
    this.frame.setLayout(new BorderLayout());

    this.canvas = new GameCanvas(width, height, scale);
    this.frame.add(this.canvas.getCanvas(), BorderLayout.CENTER);

    this.frame.pack();
    this.frame.setResizable(false);
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
    this.frame.toFront();

    this.canvas.getCanvas().setFocusable(true);
    this.canvas.getCanvas().requestFocusInWindow();
  }

  protected void addInput(KeyboardHandler kh, MouseHandler mh) {
    this.canvas.getCanvas().addKeyListener(kh);
    this.canvas.getCanvas().addMouseListener(mh);
    this.canvas.getCanvas().addMouseMotionListener(mh);
  }

  protected Renderer getRenderer() {
    return this.canvas.getRenderer();
  }

  protected void displayToScreen() {
    this.canvas.render();
  }

  protected void createCloseGameCallback(Game game) {

    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        // Route the OS window close event to this
        game.closeGame();
      }
    });

  }

  protected void dispose() {
    this.frame.dispose();
  }

}