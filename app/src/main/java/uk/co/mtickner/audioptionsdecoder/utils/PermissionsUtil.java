package uk.co.mtickner.audioptionsdecoder.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static java.lang.String.format;

public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    private static boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity, String permission, int permissionCode) {
        if (!isPermissionGranted(activity, permission)) {
            Log.i(TAG, format("Requesting permission for: %s", permission));

            ActivityCompat.requestPermissions(activity, new String[]{permission, permission}, permissionCode);
        }
    }

}
