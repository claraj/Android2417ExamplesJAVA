package com.clara.oauthandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;
import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;

public class MainActivity extends AppCompatActivity {

	/*Don't write app code like this. Separate the requests into a class other than your activity.
	Keys and secrets need to go in a file, read into program as needed, .giignore key files. */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new RequestMinneapolisCurrentTemp().execute();
	}
}


 class RequestMinneapolisCurrentTemp extends AsyncTask<String, Void, Void> {

	 OAuthService service;
	 Token accessToken;

	 String consumerKey = "";   //todo your key here. Actually, read in from file and provide to app.
	 String consumerSecret=	"";  // same
	 String token = "";   // same
	 String tokenSecret="";  // same


	 private static final String API_HOST = "api.yelp.com";
	 private static final String DEFAULT_TERM = "dinner";
	 private static final String DEFAULT_LOCATION = "Minneapolis, MN";
	 private static final int SEARCH_LIMIT = 3;
	 private static final String SEARCH_PATH = "/v2/search";    //todo configure URL needed by your program.

	@Override
	protected Void doInBackground(String... strings) {   //change from String to another type if needed

		this.service =
				new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
						.apiSecret(consumerSecret).build();
		this.accessToken = new Token(token, tokenSecret);

		OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + SEARCH_PATH);

		request.addQuerystringParameter("term", DEFAULT_TERM);
		request.addQuerystringParameter("location", DEFAULT_LOCATION);
		request.addQuerystringParameter("Reslimit", String.valueOf(SEARCH_LIMIT));
		service.signRequest(accessToken, request);

		Response response = request.send();
		Log.d("MAIN", response.getBody());

		return null;   //change return type and return data as needed.
	}

}

 class TwoStepOAuth extends DefaultApi10a {

	@Override
	public String getAccessTokenEndpoint() {
		return null;
	}

	@Override
	public String getAuthorizationUrl(Token arg0) {
		return null;
	}

	@Override
	public String getRequestTokenEndpoint() {
		return null;
	}
}

