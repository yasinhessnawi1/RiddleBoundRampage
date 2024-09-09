package org.example.pathsgame.ui.views.game.beforegameview;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.example.pathsgame.setup_costumize.BackgroundSetUp;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.utility.CheckValid;
import org.example.pathsgame.utility.SceneHandler;

/**
 * CharacterChoosePageView class represents the view of the Character Choose Page in the game.
 * It sets up the UI elements and handles some interactions for the character choosing process.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class CharacterChoosePageView {
  private static CharacterChoosePageView instance;
  private ImageView spriteImageView;
  private BorderPane mainBorderPane;
  private String spritePath;
  private final Button nextButton;
  private final Button prevButton;

  /**
   * Constructor to initialize instance variables and set up UI elements.
   */
  private CharacterChoosePageView() {

    spriteImageView = new ImageView();
    mainBorderPane = new BorderPane();
    nextButton = new Button();
    ButtonCustomizer.getInstance().getEffect(nextButton, "next_image.png",
        220, 220);
    prevButton = new Button();
    ButtonCustomizer.getInstance().getEffect(prevButton, "prev.png",
        250, 250);

  }

  /**
   * A synchronized method to get the singleton instance of the CharacterChoosePageView class.
   *
   * @return The singleton instance of the CharacterChoosePageView class.
   */
  public static synchronized CharacterChoosePageView getInstance() {
    if (instance == null) {
      instance = new CharacterChoosePageView();
    }
    return instance;
  }

  /**
   * Getter method for spriteImageView.
   *
   * @return The sprite ImageView.
   */
  public ImageView getSpriteImageView() {
    return  spriteImageView;
  }

  /**
   * Getter method for nextButton.
   *
   * @return The next button.
   */
  public Button getNextButton() {
    return nextButton;
  }

  /**
   * Getter method for previousButton.
   *
   * @return The previous button.
   */
  public Button getPrevButton() {
    return prevButton;
  }

  /**
   * Method to set up the view for Character Choose Page.
   *
   * @param stage The main application stage.
   */
  public void getCharacterChoosePageView(Stage stage) {
    mainBorderPane = new BorderPane();
    mainBorderPane.setBackground(
        BackgroundSetUp.getInstance().getBackground(
            "/images/backgrounds/charcterChoseBackground.gif"));
    StackPane spriteStack = new StackPane();
    mainBorderPane.setCenter(spriteStack);
    spriteImageView = new ImageView();
    spriteStack.getChildren().add(spriteImageView);
    HBox leftBox = new HBox(prevButton);
    leftBox.setAlignment(Pos.CENTER_LEFT);
    HBox rightBox = new HBox(nextButton);
    rightBox.setAlignment(Pos.CENTER_RIGHT);
    mainBorderPane.setLeft(leftBox);
    mainBorderPane.setRight(rightBox);
    SceneHandler.getInstance()
        .setScene(new Scene(mainBorderPane), "characterChoosePage");
    stage.setTitle("Character Choose Page");
    stage.setScene(SceneHandler.getInstance().getScenes().get("characterChoosePage"));
  }

  /**
   * Method to set up the sprite in the Character Choose Page.
   *
   * @param imagePath The path of the image.
   * @param spriteImageView The ImageView object where the sprite is to be displayed.
   * @return The ImageView object of the character.
   */
  public ImageView setUpSprite(String imagePath, ImageView spriteImageView) {
    Image spriteImage = new Image(getClass().getResourceAsStream(imagePath));
    spriteImageView.setImage(spriteImage);
    spriteImageView.setFitWidth(200);
    spriteImageView.setPreserveRatio(true);
    spriteImageView.getProperties().put("imagePath", imagePath);
    return spriteImageView;
  }

  /**
   * Getter method for the spritePath.
   *
   * @return the spritePath as a string.
   */
  public String getSpritePath() {
    return spritePath;
  }

  /**
   * Setter method for the spritePath.
   *
   * @param spritePath the spritePath to set as a string.
   */
  public void setSpritePath(String spritePath) {
    this.spritePath = spritePath;
  }
}


