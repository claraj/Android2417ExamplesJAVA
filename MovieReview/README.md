### Movie Review - Activities and Intents

This app has 3 Activities. The Main Activity will start MovieNameActivity to configure a movie name, and MovieReviewActivity for the user to enter their review of that movie. When MainActivity starts the other Activities, it sends data; and when the Activities finish, they return data to MainActivity.

MainActivity uses startActivityForResult() to start the other Activities, which means onActivityResult will be called when they finish.

MainActivity puts extras into the Intent to send data to the Activity being started.

MovieReviewActivity and MovieNameActivity both read extra data from the launching Intent as they start.

Both Activities use an Intent with extra(s) to send data back to MainActivity when they finish.

Note that onActivityResult has to figure out which Activity is returning data, using the requestCode set when an Activity is launched.