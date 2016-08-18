package uk.co.mtickner.audioptionsdecoder;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;

import uk.co.mtickner.audioptionsdecoder.utils.PermissionsUtil;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.PERMISSION_DENIED;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;
import static java.lang.String.format;
import static uk.co.mtickner.audioptionsdecoder.utils.FileUtil.getAppTempDirectory;
import static uk.co.mtickner.audioptionsdecoder.utils.FileUtil.getTempImagePath;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 0;
    private static final int TAKE_PICTURE = 2;
    private static final String PHOTO_TAKEN = "photo_taken";

    protected ImageView imgCapturedPhoto;

    private boolean photoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgCapturedPhoto = (ImageView) findViewById(R.id.img_captured_photo);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                Log.i(TAG, format("Permission: %s granted", WRITE_EXTERNAL_STORAGE));

                openCamera();
            } else if (grantResults[0] == PERMISSION_DENIED) {
                Log.i(TAG, format("Permission: %s denied", WRITE_EXTERNAL_STORAGE));

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Permissions required to use this feature were denied, " +
                                "allow them to continue.")
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }
        }
    }

    public void openCameraHandler(View view) {
        openCamera();
    }

    private void initialiseDirectory() {
        File appTempDirectory = new File(getAppTempDirectory());
        if (!appTempDirectory.isDirectory()) {
            appTempDirectory.mkdirs();
        }
    }

    private void openCamera() {
        if (PermissionsUtil.isPermissionGranted(MainActivity.this, WRITE_EXTERNAL_STORAGE)) {
            initialiseDirectory();
            startCameraActivity();
        } else {
            PermissionsUtil.requestPermission(
                    MainActivity.this,
                    WRITE_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
    }

    private void startCameraActivity() {
        File tempImageFile = new File(getTempImagePath());
        Uri outputFileUri = Uri.fromFile(tempImageFile);

        Intent intent = new Intent(ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(PHOTO_TAKEN, photoTaken);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.getBoolean(PHOTO_TAKEN)) {
            onPhotoTaken();
        }
    }

    protected void onPhotoTaken() {
        photoTaken = true;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;

        Bitmap bitmap = BitmapFactory.decodeFile(getTempImagePath(), options);
        imgCapturedPhoto.setImageBitmap(bitmap);
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
                    Log.i(TAG, "Photo taken");

                    onPhotoTaken();

                    break;
            }
        }
    }

}
