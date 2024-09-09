package org.example.pathsgame.ui.views.game.ingamesettingview;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import org.example.pathsgame.setup_costumize.ButtonCustomizer;
import org.example.pathsgame.setup_costumize.GameMusicHandler;
import org.example.pathsgame.ui.controllers.game.GameSettingController;


/**
 * GameSettingsFactory is a singleton factory class that generates the game's settings dialog.
 * It allows the player to view story information, change the game colors, and restart the game.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GameSettingView {
  private static GameSettingView instance;

  private Dialog<ButtonType> settingDialog;

  private HBox settingBox;

  private Button storyInformation;

  private final VBox buttonBox;

  private Button musicButton;

  private Button backToHome;

  private Button restartButton;

  private Button help;

  private Button saveProgress;

  private Button changeGameColor;


  /**
   * Creates a new GameSettingsFactory instance with the given settings.
   */
  private GameSettingView() {

    buttonBox = new VBox();
    setUpSettingButton();
  }

  /**
   * Sets up the settings button with its associated actions.
   * It creates the buttons and associated an event handlers with it that triggers the settings
   * dialog.
   */
  private void setUpSettingButton() {
    settingBox = new HBox();
    ImageView settingIcon =
        new ImageView(new Image(getClass()
            .getResourceAsStream("/images/icons/settings.gif")));
    settingIcon.setPreserveRatio(true);
    settingIcon.setFitHeight(50);
    settingIcon.setFitWidth(50);
    Button settingButton = new Button("", settingIcon);
    settingButton.setStyle("-fx-background-color: transparent;"
        + " -fx-border-color: transparent; -fx-border-width: 0; -fx-padding: 0;");
    settingIcon.setStyle("-fx-background-color: transparent;");
    Text settingText = new Text("Settings");
    settingText.setStyle("-fx-font-size: 40px;");
    settingButton.setOnAction(event -> setUpSettingDialog());
    settingBox.getChildren().addAll(settingText, settingButton);
    settingBox.setSpacing(10);
  }

  /**
   * Constructs and displays the settings dialog pane.
   * The dialog pane includes buttons for viewing story information, changing game color, saving
   * progress, restoring the game, and go back to the home page.
   */
  public void setUpSettingDialog() {

    settingDialog = new Dialog<>();

    settingDialog.getDialogPane().setStyle(
        "-fx-background-color: linear-gradient(to bottom, #3e3e3e 0%, #191919 50%, #3e3e3e 100%);");
    storyInformation = new Button();
    ButtonCustomizer.getInstance()
        .getEffect(storyInformation, "setting_button_icons/story_information.png",
            250, 250);
    changeGameColor = new Button();
    ButtonCustomizer.getInstance()
        .getEffect(changeGameColor, "setting_button_icons/change_color.png",
            250, 250);
    help = new Button();
    ButtonCustomizer.getInstance()
        .getEffect(help, "setting_button_icons/help.png", 250, 250);
    restartButton = new Button();
    ButtonCustomizer.getInstance()
        .getEffect(restartButton, "setting_button_icons/restart.png",
            250, 250);
    backToHome = new Button();
    ButtonCustomizer.getInstance()
        .getEffect(backToHome, "setting_button_icons/back_to_home.png",
            250, 250);
    saveProgress = new Button();
    ButtonCustomizer.getInstance()
        .getEffect(saveProgress, "setting_button_icons/save_progress.png",
            250, 250);
    addMusicButton();
    GameSettingController.getInstance().onHelpButtonClicked();
    GameSettingController.getInstance().onExitGameClicked(backToHome);
    GameSettingController.getInstance().onRestartGameClicked(restartButton);
    GameSettingController.getInstance().onChangeColorClicked();
    GameSettingController.getInstance().showStoryInformation();
    GameSettingController.getInstance().onSaveProgressClicked();
    if (buttonBox.getChildren().isEmpty() || buttonBox.getChildren().size() == 1) {
      buttonBox.getChildren()
          .addAll(musicButton, storyInformation, changeGameColor, saveProgress, restartButton,
              backToHome, help);
      buttonBox.setSpacing(10);
    }
    DialogPane settingDialogPane = settingDialog.getDialogPane();
    settingDialogPane.getButtonTypes().addAll(ButtonType.CLOSE);
    settingDialogPane.setContent(buttonBox);
    settingDialog.show();
  }

  /**
   * The method adds a button to the settings dialog that controls the game music.
   * The button toggles between playing and passing the music.
   */
  public void addMusicButton() {

    if (musicButton == null) {
      musicButton = new Button();
    }
    MediaPlayer mediaPlayer = GameMusicHandler.getInstance().getMediaPlayer();

    if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
      ButtonCustomizer.getInstance().getEffect(musicButton, "stop_music.png",
          250, 250);
      musicButton.setOnAction(e -> GameMusicHandler.getInstance().pauseMusic());
    } else {
      ButtonCustomizer.getInstance().getEffect(musicButton, "start_music.png",
          250, 250);
      musicButton.setOnAction(e -> GameMusicHandler.getInstance().resumeMusic());
    }
    mediaPlayer.statusProperty().addListener((obs, oldStatus, newStatus) -> {
      if (newStatus == MediaPlayer.Status.PLAYING) {
        ButtonCustomizer.getInstance().getEffect(musicButton, "stop_music.png",
            250, 250);
        musicButton.setOnAction(e -> GameMusicHandler.getInstance().pauseMusic());
      } else {
        ButtonCustomizer.getInstance().getEffect(musicButton, "start_music.png",
            250, 250);
        musicButton.setOnAction(e -> GameMusicHandler.getInstance().resumeMusic());
      }
    });

  }

  /**
   * Returns the single instance of the GameSettingView.
   *
   * @return The single instance of the GameSettingView.
   */
  public static synchronized GameSettingView getInstance() {

    if (instance == null) {
      instance = new GameSettingView();
    }
    return instance;
  }

  /**
   * Returns the button that allows the player to view the story information.
   *
   * @return The button that allows the player to view the story information
   */
  public Button getStoryInformation() {

    return storyInformation;
  }

  /**
   * Returns the button that allows the player to go back to the home page.
   *
   * @return The button that allows the player to go back to the home page
   */
  public Button getBackToHome() {

    return backToHome;
  }

  /**
   * Returns the button that allows the player to restart the game.
   *
   * @return The button that allows the player to restart the game
   */
  public Button getRestartButton() {

    return restartButton;
  }

  /**
   * Returns the button that allows the player to view the game help.
   *
   * @return The button that allows the player to view the game help
   */
  public Button getHelp() {

    return help;
  }

  /**
   * Returns the button that allows the player to save the game progress.
   *
   * @return The button that allows the player to save the game progress
   */
  public Button getSaveProgress() {

    return saveProgress;
  }

  /**
   * Returns the button that allows the player to change the game color.
   *
   * @return The button that allows the player to change the game color
   */
  public Button getChangeGameColor() {

    return changeGameColor;
  }

  /**
   * Returns the settings box containing the settings button and text.
   *
   * @return The settings box containing the settings button and text
   */
  public HBox getSettingBox() {

    return settingBox;
  }

  /**
   * gets the setting dialog.
   *
   * @return the setting dialog.
   */
  public Dialog<ButtonType> getSettingDialog() {

    return settingDialog;
  }

}
