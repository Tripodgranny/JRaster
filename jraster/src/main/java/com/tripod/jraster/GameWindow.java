package com.tripod.jraster;

import javax.swing.JFrame;

public class GameWindow {

  private JFrame frame;
  
  public GameWindow(String title) {
    frame = new JFrame(title);
    
    this.frame.setResizable(false);
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public void setCanvas(GameCanvas canvas) {
    this.frame.add(canvas.getCanvas());
    this.frame.pack();
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
  }

}
