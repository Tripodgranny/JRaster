package com.tripod.jraster.entity;

public class BoxCollider implements EntityComponent {
  
  private Transform transform;
  private int w, h;
  
  public BoxCollider(Transform transform, int w, int h) {
    this.transform = transform;
    this.w = w;
    this.h = h;
  }
  
  public double getLeft() { return transform.x; }
  public double getRight() { return transform.x + w; }
  public double getTop() { return transform.y; }
  public double getBottom() { return transform.y + h; }
  
  public double[] getPredictedBounds(double offsetX, double offsetY) {
    return new double[] {
        transform.x + offsetX,       
        transform.x + offsetX + w,   
        transform.y + offsetY,       
        transform.y + offsetY + h    
    };
}

}
