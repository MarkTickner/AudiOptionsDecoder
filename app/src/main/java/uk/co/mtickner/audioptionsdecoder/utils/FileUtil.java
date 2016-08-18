package uk.co.mtickner.audioptionsdecoder.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtil {


    public static String appDataDirectory() {
        return Environment.getExternalStorageDirectory() +
                "/Android/data/uk.co.mtickner.audioptionsdecoder";
    }

    public static String appTempDirectory() {
        return appDataDirectory() + "/temp";
    }

    public static String tempImagePath() {
        return appTempDirectory() + "/temp.jpg";
    }

    public static String tessDirectory() {
        return appDataDirectory() + "/tessdata";
    }

    public static void createDirectory(String directoryName) {
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }
    }

    public static boolean copyFromAssets(Context context, String sourceFileName) {
        return false;
    }

}
