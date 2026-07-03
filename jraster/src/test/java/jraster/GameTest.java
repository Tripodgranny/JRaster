package jraster;

import com.tripod.jraster.Game;
import com.tripod.jraster.asset.Sprite;
import com.tripod.jraster.asset.SpriteSheet;
import com.tripod.jraster.entity.Entity;
import com.tripod.jraster.entity.SpriteAnimator;
import com.tripod.jraster.entity.Transform;

public class GameTest extends Game {
  
  Entity e1;
  
  // TODO : We need to abstract this out of the test.
  // TODO : We need to create a configuration file for creating the Game
  public static void main(String[] args) {
    new GameTest("TEST", 256, 224, 3);
  }

  public GameTest(String title, int width, int height, int scale) {
    super(title, width, height, scale);
  }
  
  protected void loadResources() {
    
    SpriteSheet sheet = getGameAssetManager().loadSpriteSheet("test");
    
    Sprite testSprite = new Sprite(sheet, 16, 16, 0, 0, 4);
    SpriteAnimator animator = new SpriteAnimator(testSprite, 10);
    Transform transform = new Transform(100, 100);
    e1 = new Entity();
    e1.addComponent(animator);
    e1.addComponent(transform);
    addEntity(e1);
  }

  double i = 0;
  @Override
  protected void update() {
    i += 0.05D;
    e1.getComponent(Transform.class).y += 1;
    
  }

  @Override
  protected void render() {
    
    getGameCanvas().drawRect(i, 0, 100, 100, 0xFF00FF, false);
    getGameCanvas().drawCircle(32, 32, 16, 0x00FF00, false);
    
  }

}
