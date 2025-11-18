package com.hospital.repository;

public interface IFileStorage {
    void writeToFile(String data);

    String readFromFile();
}
