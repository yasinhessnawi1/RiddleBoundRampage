package org.example.pathsgame.ui.views.game.ingamesettingview;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Dialog;

/**
 * The GameColorChangerView class is a Singleton that manages the creation of a color selection
 * dialog in the game view. It allows users to choose a color from a ColorPicker and confirms or
 * cancels the color change with Confirmation and Cancel buttons.
 *
 * @author Rami.
 * @author Yasin.
 * @version 0.1
 */
public class GameColorChangerView {
  private static GameColorChangerView instance;
  private final ColorPicker colorPicker;
  private final ButtonType confirmationButton;
  private final ButtonType cancelButton;

  /**
   * The private constructor of the GameColorChangerView class.
   * It initializes a ColorPicker, a confirmation button, and a cancel button.
   */
  private GameColorChangerView() {
    colorPicker = new ColorPicker();
    confirmationButton = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
    cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
  }

  /**
   * This method provides a way to access a single instance of the GameColorChangerView class.
   *
   * @return The single instance of the GameColorChangerView class.
   */
  public static synchronized GameColorChangerView getInstance() {
    if (instance == null) {
      instance = new GameColorChangerView();
    }
    return instance;
  }

  /**
   * This method creates a dialog with a ColorPicker for color selection, and a confirmation and a
   * cancel button to confirm or cancel the color change.
   *
   * @return A dialog with a ColorPicker and two buttons.
   */
  public Dialog<ButtonType> getColorChangeDialog() {
    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.getDialogPane().setContent(colorPicker);
    dialog.getDialogPane().getButtonTypes().addAll(confirmationButton, cancelButton);
    return dialog;

  }

  /**
   * The method returns the ColorPicker instance.
   *
   * @return A ColorPicker instance.
   */
  public ColorPicker getColorPicker() {
    return colorPicker;
  }


  /**
   * The method returns the confirmation button instance.
   *
   * @return A ButtonType instance representing the confirmation button.
   */
  public ButtonType getConfirmationButton() {
    return confirmationButton;
  }
}
