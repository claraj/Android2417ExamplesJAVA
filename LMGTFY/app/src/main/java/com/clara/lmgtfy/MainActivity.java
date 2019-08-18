package com.clara.lmgtfy;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText searchText;
    private Button searchButton;
    private TextView searchConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.enter_search_text);
        searchButton = findViewById(R.id.search_button);
        searchConfirmation = findViewById(R.id.show_search_text);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                // Read text, and use it to set the text in searchConfirmation
                String text = s.length() == 0 ? "..." : s.toString();
                searchConfirmation.setText(getString(R.string.search_display_text, text));

            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read text in EditText
                String search = searchText.getText().toString();
                if (search.isEmpty()) { return; }

                // show Toast confirmation
                Toast confirmation = Toast.makeText(MainActivity.this, "Searching for " + search, Toast.LENGTH_LONG);
                confirmation.show();

                // Launch web browser with URI for Google search
                String uriString = "https://www.google.com/search?q=" + search;
                Uri uri = Uri.parse(uriString);

                Intent launchBrowserIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(launchBrowserIntent);

            }
        });
    }
}



