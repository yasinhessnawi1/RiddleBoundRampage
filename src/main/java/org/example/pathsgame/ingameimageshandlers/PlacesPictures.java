package org.example.pathsgame.ingameimageshandlers;

/**
 * The PlacesPictures enum is used to represent pictures of the places in the game.
 * Each enum constant is associated with a specific filename.
 *
 * @author Rami.
 * @author Yasin
 * @version 0.1
 */
public enum PlacesPictures {
  BEDROOM("Bedroom"),
  VILLAGE("Village"),
  TheHeartstone("deeper into the forest"),
  deeperintotheforest("deeper into the forest"),
  Staremoreforest("Staremoreforest"),
  thebusses("the busses"),
  Theforbiddentower("The forbidden tower"),
  TheWearyWillowInn("The Weary Willow Inn");

  private final String fileName;

  /**
   * Enum constructor for the PlacesPictures enum.
   *
   * @param fileName The filename associated with the enum constant.
   */
  PlacesPictures(String fileName) {

    this.fileName = fileName;
  }

  /**
   * Method to get the filename associated with the enum constant.
   *
   * @return The filename associated with the enum constant.
   */
  public String getFileName() {
    return fileName;
  }
}
