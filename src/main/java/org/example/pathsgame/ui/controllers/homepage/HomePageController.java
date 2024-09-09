package org.example.pathsgame.ui.controllers.homepage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.entities.characters.Player;
import org.example.pathsgame.entities.characters.PlayerFileHandler;
import org.example.pathsgame.entities.game.Game;
import org.example.pathsgame.entities.goals.GoalsSavingHandler;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.entities.story.StoryFileHandler;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.factories.PlayerInformationHolder;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.setup_costumize.GameMusicHandler;
import org.example.pathsgame.ui.controllers.game.CharacterChoosePageController;
import org.example.pathsgame.ui.controllers.game.GameController;
import org.example.pathsgame.ui.controllers.game.PlayerInformationProvidingController;
import org.example.pathsgame.ui.views.homepage.HelpView;
import org.example.pathsgame.ui.views.homepage.HomePageView;
import org.example.pathsgame.utility.CheckValid;
import org.example.pathsgame.utility.SceneHandler;

/**
 * The HomePageController class is responsible for handling the actions on the HomePageView of the
 * game. It includes initializing the Home page view, handling story upload, starting the game,
 * and managing the game stories.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class HomePageController {
  private Pane homePane;

  private static HomePageController instance;

  private FileUploadPageController fileUploadPageController;

  private final HomePageView view;

  private String filePath;

  private Story story;

  private static final String ERROR_HEADER = "Error reading directory";

  private static final String ERROR_CONTENT = "Failed to read the directory : ";

  private static final String ERROR_TITLE = "Error";

  private Stage stage;

  /**
   * Private constructor for the HomePageController.
   * Initializes the view for this controller and player information providing controller.
   */
  public HomePageController() {

    view = HomePageView.getInstance();
    view.setHomePageController(this);
  }

  /**
   * Singleton pattern getInstance method. Returns the instance of this class , creating it if
   * necessary.
   *
   * @return The instance of this class.
   */
  public static synchronized HomePageController getInstance() {
    if (instance == null) {
      instance = new HomePageController();
    }
    return instance;
  }

  /**
   * Getter method for the file path.
   *
   * @return The file path as a string.
   */
  public String getFilePath() {
    return filePath;
  }

  /**
   * Handles the action for uploading a new story.
   */
  public void onUploadStory() {
    fileUploadPageController.getFileUpload();
  }

  /**
   * Returns the home page view pane.
   *
   * @param stage The stage on which to display the game.
   * @return The Pane object representing the home page view.
   */
  public Pane getHomePageView(Stage stage) {

    this.stage = stage;
    if (homePane == null) {
      fileUploadPageController = FileUploadPageController.getInstance(
          () -> stage.setScene(SceneHandler.getInstance().getScenes().get("homePage")),
          myStory -> {
          }, stage);
      homePane = view.createHomePageView(stage);
      HomePageController.getInstance().addMusicButton();
      onActionContinueButton();
      startButtonOnAction();
      onUploadStoryButtonAction();
      onExitButtonOnAction();
      onHelpButtonClicked(view.getHelp());
    }
    return homePane;
  }

  /**
   * Adds a button for controlling the music to the home page.
   */
  public void addMusicButton() {

    GameMusicHandler.getInstance().playMusic("/music/homePage.wav");
    if (view.getMainPanel().getBottom() instanceof Button) {
      // Dispose resources if needed
      view.getMainPanel().setBottom(null);
    }
    Button musicButton = new Button();
    MediaPlayer musicButtonMediaPlayer = GameMusicHandler.getInstance().getMediaPlayer();
    musicButtonMediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
      if (newStatus == MediaPlayer.Status.PLAYING) {
        ButtonCustomizer.getInstance().getEffect(musicButton, "stop_music.png",
            250, 250);
      } else {
        ButtonCustomizer.getInstance().getEffect(musicButton, "start_music.png",
            250, 250);
      }
    });
    musicButton.setOnAction(e -> {
      if (musicButtonMediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
        GameMusicHandler.getInstance().pauseMusic();
      } else {
        GameMusicHandler.getInstance().resumeMusic();
      }
    });
    musicButton.setAlignment(Pos.BASELINE_RIGHT);
    view.getMainPanel().setBottom(musicButton);
  }

  /**
   * handles to continue progress button on clicked.
   */
  private void onActionContinueButton() {

    view.getContinueButton().setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      createContinueButtonComboBox();
    });
  }

  /**
   * Retrieves the file name of the current selected story.
   *
   * @return The file name as a string.
   */
  public String getFileName() {
    return view.getStoryInformationComboBox().getValue();
  }

  /**
   * Updates the story ComboBox with the available story files.
   */
  public void updateStoryComboBox() {
    ComboBox<String> newStoryInformationComboBox =
        HomePageView.getInstance().createStoryComboBox();
    newStoryInformationComboBox.getStyleClass().add("combo-box");
    newStoryInformationComboBox.setValue(newStoryInformationComboBox.getItems().get(0));

    view.getMainPanel().getChildren().remove(view.getStoryInformationComboBox());
    view.getMainPanel().setRight(newStoryInformationComboBox);

    view.setStoryInformationComboBox(newStoryInformationComboBox);
  }

  /**
   * Returns the Story object.
   *
   * @return The Story object.
   */
  public Story getStory() {
    return story;
  }

  /**
   * Handles the action for the start game button.
   */
  public void startButtonOnAction() {

    view.getStartGameButton().setOnAction(actionEvent -> {
      try {
        PathGameApplication.getButtonSound().play();
        HomePageController.getInstance().initializeStory();
        onStartGame(view.getStage());
      } catch (NoSuchElementException e) {
        AlertFactory.getInstance().getErrorAlert("Error", "The story is not valid",
            "PLease try to upload the story from upload button,"
                + "and try again.").showAndWait();
      } catch (NumberFormatException e) {
        AlertFactory.getInstance().getErrorAlert("Error", "The story is not valid",
                "Error reading the story from the JSON file due to an invalid number format.")
            .showAndWait();
      } catch (IndexOutOfBoundsException e) {
        AlertFactory.getInstance().getErrorAlert("Error", "The story is not valid",
                "Error reading the story from the file due a wrong format of the story pattern")
            .showAndWait();
      } catch (Exception e) {
        e.printStackTrace();
        AlertFactory.getInstance().getErrorAlert("Error", "The story is not valid",
                "Error reading the story from the file due to an unknown error.")
            .showAndWait();
      }
    });
  }

  /**
   * handles to upload story button on clicked.
   */
  private void onUploadStoryButtonAction() {

    view.getUpLoadStory().setOnAction(actionEvent -> {
      PathGameApplication.getButtonSound().play();
      onUploadStory();
    });
  }

  /**
   * handles to exit button on clicked.
   */
  private void onExitButtonOnAction() {

    view.getExit().setOnAction(actionEvent -> {
      PathGameApplication.getButtonSound().play();
      System.exit(0);
    });
  }

  /**
   * Defines the behavior for when the help button is clicked.
   */
  public void onHelpButtonClicked(Button helpButton) {

    helpButton.setOnAction(actionEvent -> {
      Stage helpStage = new Stage();
      helpStage.setScene(HelpView.getInstance().getHelpScene());
      helpStage.show();
    });
  }

  /**
   * Creates a ComboBox allowing the user to continue a previously saved story.
   */
  public void createContinueButtonComboBox() {

    ComboBox<String> savedStories = new ComboBox<>();
    savedStories.setPromptText("Choose a saved progress");
    String userHome = System.getProperty("user.home");
    String folderPath = userHome + "/Riddlebound rampage/saved_stories/";
    String playerFolderName = userHome + "/Riddlebound rampage/players/";
    String goalPath = userHome + "/Riddlebound rampage/goals/";
    Path targetFolderPath = Paths.get(folderPath);
    Path playerFolderPath = Paths.get(playerFolderName);
    Path goalFolderPath = Paths.get(goalPath);
    handelLoadStoryAction(savedStories, folderPath, targetFolderPath, playerFolderPath,
        goalFolderPath);
  }

  /**
   * Retrieves the story from a file in the stories' folder.
   */
  public void initializeStory() {

    String fileName = getFileName();
    String userHome = System.getProperty("user.home");
    String folderPath = userHome + "/Riddlebound rampage/stories/";
    Path path = Paths.get(folderPath);
    try (Stream<Path> filePathStream = Files.list(path)) {

      Optional<Path> foundPath = filePathStream
          .filter(file -> file.getFileName().toString().equals(fileName))
          .findFirst();

      if (foundPath.isPresent()) {
        filePath = foundPath.get().toString();
        setStory(StoryFileHandler.getInstance().readStoryFromFile(foundPath.get().toString()));
      } else {
        AlertFactory.getInstance().getErrorAlert("Error while reading file", "File not found",
            "File with name '" + fileName + "' not found in folder: " + folderPath);
      }
    } catch (IOException e) {
      AlertFactory.getInstance().getErrorAlert("Error", "Error while reading folder",
          "Error while reading files from folder: " + folderPath);

    } catch (IllegalArgumentException e) {
      AlertFactory.getInstance().getErrorAlert("Error", "Error while reading file, "
              + "story is invalid due format error",
          "Error while reading file: " + fileName);
    }
  }

  /**
   * Starts the game by retrieving player information and setting up the game scene.
   *
   * @param stage The stage on which to display the game.
   */
  public void onStartGame(Stage stage) {

    PlayerInformationProvidingController.getInstance().getPlayerInformationPanel(stage, story);
  }

  /**
   * Handles the story reading method, this is the base method that retrieves a story form a
   * saved file.
   */
  private void handelLoadStoryAction(ComboBox<String> savedStories, String folderPath,
                                     Path targetFolderPath,
                                     Path playerFolderPath, Path goalFolderPath) {

    if (fillUpComboBoxWithSavesStories(savedStories, folderPath, targetFolderPath)) {
      return;
    }
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    dialog.getDialogPane().setContent(savedStories);
    Optional<ButtonType> result = dialog.showAndWait();
    if (result.isPresent() && result.get().equals(ButtonType.OK)) {
      String fileName = savedStories.getValue();
      String storyName = fileName.split("\\.paths")[0];
      Optional<Path> foundPath;
      Optional<Path> foundPlayerPath;
      Optional<Path> foundGoalPath;
      try (Stream<Path> filePathStream = Files.list(targetFolderPath)) {
        foundPath = filePathStream
            .filter(file -> file.getFileName().toString().equals(fileName))
            .findFirst();
      } catch (IOException e) {
        AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, ERROR_HEADER,
            ERROR_CONTENT + targetFolderPath).showAndWait();
        return;
      }
      try (Stream<Path> filePathStream = Files.list(playerFolderPath)) {
        foundPlayerPath = filePathStream
            .filter(file -> file.getFileName().toString().equals(storyName + ".player"))
            .findFirst();
      } catch (IOException e) {
        AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, ERROR_HEADER,
            ERROR_CONTENT + playerFolderPath).showAndWait();
        return;
      }
      try (Stream<Path> filePathStream = Files.list(goalFolderPath)) {
        foundGoalPath = filePathStream
            .filter(file -> file.getFileName().toString().equals(storyName + ".goals"))
            .findFirst();
      } catch (IOException e) {
        AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, ERROR_HEADER,
            ERROR_CONTENT + goalFolderPath).showAndWait();
        return;
      }
      if (!foundPath.isPresent() || !foundPlayerPath.isPresent() || !foundGoalPath.isPresent()) {
        AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, "File not found",
            "One or more required files were not found.").showAndWait();
        return;
      }
      setUpNewGame(foundPath, foundPlayerPath, foundGoalPath);
    }
  }

  /**
   * Sets the Story object.
   *
   * @param story The Story object.
   */
  public void setStory(Story story) {

    try {
      CheckValid.checkObjectAndThrowException(story, "Story");
      this.story = story;
    } catch (Exception e) {
      AlertFactory.getInstance().getErrorAlert("Error",
          "Error creating game with new story",
          "Please restart the game and try again.");
    }
  }

  /**
   * Reads the fiels that are saved in the folder saved stories and fills up the combo box with the
   * name of the files.
   *
   * @param savedStories     the cobo box that will be filled up with the names of the files.
   * @param folderPath       the path of the folder that contains the saved stories.
   * @param targetFolderPath the path of the file that contains the target story.
   * @return true if the process is successful, false otherwise.
   */
  private static boolean fillUpComboBoxWithSavesStories(ComboBox<String> savedStories,
                                                        String folderPath,
                                                        Path targetFolderPath) {

    if (!new File(folderPath).exists()) {
      new File(folderPath).mkdirs();
    }
    try (Stream<Path> filePathStream = Files.list(targetFolderPath)) {
      filePathStream.filter(Files::isRegularFile)
          .map(Path::getFileName)
          .map(Path::toString)
          .forEach(savedStories.getItems()::add);

    } catch (IOException e) {
      AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, ERROR_HEADER,
          ERROR_CONTENT + targetFolderPath).showAndWait();
      return true;
    }
    return false;
  }

  /**
   * Sets up a new game. retrieves teh story and constructs the game and sets up and sends objects
   * to responsible classes for setting up game view.
   *
   * @param foundPath       the path of the file that contains the target story.
   * @param foundPlayerPath the path of the file that contains the target player.
   * @param foundGoalPath   the path of the file that contains the target goals.
   */
  private void setUpNewGame(Optional<Path> foundPath, Optional<Path> foundPlayerPath,
                            Optional<Path> foundGoalPath) {

    try {
      Player player =
          PlayerFileHandler.getInstance().loadPlayer(foundPlayerPath.get().toString());

      Game savedgame = new Game(player,
          StoryFileHandler.getInstance().readStoryFromFile(foundPath.get().toString()),
          GoalsSavingHandler.getInstance().deserializeGoalList(foundGoalPath.get().toString()));
      PlayerInformationHolder.getInstance().setPlayer(player);
      CharacterChoosePageController.getInstance().setGame(savedgame);
      CharacterChoosePageController.getInstance().setPlayer(player);
      CharacterChoosePageController.getInstance().setStory(savedgame.getStory());
      GameController gameController = new GameController(savedgame,
          stage, player.getPlayerSpritePath());
      stage.setScene(gameController
          .getIngameScene(CharacterChoosePageController.getInstance().getGame().begin()));
      GameController.setInstance(gameController);
      GameMusicHandler.getInstance().playMusic("/music/in_game.mp3");
    } catch (IOException | ClassNotFoundException | NullPointerException |
             IndexOutOfBoundsException e) {
      AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, "Error loading game",
          "There was an error loading the game. Please try again").showAndWait();
    } catch (IllegalArgumentException e) {
      AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, "Error loading game",
          "There was an error loading the game a link or a passage has a wrong "
              + "format or pattern. Please try again").showAndWait();
    }
  }
}
