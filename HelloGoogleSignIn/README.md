Follow instructions here

https://developers.google.com/identity/sign-in/android/start-integrating

1. Click the Configure a project button.

Follow the prompts. It will ask for your app's package name and a SHA1 key.

To get SHA1 key, run this command  (Mac)

keytool -list  -v  -alias androiddebugkey -keystore ~/.android/debug.keystore

Or this (Windows)

keytool -list -v -alias androiddebugkey -keystore %USERPROFILE%\.android\debug.keystore

You'll be prompted to download credentials. Save the credentials.json file to your /app directory and add it to .gitignore.



Skip the "Get your backend server's OAuth 2.0 client ID" part.


2. And then follow instructions here (which I adapted for the code in this app).

https://developers.google.com/identity/sign-in/android/sign-in


