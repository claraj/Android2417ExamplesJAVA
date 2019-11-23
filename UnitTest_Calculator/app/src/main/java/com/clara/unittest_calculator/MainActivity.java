package com.clara.unittest_calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText mNumber1, mNumber2;
    Button mAddButton;
    TextView mResultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumber1 = findViewById(R.id.number_1_input);
        mNumber2 =  findViewById(R.id.number_2_input);
        mAddButton = findViewById(R.id.add_button);
        mResultText = findViewById(R.id.answer_text);

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
