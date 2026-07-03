package com.tripod.jraster.entity;

import java.util.HashMap;
import java.util.Map;

public class Entity {

  // Simple static counter for unique, sequential IDs
  private static int nextId = 0;
  public final int id;

  // Key: The exact Class type | Value: The component instance
  private final Map<Class<? extends EntityComponent>, EntityComponent> components;

  public Entity() {
    this.id = nextId++;
    this.components = new HashMap<>();
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