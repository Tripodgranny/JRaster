package com.tripod.jraster.entity;

import java.util.HashMap;
import java.util.Map;

public class Entity {

  private static int nextId = 0;
  public final int id;

  private int depth = 0; 

  private final Map<Class<? extends EntityComponent>, EntityComponent> components;

  public Entity(int x, int y, int depth) {
    this.id = nextId++;
    this.components = new HashMap<>();
    this.depth = depth;
    
    Transform transform = new Transform(x, y);
    this.addComponent(transform);
  }

  public int getDepth() {
    return this.depth;
  }

  public void setDepth(int depth) {
    this.depth = depth;
  }

  // O(1) Insertion
  public <T extends EntityComponent> void addComponent(T component) {
    this.components.put(component.getClass(), component);
  }

  // O(1) Retrieval
  public <T extends EntityComponent> T getComponent(Class<T> componentClass) {
    return componentClass.cast(this.components.get(componentClass));
  }

  // O(1) Deletion
  public <T extends EntityComponent> void removeComponent(Class<T> componentClass) {
    this.components.remove(componentClass);
  }
}