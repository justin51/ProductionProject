public class Screen implements ScreenSpec {

    private String resolution;
    private int refreshrate;
    private int responsetime;

    Screen(String resolution, int refreshrate, int responsetime) {
        this.resolution = resolution;
        this.refreshrate = refreshrate;
        this.responsetime = responsetime;
    }

    @Override
    public String toString() {
        return "Screen:\nResolution: "+this.getResolution()+"\nRefresh rate: "+this.getRefreshRate()+"\nResponse time: "+this.getResponseTime();
    }

    @Override
    public String getResolution() {
        return this.resolution;
    }

    @Override
    public int getRefreshRate() {
        return this.refreshrate;
    }

    @Override
    public int getResponseTime() {
        return this.responsetime;
    }
}
