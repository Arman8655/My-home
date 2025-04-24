package home;

public abstract class Device {
    protected String name;
    protected String protocol;
    protected String status;

    public Device(String name, String protocol) {
        this.name = name;
        this.protocol = protocol;
        this.status = "off";
    }

    public String getName() { return name; }
    public String getProtocol() { return protocol; }
    public String getStatus() { return status; }

    public void setStatus(String status) {
        if (status.equals("on") || status.equals("off")) {
            this.status = status;
        }
    }

    @Override
    public abstract String toString();
}

