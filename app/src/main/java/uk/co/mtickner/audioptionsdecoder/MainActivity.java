package uk.co.mtickner.audioptionsdecoder;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.io.File;

import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int TAKE_PICTURE = 2;

    private String tempImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
