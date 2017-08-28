package com.clara.hellotesting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {


	EditText mNumber1, mNumber2;
	Button mAddButton;
	TextView mResultText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calculator);

		configureWidgets();
		addListeners();


	}

	/** Locate widgets in the UI and assign to variables */
	private void configureWidgets() {
		mNumber1 = (EditText) findViewById(R.id.number_1_input);
		mNumber2 = (EditText) findViewById(R.id.number_2_input);
		mAddButton = (Button) findViewById(R.id.add_button);
		mResultText = (TextView) findViewById(R.id.answer_text);
	}


	/** Configure event listeners for widgets */
	private void addListeners() {

		mAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				add();
			}
		});

	}

	/** Read the user-entered data from the TextViews,
	 * validate for integer input, perform calculation, and display result.
	 */
	private void add() {

		Integer number1 = Math.verifyInputIsNumber(mNumber1.getText().toString());
		Integer number2 = Math.verifyInputIsNumber(mNumber2.getText().toString());

		if (number1 == null || number2 == null) {
			showErrorToast(getString(R.string.error_enter_two_numbers));
			return;
		}

		int result = Math.add(number1, number2);

		displayResult(result);
	}


	/** Display a value in the answer TextView
	 * @param result the number to display */
	private void displayResult(int result) {
		mResultText.setText(getString(R.string.answer, result));
	}


	/** Show a String in a Toast.
	 * @param msg the message to display.
	 */
	private void showErrorToast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}


}
