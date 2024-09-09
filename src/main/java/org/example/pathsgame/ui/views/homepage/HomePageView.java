package org.example.pathsgame.ui.views.homepage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.setup_costumize.BackgroundSetUp;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.ui.controllers.homepage.HomePageController;

/**
 * HomePageView is the primary view class for displaying the home page of the Paths Game
 * Application. It is implemented as a Singleton and provides various methods for initialization
 * and manipulation the UI elements of the home page.
 *
 * @author Rami.
 * @author Yasin
 */
public class HomePageView {
  private static HomePageView homePageViewInstance;

  private ComboBox<String> storyInformationComboBox;

  private HomePageController homePageController;

  private Stage stage;

  private final BorderPane mainPanel;

  private Button startGameButton;

  private Button upLoadStory;

  private Button continueButton;

  private Button help;

  private Button exit;

  /**
   * Private constructor that initializes the storyInformationComboBox and the mainPanel UI
   * elements. This constructor is private to enforce the Singleton design pattern.
   */
  private HomePageView() {

    storyInformationComboBox = new ComboBox<>();
    mainPanel = new BorderPane();

  }

  public Button getStartGameButton() {

    return startGameButton;
  }

  /**
   * Synchronized method to get the singleton instance HomePageView.
   * If the instance does not exist, it is created.
   *
   * @return The singleton instance HomePageView.
   */
  public static synchronized HomePageView getInstance() {
    if (homePageViewInstance == null) {
      homePageViewInstance = new HomePageView();
    }
    return homePageViewInstance;
  }

  /**
   * Getter method for the current stage of the application.
   *
   * @return The current stage of the application.
   */
  public Stage getStage() {
    return stage;
  }

  /**
   * Setter method for setting the HomePageController.
   *
   * @param homePageController The HomePageController instance to be set.
   */
  public void setHomePageController(HomePageController homePageController) {
    this.homePageController = homePageController;
  }

  /**
   * Creates the home page view with the supplied stage.
   *
   * @param stage The primary stage of the application.
   * @return The pane with the created home page view.
   */
  public Pane createHomePageView(Stage stage) {
    this.stage = stage;

    mainPanel.setBackground(
        BackgroundSetUp.getInstance()
            .getBackground("/images/backgrounds/home_page_background.png"));
    VBox buttonBox = createButtonBox(stage);
    mainPanel.setCenter(buttonBox);
    mainPanel.getCenter().setTranslateX(100);
    mainPanel.getCenter().setTranslateY(-40);
    HBox titleBox = createTitleBox();
    mainPanel.setTop(titleBox);
    storyInformationComboBox = createStoryComboBox();
    storyInformationComboBox.getStyleClass().add("combo-box");
    if (!storyInformationComboBox.getItems().isEmpty()) {
      storyInformationComboBox.setValue(storyInformationComboBox.getItems().get(0));
    }
    BorderPane.setAlignment(storyInformationComboBox, Pos.TOP_RIGHT);
    mainPanel.setRight(storyInformationComboBox);

    return mainPanel;
  }

  /**
   * Creates a VBox containing buttons for the home page.
   *
   * @param stage The primary stage of the application.
   * @return VBox containing buttons for the home page.
   */
  public VBox createButtonBox(Stage stage) {

    startGameButton = new Button();
    ButtonCustomizer.getInstance().getEffect(startGameButton, "new_game.png",
        250, 200);
    upLoadStory = new Button();
    ButtonCustomizer.getInstance().getEffect(upLoadStory, "upload_story.png",
        250, 200);


    exit = new Button();
    ButtonCustomizer.getInstance().getEffect(exit, "exit.png",
        250, 200);
    help = new Button();
    ButtonCustomizer.getInstance().getEffect(help, "help.png",
        250, 200);

    continueButton = new Button();

    continueButton.setText("");
    ButtonCustomizer.getInstance().getEffect(continueButton,
        "continue.png", 250, 200);
    VBox buttonBox = new VBox();
    buttonBox.setAlignment(Pos.CENTER);
    buttonBox.setSpacing(30);
    buttonBox.getChildren().addAll(startGameButton, continueButton, upLoadStory, help, exit);
    return buttonBox;
  }

  /**
   * Creates the title box UI elements.
   *
   * @return The HBox containing the title.
   */
  private HBox createTitleBox() {
    HBox titleBox = new HBox();
    ImageView title = new ImageView(
        new Image(getClass().getResourceAsStream("/images/logos/home_page_game_logo.png")));
    titleBox.setAlignment(Pos.TOP_CENTER);
    titleBox.getChildren().add(title);
    return titleBox;
  }

  /**
   * Creates a ComboBox for selecting a story.
   *
   * @return The created ComboBox for selecting a story.
   */
  public ComboBox<String> createStoryComboBox() {
    storyInformationComboBox = new ComboBox<>();
    storyInformationComboBox.setPromptText("Choose a story");
    String userHome = System.getProperty("user.home");
    String folderPath = userHome + "/Riddlebound rampage/stories/";
    Path targetFolderPath = Paths.get(folderPath);

    // Check if the directory exists, if not create it.
    if (!Files.exists(targetFolderPath)) {
      try {
        Files.createDirectories(targetFolderPath);
        // Once the directory is created, copy the file from my resources to the new directory.
        InputStream sourceFileInputStream =
            getClass().getResourceAsStream("/stories/The Legend of the Heartstone.paths");
        Path targetFilePath = targetFolderPath.resolve("The Legend of the Heartstone.paths");
        Files.copy(sourceFileInputStream, targetFilePath, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        AlertFactory.getInstance().getErrorAlert("Error", "Error creating directory",
            "System Can't find the directory of the files and cannot create new one. "
                + " Please check if the directory Riddlebound rampage exist in your user home "
                + "directories").showAndWait();
      }
    }
    try (Stream<Path> filePathStream = Files.list(targetFolderPath)) {
      filePathStream.filter(Files::isRegularFile)
          .map(Path::getFileName)
          .map(Path::toString)
          .forEach(storyInformationComboBox.getItems()::add);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return storyInformationComboBox;
  }

  /**
   * Getter method for the mainPanel.
   *
   * @return The BorderPane representing the main panel of the home page.
   */
  public BorderPane getMainPanel() {
    return mainPanel;
  }

  /**
   * Getter method for the storyInformationComboBox.
   *
   * @return The ComboBox allowing the user to choose a story.
   */
  public ComboBox<String> getStoryInformationComboBox() {
    return storyInformationComboBox;
  }

  /**
   * Setter method for the storyInformationComboBox.
   *
   * @param storyInformationComboBox The ComboBox allowing the user to choose a story.
   */
  public void setStoryInformationComboBox(ComboBox<String> storyInformationComboBox) {

    this.storyInformationComboBox = storyInformationComboBox;
    mainPanel.setRight(storyInformationComboBox);
  }

  /**
   * getter method for the upload story button.
   *
   * @return the upload story button
   */
  public Button getUpLoadStory() {

    return upLoadStory;
  }

  /**
   * getter method for the continue  progress button.
   *
   * @return the continue progress button
   */
  public Button getContinueButton() {

    return continueButton;
  }

  /**
   * getetr method for the help button.
   *
   * @return the help button
   */
  public Button getHelp() {

    return help;
  }

  /**
   * getter method for the exit button.
   *
   * @return the exit button.
   */
  public Button getExit() {

    return exit;
  }
}