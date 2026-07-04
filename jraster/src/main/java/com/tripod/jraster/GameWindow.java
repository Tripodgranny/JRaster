package com.tripod.jraster;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class GameWindow {

  private JFrame frame;
  
  public GameWindow(String title) {
    frame = new JFrame(title);
    
    this.frame.setAlwaysOnTop(true);
    this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frame.setLayout(new BorderLayout());
  }
  
  public void setCanvas(GameCanvas canvas) {
    this.frame.add(canvas.getCanvas(), BorderLayout.CENTER);
    
    this.frame.pack();
    this.frame.setResizable(false);
    this.frame.setLocationRelativeTo(null);
    this.frame.setVisible(true);
  }
}