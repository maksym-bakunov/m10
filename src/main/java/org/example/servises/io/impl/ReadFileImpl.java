package org.example.servises.io.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.example.servises.io.ReadFile;

public class ReadFileImpl implements ReadFile<Long> {
    @Override
    public ArrayList<Long> read(File file) {
        ArrayList<Long> arrayList = new ArrayList<>();
        String line;
        long val;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                val = Integer.parseInt(line.trim());
                arrayList.add(val);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return arrayList;
    }
}
