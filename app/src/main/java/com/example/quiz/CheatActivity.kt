package com.example.quiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders

const val EXTRA_ANSWER_SHOWN = "com.example.quiz.answer_shown"
const val EXTRA_ANSWER_IS_TRUE = "com.example.quiz.answer_is_true"
private const val CHEATED_INDEX = "cheated_index"
class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private var cheated = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        cheated = savedInstanceState?.getBoolean(CHEATED_INDEX,false) ?: false
        setAnswerShowResult(cheated)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener{
            cheated = true
            quizViewModel.questionBank[quizViewModel.currentIndex].didCheat = true
            val answerText = when{
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            setAnswerShowResult(true)
        }
    }
    companion object{
        fun newIntent(packageContext: Context, answerIsTrue:Boolean): Intent {

            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue)
            }
        }
    }
    private fun setAnswerShowResult(isAnswerShown: Boolean){
        if (cheated){
            val data = Intent().apply{
                putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown)
            }
            setResult(Activity.RESULT_OK,data)
        }


    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putBoolean(CHEATED_INDEX,cheated)

    }
}