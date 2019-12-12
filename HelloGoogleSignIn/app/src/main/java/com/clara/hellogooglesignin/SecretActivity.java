package com.clara.hellogooglesignin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class SecretActivity extends AppCompatActivity {

    TextView userNameText;
    TextView userEmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);

        GoogleSignInAccount account = getIntent().getParcelableExtra(SignInActivity.EXTRA_ACCOUNT);

        if (account != null) {
            String name = account.getDisplayName();
            String email = account.getEmail();

            userEmailText = findViewById(R.id.user_email);
            userNameText = findViewById(R.id.user_name);

            userNameText.setText(name);
            userEmailText.setText(email);
        }
    }
}
