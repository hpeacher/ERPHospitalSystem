package com.hospital.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public class JsonSerializer {

    // Pretty-printing, stable Gson instance
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    /**
     * Serialize any object to JSON string.
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * Serialize object to JSON file.
     */
    public static <T> void writeToFile(File file, T object) {
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(object, writer);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write JSON to file: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Deserialize JSON file into object of type T.
     */
    public static <T> T readFromFile(File file, Class<T> clazz) {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON from file: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Deserialize JSON file into a List<T>.
     * Example: readListFromFile(file, new TypeToken<List<PatientRecord>>() {}.getType());
     */
    public static <T> T readListFromFile(File file, Type type) {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read list JSON from file: " + file.getAbsolutePath(), e);
        }
    }

    /**
     * Deserialize JSON string into a type T.
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * Deserialize JSON string into a List or generic collection.
     */
    public static <T> T fromJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
