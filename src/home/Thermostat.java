package home;

public class Thermostat extends Device{
    private int temperature;

    public Thermostat(String name, String protocol) {
        super(name, protocol);
        this.temperature = 20;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        if (temperature >= 0 && temperature <= 100) {
            this.temperature = temperature;
        }
    }

    @Override
    public String toString() {
        return "thermostat: " + name + " " + status + " " + temperature + "C " + protocol;
    }
}
