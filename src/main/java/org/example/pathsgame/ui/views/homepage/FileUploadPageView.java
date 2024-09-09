package org.example.pathsgame.ui.views.homepage;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.pathsgame.factories.GoBackButtonFactory;
import org.example.pathsgame.setup_costumize.BackgroundSetUp;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.setup_costumize.TextStyler;
import org.example.pathsgame.utility.NavigationBetweenSceneData;
import org.example.pathsgame.utility.SceneHandler;

/**
 * FileUploadPageView class represents the view of the file upload page in the game.
 * It sets up the UI elements and handles some interactions for the file uploading process.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class FileUploadPageView {
  public static final String SCENE_NAME = "Upload Story";
  private static FileUploadPageView instance;
  private final TextField fileLocationField;
  private final Button nextButton;
  private final Button backButton;
  private final Button icon;
  private final FileChooser fileChooser;
  private final Stage stage;

  /**
   * Constructor to initialize instance variables and set up UI elements.
   *
   * @param stage The main application stage.
   */
  private FileUploadPageView(Stage stage) {

    this.stage = stage;
    fileLocationField = new TextField();
    fileLocationField.setPromptText("Paste file location link or click icon to choose file ->");
    fileLocationField.setPrefWidth(400);
    fileLocationField.setPrefHeight(30);
    nextButton = new Button();
    ButtonCustomizer.getInstance().getEffect(nextButton, "next.png",
        150, 100);
    fileLocationField.setFocusTraversable(false);
    backButton =
        GoBackButtonFactory.getInstance(stage, NavigationBetweenSceneData.getInstance(),
            SceneHandler.getInstance().getScenes()).getButton();
    icon = getIcon("/images/animatedicons/folder.gif");
    fileChooser = new FileChooser();
    fileChooser.setTitle("Select Story File");
    fileChooser.getExtensionFilters()
        .addAll(new FileChooser.ExtensionFilter("Text Files", "*.*"),
            new FileChooser.ExtensionFilter("All Files", "*.*"));
  }

  /**
   * A synchronized method to get the singleton instance of the FileUploadPageVie class.
   *
   * @param stage The main stage application stage.
   * @return The singleton instance of the FileUploadPageVie class.
   */
  public static synchronized FileUploadPageView getInstance(Stage stage) {
    if (instance == null) {
      instance = new FileUploadPageView(stage);
    }
    return instance;
  }

  /**
   * Method to create an icon button with the provided image path.
   *
   * @param path The image path for the icon.
   * @return The icon button.
   */
  public Button getIcon(String path) {
    ImageView iconImage = new ImageView(new Image(getClass().getResourceAsStream(path)));
    iconImage.setPreserveRatio(true);
    iconImage.setFitHeight(29);
    iconImage.setFitWidth(29);
    iconImage.setSmooth(true);
    Button button = new Button("", iconImage);
    button.setStyle(
        "-fx-background-color: transparent; -fx-border-color: transparent;"
            + " -fx-border-width: 0; -fx-padding: 0; ");
    return button;
  }

  /**
   * Getter method for icon button.
   *
   * @return The icon button.
   */
  public Button getIconButton() {
    return icon;
  }

  /**
   * Method to create the scene for the file upload page.
   *
   * @return The scene object for the file upload page.
   */
  public Scene createScene() {
    HBox buttonBox = new HBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(40);
    buttonBox.getChildren().addAll(backButton, nextButton);
    Text infoLabel = TextStyler.getInstance().styleText("Story File Location:  ", 25);
    HBox centerBox = new HBox(infoLabel, fileLocationField, icon);
    centerBox.setAlignment(Pos.CENTER);
    centerBox.setSpacing(10);
    ImageView name =
        new ImageView(new Image(getClass()
            .getResourceAsStream("/images/logos/upload_story.png")));
    name.setPreserveRatio(true);
    name.setFitHeight(600);
    name.setFitWidth(600);
    HBox nameBox = new HBox();
    nameBox.getChildren().add(name);
    nameBox.setAlignment(Pos.CENTER);
    BorderPane uploadPane = new BorderPane();
    uploadPane.setTop(nameBox);
    uploadPane.setCenter(centerBox);
    buttonBox.setAlignment(Pos.CENTER);
    uploadPane.setBottom(buttonBox);
    uploadPane.setBackground(
        BackgroundSetUp.getInstance()
            .getBackground("/images/backgrounds/file_upload_page.png"));
    return new Scene(uploadPane);
  }

  /**
   * Getter method for file location field.
   *
   * @return The file location text field.
   */
  public TextField getFileLocationField() {
    return fileLocationField;
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
   * Getter method for goBackButton.
   *
   * @return The goBack button.
   */
  public Button getBackButton() {
    return backButton;
  }

  /**
   * Getter method for FileChooser.
   *
   * @return The File Chooser object.
   */
  public FileChooser getFileChooser() {
    return fileChooser;
  }
}
