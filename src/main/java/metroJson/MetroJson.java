package metroJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import metro.Metro;

import java.io.*;
import java.nio.file.Path;

public abstract class MetroJson {
    public static void save(Metro metro, Path pathToSave) throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        FileWriter jsonFile = new FileWriter(pathToSave.toFile());
        jsonFile.write(gson.toJson(metro));
        jsonFile.flush();
        jsonFile.close();
        System.out.println("Successfully saved to " + pathToSave);
    }

    public static Metro load(Path pathToLoad) throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader(pathToLoad.toFile());
        Metro metro = gson.fromJson(reader, Metro.class);
        return metro;
    }
}
