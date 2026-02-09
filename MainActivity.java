package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private StringBuilder currentInput = new StringBuilder();
    private double lastValue = 0;
    private String pendingOperation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Array of all button IDs for numbers
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        // Set listeners for numbers
        for (int id : numberIds) {
            Button b = findViewById(id);
            b.setOnClickListener(v -> onButtonClick(b.getText().toString(), "Number"));
        }

        // Set listeners for operations
        findViewById(R.id.btnPlus).setOnClickListener(v -> onButtonClick("+", "Addition"));
        findViewById(R.id.btnMinus).setOnClickListener(v -> onButtonClick("-", "Subtraction"));
        findViewById(R.id.btnMult).setOnClickListener(v -> onButtonClick("×", "Multiplication"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> onButtonClick("÷", "Division"));

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput.setLength(0);
            lastValue = 0;
            pendingOperation = "";
            display.setText("0");
            showAlert("Clear", "Calculator has been reset.");
        });

        findViewById(R.id.btnEqual).setOnClickListener(v -> calculateResult());
    }

    private void onButtonClick(String value, String type) {
        // 1. Immediate Toast Feedback
        Toast.makeText(this, "Pressed: " + value, Toast.LENGTH_SHORT).show();

        // 2. Alert Popup
        showAlert(type, "You clicked the " + value + " button.");

        // 3. Logic: Update the display
        if (type.equals("Number")) {
            currentInput.append(value);
            display.setText(currentInput.toString());
        } else {
            // Store the first number and the operation
            if (currentInput.length() > 0) {
                lastValue = Double.parseDouble(currentInput.toString());
                pendingOperation = value;
                currentInput.setLength(0);
            }
        }
    }

    private void calculateResult() {
        if (currentInput.length() == 0) return;

        double currentValue = Double.parseDouble(currentInput.toString());
        double result = 0;

        switch (pendingOperation) {
            case "+": result = lastValue + currentValue; break;
            case "-": result = lastValue - currentValue; break;
            case "×": result = lastValue * currentValue; break;
            case "÷": result = lastValue / currentValue; break;
            default: result = currentValue; break;
        }

        String finalResult = String.valueOf(result);
        display.setText(finalResult);
        currentInput.setLength(0);
        currentInput.append(finalResult);

        showAlert("Result", "The answer is: " + finalResult);
    }

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false) // User MUST click OK (good for elderly focus)
                .show();
    }
}
