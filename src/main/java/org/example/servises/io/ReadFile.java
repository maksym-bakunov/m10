package org.example.servises.io;

import java.io.File;
import java.util.ArrayList;

public interface ReadFile<T> {
    ArrayList<T> read(File file);
}
