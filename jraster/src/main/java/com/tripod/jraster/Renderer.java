package com.tripod.jraster;

import java.util.ArrayList;

import com.tripod.jraster.entity.SpriteAnimator;
import com.tripod.jraster.graphics.fx.animation.PixelEffectAnimation;
import com.tripod.jraster.graphics.fx.animation.PixelEffectAnimationManager;
import com.tripod.jraster.graphics.renderable.Circle;
import com.tripod.jraster.graphics.renderable.Rect;
import com.tripod.jraster.graphics.renderable.Renderable;
import com.tripod.jraster.graphics.renderable.SpriteRender;

public class Renderer {
  
  public final int WIDTH, HEIGHT;
  private final int[] pixels;
  
  private final ArrayList<Renderable> renderables = new ArrayList<>();

  private PixelEffectAnimationManager pixelEffectManager = new PixelEffectAnimationManager();
  
  public Renderer(int width, int height, int[] pixels) {
    this.WIDTH = width;
    this.HEIGHT = height;
    this.pixels = pixels;
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
  
  public void clear() {
    for (int i = 0; i < WIDTH * HEIGHT; i++) {
      pixels[i] = 0;
    }
  }
  
  protected void rasterise() {
    clear();
    
    for (Renderable prim : renderables) {
      prim.execute(pixels, WIDTH, HEIGHT);
    }
    
    renderables.clear();
    
    pixelEffectManager.render(this, pixels);
    
  }

}
