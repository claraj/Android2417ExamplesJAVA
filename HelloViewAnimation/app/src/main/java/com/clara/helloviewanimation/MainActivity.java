package com.clara.helloviewanimation;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


/*
* https://developer.android.com/guide/topics/graphics/view-animation.html
*
* "the view animation system to perform(s) tweened animation on Views.
* Tween animation calculates the animation with information such as the
* start point, end point, size, rotation, and other common aspects of an animation.

A tween animation can perform a series of simple transformations (position, size,
rotation, and transparency) on the contents of a View object....

"
A sequence of animation instructions defines the tween animation, defined by either XML
or Android code. As with defining a layout, an XML file is recommended because it's more
readable, reusable, and swappable than hard-coding the animation."

Step 1. Create an res/anim directory and a xml file alien_moves.xml
 2. Define animation rotations, moves etc. , as described in https://developer.android.com/guide/topics/graphics/view-animation.html
 3. Find your view, in your layout
 4. Create an Animation object, load your xml into it
 5. Call startAnimation method on your view, passing in the Animation object
* */


public class MainActivity extends AppCompatActivity {

	ImageView alien;
	Animation alienAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		alien = (ImageView) findViewById(R.id.alien);

		alienAnimation = AnimationUtils.loadAnimation(this, R.anim.alien_moves);


		alien.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//If the animation hasn't started yet, or has already run and ended...
				if (!alienAnimation.hasStarted() || alienAnimation.hasEnded()) {
						//... animate the alien ImageView
						alien.startAnimation(alienAnimation);
				}
			}
		});

	}


}
