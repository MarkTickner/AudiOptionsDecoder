package uk.co.mtickner.audioptionsdecoder.utils;

import android.os.Environment;

public class FileUtil {

    private static String appDataDirectory = Environment.getExternalStorageDirectory() +
            "/Android/data/uk.co.mtickner.audioptionsdecoder";
    private static String appTempDirectory = appDataDirectory + "/temp";

    public static String getAppTempDirectory() {
        return appTempDirectory;
    }

}
