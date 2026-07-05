package com.tripod.jraster;

import java.awt.Canvas;
import java.awt.Color;
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

  private final ArrayList<Renderable> renderables = new ArrayList<>();

  private PixelEffectAnimationManager pixelEffectManager = new PixelEffectAnimationManager();

  public GameCanvas(int w, int h, int s) {
    this.WIDTH = w;
    this.HEIGHT = h;
    this.SCALE = s;
    Dimension size = new Dimension(w * s, h * s);

    this.canvas = new Canvas();
    this.canvas.setPreferredSize(size);
    
    this.canvas.setBackground(Color.BLACK);

    this.image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
    this.pixels = ((DataBufferInt) (image.getRaster().getDataBuffer()))
        .getData();
  }

  // TODO : move these primitive draw calls somewhere else?
  public void drawRect(double x, double y, int w, int h, int c,
      boolean outline) {
    renderables.add(new Rect(x, y, w, h, c, outline));
  }

  public void drawCircle(double xc, double yc, int radius, int color,
      boolean outline) {
    renderables.add(new Circle(xc, yc, radius, color, outline));
  }

  public void pushSpriteAnimatorToRenderer(double x, double y,
      SpriteAnimator spriteAnimator, int depth) {
    renderables.add(new SpriteRender(x, y, spriteAnimator, depth));
  }
  // END primitive draw calls
  
  // TODO : move pixel effect animation calls somewhere else?
  public void applyPixelEffectAnimation(PixelEffectAnimation fxAnimator) {
    pixelEffectManager.addPixelEffect(fxAnimator);
  }
  // END pixel effect animation

  protected Canvas getCanvas() {
    return this.canvas;
  }

  protected void render() {
    if (canvas == null || !this.canvas.isDisplayable())
      return;

    Graphics g = prepareGraphics();
    if (g == null)
      return;

    // Render your internal software pixel array at its fixed game size
    for (Renderable prim : renderables) {
      prim.execute(pixels, WIDTH, HEIGHT);
    }
    renderables.clear();
    pixelEffectManager.render(this, pixels);

    // --- CROSS PLATFORM SOLUTION (hopefully....) ---
    int canvasW = canvas.getWidth();
    int canvasH = canvas.getHeight();

    // maintain aspect ratio perfectly, filling as much space as possible
    int drawW = canvasW;
    int drawH = (int) (((double) HEIGHT / WIDTH) * canvasW);
    if (drawH > canvasH) {
      drawH = canvasH;
      drawW = (int) (((double) WIDTH / HEIGHT) * canvasH);
    }

    // center the image inside the canvas space
    int xOffset = (canvasW - drawW) / 2;
    int yOffset = (canvasH - drawH) / 2;

    g.drawImage(image, xOffset, yOffset, drawW, drawH, null);
    
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

    clear();

    try {
      return bs.getDrawGraphics();
    } catch (IllegalStateException e) {
      bs = null;
      return null;
    }

  }

  private void clear() {
    for (int i = 0; i < WIDTH * HEIGHT; i++) {
      pixels[i] = 0;
    }
  }

}
