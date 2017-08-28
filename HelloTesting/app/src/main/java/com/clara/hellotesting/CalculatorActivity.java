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

	private void configureWidgets() {
		mNumber1 = (EditText) findViewById(R.id.number_1_input);
		mNumber2 = (EditText) findViewById(R.id.number_2_input);
		mAddButton = (Button) findViewById(R.id.add_button);
		mResultText = (TextView) findViewById(R.id.answer_text);
	}

	private void addListeners() {

		mAddButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Integer number1 = verifyInputIsNumber(mNumber1.getText().toString());
				Integer number2 = verifyInputIsNumber(mNumber2.getText().toString());

				if (number1 == null || number2 == null) {
					error(getString(R.string.error_enter_two_numbers));
					return;
				}

				int result = add(number1, number2);

				displayResult(result);
			}
		});

	}

	private void displayResult(int result) {

		mResultText.setText(getString(R.string.answer, result));
	}



	protected int add(Integer number1, Integer number2) {
		return number1 * number2;
		// oh dear, there's a bug in this method. Hopefully, a unit test will catch it!
	}


	protected Integer verifyInputIsNumber(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException nfe) {
			return null;
		}
	}


	private void error(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}


}
