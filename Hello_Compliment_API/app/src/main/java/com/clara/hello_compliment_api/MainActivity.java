package com.clara.hello_compliment_api;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

	private Button mGetComplimentButton;
	private TextView mShowComplimentText;

	private static final String url = "https://random-compliment.herokuapp.com/random";

	private static final String TAG = "RANDOM_COMPLIMENT";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mGetComplimentButton = findViewById(R.id.get_compliment_btn);
		mShowComplimentText = findViewById(R.id.compliment_text);

		mGetComplimentButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getRandomCompliment();
			}
		});
	}

	public void getRandomCompliment() {

		RequestQueue queue = Volley.newRequestQueue(this);

		JsonObjectRequest complimentRequest = new JsonObjectRequest(Request.Method.GET, url, null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {

						try {
							String compliment = response.getString("text");
							mShowComplimentText.setText(compliment);
						} catch (JSONException e) {
							Log.e(TAG, "Error processing JSON response", e);
						}
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error fetching data from compliment server", error);
					}
				}
		);

		queue.add(complimentRequest);
	}

}


