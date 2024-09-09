package org.example.pathsgame.ui.controllers.game;

import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.example.pathsgame.PathGameApplication;
import org.example.pathsgame.factories.AlertFactory;
import org.example.pathsgame.ui.views.game.ingame.InGameLayout;
import org.example.pathsgame.ui.views.game.ingamesettingview.GameColorChangerView;

/**
 * The GameColorChangerController class manages the operations related to changing the color of
 * the game view.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1.
 */
public class GameColorChangerController {
  private static GameColorChangerController instance;
  private final GameColorChangerView view;
  private static final String BACKGROUND_COLOR = "-fx-background-color: ";

  /**
   * The constructor of the GameColorChangerController class.
   * It initializes the GameColorChangerView.
   */
  private GameColorChangerController() {
    view = GameColorChangerView.getInstance();
  }

  /**
   * This method provides a way to access a single instance of the GameColorChangerController
   * class.
   *
   * @return The single instance of the GameColorChangerController.
   */
  public static synchronized GameColorChangerController getInstance() {
    if (instance == null) {
      instance = new GameColorChangerController();
    }
    return instance;
  }

  /**
   * This method manages the color change operation. It shows a dialog to the user for color
   * selection, applies the color change if the user confirms the change, and restores the old
   * color otherwise.
   */
  public void changeColor() {

    String oldColor = InGameLayout.getInstance().getRoomViewBorderPane().getStyle();
    EventHandler<ActionEvent> colorChangeHandler =
        colorPickerEvent();
    // Add the EventHandler to the ColorPicker
    onColorPickerClickedAction(colorChangeHandler);
    Dialog<ButtonType> changeColorDialog = view.getColorChangeDialog();
    Optional<ButtonType> result = changeColorDialog.showAndWait();
    if (result.isPresent() && result.get() == view.getConfirmationButton()) {
      Alert alert = AlertFactory.getInstance()
          .getInformationAlert("Congratulation", "Color changed",
              "The color has been changed");
      alert.showAndWait();
    } else {
      InGameLayout.getInstance().getRoomViewBorderPane().setStyle(oldColor);
      InGameLayout.getInstance().getLeftVbox().setStyle(oldColor);
      InGameLayout.getInstance().getRightVbox().setStyle(oldColor);
    }
    // Remove the EventHandler from the ColorPicker
    view.getColorPicker().setOnAction(null);
  }

  /**
   * Handles the event of the color picker.
   *
   * @return the event of the color picker.
   */
  private EventHandler<ActionEvent> colorPickerEvent() {

    return e -> {
      String newColor = view.getColorPicker().getValue().toString().replace("0x",
          "#");
      InGameLayout.getInstance().getRoomViewBorderPane()
          .setStyle(BACKGROUND_COLOR + newColor + ";");
      InGameLayout.getInstance().getLeftVbox().setStyle(BACKGROUND_COLOR + newColor + ";");
      InGameLayout.getInstance().getRightVbox().setStyle(BACKGROUND_COLOR + newColor + ";");
    };
  }

  /**
   * Handles the click event of the color picker
   *
   * @param colorChangeHandler the event handler of the color picker.
   */
  private void onColorPickerClickedAction(EventHandler<ActionEvent> colorChangeHandler) {

    view.getColorPicker().setOnAction(e -> {
      PathGameApplication.getButtonSound().play();
      colorChangeHandler.handle(e);
    });
  }


}
