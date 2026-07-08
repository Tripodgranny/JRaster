package com.tripod.jraster.entity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tripod.jraster.Renderer;
import com.tripod.jraster.asset.GameAssetSystem;
import com.tripod.jraster.asset.Sprite;
import com.tripod.jraster.asset.SpriteSheet;

// TODO : Need to put most of this loading logic into GameAssetSystem maybe..
public class EntityManager {

  private final GameAssetSystem assetSystem;

  private final EntityCollisionSystem entityCollisionSystem;

  private ArrayList<Entity> entities = new ArrayList<>();

  private final Map<String, JsonNode> blueprints = new HashMap<>();
  private final ObjectMapper mapper = new ObjectMapper();

  public EntityManager(Renderer renderer, GameAssetSystem assetSystem) {
    this.assetSystem = assetSystem;

    this.entityCollisionSystem = new EntityCollisionSystem(entities);
  }

  public void loadBlueprints(String name, String filePath) {

    try (InputStream is = assetSystem.getAssetStream(filePath)) {
      if (is == null) {
        throw new IllegalArgumentException("Resource not found: " + filePath);
      }
      JsonNode root = mapper.readTree(is);
      blueprints.put(name, root);
      System.out.println("[EntityManager] Successfully loaded blueprint: "
          + name + " from " + filePath);
    } catch (Exception e) {
      System.err.println("Failed to load entity blueprint: " + filePath);
      e.printStackTrace();
    }
  }

  public Entity instantiate(String blueprintName, int x, int y, int depth) {
    JsonNode blueprint = blueprints.get(blueprintName);
    if (blueprint == null) {
      throw new IllegalArgumentException(
          "Blueprint not found: " + blueprintName);
    }

    // Create the base Entity container
    Entity entity = new Entity(x, y, depth);
    JsonNode componentsNode = blueprint.get("components");

    // Parse and attach SpriteAnimator if present
    if (componentsNode.has("SpriteAnimator")) {
      JsonNode animNode = componentsNode.get("SpriteAnimator");
      String path = animNode.get("spritePath").asText();
      int animationSpeed = animNode.get("animationSpeed").asInt();
      int w = animNode.get("width").asInt();
      int h = animNode.get("height").asInt();
      int r = animNode.get("row").asInt();
      int c = animNode.get("column").asInt();
      int f = animNode.get("numberOfFrames").asInt();

      // --- STEP 1: Convert path ("assets/sprites/ToxicValleyWorldMap.png") to
      // clean registry key ---
      String normalizedPath = path.replace("\\", "/"); // Keep path characters
                                                       // consistent
      String fileName = normalizedPath
          .substring(normalizedPath.lastIndexOf("/") + 1);
      String assetKey = fileName.contains(".")
          ? fileName.substring(0, fileName.lastIndexOf("."))
          : fileName;

      // --- STEP 2: Fetch the sheet with a safety guard-rail ---
      SpriteSheet sheet = assetSystem.getSpriteSheet(assetKey);
      if (sheet == null) {
        throw new IllegalStateException(
            "Blueprint instantiation failed! The entity '" + blueprintName
                + "' requires SpriteSheet '" + assetKey + "' (parsed from: "
                + path + "), but it isn't loaded in GameAssetSystem.");
      }

      Sprite sprite = new Sprite(sheet, w, h, r, c, f);
      entity.addComponent(new SpriteAnimator(sprite, animationSpeed));
    }

    // Parse and attach BoxCollider if present
    if (componentsNode.has("BoxCollider")) {
      JsonNode colliderNode = componentsNode.get("BoxCollider");
      Transform transform = entity.getComponent(Transform.class);

      if (colliderNode.path("useSpriteDimensions").asBoolean(false)) {
        SpriteAnimator animator = entity.getComponent(SpriteAnimator.class);
        if (animator != null) {
          entity.addComponent(new BoxCollider(transform, animator.getWidth(),
              animator.getHeight()));
        }
      } else {
        int w = colliderNode.path("width").asInt(32);
        int h = colliderNode.path("height").asInt(32);
        entity.addComponent(new BoxCollider(transform, w, h));
      }
    }

    this.addEntity(entity);
    return entity;
  }

  public EntityCollisionSystem getEntityCollisionSystem() {
    return this.entityCollisionSystem;
  }

  private void addEntity(Entity entity) {
    entities.add(entity);
  }

  public void removeEntity(Entity entity) {
    entities.remove(entity);
  }

  public void clear() {
    entities.clear();
    blueprints.clear();
  }

  public void update(Renderer renderer) {
    // Avoid concurrent modification by iterating cleanly
    for (int i = 0; i < entities.size(); i++) {
      Entity entity = entities.get(i);
      Transform transform = entity.getComponent(Transform.class);
      SpriteAnimator animator = entity.getComponent(SpriteAnimator.class);

      if (transform != null && animator != null) {
        animator.update();
        renderer.pushSpriteAnimatorToRenderer(transform.x, transform.y, animator,
            entity.getDepth());
      }
    }
  }
}