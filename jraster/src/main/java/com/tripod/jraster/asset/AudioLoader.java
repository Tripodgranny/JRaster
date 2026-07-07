package com.tripod.jraster.asset;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioLoader {

  private static final String EXTERNAL_DIR = "assets/audio/";
  private static final String CLASSPATH_DIR = "/audio/";

  private final HashMap<String, SoundClip> cache = new HashMap<>();

  public AudioLoader() {
  }

  public SoundClip getSound(String name) {
    if (cache.containsKey(name)) {
      return cache.get(name);
    }
    return null;
  }

  public SoundClip load(String name) {
    // If already cached, just return the existing instance
    if (cache.containsKey(name)) {
      return cache.get(name);
    }

    InputStream targetStream = null;
    String externalPath = EXTERNAL_DIR + name + ".wav";
    String classpathPath = CLASSPATH_DIR + name + ".wav";

    try {
      // Try loading from external files directory first
      File externalFile = new File(externalPath);
      if (externalFile.exists()) {
        System.out.println("[AudioLoader] Loading external audio: "
            + externalFile.getAbsolutePath());
        targetStream = new FileInputStream(externalFile);
      } else {
        // Fallback to internal JAR classpath resources
        System.out.println(
            "[AudioLoader] Attempting classpath audio load: " + classpathPath);
        targetStream = getClass().getResourceAsStream(classpathPath);
      }

      // Crash if the file doesn't exist
      if (targetStream == null) {
        throw new IllegalArgumentException(
            "Audio asset not found : " + externalFile.getAbsolutePath()
                + " and classpath: " + classpathPath);
      }

      // Wrap in BufferedInputStream to support mark/reset in AudioSystem
      try (InputStream bufferedIs = new BufferedInputStream(targetStream)) {
        AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIs);
        Clip nativeClip = AudioSystem.getClip();
        nativeClip.open(ais);

        // Cache the sound clip
        SoundClip soundClip = new SoundClip(nativeClip);
        cache.put(name, soundClip);

        return soundClip;
      }

    } catch (UnsupportedAudioFileException e) {
      System.err.println(
          "Unsupported audio format (Use 16-bit PCM .wav): " + name + ".wav");
      e.printStackTrace();
    } catch (IOException | LineUnavailableException e) {
      System.err
          .println("Failed to read audio file resource: " + name + ".wav");
      e.printStackTrace();
    }

    return null;
  }

  public void clear() {
    for (SoundClip sound : cache.values()) {
      sound.dispose();
    }
    cache.clear();
  }
}