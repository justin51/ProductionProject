package product.devices;

/**
 * @author Justin Kenney
 */
public class Screen implements ScreenSpec {

  /**
   * the resolution of this screen
   */
  private String resolution;
  /**
   * the refresh rate of this screen
   */
  private int refreshrate;
  /**
   * the response time of this screen
   */
  private int responsetime;

  /**
   * Constructor to create a Screen object
   *
   * @param resolution screen resolution
   * @param refreshrate screen refresh rate
   * @param responsetime screen response time
   */
  public Screen(String resolution, int refreshrate, int responsetime) {
    this.resolution = resolution;
    this.refreshrate = refreshrate;
    this.responsetime = responsetime;
  }

  @Override
  public String toString() {
    return "product.device.Screen:\nResolution: " + this.getResolution() + "\nRefresh rate: " + this
        .getRefreshRate() + "\nResponse time: " + this.getResponseTime();
  }

  /**
   * gets the resolution
   *
   * @return this screens resolution
   */
  @Override
  public String getResolution() {
    return this.resolution;
  }

  /**
   * get the refresh rate
   *
   * @return the refresh rate of this screen
   */
  @Override
  public int getRefreshRate() {
    return this.refreshrate;
  }

  /**
   * gets the response time
   *
   * @return this screens response time
   */
  @Override
  public int getResponseTime() {
    return this.responsetime;
  }
}
