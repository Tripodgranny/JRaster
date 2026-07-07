package com.tripod.jraster.asset;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PlainResourceLoader implements ResourceLoader {

  @Override
  public InputStream loadResource(String path) {
    // 1. Try loading as an external file first
    // Strip leading slash for FileInputStream so it looks in the project folder
    String relativePath = path.startsWith("/") ? path.substring(1) : path;
    File externalFile = new File(relativePath);

    if (externalFile.exists()) {
      try {
        System.out.println("[ResourceLoader] Loading external file: "
            + externalFile.getAbsolutePath());
        return new FileInputStream(externalFile);
      } catch (IOException e) {
        // Log and fall through to classpath attempt
        System.err.println(
            "[ResourceLoader] Failed to read external file, trying classpath...");
      }
    }

    // 2. Fallback to internal JAR resources (requires the leading slash)
    String classpathPath = path.startsWith("/") ? path : "/" + path;
    System.out.println(
        "[ResourceLoader] Attempting classpath load: " + classpathPath);

    InputStream is = getClass().getResourceAsStream(classpathPath);

    if (is == null) {
      // Crash early and clearly so you know exactly what path failed
      throw new IllegalArgumentException(
          "Resource not found anywhere! Tried file: "
              + externalFile.getAbsolutePath() + " and classpath: "
              + classpathPath);
    }

    return is;
  }
}