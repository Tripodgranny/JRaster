package com.tripod.jraster.entity;

public class Transform implements EntityComponent {
  public double x;
  public double y;

  public Transform(double x, double y) {
      this.x = x;
      this.y = y;
  }

}
