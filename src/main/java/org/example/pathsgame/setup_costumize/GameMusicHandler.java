package org.example.pathsgame.setup_costumize;

import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * GameMusicHandler is a Singleton class that controls the music playback in the game.
 * It has methods to play, stop, pause, and resume the music.
 * The MediaPlayer object from JavaFx is used to handle music.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GameMusicHandler {
  private static GameMusicHandler instance;
  private MediaPlayer mediaPlayer;

  /**
   * Private constructor to prevent creating instances of this class directly.
   * Singleton pattern is used here.
   */
  private GameMusicHandler() {
  }

  /**
   * Provides the singleton instance of the GameMusicHandler.
   * if the instance does not already exist, it will be created.
   *
   * @return The single instance of the GameMusicHandler.
   */
  public static synchronized GameMusicHandler getInstance() {
    if (instance == null) {
      instance = new GameMusicHandler();
    }
    return instance;
  }

  /**
   * Plays the music from the specified file path.
   * If there is any music currently playing, it stops that before starting the music.
   * The new music is set to play indefinitely.
   *
   * @param filePath The file path of the music to be played.
   * @throws IllegalArgumentException if the file path is not found.
   */
  public void playMusic(String filePath) {
    URL resource = getClass().getResource(filePath);
    if (resource == null) {
      throw new IllegalArgumentException("File not found!");
    }
    Media media = new Media(resource.toString());
    if (mediaPlayer != null) {
      mediaPlayer.stop();
    }
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    mediaPlayer.play();
  }

  /**
   * Pauses the current music playback if there is any.
   */
  public void pauseMusic() {
    if (mediaPlayer != null) {
      mediaPlayer.pause();
    }
  }

  /**
   * Resumes the current music playback if there is any.
   */
  public void resumeMusic() {
    if (mediaPlayer != null) {
      mediaPlayer.play();
    }
  }

  /**
   * Returns the MediaPlayer instance.
   *
   * @return the MediaPlayer instance.
   */
  public MediaPlayer getMediaPlayer() {
    return mediaPlayer;
  }
}
