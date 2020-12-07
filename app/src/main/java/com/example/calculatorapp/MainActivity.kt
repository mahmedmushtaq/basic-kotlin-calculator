package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException
import kotlinx.android.synthetic.main.activity_main.*

private val STATE_OPERAND1 = "STATE_OPERAND1"
private val PENDING_OPERATION = "PENDING_OPERATION"
private val OPERAND1_STORED = "OPERAND1_STORED"

class MainActivity : AppCompatActivity() {
//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val displayOperation by lazy { findViewById<TextView>(R.id.operation) }

    private var operand1: Double? = null

    private var pendingOperation = "="
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
//
//        val button0 = findViewById<Button>(R.id.button0)
//        val button1 = findViewById<Button>(R.id.button1)
//        val button2 = findViewById<Button>(R.id.button2)
//        val button3 = findViewById<Button>(R.id.button3)
//        val button4 = findViewById<Button>(R.id.button4)
//        val button5 = findViewById<Button>(R.id.button5)
//        val button6 = findViewById<Button>(R.id.button6)
//        val button7 = findViewById<Button>(R.id.button7)
//        val button8 = findViewById<Button>(R.id.button8)
//        val button9 = findViewById<Button>(R.id.button9)
//        val buttonDot = findViewById<Button>(R.id.buttonDot)
//
//        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
//        val buttonEqual = findViewById<Button>(R.id.buttonEquals)
//        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
//        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
//        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        val listener = View.OnClickListener { v ->
            val button = v as Button
            newNumber.append(button.text)
        }

        val operationListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperations(value, op)

            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperation = op
            operation.text = op
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        buttonDivide.setOnClickListener(operationListener)
        buttonMultiply.setOnClickListener(operationListener)
        buttonPlus.setOnClickListener(operationListener)
        buttonMinus.setOnClickListener(operationListener)
        buttonEquals.setOnClickListener(operationListener)

    }

    private fun performOperations(value: Double, operation: String) {
        if (operand1 == null) {
            operand1 = value
        } else {

            if (pendingOperation == "=") {
                // to add another value after result
                pendingOperation = operation
            }

            when (pendingOperation) {
                // when user present double equal sign then print only value
                "=" -> operand1 = value
                "/" -> operand1 = if (value == 0.0) {
                    Double.NaN
                } else {
                    operand1!! / value
                }
                "*" -> operand1 = operand1!! * value
                "-" -> operand1 = operand1!! - value
                "+" -> operand1 = operand1!! + value
            }


        }
        result.setText(operand1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        if (operand1 !== null) {
            outState.putDouble(STATE_OPERAND1, operand1!!)
                outState.putBoolean(OPERAND1_STORED, true)
        }

        outState.putString(PENDING_OPERATION, pendingOperation);
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operand1 = if(savedInstanceState.getBoolean(OPERAND1_STORED)){
            savedInstanceState.getDouble(STATE_OPERAND1)
        }else{
            null
        }
        pendingOperation = savedInstanceState.getString(PENDING_OPERATION)!!;
        operation.text = pendingOperation;


    }
}