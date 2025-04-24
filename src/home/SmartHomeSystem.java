package home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartHomeSystem {
    private Map<String, Device> devicesMap = new HashMap<>();
    private List<String> devicesOrder = new ArrayList<>();

    public String addDevice(String type, String name, String protocol) {
        if (devicesMap.containsKey(name)) {
            return "duplicate device name";
        }

        if (!(type.equals("light") || type.equals("thermostat"))) {
            return "invalid input";
        }

        if (!(protocol.equals("WiFi") || protocol.equals("Bluetooth"))) {
            return "invalid input";
        }

        Device device;
        if (type.equals("light")) {
            device = new Light(name, protocol);
        }

        else {
            device = new Thermostat(name, protocol);
        }

        devicesMap.put(name, device);
        devicesOrder.add(name);

        return "device added successfully";
    }

    public String setDevice(String name, String property, String value) {
        if (!devicesMap.containsKey(name)) {
            return "device not found";
        }

        Device device = devicesMap.get(name);

        try {
            if (device instanceof Light) {
                Light light = (Light) device;
                switch (property) {
                    case "status":
                        if (!(value.equals("on") || value.equals("off"))) {
                            return "invalid value";
                        }
                        light.setStatus(value);
                        break;
                    case "brightness":
                        int brightness = Integer.parseInt(value);
                        light.setBrightness(brightness);
                        break;
                    default:
                        return "invalid property";
                }
            }

            else if (device instanceof Thermostat) {
                Thermostat thermostat = (Thermostat) device;
                switch (property) {
                    case "status":
                        if (!(value.equals("on") || value.equals("off"))) {
                            return "invalid value";
                        }
                        thermostat.setStatus(value);
                        break;
                    case "temperature":
                        int temp = Integer.parseInt(value);
                        thermostat.setTemperature(temp);
                        break;
                    default:
                        return "invalid property";
                }
            }

            return "device updated successfully";
        }

        catch (NumberFormatException e) {
            return "invalid value";
        }

        catch (Exception e) {
            return "invalid input";
        }
    }

    public String removeDevice(String name) {
        if (!devicesMap.containsKey(name)) {
            return "device not found";
        }

        devicesMap.remove(name);
        devicesOrder.remove(name);
        return "device removed successfully";
    }

    public String listDevices() {
        if (devicesMap.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (String name : devicesOrder) {
            Device device = devicesMap.get(name);
            if (device != null) {
                result.append(device.toString()).append("\n");
            }
        }
        return result.toString().trim();
    }
}
