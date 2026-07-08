package com.tripod.jraster;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.tripod.jraster.input.KeyboardHandler;
import com.tripod.jraster.input.MouseHandler;

public class GameCanvas {

  public final int WIDTH, HEIGHT, SCALE;
  private int lastWidth, lastHeight;

  private Canvas canvas;
  private BufferedImage image;
  private BufferStrategy bs;
  private Renderer renderer;

  private int[] pixels;

  public GameCanvas(int w, int h, int s) {

    this.WIDTH = w;
    this.HEIGHT = h;
    this.SCALE = s;
    Dimension size = new Dimension(w * s, h * s);
    this.canvas = new Canvas();
    this.canvas.setPreferredSize(size);
    this.canvas.setBackground(Color.BLACK);
    this.lastWidth = this.canvas.getWidth();
    this.lastHeight = this.canvas.getHeight();
    this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    this.pixels = ((DataBufferInt) (image.getRaster().getDataBuffer()))
        .getData();
    this.renderer = new Renderer(w, h, pixels);

  }

  protected Canvas getCanvas() {
    return this.canvas;
  }

  protected Renderer getRenderer() {
    return this.renderer;
  }

  protected void addInput(KeyboardHandler kh, MouseHandler mh) {
    this.canvas.addKeyListener(kh);
    this.canvas.addMouseListener(mh);
    this.canvas.addMouseMotionListener(mh);
  }

  protected void render() {
    if (canvas == null || !this.canvas.isDisplayable())
      return;

    Graphics g = prepareGraphics();
    if (g == null)
      return;

    renderer.rasterise();

    // --- CROSS PLATFORM SOLUTION (hopefully....) ---
    int canvasW = canvas.getWidth();
    int canvasH = canvas.getHeight();

    if (canvasW != lastWidth || canvasH != lastHeight) {
      // maintain aspect ratio perfectly, filling as much space as possible
      int drawW = canvasW;
      int drawH = (int) (((double) HEIGHT / WIDTH) * canvasW);
      if (drawH > canvasH) {
        drawH = canvasH;
        drawW = (int) (((double) WIDTH / HEIGHT) * canvasH);
      }

      int xOffset = (canvasW - drawW) / 2;
      int yOffset = (canvasH - drawH) / 2;

      g.drawImage(image, xOffset, yOffset, drawW, drawH, null);

    } else {
      g.drawImage(image, 0, 0, lastWidth, lastHeight, null);
    }

    lastWidth = canvasW;
    lastHeight = canvasH;

    g.dispose();

    if (!bs.contentsLost()) {
      bs.show();
    } else {
      bs = null;
    }
  }

  private Graphics prepareGraphics() {

    bs = this.canvas.getBufferStrategy();

    if (bs == null) {
      this.canvas.createBufferStrategy(3);
      bs = this.canvas.getBufferStrategy();
    }

    try {
      return bs.getDrawGraphics();
    } catch (IllegalStateException e) {
      bs = null;
      return null;
    }

  }

}
