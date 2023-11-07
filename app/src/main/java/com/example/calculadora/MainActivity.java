package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView textViewScreen;
    private TextView textViewOperation;
    private String currentInput = "";
    private String operator = "";
    private String lastOperator = "";
    private BigDecimal result = BigDecimal.ZERO;
    private boolean isEnteringSecondNumber = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewScreen = findViewById(R.id.textViewScreen);
        textViewOperation = findViewById(R.id.textViewOperation);


        View.OnClickListener buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonText = ((Button) v).getText().toString();

                if (isEnteringSecondNumber) {
                    currentInput = buttonText;
                    isEnteringSecondNumber = false;
                } else {
                    currentInput += buttonText;
                }
                updateScreen();
            }
        };


        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);

        button0.setOnClickListener(buttonClickListener);
        button1.setOnClickListener(buttonClickListener);
        button2.setOnClickListener(buttonClickListener);
        button3.setOnClickListener(buttonClickListener);
        button4.setOnClickListener(buttonClickListener);
        button5.setOnClickListener(buttonClickListener);
        button6.setOnClickListener(buttonClickListener);
        button7.setOnClickListener(buttonClickListener);
        button8.setOnClickListener(buttonClickListener);
        button9.setOnClickListener(buttonClickListener);


        View.OnClickListener operatorClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newOperator = ((Button) v).getText().toString();

                if (!currentInput.isEmpty()) {
                    if (!operator.isEmpty() && !lastOperator.isEmpty()) {

                        performOperation();
                        lastOperator = newOperator;
                    } else {
                        lastOperator = newOperator;
                        result = new BigDecimal(currentInput);
                    }
                    operator = newOperator;
                    isEnteringSecondNumber = true;
                    currentInput = "";
                }
                updateOperationText();
            }
        };

        Button buttonAddition = findViewById(R.id.buttonAddition);
        Button buttonSubtraction = findViewById(R.id.buttonSubtraction);
        Button buttonMultiplication = findViewById(R.id.buttonMultiplication);
        Button buttonDivision = findViewById(R.id.buttonDivision);
        Button buttonPoint = findViewById(R.id.buttonPoint);

        buttonAddition.setOnClickListener(operatorClickListener);
        buttonSubtraction.setOnClickListener(operatorClickListener);
        buttonMultiplication.setOnClickListener(operatorClickListener);
        buttonDivision.setOnClickListener(operatorClickListener);
        buttonPoint.setOnClickListener(buttonClickListener);


        Button buttonEqual = findViewById(R.id.buttonEqual);
        buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!operator.isEmpty() && !lastOperator.isEmpty()) {
                    performOperation();
                    lastOperator = "";
                }
            }
        });


        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentInput = "";
                operator = "";
                lastOperator = "";
                result = BigDecimal.ZERO;
                isEnteringSecondNumber = false;
                textViewOperation.setText(""); // Limpiar el texto de operación
                updateScreen();
            }
        });
    }

    private void updateScreen() {
        textViewScreen.setText(currentInput);
    }

    private void updateOperationText() {
        textViewOperation.setText(result.toPlainString() + " " + lastOperator);
    }

    private void performOperation() {
        BigDecimal number = new BigDecimal(currentInput);
        switch (operator) {
            case "+":
                result = result.add(number);
                break;
            case "-":
                result = result.subtract(number);
                break;
            case "×":
                result = result.multiply(number);
                break;
            case "/":
                if (number.compareTo(BigDecimal.ZERO) != 0) {
                    result = result.divide(number, 10, RoundingMode.HALF_UP);
                }
                break;
        }
        currentInput = result.stripTrailingZeros().toPlainString();
        operator = "";
        updateScreen();
    }
}
