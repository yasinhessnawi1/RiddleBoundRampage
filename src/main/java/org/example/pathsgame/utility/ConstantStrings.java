package org.example.pathsgame.utility;

/**
 * holds strings that are constant and used everywhere in the project,
 * this class will make it easy to find refactor these strings.
 *
 * @author yasinhessnawi
 * @author Rami.
 * @version 0.0.1(22.02.23)
 */
public class ConstantStrings {

  public static final String CHARACTERS_LOGGER = "src/main/java/characters";
  public static final String STORY_LOGGER = "src/main/java/story";

  /**
   * An empty Constructor.
   */
  private ConstantStrings() {
    // An empty constructor.
  }

  /**
   * builds a string with string format, this will return the warning info when throwing, logging.
   *
   * @return the string information
   */
  public static String warningInfo() {
    String className = Thread.currentThread().getStackTrace()[2].getClassName();
    int line = Thread.currentThread().getStackTrace()[2].getLineNumber();
    return String.format(". (Error from: %s at line %d)", className, line);
  }
}
