package uk.co.mtickner.audioptionsdecoder.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static java.lang.String.format;

public class PermissionsUtil {

    private static final String TAG = "PermissionsUtil";

    public static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED) {
            Log.i(TAG, format("Permission: %s already granted", permission));

            return true;
        } else {
            return false;
        }
    }

    public static void requestPermission(final Activity activity, final String permission,
                                         final int requestCode) {
        Log.i(TAG, format("Requesting permission for: %s", permission));

        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            new AlertDialog.Builder(activity)
                    .setMessage("The permissions on the next screen are required to use this " +
                            "feature, please allow them.")
                    .setPositiveButton("Next", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{permission, permission},
                                    requestCode);
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{permission, permission},
                    requestCode);
        }
    }

}
