package com.tripod.jraster;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class GameWindow {

  private JFrame frame;

  protected GameWindow(String title) {
    frame = new JFrame(title);

    this.frame.setAlwaysOnTop(true);
    frame.setDefaultCloseOperation(
        javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

    this.frame.setLayout(new BorderLayout());
  }

  protected void setCanvas(GameCanvas canvas) {
    this.frame.add(canvas.getCanvas(), BorderLayout.CENTER);

    this.frame.pack();
    this.frame.setResizable(false);
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
    this.frame.toFront();
    canvas.getCanvas().setFocusable(true);
    canvas.getCanvas().requestFocusInWindow();
  }

  protected void createCloseGameCallback(Game game) {

    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {

        // Route the OS window close event right into your clean shutdown method
        game.closeGame();
      }
    });

  }

  protected void dispose() {
    this.frame.dispose();
  }

}