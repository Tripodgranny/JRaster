package com.tripod.jraster;

import com.tripod.jraster.asset.GameAssetSystem;
import com.tripod.jraster.entity.Entity;
import com.tripod.jraster.entity.EntityCollisionSystem;
import com.tripod.jraster.entity.EntityManager;
import com.tripod.jraster.input.InputBindingSystem;
import com.tripod.jraster.input.KeyboardHandler;
import com.tripod.jraster.input.MouseHandler;

public abstract class Game implements Runnable {

  private final GameAssetSystem assetSystem;

  private EntityManager entityManager;

  private KeyboardHandler keyboardHandler;
  private MouseHandler mouseHandler;
  private InputBindingSystem inputBindingSystem;

  private static final double DESIRED_UPS = 60.0;
  private static final double DESIRED_FPS = 60.0;

  private final GameWindow window;
  private Thread thread;

  private double deltaTime = 0;
  private volatile boolean running = false;

  public Game(String title, int width, int height) {

    int scale = calculateScale(width, height);

    this.keyboardHandler = new KeyboardHandler();
    this.mouseHandler = new MouseHandler();
    
    this.window = new GameWindow(title, width, height, scale);
    this.window.addInput(keyboardHandler, mouseHandler);

    this.assetSystem = new GameAssetSystem();
    
    this.inputBindingSystem = new InputBindingSystem(keyboardHandler,
        mouseHandler);

    // TODO : Make this just the entity management system and break out all the
    // sub systems into this one object through composition
    entityManager = new EntityManager(this.window.getRenderer(), assetSystem);
    
    entityManager.loadBlueprints("background", "blueprints/test.json");
    entityManager.loadBlueprints("test_entity", "blueprints/test_entity.json");
    
    loadResources();

    createBindings();

    thread = new Thread(this);
    thread.start();

  }

  protected abstract void start();
  protected abstract void loadResources();
  protected abstract void createBindings();
  protected abstract void update();
  protected abstract void render();
  
  public EntityCollisionSystem getEntityCollisionSystem() {
    return this.entityManager.getEntityCollisionSystem();
  }
  
  public Entity instantiate(String entityName, int x, int y, int depth) {
    return this.entityManager.instantiate(entityName, x, y, depth);
  }
  
  public Renderer getRenderer() {
    return this.window.getRenderer();
  }

  public InputBindingSystem getInput() {
    return this.inputBindingSystem;
  }

  public GameAssetSystem getAssetSystem() {
    return this.assetSystem;
  }

  public double getDeltaTime() {
    return this.deltaTime;
  }

  private void init() {
    this.window.createCloseGameCallback(this);
  }

  @Override
  public void run() {

    init();
    
    start();

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

        update();

        keyboardHandler.update();
        mouseHandler.update();
        
        entityManager.update(this.window.getRenderer());

        updates++;
        deltaTicks--;

      }

      if (deltaFrames >= 1) {

        // TODO : It might be useless to handle any form of render calls from
        // the users game, instead push it to the system for rendering in the
        // update loop
        render();

        this.window.displayToScreen();
        
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

  public void closeGame() {

    if (!running) return;

    System.out.println("Stopping game loop...");
    running = false;

    // Wait for the game loop thread to finish its last tick
    if (thread != null && Thread.currentThread() != thread) {
      try {
        thread.join(2000);
      } catch (InterruptedException e) {
        System.err.println("Game shutdown interrupted.");
        Thread.currentThread().interrupt(); // Restore interrupted status
      }
    }

    // Clean up UI and system assets cleanly
    System.out.println("Disposing windows and freeing assets...");
    if (window != null) window.dispose();

    if (assetSystem != null) {
      // TODO : maybe we need to implement something?
      assetSystem.clear();
    }

    System.out.println("Shutdown complete.");
    System.exit(0);
  }

  // TODO : Fix this as it will break small monitors because we are manually
  // subtracting by a value to make the scale a bit smaller
  private int calculateScale(int width, int height) {
    // Get the physical screen dimensions of the primary monitor
    java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
        .getScreenSize();
    int maxScaleX = screenSize.width / width;
    int maxScaleY = (screenSize.height - 100) / height;
    int idealScale = Math.min(maxScaleX - 1, maxScaleY - 1);
    if (idealScale < 1)
      idealScale = 1;

    return idealScale;
  }

}
