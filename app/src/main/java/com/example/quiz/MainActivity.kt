package com.example.quiz

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.Exception

import kotlin.math.round

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {
    private var Score = 0
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var nextButton : ImageButton
    private lateinit var prevButton : ImageButton
    private lateinit var questionTextView : TextView

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private fun checkAnswer(userAnswer : Boolean){


        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer){

            Score += 1
            R.string.correct_toast


        }else{

            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId,Toast.LENGTH_SHORT).show()
        quizViewModel.questionBank[quizViewModel.currentIndex].enabled = false
        if (!quizViewModel.questionBank[quizViewModel.currentIndex].enabled){
            trueButton.isEnabled = false
            falseButton.isEnabled = false
        }

        if (quizViewModel.currentIndex == quizViewModel.questionBank.size-1){
            Log.d(TAG,"questionbank")




            //val score:Double = (Score.toDouble()/questionBank.size)*100
            var total:Double = (Score.toDouble()/quizViewModel.questionBank.size)*100
            total = round(total*100) / 100
            val sb = StringBuilder()
            sb.append(total)
            sb.append("%")
            val scoreMessage = sb.toString()
            //var score2 = score.toBigDecimal().toPlainString()
            //Toast.makeText(this,score2,Toast.LENGTH_LONG).show();
            Toast.makeText(this, scoreMessage,Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateQuestion(){

        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        if (quizViewModel.questionBank[quizViewModel.currentIndex].enabled){
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.prev_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener{ view: View ->
            checkAnswer(true)
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }
        nextButton.setOnClickListener(){
            quizViewModel.moveToNext()
            updateQuestion()
        }
        prevButton.setOnClickListener(){
            if (quizViewModel.currentIndex == 0){
                quizViewModel.currentIndex = quizViewModel.questionBank.size
            }
            quizViewModel.currentIndex = (quizViewModel.currentIndex - 1) % quizViewModel.questionBank.size
            updateQuestion()
        }
        questionTextView.setOnClickListener(){
            quizViewModel.currentIndex = (quizViewModel.currentIndex + 1) % quizViewModel.questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG,"onSavedInstanceState")
        savedInstanceState.putInt(KEY_INDEX,quizViewModel.currentIndex)

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")

    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}
