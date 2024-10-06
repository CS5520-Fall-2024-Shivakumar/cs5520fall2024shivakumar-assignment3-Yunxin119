package com.group5.quickcalc;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Button button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    Button buttonplus, buttonminus, buttonx, buttonequal;
    TextView displayText;
    Stack<Integer> currSum;
    Stack<Character> operatorStack;
    int curr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        currSum = new Stack<>();
        operatorStack = new Stack<>();

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        buttonplus = findViewById(R.id.buttonplus);
        buttonminus = findViewById(R.id.buttonminus);
        buttonx = findViewById(R.id.buttonx);
        buttonequal = findViewById(R.id.buttonequal);
        displayText = findViewById(R.id.displayText);


        button0.setOnClickListener(view -> handleNumber(button0));
        button1.setOnClickListener(view -> handleNumber(button1));
        button2.setOnClickListener(view -> handleNumber(button2));
        button3.setOnClickListener(view -> handleNumber(button3));
        button4.setOnClickListener(view -> handleNumber(button4));
        button5.setOnClickListener(view -> handleNumber(button5));
        button6.setOnClickListener(view -> handleNumber(button6));
        button7.setOnClickListener(view -> handleNumber(button7));
        button8.setOnClickListener(view -> handleNumber(button8));
        button9.setOnClickListener(view -> handleNumber(button9));

        buttonplus.setOnClickListener(view -> {
            handleOperator('+');
            displayText.append("+");
        });
        buttonminus.setOnClickListener(view -> {
            handleOperator('-');
            displayText.append("-");
        });

        buttonequal.setOnClickListener(view -> getResult());

        buttonx.setOnClickListener(view -> {
            String text = displayText.getText().toString();
            if (text.length() > 0 && !text.equals("CALC")) {
                char lastChar = text.charAt(text.length() - 1);
                if (Character.isDigit(lastChar)) {
                    curr = curr / 10;
                }
                String replace = text.substring(0, text.length() - 1);
                displayText.setText(replace);
                if (displayText.getText().toString().isEmpty()) {
                    displayText.setText("CALC");
                    curr = 0;
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void handleNumber(Button button) {
        if (displayText.getText().toString().equals("CALC")) {
            displayText.setText(button.getText());
        } else {
            displayText.append(button.getText());
        }
        curr = curr*10 + Integer.parseInt(button.getText().toString());
    }

    private void handleOperator(char operator) {
        currSum.push(curr);
        operatorStack.push(operator);
        curr = 0;
    }

    private void getResult() {
        currSum.push(curr);
        while (!operatorStack.isEmpty()) {
            char operator = operatorStack.pop();

            int result = performOperation(operator);
            currSum.push(result);
        }
        int finalResult = currSum.pop();
        displayText.setText(String.valueOf(finalResult));
        curr = finalResult;
    }

    private int performOperation(char operator) {
        int num2 = currSum.pop();
        int num1 = currSum.pop();
        switch (operator) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            default:
                return 0;
        }
    }
}
