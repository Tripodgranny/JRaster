package com.tripod.jraster;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import com.tripod.jraster.entity.SpriteAnimator;
import com.tripod.jraster.graphics.fx.animation.PixelEffectAnimation;
import com.tripod.jraster.graphics.fx.animation.PixelEffectAnimationManager;
import com.tripod.jraster.graphics.renderable.Circle;
import com.tripod.jraster.graphics.renderable.Rect;
import com.tripod.jraster.graphics.renderable.Renderable;
import com.tripod.jraster.graphics.renderable.SpriteRender;

public class GameCanvas {

  public final int WIDTH, HEIGHT, SCALE;

  private Canvas canvas;
  private BufferedImage image;
  private BufferStrategy bs;
  private int[] pixels;

  private final ArrayList<Renderable> renderableQueue = new ArrayList<>();
  
  private PixelEffectAnimationManager pixelEffectManager = new PixelEffectAnimationManager();

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

  }

  // TODO : move these primitive draw calls somewhere else?
  public void drawRect(double x, double y, int w, int h, int c,
      boolean outline) {
    renderableQueue.add(new Rect(x, y, w, h, c, outline));
  }

  public void drawCircle(double xc, double yc, int radius, int color,
      boolean outline) {
    renderableQueue.add(new Circle(xc, yc, radius, color, outline));
  }

  public void drawSpriteAnimator(double x, double y,
      SpriteAnimator spriteAnimator, int depth) {
    renderableQueue.add(new SpriteRender(x, y, spriteAnimator, depth));
  }
  // TODO : move these primitive draw calls somewhere else?

  protected Canvas getCanvas() {
    return this.canvas;
  }
  
  public void applyPixelEffectAnimation(PixelEffectAnimation fxAnimator) {
    pixelEffectManager.addPixelEffect(fxAnimator);
  }

  protected void render() {
    if (canvas == null || !this.canvas.isDisplayable())
      return;

    Graphics g = prepareGraphics();

    // Render entities and primitives
    for (Renderable prim : renderableQueue) {
      prim.execute(pixels, WIDTH, HEIGHT);
    }
    
    renderableQueue.clear();
    // End Render entities and primitives
    

   pixelEffectManager.render(this, pixels);
    
    
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
