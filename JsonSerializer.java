import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class JsonSerializer {

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static <T> T fromJson(File file, Class<T> clazz) {
        try {
            return gson.fromJson(new FileReader(file), clazz);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeToFile(File file, String content) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
