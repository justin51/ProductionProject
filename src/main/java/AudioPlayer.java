public class AudioPlayer extends Product implements MultimediaControl {

    String supportedAudioFormats;
    String supportedPlaylistFormats;

    AudioPlayer(String name, String manufacturer, String supportedAudioFormats, String supportedPlaylistFormats) {
        super(name, manufacturer, ItemType.AUDIO);
        this.supportedAudioFormats = supportedAudioFormats;
        this.supportedPlaylistFormats = supportedPlaylistFormats;
    }

    @Override
    public String toString() {
        return super.toString()+"\nSupported Audio Formats: "+this.supportedAudioFormats+"\nSupported Playlist Formats: "+this.supportedPlaylistFormats;
    }

    @Override
    public void play() {
        System.out.println("Playing");
    }

    @Override
    public void stop() {
        System.out.println("Stopping");
    }

    @Override
    public void previous() {
        System.out.println("Previous");
    }

    @Override
    public void next() {
        System.out.println("Next");
    }
}
