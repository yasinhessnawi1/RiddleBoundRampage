package org.example.pathsgame.ui.views.game.ingame;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.pathsgame.utility.RunningSpriteSimulation;

/**
 * The GameView class is responsible for rendering game characters, handles game state (win, lose),
 * and manging character animations in the game.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GameView {
  private ImageView characterImageView;
  private final String spritePath;
  private ImageView mosnterImageView;
  private final Stage stage;
  private static final String IMAGE_TARGET = "stand.png";

  /**
   * GameView constructor that initializes the game view with a specified stage and character
   * sprite path.
   *
   * @param stage      The stage in which the game is played.
   * @param spritePath The path to the sprite image for the main character.
   */
  public GameView(Stage stage, String spritePath) {
    this.stage = stage;
    this.spritePath = spritePath;
    Image characterImage = new Image(getClass().getResourceAsStream(spritePath));
    this.characterImageView = new ImageView(characterImage);
    this.characterImageView.setFitHeight(400);
    this.characterImageView.setFitWidth(200);
    this.characterImageView.setPreserveRatio(true);
    mosnterImageView =
        new ImageView(new Image(getClass()
            .getResourceAsStream("/images/sprite/zombie_stand.png")));
    mosnterImageView.setFitHeight(400);
    mosnterImageView.setFitWidth(200);
    mosnterImageView.setPreserveRatio(true);
  }

  /**
   * Returns the ImageView of the main character .
   *
   * @return ImageView of the main character.
   */
  public ImageView getCharacterImageView() {
    return characterImageView;
  }

  /**
   * Returns the ImageView of the monster.
   *
   * @return ImageView of the monster.
   */
  public ImageView getMosnterImageView() {
    return mosnterImageView;
  }

  /**
   * Returns the stage of the game.
   *
   * @return Stage of the game.
   */
  public Stage getStage() {
    return stage;
  }

  /**
   * Changes the current scene to the win scene.
   */
  public void showWinScene() {
    stage.setScene(WinView.getInstance().getEndScene());
  }

  /**
   * Changes the current scene to the loss scene.
   */
  public void showLossScene() {
    stage.setScene(LossView.getInstance().getEndScene());
  }

  /**
   * Changes the animation of the main character to running.
   */
  public void setMainCharacterRunning() {
    String walk1Url = spritePath.replace(IMAGE_TARGET, "walk1.png");
    String walk2Url = spritePath.replace(IMAGE_TARGET, "walk2.png");
    characterImageView = setRunningSprite(walk1Url, walk2Url, characterImageView);
  }

  /**
   * Set the running sprite for a given ImageView by alternating between two images URLs.
   *
   * @param imageUrl1      The first image URL for the running animation.
   * @param imageUrl2      The second image URL for the running animation.
   * @param characterImage The ImageView that will have its image changed.
   * @return ImageView with updated sprite animation.
   */
  public ImageView setRunningSprite(String imageUrl1, String imageUrl2, ImageView characterImage) {
    // Create Image objects for the running frames
    Image runningImage1 = new Image(getClass().getResourceAsStream(imageUrl1));
    Image runningImage2 = new Image(getClass().getResourceAsStream(imageUrl2));

    Image[] frameImages = new Image[] {runningImage1, runningImage2};

    RunningSpriteSimulation runningSprite = new RunningSpriteSimulation(frameImages);
    ImageView newImageView = runningSprite.getImageView();
    newImageView.setFitWidth(200);
    newImageView.setFitHeight(250);

    ObservableList<Node> bottomViewChildren =
        InGameLayout.getInstance().getBottomView().getChildren();
    int oldIndex = bottomViewChildren.indexOf(characterImage);
    if (oldIndex != -1) {
      bottomViewChildren.set(oldIndex, newImageView);
    }
    runningSprite.start();
    return newImageView;
  }

  /**
   * Changes the animation of the monster to running.
   */
  public void setMonsterRunning() {
    String url1 = "/images/sprite/zombie_walk1.png";
    String url2 = "/images/sprite/zombie_walk2.png";
    mosnterImageView = setRunningSprite(url1, url2, mosnterImageView);
  }

  /**
   * Changes the animation of the main character to fighting.
   */
  public void setMainCharacterFighting() {
    String url1 = spritePath.replace(IMAGE_TARGET, "action1.png");
    String url2 = spritePath.replace(IMAGE_TARGET, "action2.png");
    characterImageView = setRunningSprite(url1, url2, characterImageView);
  }

  /**
   * Changes the animation of the monster to fighting.
   */
  public void setMonsterFighting() {
    String url1 = "/images/sprite/zombie_idle.png";
    String url2 = "/images/sprite/zombie_hurt.png";
    mosnterImageView = setRunningSprite(url1, url2, mosnterImageView);
  }

  /**
   * Switches the position of the main character and the monster.
   */
  public void switchCharacterPositions() {

    ObservableList<Node> children = InGameLayout.getInstance().getBottomView().getChildren();
    int characterIndex = children.indexOf(characterImageView);
    int monsterIndex = children.indexOf(mosnterImageView);
    if (characterIndex != -1 && monsterIndex != -1) {
      Node characterNode = children.get(characterIndex);
      Node monsterNode = children.get(monsterIndex);

      // Remove the nodes
      children.remove(characterNode);
      children.remove(monsterNode);

      // Add them back at new positions
      if (characterIndex < monsterIndex) {
        children.add(characterIndex, monsterNode);
        children.add(monsterIndex, characterNode);
      } else {
        children.add(monsterIndex, characterNode);
        children.add(characterIndex, monsterNode);
      }
    }
  }

}

