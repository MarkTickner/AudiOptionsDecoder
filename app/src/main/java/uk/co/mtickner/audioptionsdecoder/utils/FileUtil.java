package uk.co.mtickner.audioptionsdecoder.utils;

import android.os.Environment;

public class FileUtil {

    private static String appDataDirectory = Environment.getExternalStorageDirectory() +
            "/Android/data/uk.co.mtickner.audioptionsdecoder";
    private static String appTempDirectory = appDataDirectory + "/temp";
    private static String tempImagePath = appTempDirectory + "/temp.jpg";

    public static String getAppTempDirectory() {
        return appTempDirectory;
    }

    public static String getTempImagePath() {
        return tempImagePath;
    }

}
