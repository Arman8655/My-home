package home;

public class Light extends Device{
    private int brightness;

    public Light(String name, String protocol) {
        super(name, protocol);
        this.brightness = 50;
    }

    public int getBrightness() { return brightness; }

    public void setBrightness(int brightness) {
        if (brightness >= 0 && brightness <= 100) {
            this.brightness = brightness;
        }
    }

    @Override
    public String toString() {
        return "light: " + name + " " + status + " " + brightness + "% " + protocol;
    }
}
