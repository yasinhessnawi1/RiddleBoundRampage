package org.example.pathsgame.ui.controllers.homepage;

import static org.example.pathsgame.ui.views.homepage.FileUploadPageView.SCENE_NAME;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import javafx.stage.Stage;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.entities.story.Story;
import org.example.pathsgame.entities.story.StoryFileHandler;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.ui.views.homepage.FileUploadPageView;
import org.example.pathsgame.utility.NavigationBetweenSceneData;
import org.example.pathsgame.utility.SceneHandler;

/**
 * Controller for the file upload page. This class handles user interaction on the file
 * upload page, reads the story file, update the view, and manages the corresponding view.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class FileUploadPageController {
  private static FileUploadPageController instance;
  private final Runnable onBackButtonClicked;
  private final Consumer<Story> onFileLoaded;
  private final FileUploadPageView view;
  private final Stage stage;
  private static final String ERROR_TITLE = "File Error";

  /**
   * Constructor for FileUploadPageController.
   *
   * @param onBackButtonClicked A Runnable called when the back button is clicked.
   * @param onFileLoaded        A Consumer function called when the file is successfully loaded.
   * @param stage               The stage on which to display the views.
   */
  private FileUploadPageController(Runnable onBackButtonClicked,
                                   Consumer<Story> onFileLoaded, Stage stage) {
    this.stage = stage;
    view = FileUploadPageView.getInstance(stage);
    this.onBackButtonClicked = onBackButtonClicked;
    this.onFileLoaded = onFileLoaded;
  }

  /**
   * Singleton pattern getInstance method. Returns the instance of this class, creating it if
   * necessary.
   *
   * @param onBackButtonClicked A Runnable called when the back button is clicked.
   * @param onFileLoaded        A Consumer function called when the file is successfully loaded.
   * @param stage               The stage on which to display the views.
   * @return The instance of this class.
   */
  public static synchronized FileUploadPageController getInstance(Runnable onBackButtonClicked,
                                                                  Consumer<Story> onFileLoaded,
                                                                  Stage stage) {
    if (instance == null) {
      instance = new FileUploadPageController(onBackButtonClicked, onFileLoaded, stage);
    }
    return instance;
  }

  /**
   * Initializes the file upload page. Sets up event handlers for the view buttons and displays the
   * view.
   */
  public void getFileUpload() {

    onNextButtonClickedAction();
    onfileUploadAction();
    onGoBackButtonAction();
    SceneHandler.getInstance().setScene(view.createScene(), SCENE_NAME);
    stage.setScene(SceneHandler.getInstance().getScenes().get(SCENE_NAME));
    stage.show();
  }

  /**
   * Handles teh next button action,  copying the selected file to the specified destination.
   */
  private void onNextButtonClickedAction() {

    view.getNextButton().setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      if (!view.getFileLocationField().getText().isEmpty()) {
        StoryFileHandler storyFileHandler = StoryFileHandler.getInstance();
        try {
          Story story = storyFileHandler.readStoryFromFile(view.getFileLocationField().getText());
          onFileLoaded.accept(story);
          stage.setScene(SceneHandler.getInstance().getScenes().get("homePage"));
        } catch (NoSuchElementException | IOException | IllegalArgumentException
                 | IndexOutOfBoundsException e) {
          AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, "Error while reading file",
              "There was an error while reading the file").showAndWait();
        }
      } else {
        AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, "Invalid file location",
            "Please enter a valid file location. ").showAndWait();
      }
    });
  }

  /**
   * Handles the click action of the file upload icon.
   */
  private void onfileUploadAction() {

    view.getIconButton().setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      checkChooserFile();
    });
  }

  /**
   * Handles the go back button action.
   */
  private void onGoBackButtonAction() {

    view.getBackButton().setOnAction(event -> {
      PathGameApplication.getButtonSound().play();
      NavigationBetweenSceneData.getInstance()
          .storeData("fileLocation", view.getFileLocationField().getText());
      NavigationBetweenSceneData.getInstance().storeData("currentScene", SCENE_NAME);
      onBackButtonClicked.run();
    });
  }

  /**
   * Check if the file selected by the user is valid and can be read as a story file.
   * If valid, the file is copied to a specified location and the view is updated.
   */
  private void checkChooserFile() {
    File selectedFile = view.getFileChooser().showOpenDialog(stage);
    if (selectedFile != null) {

      try {
        // Check if the selected file can be read correctly before copying it.
        StoryFileHandler.getInstance().readStoryFromFile(selectedFile.getAbsolutePath());

        // File can be read correctly, so continue with the copying process.
        String userHome = System.getProperty("user.home");
        String destinationPath = userHome + "/Riddlebound rampage/stories/";
        File destinationDir = new File(destinationPath);
        if (!destinationDir.exists()) {
          destinationDir.mkdirs();
        }
        copyTheChoseFile(selectedFile, destinationPath);
        AlertFactory.getInstance().getInformationAlert("Confirmation", "The Story is "
                + "added to list of stories", "Congratulations you have got new story")
            .showAndWait();
      } catch (NoSuchElementException | IOException
               | IndexOutOfBoundsException | IllegalArgumentException e) {
        AlertFactory.getInstance().getErrorAlert(ERROR_TITLE, "Error reading file",
            "There was an error while reading the file").showAndWait();
      }
    }
  }

  /**
   * Copies the chosen file to the specified destination and updates the view to display the new
   * file path.
   *
   * @param selectedFile    The selected file to be copied
   * @param destinationPath The destination where the file is to be copied.
   */
  private void copyTheChoseFile(File selectedFile, String destinationPath) {
    String destinationFilePath = destinationPath + selectedFile.getName();
    Path source = selectedFile.toPath();
    Path destination = Paths.get(destinationFilePath);

    try {
      Files.copy(source, destination);
      HomePageController.getInstance().updateStoryComboBox();
      view.getFileLocationField().setText(destinationFilePath);
    } catch (IOException e) {
      AlertFactory.getInstance().getErrorAlert(ERROR_TITLE,
          "Problem copying the file",
          "The destination file not found").showAndWait();
    }
  }
}
