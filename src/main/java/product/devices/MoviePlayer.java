package product.devices;

import product.Product;

/**
 * @author Justin Kenney
 */
public class MoviePlayer extends Product implements MultimediaControl {

  /**
   * The screen for this movie player
   */
  private Screen screen;
  /**
   * the monitor type of this movie player
   */
  private MonitorType monitorType;

  /**
   * Constructor for a MoviePlayer
   *
   * @param name
   * @param manufacturer
   * @param screen
   * @param monitorType
   */
  public MoviePlayer(String name, String manufacturer, Screen screen, MonitorType monitorType) {
    super(name, manufacturer, product.ItemType.VISUAL);
    this.screen = screen;
    this.monitorType = monitorType;
  }

  @Override
  public String toString() {
    return super.toString() + "\n" + this.screen + "\nMonitor Type: " + this.monitorType;
  }

  @Override
  public void play() {
    System.out.println("Playing movie");
  }

  @Override
  public void stop() {
    System.out.println("Stopping movie");
  }

  @Override
  public void previous() {
    System.out.println("Previous movie");
  }

  @Override
  public void next() {
    System.out.println("Next movie");
  }
}
