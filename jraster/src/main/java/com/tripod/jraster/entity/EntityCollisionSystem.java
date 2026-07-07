package com.tripod.jraster.entity;

import java.util.ArrayList;

public class EntityCollisionSystem {

  private ArrayList<Entity> entities;

  protected EntityCollisionSystem(ArrayList<Entity> entities) {
    this.entities = entities;
  }

  public boolean willCollide(Entity entity, double offsetX, double offsetY) {
    BoxCollider actorCollider = entity.getComponent(BoxCollider.class);

    if (actorCollider == null)
      return false;

    double[] predicted = actorCollider.getPredictedBounds(offsetX, offsetY);
    double left = predicted[0];
    double right = predicted[1];
    double top = predicted[2];
    double bottom = predicted[3];

    for (Entity other : entities) {
      if (other == entity)
        continue; // do not check against self

      BoxCollider otherCollider = other.getComponent(BoxCollider.class);
      if (otherCollider == null)
        continue;

      boolean intersects = left < otherCollider.getRight()
          && right > otherCollider.getLeft() && top < otherCollider.getBottom()
          && bottom > otherCollider.getTop();

      if (intersects) {
        return true;
      }
    }
    
    return false;
  }

}
