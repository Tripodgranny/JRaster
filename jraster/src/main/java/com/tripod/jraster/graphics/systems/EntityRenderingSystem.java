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

    public void updateAndRender(List<Entity> entities) {
        for (Entity entity : entities) {
            Transform transform = entity.getComponent(Transform.class);
            SpriteAnimator animator = entity.getComponent(SpriteAnimator.class);

            // If an entity has both a position and a sprite, we process it
            if (transform != null && animator != null) {
                // 1. Update the component animation state
                animator.update();

                // 2. Queue it to the canvas for drawing
                canvas.drawSpriteAnimator(transform.x, transform.y, animator);
            }
        }
    }
}