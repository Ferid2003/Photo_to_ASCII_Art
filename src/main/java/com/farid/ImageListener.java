package com.farid;

import org.fusesource.jansi.Ansi;

public interface ImageListener {
    void onImageSelected(String path, String brightness_mapping, Boolean invert, Ansi.Color color);
}
