package com.postdesign.detectsystem.utils;

import javax.imageio.stream.FileImageInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class IOUtil {

    public static byte[] image2byte(File path){
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(path);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (IOException ex1) {
            ex1.printStackTrace();
        }
        return data;
    }
}
