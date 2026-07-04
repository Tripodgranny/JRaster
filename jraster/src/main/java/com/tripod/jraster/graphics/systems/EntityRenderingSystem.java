package com.tripod.jraster.graphics.systems;

import java.util.List;

import com.tripod.jraster.GameCanvas;
import com.tripod.jraster.entity.Entity;
import com.tripod.jraster.entity.SpriteAnimator;
import com.tripod.jraster.entity.Transform;

public class EntityRenderingSystem {

  private final GameCanvas canvas;

  public EntityRenderingSystem(GameCanvas canvas) {
    this.canvas = canvas;
  }

  public void update(List<Entity> entities) {
    for (Entity entity : entities) {
      Transform transform = entity.getComponent(Transform.class);
      SpriteAnimator animator = entity.getComponent(SpriteAnimator.class);

      if (transform != null && animator != null) {
        animator.update();
        canvas.drawSpriteAnimator(transform.x, transform.y, animator, entity.getDepth());
      }
    }
  }
  
}