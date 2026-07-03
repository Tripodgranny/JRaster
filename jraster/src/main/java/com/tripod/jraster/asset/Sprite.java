package com.tripod.jraster.asset;

public class Sprite {

    private final SpriteSheet sheet;

    private final int width;
    private final int height;

    // Row and column (in sprite units, not pixels)
    private final int row;
    private final int col;

    private final int numberOfImages;

    private int imageIndex;

    // pixels[frame][pixel]
    private final int[][] pixels;

    /**
     * Creates a sprite with one or more animation frames.
     *
     * @param sheet Sprite sheet
     * @param width Width of each frame in pixels
     * @param height Height of each frame in pixels
     * @param row Row of the sprite on the sheet (0-based)
     * @param col Starting column of the sprite on the sheet (0-based)
     * @param numberOfImages Number of animation frames
     */
    public Sprite(SpriteSheet sheet, int width, int height,
                  int row, int col, int numberOfImages) {

        this.sheet = sheet;
        this.width = width;
        this.height = height;
        this.row = row;
        this.col = col;
        this.numberOfImages = numberOfImages;

        this.imageIndex = 0;

        pixels = new int[numberOfImages][width * height];

        loadFrames();
    }

    /**
     * Creates a sprite from the entire sprite sheet.
     */
    public Sprite(SpriteSheet sheet) {
        this.sheet = sheet;

        this.width = sheet.getWidth();
        this.height = sheet.getHeight();

        this.row = 0;
        this.col = 0;

        this.numberOfImages = 1;

        this.imageIndex = 0;

        pixels = new int[1][width * height];

        System.arraycopy(sheet.getPixels(), 0, pixels[0], 0, width * height);
    }

    private void loadFrames() {

        for (int frame = 0; frame < numberOfImages; frame++) {

            int startX = (col + frame) * width;
            int startY = row * height;

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {

                    int sheetIndex =
                            (startX + x) + (startY + y) * sheet.getWidth();

                    int spriteIndex =
                            x + y * width;

                    pixels[frame][spriteIndex] = sheet.getPixels()[sheetIndex];
                }
            }
        }
        
    }

    public int[] getPixels() {
        return pixels[imageIndex];
    }

    public int[] getPixels(int frame) {
        return pixels[frame];
    }

    public void setFrame(int frame) {
        if (frame >= 0 && frame < numberOfImages) {
            imageIndex = frame;
        } else if (frame >= numberOfImages) {
          imageIndex = 0;
        } else if (frame < 0) {
          imageIndex = numberOfImages-1;
        }
    }

    public int getFrame() {
        return imageIndex;
    }

    public int getFrameCount() {
        return numberOfImages;
    }

    public int getWidth() {
        return width;
    }

    public int getHalfWidth() {
      return width / 2;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getHalfHeight() {
      return height / 2;
    }
    
}