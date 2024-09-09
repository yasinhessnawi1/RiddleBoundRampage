package org.example.pathsgame.factories;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * AlertFactory is a singleton class used for creating and displaying various types of alert dialog
 * boxes in a JavaFX application. This includes confirmation, error, and information alert.
 * Alert are styled using an external stylesheet and can be animated using a shake effect.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.0.1
 */
public class AlertFactory {
  private static AlertFactory instance;
  private final Alert confirmationAlert;
  private final Alert errorAlert;
  private final Alert informationAlert;
  private static final String DIALOG_NAME = "myDialog";

  /**
   * Private constructor for singleton AlertFactory class. Initializes the different types of alerts
   * and sets up their styling and buttons types.
   */
  private AlertFactory() {
    confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    styleAlert(confirmationAlert, "confirmation-alert");
    errorAlert = new Alert(Alert.AlertType.ERROR);
    styleAlert(errorAlert, "error-alert");
    informationAlert = new Alert(Alert.AlertType.INFORMATION);
    styleAlert(informationAlert, "information-alert");
    ButtonType okButton = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
    ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
    confirmationAlert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
    errorAlert.getButtonTypes().setAll(okButton, cancelButton);
    DialogPane errorAlertDialogPane = errorAlert.getDialogPane();
    errorAlertDialogPane.getStyleClass().add(DIALOG_NAME);
    DialogPane confirmationAlertDialogPane = confirmationAlert.getDialogPane();
    confirmationAlertDialogPane.getStyleClass().add(DIALOG_NAME);
    DialogPane informationAlertDialogPane = informationAlert.getDialogPane();
    informationAlertDialogPane.getStyleClass().add(DIALOG_NAME);
  }

  /**
   * Applies the specified style class to the given alert and sets its DialogPane's minHeight
   * property.
   *
   * @param alert      The alert to apply the style to.
   * @param styleClass The style class to be added to the alert's DialogPane.
   */
  private void styleAlert(Alert alert, String styleClass) {
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets().add(getClass().getResource("/style/alerts.css")
        .toExternalForm());
    dialogPane.getStyleClass().add(styleClass);
    dialogPane.setMinHeight(Region.USE_PREF_SIZE);
  }

  /**
   * Returns the singleton instance of the AlertFactory class, creating it if it doesn't exist.
   *
   * @return The singleton instance of the AlertFactory class.
   */
  public static synchronized AlertFactory getInstance() {
    if (instance == null) {
      instance = new AlertFactory();
    }
    return instance;
  }

  /**
   * Animates the given alert with a shake effect using a Timeline.
   *
   * @param alert The alert to apply the shake effect to.
   */
  private void shakeAlert(Alert alert) {
    Timeline shakeTimeline = new Timeline(new KeyFrame(Duration.seconds(0),
        new KeyValue(alert.getDialogPane().translateXProperty(), 0)),
        new KeyFrame(Duration.seconds(0.1),
            new KeyValue(alert.getDialogPane().translateXProperty(), -10)),
        new KeyFrame(Duration.seconds(0.2),
            new KeyValue(alert.getDialogPane().translateXProperty(), 10)),
        new KeyFrame(Duration.seconds(0.3),
            new KeyValue(alert.getDialogPane().translateXProperty(), -10)),
        new KeyFrame(Duration.seconds(0.4),
            new KeyValue(alert.getDialogPane().translateXProperty(), 10)),
        new KeyFrame(Duration.seconds(0.5),
            new KeyValue(alert.getDialogPane().translateXProperty(), 0)));
    shakeTimeline.play();
  }

  /**
   * Returns an information alert dialog box with the given title, header text, and content.
   *
   * @param title   the title of the alert dialog box.
   * @param header  the header text of the alert dialog box.
   * @param content the content of the alert dialog box.
   */
  public Alert getInformationAlert(String title, String header, String content) {
    informationAlert.setTitle(title);
    informationAlert.setHeaderText(header);
    informationAlert.setContentText(content);
    PauseTransition pause = new PauseTransition(Duration.seconds(5));
    pause.setOnFinished(e -> informationAlert.hide());
    shakeAlert(errorAlert);
    pause.play();
    return informationAlert;
  }

  /**
   * Returns a confirmation alert dialog box with the specified title, header text, and content.
   *
   * @param title   The title of the alert dialog box.
   * @param header  The header text of the alert dialog box.
   * @param content The content of the alert dialog box.
   * @return A confirmation alert dialog box with the given title, header text, and content.
   */
  public Alert getConfirmationAlert(String title, String header, String content) {
    confirmationAlert.setTitle(title);
    confirmationAlert.setHeaderText(header);
    confirmationAlert.setContentText(content);
    shakeAlert(errorAlert);
    return confirmationAlert;
  }

  /**
   * Returns an error alert dialog box with the given title, header text, and content.
   *
   * @param title   the title of the alert dialog box.
   * @param header  the header text of the alert dialog box.
   * @param content the content of the alert dialog box.
   * @return An alert dialog box with the given title, header text, and content.
   */
  public Alert getErrorAlert(String title, String header, String content) {
    errorAlert.setTitle(title);
    errorAlert.setHeaderText(header);
    errorAlert.setContentText(content);
    shakeAlert(errorAlert);
    return errorAlert;

  }
}
