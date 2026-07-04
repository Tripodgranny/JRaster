package jraster;

import com.tripod.jraster.Game;
import com.tripod.jraster.asset.Sprite;
import com.tripod.jraster.asset.SpriteSheet;
import com.tripod.jraster.entity.Entity;
import com.tripod.jraster.entity.SpriteAnimator;
import com.tripod.jraster.entity.Transform;
import com.tripod.jraster.graphics.fx.CRTScan;
import com.tripod.jraster.graphics.fx.Dithering;
import com.tripod.jraster.graphics.fx.MonoChrome;
import com.tripod.jraster.graphics.fx.PalletSwap;
import com.tripod.jraster.graphics.fx.RadialBlur;
import com.tripod.jraster.graphics.fx.Ripple;
import com.tripod.jraster.graphics.fx.animation.NoPixelEffectAnimation;
import com.tripod.jraster.graphics.fx.animation.SweepPixelEffectAnimation;

public class GameTest extends Game {

  Entity e1;
  Entity e2;
  
  CRTScan crtScan = new CRTScan(0, 0, 256, 224);
  MonoChrome mono = new MonoChrome(0, 0, 256, 224);
  Ripple ripple = new Ripple(0, 0, 256, 224);
  PalletSwap pSwap = new PalletSwap(0, 0, 256, 224);
  Dithering dither = new Dithering(0, 0, 256, 224, 6);
  RadialBlur blur = new RadialBlur(0,0,256,224);
  
  SweepPixelEffectAnimation crtAnim = new SweepPixelEffectAnimation(crtScan, 1);
  
  NoPixelEffectAnimation rippleAnim = new NoPixelEffectAnimation(ripple);
  NoPixelEffectAnimation palletSwapAnim = new NoPixelEffectAnimation(pSwap);
  NoPixelEffectAnimation ditherAnim = new NoPixelEffectAnimation(dither);
  NoPixelEffectAnimation blurAnim = new NoPixelEffectAnimation(blur);
  
  // TODO : We need to abstract this out of the test.
  // TODO : We need to create a configuration file for creating the Game
  public static void main(String[] args) {
    
    // this is not cross platform and only works on java version 9+
    System.setProperty("sun.java2d.uiScale", "1.0");
    
    new GameTest("TEST", 256, 224);
  }

  public GameTest(String title, int width, int height) {
    super(title, width, height);
  }

  protected void loadResources() {

    SpriteSheet sheet = getGameAssetManager().loadSpriteSheet("test");

    Sprite testSprite = new Sprite(sheet, 256, 224, 0, 0, 1);
    SpriteAnimator animator = new SpriteAnimator(testSprite, 10);

    e1 = new Entity(0, 0, 0);
    e1.addComponent(animator);
    addEntity(e1);
    
    
    Sprite testSprite2 = new Sprite(sheet, 32, 32, 0, 0, 1);
    SpriteAnimator animator2 = new SpriteAnimator(testSprite2, 10);
    e2 = new Entity(100, 100, 1);
    e2.addComponent(animator2);
    addEntity(e2);
    
  }

  double i = 0;
  @Override
  protected void update() {
    i += 1;
    e2.getComponent(Transform.class).y = 100 + (Math.cos(i)) * 4 * this.getDeltaTime();

  }

  @Override
  protected void render() {
    //pixelEffectAnimation.setSweepVertical(true);
    // getGameCanvas().applyPixelEffectAnimation(blurAnim);
    ripple.setWaveSpeed(0.12F);
    ripple.setAmplitude(3F);
    ripple.setFrequency(0.1f);
    
    getGameCanvas().applyPixelEffectAnimation(rippleAnim);
    
    dither.setColorDepth(8);
    getGameCanvas().applyPixelEffectAnimation(ditherAnim);

  }

}
