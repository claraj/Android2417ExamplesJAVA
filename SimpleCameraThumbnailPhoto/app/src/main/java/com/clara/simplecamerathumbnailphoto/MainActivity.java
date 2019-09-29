package com.clara.simplecamerathumbnailphoto;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

	private Button mTakePictureButton;
	private ImageView mCameraPicture;

	private static int REQUEST_CODE_TAKE_PICTURE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mCameraPicture = findViewById(R.id.camera_thumbnail_picture);
		mTakePictureButton = findViewById(R.id.take_picture_button);

		mTakePictureButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				takeThumbnailPicture();
			}
		});
	}

	private void takeThumbnailPicture() {
		// Implicit Intent to open an app which can take a picture, probably the built-in camera app
		Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// Is there a camera on this device? Can check by asking the intent which Activity it plans
		// to open to handle this request. If Activity is null, no suitable activity was found
		if (pictureIntent.resolveActivity(getPackageManager()) != null) {
			startActivityForResult(pictureIntent, REQUEST_CODE_TAKE_PICTURE);
		} else {
			Toast.makeText(this, "Your device does not have a camera app", Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
			Bitmap thumbnail = data.getParcelableExtra("data");
			mCameraPicture.setImageBitmap(thumbnail);
		}
	}

}




