package com.clara.collage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MAIN_ACTIVITY";

    private ImageButton mImageButton1, mImageButton2, mImageButton3, mImageButton4;

    private List<ImageButton> mImageButtons;
    private ArrayList<String> mImageFilePaths;

    private String mCurrentImagePath = "";

    private final static String BUNDLE_KEY_IMAGE_FILE_PATHS = "bundle key image file paths";
    private final static String BUNDLE_KEY_MOST_RECENT_FILE_PATH = "bundle key most recent file path";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageButton1 = findViewById(R.id.imageButton1);
        mImageButton2 = findViewById(R.id.imageButton2);
        mImageButton3 = findViewById(R.id.imageButton3);
        mImageButton4 = findViewById(R.id.imageButton4);

        mImageButtons = new ArrayList<>(Arrays.asList(mImageButton1, mImageButton2, mImageButton3, mImageButton4));

        if (savedInstanceState != null) {
            mImageFilePaths = savedInstanceState.getStringArrayList(BUNDLE_KEY_IMAGE_FILE_PATHS);
            mCurrentImagePath = savedInstanceState.getString(BUNDLE_KEY_MOST_RECENT_FILE_PATH);
        }

        if (mImageFilePaths == null) {
            mImageFilePaths = new ArrayList<>(Arrays.asList( "", "", "", ""));
        }

        if (mCurrentImagePath == null) {
            mCurrentImagePath = "";
        }

        for (ImageButton button: mImageButtons) {
            button.setOnClickListener(this);
        }

        for (int index = 0; index < mImageFilePaths.size() ; index++) {
            String imageFilePath = mImageFilePaths.get(index);
            if (imageFilePath != null) {
                loadImage(index);
            }
        }
    }


    @Override
    public void onClick(View view) {

        // Use the position in the ArrayList as the request code
        // When the image capture Activity returns, can read the request code and work out which ImageButton was clicked.
        int requestCodeButtonIndex = mImageButtons.indexOf(view);
        Log.d(TAG, "Click on image button at index " + requestCodeButtonIndex);

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                File imageFile = createImageFile();
                if (imageFile != null) {
                    Uri imageURI = FileProvider.getUriForFile(this, "com.clara.collage.fileprovider", imageFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
                    startActivityForResult(takePictureIntent, requestCodeButtonIndex);
                } else {
                    Log.e(TAG, "Image file is null");
                }
            } catch (IOException e) {
                Log.e(TAG, "Error creating image file " + e);
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        super.onSaveInstanceState(outBundle);
        outBundle.putStringArrayList(BUNDLE_KEY_IMAGE_FILE_PATHS, mImageFilePaths);
        outBundle.putString(BUNDLE_KEY_MOST_RECENT_FILE_PATH, mCurrentImagePath);
    }


    private File createImageFile() throws IOException {
        String imageFilename = "COLLAGE_" + new Date().getTime();    // Unique filename created with timestamp
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFilename,
                ".jpg",
                storageDir
        );

        // Save the file path globally, when the take picture intent returns this location will be where the image is saved
        mCurrentImagePath = imageFile.getAbsolutePath();
        return imageFile;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult for code (mImageButton index) " + requestCode + " current path " + mCurrentImagePath);
            mImageFilePaths.set(requestCode, mCurrentImagePath);    // Save the path in mImageFilePaths
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        // The view hasn't loaded in onActivityResult if the device is rotated when the picture is taken
        // onWindowFocusedChanged is called after onActivityResult and the view has loaded, so override this
        // method to display the images.

        Log.d(TAG, "focus changed " + hasFocus);
        if (hasFocus) {
            for (int index = 0; index < mImageFilePaths.size(); index++) {
                String path = mImageFilePaths.get(index);
                if (path != null && !path.isEmpty()) {
                    loadImage(index);
                }
            }
        }
    }


    private void loadImage(final int index) {
        ImageButton imageButton = mImageButtons.get(index);
        String path = mImageFilePaths.get(index);

        if (!path.isEmpty()) {
            Picasso.get()
                    .load(new File(path))
                    .error(android.R.drawable.stat_notify_error) // built-in error icon
                    .fit()
                    .centerCrop()
                    .into(imageButton, new Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d(TAG, "Image " + index + " loaded");
                        }
                        @Override
                        public void onError(Exception e) {
                            Log.e(TAG, "error loading image", e);
                        }
                    });

            // The Callback argument to into() is not required but may be helpful for debugging.
            // Can simply write the following, if preferred

            /*
            Picasso.get()
                    .load(new File(path))
                    .error(android.R.drawable.stat_notify_error) // built-in error icon
                    .fit()
                    .centerCrop()
                    .into(imageButton);
             */

        }
    }

}
