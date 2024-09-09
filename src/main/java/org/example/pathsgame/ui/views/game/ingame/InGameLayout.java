package org.example.pathsgame.ui.views.game.ingame;

import java.net.URL;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.factories.PlayerInformationHolder;
import org.example.pathsgame.setup_costumize.BackgroundSetUp;

/**
 * InGameLayout class is responsible for the view of various places in the game.
 * It utilizes JavaFX components to create a detailed and interactive view.
 * It is implemented as a singleton, ensuring only a single instance exists during runtime.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class InGameLayout {
  private final BorderPane roomView;

  private final ScrollPane leftView;

  private final ScrollPane rightView;

  private final VBox leftVerticalBox;

  private final VBox rightVerticalBox;

  private final HBox topView;

  private final HBox bottomView;

  private static InGameLayout instance;

  private final Scene roomViewScene;

  private final VBox centerView;

  /**
   * Private constructor for the singleton PlaceView class.
   * It initializes the view components for the game scenes.
   */
  private InGameLayout() {

    roomView = new BorderPane();
    leftView = new ScrollPane();
    rightView = new ScrollPane();
    leftVerticalBox = new VBox();
    rightVerticalBox = new VBox();
    topView = new HBox(5);
    bottomView = new HBox(5);
    roomViewScene = new Scene(roomView);
    centerView = new VBox();
    leftView.setContent(leftVerticalBox);
    leftVerticalBox.setMinSize(leftView.getVmin(), leftView.getHmin());
    rightView.setContent(rightVerticalBox);
    centerView.setAlignment(Pos.TOP_CENTER);
    topView.setAlignment(Pos.CENTER);
    leftView.setStyle("-fx-background: TRANSPARENT");
    rightView.setStyle("-fx-background: TRANSPARENT");
    leftView.setFitToWidth(true);
    rightView.setFitToWidth(true);
    leftView.setFitToHeight(true);
    rightView.setFitToHeight(true);
    String deepPurple = "#4B0082";
    String lavender = "#E6E6FA";
    String gradient =
        "-fx-background-color: linear-gradient(to bottom, " + deepPurple + ", " + lavender + ");";
    roomView.setStyle(gradient);
    leftVerticalBox.setStyle(gradient);
    rightVerticalBox.setStyle(gradient);
  }

  /**
   * Static method to ensure that there is only one instance of InGameLayout is active during
   * runtime.
   * If no instance exists, it creates a new one. Otherwise, it returns the existing instance.
   *
   * @return The single instance of InGameLayout class.
   */
  public static synchronized InGameLayout getInstance() {

    if (instance == null) {
      instance = new InGameLayout();
    }
    return instance;
  }

  /**
   * Method to get the game room view.
   * It sets up the room background, player information, stylesheet, etc.
   *
   * @param fileName The file name of the room image.
   * @return The configured room view scene.
   */
  public Scene getRoomViewScene(String fileName) {
    setBackgroundRoomImage(fileName);
    roomView.setTop(PlayerInformationHolder.getInstance()
        .getInGameTopBox());
    roomView.setCenter(centerView);
    URL resource = getClass().getResource("/style/passageBoxStyle.css");
    if (resource == null) {
      AlertFactory.getInstance()
          .getErrorAlert("Error", "Loading Error",
              "Unable to load image to the room ").showAndWait();
    } else {
      String cssPath = resource.toExternalForm();
      roomViewScene.getStylesheets().add(cssPath);
    }
    return roomViewScene;
  }

  /**
   * Getter for the current room view in BorderPane format.
   *
   * @return the current room view in BorderPane format.
   */
  public BorderPane getRoomViewBorderPane() {
    return roomView;
  }

  /**
   * Method to set the background image of the room.
   *
   * @param fileName The file name of the background image to be set.
   */
  public void setBackgroundRoomImage(String fileName) {

    if (fileName == null || fileName.isEmpty()) {
      centerView.setBackground(null);
      return;
    }
    Background background = BackgroundSetUp.getInstance()
        .getBackground("/images/game/" + fileName + ".png");
    if (background != null) {
      centerView.setBackground(background);
    }
  }

  /**
   * The method gets the Vbox that constitutes the left view in the game scene.
   *
   * @return the Vbox that represents the left view.
   */
  public VBox getLeftVbox() {
    return leftVerticalBox;
  }

  /**
   * The method gets the Vbox that constitutes the right view in the game scene.
   *
   * @return the Vbox that represents the right view.
   */
  public VBox getRightVbox() {
    return rightVerticalBox;
  }

  /**
   * The method gets the HBox that constitutes the bottom view in the game scene.
   *
   * @return the HBox that represents the bottom view.
   */
  public HBox getBottomView() {
    return bottomView;
  }

  /**
   * The method gets the Vbox that constitutes the center view in the game scene.
   *
   * @return the Vbox that represents the center view.
   */
  public VBox getCenterView() {
    return centerView;
  }

  /**
   * The method adds a node to the left view of the game borderPane.
   *
   * @param node The Node to be added to the left view.
   */
  public void addToLeftView(Node node) {
    leftVerticalBox.getChildren().add(node);
    roomView.setLeft(leftView);
  }

  /**
   * The method adds a node to the right view of the game borderPane.
   *
   * @param node The Node to be added to the right view.
   */
  public void addToRightView(Node node) {
    rightVerticalBox.getChildren().add(node);
    roomView.setRight(rightView);
  }

  /**
   * The method adds a node to the bottom view of the game borderPane.
   *
   * @param node The Node to be added to the bottom view.
   */
  public void addToBottomView(Node node) {
    bottomView.getChildren().add(node);
    roomView.setBottom(bottomView);
  }

}
