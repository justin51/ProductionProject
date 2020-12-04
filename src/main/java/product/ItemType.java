package product;

import java.util.stream.Stream;

/**
 * An enumeration of the available item types
 *
 * @author Justin Kenney
 */
public enum ItemType {
  AUDIO("AU"),
  VISUAL("VI"),
  AUDIOMOBILE("AM"),
  VISUAL_MOBILE("VM");

  // shorthand code for the item type
  public String code;

  ItemType(String code) {
    this.code = code;
  }

  /**
   * @return the item type code
   */
  @Override
  public String toString() {
    return this.code;
  }

  /**
   * Gets a stream of the values of the enumeration
   *
   * @return stream of the enumerations values
   */
  public static Stream<ItemType> stream() {
    return Stream.of(ItemType.values());
  }

  /**
   * Gets an item type based on it's name
   *
   * @param name name item type
   * @return the item type with the supplied name
   */
  public static ItemType forName(String name) {
    for (ItemType t : ItemType.values()) {
      if (t.name().equals(name)) {
        return t;
      }
    }
    return null;
  }
}