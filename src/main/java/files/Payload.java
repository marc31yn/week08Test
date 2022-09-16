package files;

public class Payload {

    public static String addUser(String name, String job) {
        return "{\n" +
                "    \"name\": \"" + name + "\",\n" +
                "    \"job\": \"" + job + "\"\n" +
                "}";
    }
}
