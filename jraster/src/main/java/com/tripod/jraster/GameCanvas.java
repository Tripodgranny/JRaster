package com.tripod.jraster;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import com.tripod.jraster.entity.SpriteAnimator;
import com.tripod.jraster.graphics.RenderRegistry;
import com.tripod.jraster.graphics.RenderTarget;
import com.tripod.jraster.graphics.renderable.Circle;
import com.tripod.jraster.graphics.renderable.Rect;
import com.tripod.jraster.graphics.renderable.Renderable;
import com.tripod.jraster.graphics.renderable.SpriteRender;

public class GameCanvas implements RenderRegistry {

  private final int WIDTH, HEIGHT, SCALE;

  private Canvas canvas;
  private BufferedImage image;
  private BufferStrategy bs;
  private int[] pixels;

  private ArrayList<RenderTarget> renderTargets = new ArrayList<>();

  public final RenderTarget DEFAULT_TARGET;

  private final ArrayList<Renderable> renderableQueue = new ArrayList<>();

  public GameCanvas(int w, int h, int s) {
    this.WIDTH = w;
    this.HEIGHT = h;
    this.SCALE = s;

    this.canvas = new Canvas();
    Dimension size = new Dimension(w * s, h * s);
    this.canvas.setPreferredSize(size);
    this.canvas.setMinimumSize(size);
    this.canvas.setMaximumSize(size);

    this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    this.pixels = ((DataBufferInt) (image.getRaster().getDataBuffer()))
        .getData();
    
    DEFAULT_TARGET = new RenderTarget("DEFAULT_TARGET", 0, 0, WIDTH, HEIGHT);
    registerRenderTarget(DEFAULT_TARGET);
  }

  @Override
  public void registerRenderTarget(RenderTarget target) {
    renderTargets.add(target);
  }

  @Override
  public void removeRenderTarget(RenderTarget target) {
    renderTargets.remove(target);
  }

  // TODO : move these primitive draw calls somewhere else?
  public void drawRect(double x, double y, int w, int h, int c, boolean outline) {
    renderableQueue.add(new Rect(x, y, w, h, c, outline));
  }

  public void drawCircle(double xc, double yc, int radius, int color, boolean outline) {
    renderableQueue.add(new Circle(xc, yc, radius, color, outline));
  }
  
  public void drawSpriteAnimator(double x, double y, SpriteAnimator spriteAnimator) {
    renderableQueue.add(new SpriteRender(x, y, spriteAnimator));
  }
  // TODO : move these primitive draw calls somewhere else?

  protected Canvas getCanvas() {
    return this.canvas;
  }

  protected void render() {
    if (canvas == null || !this.canvas.isDisplayable())
      return;

    Graphics g = prepareGraphics();

    renderTargets.stream().forEach(rt -> rt.drawTarget());

    // 2. Render deferred primitives on top of the render targets
    for (Renderable prim : renderableQueue) {
      prim.execute(pixels, WIDTH, HEIGHT);
    }

    // Clear the queue so commands don't stack up infinitely across frames
    renderableQueue.clear();

    g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

    g.dispose();
    bs.show();
  }

  private Graphics prepareGraphics() {

    bs = this.canvas.getBufferStrategy();
    if (bs == null) {
      this.canvas.createBufferStrategy(3);
      bs = this.canvas.getBufferStrategy();
    }

    clear();

    return bs.getDrawGraphics();

  }

  private void clear() {
    for (int i = 0; i < WIDTH * HEIGHT; i++) {
      pixels[i] = 0;
    }
  }

}
