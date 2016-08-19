package uk.co.mtickner.audioptionsdecoder.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static java.lang.String.format;

public class FileUtil {

    private static final String TAG = "FileUtil";

    private enum Directory {
        DIRECTORY_EXISTS,
        DIRECTORY_CREATED,
        DIRECTORY_NOT_CREATED
    }

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

    public static Directory createDirectory(String directoryName) {
        File directory = new File(directoryName);

        if (directory.isDirectory()) {
            Log.i(TAG, format("Directory: %s already exists", directoryName));
            return Directory.DIRECTORY_EXISTS;
        }

        if (directory.mkdirs()) {
            Log.i(TAG, format("Directory: %s created", directoryName));
            return Directory.DIRECTORY_CREATED;
        }

        return Directory.DIRECTORY_NOT_CREATED;
    }

    public static void copyFromAssets(Context context,
                                      String assetFileName,
                                      String destinationDirectory) throws IOException {

        createDirectory(destinationDirectory);

        AssetManager assetManager = context.getAssets();
        File destinationFile = new File(destinationDirectory + "/" + assetFileName);

        if (!destinationFile.exists()) {
            InputStream in = assetManager.open(assetFileName);
            OutputStream out = new FileOutputStream(destinationFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            out.close();

            Log.i(TAG, format("Asset: %s was copied to: %s", assetFileName, destinationDirectory));
        } else {
            Log.i(TAG, format("Asset: %s already exists", assetFileName));
        }
    }

}
