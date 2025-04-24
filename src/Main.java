import home.SmartHomeSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SmartHomeSystem system = new SmartHomeSystem();
        Scanner scanner = new Scanner(System.in);
        int q = scanner.nextInt();
        scanner.nextLine();


        List<String> outputs = new ArrayList<>();

        for (int i = 0; i < q; i++) {
            String command = scanner.nextLine().trim();
            if (command.isEmpty())
                continue;

            String[] parts = command.split(" ");
            String response = "";

            try {
                switch (parts[0]) {
                    case "add_device":
                        if (parts.length != 4) response = "invalid input";
                        else response = system.addDevice(parts[1], parts[2], parts[3]);
                        break;
                    case "set_device":
                        if (parts.length != 4) response = "invalid input";
                        else response = system.setDevice(parts[1], parts[2], parts[3]);
                        break;
                    case "remove_device":
                        if (parts.length != 2) response = "invalid input";
                        else response = system.removeDevice(parts[1]);
                        break;
                    case "list_devices":
                        response = system.listDevices();
                        break;
                    case "add_rule":
                        if (parts.length != 4) response = "invalid input";
                        else response = system.addRule(parts[1], parts[2], parts[3]);
                        break;
                    case "check_rules":
                        if (parts.length != 2) response = "invalid input";
                        else response = system.checkRules(parts[1]);
                        break;
                    case "list_rules":
                        response = system.listRules();
                        break;
                    default:
                        response = "invalid command";
                }
            }
            catch (Exception e) {
                response = "invalid input";
            }

            outputs.add(response);
        }

        scanner.close();

        for (String output : outputs) {
            if (output.contains("\n")) {

                System.out.println(output);
            }
            else {
                System.out.println(output);
            }
        }
    }
    }

