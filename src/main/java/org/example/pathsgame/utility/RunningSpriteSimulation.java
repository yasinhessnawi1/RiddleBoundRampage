package org.example.pathsgame.utility;

import static javafx.animation.Animation.INDEFINITE;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

/**
 * The RunningSpriteSimulation class provides functionality to animate a sequence of images in a
 * running sprite simulation.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class RunningSpriteSimulation {
  private final ImageView imageView;
  private final Image[] frames;
  private final Timeline timeline;
  private int currentFrame = 0;

  /**
   * Constructor for the RunningSpriteSimulation class.
   * Initializes the ImageView for displaying frames, the array of frames images, and the timeline
   * for the animation.
   *
   * @param frameImages An array of Images representing the frames of the animation.
   */
  public RunningSpriteSimulation(Image[] frameImages) {
    this.imageView = new ImageView();
    this.frames = frameImages;
    // Create the timeline for the animation
    this.timeline = new Timeline(new KeyFrame(Duration.millis(250), event -> updateFrame()));
    timeline.setCycleCount(INDEFINITE);
  }

  /**
   * Updates the current frame of the animation.
   */
  private void updateFrame() {
    imageView.setImage(frames[currentFrame]);
    currentFrame = (currentFrame + 1) % frames.length;
  }

  /**
   * Retrieves the ImageView used for displaying the frames of the animation.
   *
   * @return An ImageView instance.
   */
  public ImageView getImageView() {
    return imageView;
  }

  /**
   * Starts the animation.
   */
  public void start() {
    timeline.play();
  }
}

