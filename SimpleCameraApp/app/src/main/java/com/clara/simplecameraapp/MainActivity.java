package com.clara.simplecameraapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "Camera Main Activity";
	private static final int REQUEST_SAVE_IMAGE_PERMISSION_REQUEST_CODE = 123;
	Button mTakePictureButton;
	ImageView mCameraPicture;

	private static int TAKE_PICTURE = 0;
	private String mImageFilename;
	private Uri mImageFileUri;

	private static final String PICTURE_TO_DISPLAY = "picture to display key";
	private static final String IMAGE_FILENAME = "image filename key";

	private boolean mIsPictureToDisplay = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState != null) {
			mIsPictureToDisplay = savedInstanceState.getBoolean(PICTURE_TO_DISPLAY, false);
			mImageFilename = savedInstanceState.getString(IMAGE_FILENAME);
		}

		mCameraPicture = (ImageView) findViewById(R.id.camera_picture);
		mTakePictureButton = (Button) findViewById(R.id.take_picture_button);
		mTakePictureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				takePhoto();
			}
		});
	}

	private void takePhoto() {

		Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// Check to see if there is a camera on this device

		if (pictureIntent.resolveActivity(getPackageManager()) == null) {
			Toast.makeText(MainActivity.this, "Your device does not have a camera",
					Toast.LENGTH_LONG).show();
		}

		else {
			// Create a File object from the specified filename
			String filename = "simple_camera_app_" + new Date().getTime();  //Create a unique filename including a timestamp
			File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
			File imageFile = null;
			try {
				imageFile = File.createTempFile(filename, ".jpg", storageDirectory);
				Log.i(TAG, "image file " + imageFile);
				// file: path to use with ACTION_VIEW intents
				mImageFilename = imageFile.getAbsolutePath();
				Log.i(TAG, "image file path  " + mImageFilename);

			} catch (IOException ioe) {
				Log.e(TAG, "Error creating file for photo storage", ioe);
			}

			// And a URI that Android will use to find this file resource
			// Remember a URI specifies how to find a particular resource (in this case, on the file system),
			// and where to find it (the file path)

			if (imageFile != null) {
				mImageFileUri = FileProvider.getUriForFile(MainActivity.this,
						"com.clara.simplecameraapp", imageFile);

				//Include this URI as an extra
				pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageFileUri);

				//And then request the camera is launched
				startActivityForResult(pictureIntent, TAKE_PICTURE);
			}
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK && requestCode == TAKE_PICTURE) {
			mIsPictureToDisplay = true;
		} else {
			mIsPictureToDisplay = false;
		}
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus && mIsPictureToDisplay) {
			Bitmap image = scaleBitmap();
			mCameraPicture.setImageBitmap(image);
			saveToMediaStore(image);
		}
	}

	Bitmap mImage;

	private void saveToMediaStore(Bitmap image) {
		//Add image to device's MediaStore - this makes the image accessible to the
		//gallery app, and other apps that can read from the MediaStore

		//Need to request permission

		mImage = image;

		if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			MediaStore.Images.Media.insertImage(getContentResolver(), image,
					"SimpleCameraApp", "Photo taken by SimpleCameraApp");
		} else {

			ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_SAVE_IMAGE_PERMISSION_REQUEST_CODE);
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		if (requestCode == REQUEST_SAVE_IMAGE_PERMISSION_REQUEST_CODE) {

			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				//Now save
				MediaStore.Images.Media.insertImage(getContentResolver(), mImage,
						"SimpleCameraApp", "Photo taken by SimpleCameraApp");

			}

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outBundle) {
		outBundle.putBoolean(PICTURE_TO_DISPLAY, mIsPictureToDisplay);
		outBundle.putString(IMAGE_FILENAME, mImageFilename);
	}


	private Bitmap scaleBitmap() {
		// * Scale picture taken to fit into the ImageView */      
		//Step 1: what size is the ImageView?
		int imageViewHeight = mCameraPicture.getHeight();
		int imageViewWidth = mCameraPicture.getWidth();

		//Step 2: decode file to find out how large the image is.

		// BitmapFactory is used to create bitmaps from pixels in a file.
		// Many options and settings, so use a BitmapFactory.Options object to store our desired settings.
		// Set the inJustDecodeBounds flag to true,
		// which means just the *information about* the picture is decoded and stored in bOptions
		// Not all of the pixels have to be read and stored in this process.
		// When we've done this, we can query bOptions to find out the original picture's height and width.

		BitmapFactory.Options bOptions = new BitmapFactory.Options();
		bOptions.inJustDecodeBounds = true;

		//File file = new File(Environment.getExternalStorageDirectory(), mImageFilename);

		//Uri imageFileUri = Uri.fromFile(file);
		//String photoFilePath = imageFileUri.getPath();
		BitmapFactory.decodeFile(mImageFilename, bOptions);      //Get information about the image
		int pictureHeight = bOptions.outHeight;
		int pictureWidth = bOptions.outWidth;

		//Step 3. Can use the original size and target size to calculate scale factor
		int scaleFactor = Math.min(pictureHeight / imageViewHeight, pictureWidth / imageViewWidth);

		//Step 4. Decode the image file into a new bitmap, scaled to fit the ImageView
		bOptions.inJustDecodeBounds = false;   //now we want to get a bitmap
		bOptions.inSampleSize = scaleFactor;
		Bitmap bitmap = BitmapFactory.decodeFile(mImageFilename, bOptions);
		return bitmap;
	}
}

