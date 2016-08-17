package uk.co.mtickner.audioptionsdecoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static java.lang.String.format;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CODE = 0;
    private static final int TAKE_PICTURE = 2;

    protected ImageView imgCapturedPhoto;

    private String tempImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        imgCapturedPhoto = (ImageView) findViewById(R.id.img_captured_photo);
        String dataDirectory = Environment.getExternalStorageDirectory() + "/Android/data/uk.co.mtickner.audioptionsdecoder";
        String tempDirectory = dataDirectory + "/temp";

        tempImagePath = tempDirectory + "/temp.jpg";


        requestPermission(MainActivity.this, WRITE_EXTERNAL_STORAGE, PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CODE);

        // TODO Check for success
        File tempDir = new File(tempDirectory);
        if (!tempDir.isDirectory()) {
            tempDir.mkdirs();
        }
    }

    private void requestPermission(Activity activity, String permission, int permissionCode) {
        if (!isPermissionGranted(activity, permission)) {
            Log.i(TAG, format("Requesting permission for: %s", permission));

            ActivityCompat.requestPermissions(activity, new String[]{permission, permission}, permissionCode);
        }
    }

    private boolean isPermissionGranted(Context context, String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
    }

    public void openCameraHandler(View view) {
        startCameraActivity();
    }

    private void startCameraActivity() {
        File file = new File(tempImagePath);
        Uri outputFileUri = Uri.fromFile(file);

        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE) {
            switch (resultCode) {
                case RESULT_CANCELED:
                    Log.i(TAG, "User cancelled camera activity");
                    break;

                case RESULT_OK:
                    // TODO Photo taken
                    Log.i(TAG, "Photo taken");
                    break;
            }
        }
    }

}
