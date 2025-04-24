package home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartHomeSystem {
    private Map<String, Device> devicesMap = new HashMap<>();
    private List<String> devicesOrder = new ArrayList<>();
    private Map<String, List<Rule>> rules = new HashMap<>();
    private List<Rule> rulesOrder = new ArrayList<>();

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

        if (rules.containsKey(name)) {
            rules.remove(name);
            for (int i = rulesOrder.size() - 1; i >= 0; i--) {
                if (rulesOrder.get(i).getDeviceName().equals(name)) {
                    rulesOrder.remove(i);
                }
            }
        }

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
    public String addRule(String name, String time, String action) {
        if (!devicesMap.containsKey(name)) {
            return "device not found";
        }

        if (!isValidTime(time)) {
            return "invalid time";
        }

        if (!(action.equals("on") || action.equals("off"))) {
            return "invalid action";
        }

        if (rules.containsKey(name)) {
            for (Rule rule : rules.get(name)) {
                if (rule.getTime().equals(time)) {
                    return "duplicate rule";
                }
            }
        }

        Rule newRule = new Rule(name, time, action);
        if (!rules.containsKey(name)) {
            rules.put(name, new ArrayList<>());
        }
        rules.get(name).add(newRule);
        rulesOrder.add(newRule);

        return "rule added successfully";
    }

    public String checkRules(String time) {
        if (!isValidTime(time)) {
            return "invalid time";
        }


        boolean anyChange = false;

        for (Rule rule : rulesOrder) {
            if (rule.getTime().equals(time)) {
                Device device = devicesMap.get(rule.getDeviceName());
                if (device != null && !device.getStatus().equals(rule.getAction())) {
                    device.setStatus(rule.getAction());
                    anyChange = true;
                }
            }
        }

        if (!anyChange) {
            return "rules checked (no changes)";
        }
        return "rules checked" ;

    }

    public String listRules() {
        if (rulesOrder.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        for (Rule rule : rulesOrder) {
            result.append(rule.toString()).append("\n");
        }
        return result.toString().trim();
    }

    private boolean isValidTime(String time) {
        try {
            String[] parts = time.split(":");
            if (parts.length != 2) return false;

            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            return hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59;
        } catch (Exception e) {
            return false;
        }
    }

}
