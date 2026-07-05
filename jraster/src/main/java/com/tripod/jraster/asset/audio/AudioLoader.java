package com.tripod.jraster.asset.audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioLoader {

  private static final String PATH = "/audio/";

  private final HashMap<String, SoundClip> cache = new HashMap<>();

  public AudioLoader() {
  }

  public SoundClip getSound(String name) {
    if (cache.containsKey(name)) {
      return cache.get(name);
    }
    return null;
  }

  /**
   * Loads a .wav file, registers it internally, and returns a handle to the
   * asset.
   */
  public void load(String name) {
    // If already cached, just return the existing instance
    if (cache.containsKey(name)) {
      return;
    }

    final String path = PATH + name + ".wav";
    try (InputStream is = AudioLoader.class.getResourceAsStream(path);
        InputStream bufferedIs = new BufferedInputStream(is)) {

      if (is == null) {
        System.err.println("Audio file not found: " + path);
        return;
      }

      AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIs);
      Clip nativeClip = AudioSystem.getClip();
      nativeClip.open(ais);

      // Instantiate our decoupled asset
      SoundClip soundClip = new SoundClip(nativeClip);
      cache.put(name, soundClip);

    } catch (UnsupportedAudioFileException e) {
      System.err
          .println("Unsupported audio format (Use 16-bit PCM .wav): " + path);
      e.printStackTrace();
    } catch (IOException | LineUnavailableException e) {
      e.printStackTrace();
    }

  }

  /**
   * Wipes out all lines from memory.
   */
  public void clear() {
    for (SoundClip sound : cache.values()) {
      sound.dispose();
    }
    cache.clear();
  }

}