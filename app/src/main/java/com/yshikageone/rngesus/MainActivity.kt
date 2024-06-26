package com.yshikageone.rngesus

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import kotlinx.coroutines.*
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    //Initialize the variables
    private lateinit var minNumber: EditText
    private lateinit var maxNumber: EditText
    private lateinit var generatedNumber: TextView
    private lateinit var generateButton: Button

    //Initialize a coroutine for number generation
    private var job: Job? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        //Get the valoue from the textbox
        minNumber = findViewById(R.id.minNumber)
        maxNumber = findViewById(R.id.maxNumber)
        generatedNumber = findViewById(R.id.generatedNumber)
        generateButton = findViewById(R.id.generateButton)

        //When the generate button is clicked it calls on the generate number method
        generateButton.setOnClickListener {
            generateNumber()
        }
    }

    private fun generateNumber(){
        val minNum = minNumber.text.toString().toIntOrNull() ?: 0
        val maxNum = maxNumber.text.toString().toIntOrNull() ?: 100

        job?.cancel() // in case the button is clicked multiple times to avoid overlapping

        job = CoroutineScope(Dispatchers.Main).launch {
            for (i in 1..20){ // loops run 20 times
                val randomNumber = Random.nextInt(minNum, maxNum + 1)
                generatedNumber.text = randomNumber.toString()
                adjustTextSize(randomNumber.toString().length)
                delay(50)
            }
        }

    }

    //Auto adjust the generated number
    private fun adjustTextSize(length: Int){
        val TextSize = when(length) {
            1 -> 150f
            2 -> 120f
            3 -> 90f
            4 -> 60f
            else -> 50f
        }
        generatedNumber.textSize = TextSize
    }

    override fun onDestroy() {
        job?.cancel()
        super.onDestroy()
    }

}

