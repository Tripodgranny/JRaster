package com.tripod.jraster;

import java.util.ArrayList;

import com.tripod.jraster.asset.GameAssetManager;
import com.tripod.jraster.entity.Entity;
import com.tripod.jraster.graphics.systems.EntityRenderingSystem;

public abstract class Game implements Runnable {

  private final GameAssetManager assetManager;
  
  private ArrayList<Entity> entities = new ArrayList<>();
  private EntityRenderingSystem entityRenderingSystem;

  private static final double DESIRED_UPS = 60.0;
  private static final double DESIRED_FPS = 60.0;

  private final GameWindow window;
  private final GameCanvas canvas;
  private Thread thread;

  private double deltaTime = 0;
  private volatile boolean running = false;

  public Game(String title, int width, int height, int scale) {

    this.window = new GameWindow(title);
    this.canvas = new GameCanvas(width, height, scale);
    this.window.setCanvas(canvas);
    this.assetManager = new GameAssetManager();

    loadResources();
    
    entityRenderingSystem = new EntityRenderingSystem(this.canvas);

    thread = new Thread(this);
    thread.start();

  }
  
  public void addEntity(Entity e) {
    this.entities.add(e);
  }
  
  public void removeEntity(Entity e) {
    this.entities.remove(e);
  }
  
  public GameAssetManager getGameAssetManager() {
    return this.assetManager;
  }

  public double getDeltaTime() {
    return this.deltaTime;
  }

  public GameCanvas getGameCanvas() {
    return this.canvas;
  }

  public GameWindow getGameWindow() {
    return this.window;
  }

  protected abstract void loadResources();
  protected abstract void update();
  protected abstract void render();
  
  @Override
  public void run() {
    System.out.println("Thread started");
    running = true;
    long lastTime = System.nanoTime();
    double nsPerTick = 1000000000 / DESIRED_UPS;
    double nsPerFrame = 1000000000 / DESIRED_FPS;
    double deltaTicks = 0;
    double deltaFrames = 0;
    long timer = System.currentTimeMillis();
    int frames = 0;
    int updates = 0;

    while (running) {
      long now = System.nanoTime();
      long passedTime = now - lastTime;
      lastTime = now;

      deltaTime = Math.min(passedTime / 1000000000.0, 0.1);

      deltaTicks += passedTime / nsPerTick;
      deltaFrames += passedTime / nsPerFrame;

      while (deltaTicks >= 1) {
        entityRenderingSystem.updateAndRender(this.entities);
        update();
        updates++;
        deltaTicks--;
      }
      if (deltaFrames >= 1) {
        canvas.render();
        render();
        frames++;
        deltaFrames--;
      }
      try {
        Thread.sleep(2);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (System.currentTimeMillis() - timer > 1000) {
        timer += 1000;
        System.out.println("FPS: " + frames + " | UPS: " + updates);
        frames = 0;
        updates = 0;
      }
    }
  }

}
