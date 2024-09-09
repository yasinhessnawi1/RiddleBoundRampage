package org.example.pathsgame.utility;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Checks if datatype are valid.
 *
 * @author Yasin Hessnawi
 * @author Rami.
 * @version 0.0.1 (02.17.2023)
 */
public class CheckValid {
  protected static final Logger UTILITY_LOGGER =
      Logger.getLogger("src/main/java/utility/CheckValid.java");
  private static final String ERROR = ". Error from class CHECK_VALID";

  /**
   * empty constructor.
   */
  private CheckValid() {
    //nothing to initialize here. Just to not get a default constructor.
  }

  /**
   * Check's if a string is valid or not.
   *
   * @param stringToCheck the string to be checked
   * @return validString returns if string is valid
   */
  public static boolean checkString(String stringToCheck) {
    return stringToCheck == null || stringToCheck.isEmpty() || stringToCheck.isBlank();
  }


  /**
   * Check's if an integer is valid or not.
   *
   * @param intToCheck the integer to be checked
   * @return validInt check if integer is valid
   */
  public static boolean checkInt(int intToCheck) {
    return intToCheck < 0;
  }

  /**
   * Checks if the string is empty.
   *
   * @param stringToCheck the string to be checked
   * @throws IllegalArgumentException if the string is empty
   */

  public static void checkStringAndThrowException(String stringToCheck, String stringName) {
    if (CheckValid.checkString(stringToCheck)) {
      String warningMessage = String.format("Invalid object provided:"
          + " %s (%s string can't be NULL.)%s", stringToCheck, stringName, ERROR);
      UTILITY_LOGGER.warning(warningMessage);
      throw new IllegalArgumentException(
          "Invalid argument provided : " + stringToCheck + " (" + stringName
              + " can't be empty.)" + ConstantStrings.warningInfo());
    }
  }

  /**
   * Checks if the score is negative.
   *
   * @param number the number to be checked
   * @throws IllegalArgumentException if the number is negative
   */
  public static void checkIntAndThrowException(int number, String name)
      throws IllegalArgumentException {
    if (CheckValid.checkInt(number)) {
      throw new IllegalArgumentException(
          "Invalid number provided:  " + number + " (" + name
              + " needs to be a number and positive)" + ConstantStrings.warningInfo());

    }
  }

  /**
   * Checks if the object is null.
   *
   * @param obj the object to be checked
   * @throws IllegalArgumentException if the object is null
   */

  public static void checkObjectAndThrowException(Object obj, String name)
      throws NullPointerException {
    if (obj == null) {
      String warningMessage = String.format("Invalid object provided:  %s (%s can't be NULL.)%s",
          name, name, ERROR);
      UTILITY_LOGGER.warning(warningMessage);
      throw new NullPointerException(
          "Invalid object provided:  " + " (" + name + " can't be NULL.)"
              + ConstantStrings.warningInfo());

    }
  }

  /**
   * Checks if the collection is empty.
   *
   * @param collection the collection to be checked
   * @throws IllegalArgumentException if the collection is empty
   */
  public static void checkCollectionAndThrowException(Collection<?> collection)
      throws NullPointerException {
    if (collection.isEmpty()) {
      String warningMessage = String.format("Invalid collection provided: "
          + " %s (The collection is empty.)%s", ERROR);
      UTILITY_LOGGER.warning(warningMessage);
      throw new NullPointerException(
          "the Collection is empty:  " + " (The collection can't be empty.)"
              + ConstantStrings.warningInfo());

    }
  }

  /**
   * Checks if the list is empty.
   *
   * @param list the list to be checked
   * @throws IllegalArgumentException if the list is empty
   */
  public static void checkListAndThrowException(List<?> list, String name)
      throws NullPointerException {
    if (list.isEmpty()) {
      String warningMessage = String.format("Invalid list provided: %s (%s is empty.)%s",
          name, name, ERROR);
      UTILITY_LOGGER.warning(warningMessage);
      throw new NullPointerException(
          "the list is empty:  " + " (" + name + " can't be empty.)"
              + ConstantStrings.warningInfo());
    }
  }
}
