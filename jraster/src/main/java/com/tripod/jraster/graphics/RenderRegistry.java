package com.tripod.jraster.graphics;

public interface RenderRegistry {
  
    void registerRenderTarget(RenderTarget target);
    
    void removeRenderTarget(RenderTarget target);
    
}