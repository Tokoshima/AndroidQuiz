package com.example.quiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var questionBank = listOf(
        Question(R.string.question_australia, true,true, false),
        Question(R.string.question_africa, true,true, false),
        Question(R.string.question_oceans, true,true, false),
        Question(R.string.question_mideast, true,true, false),
        Question(R.string.question_americas, true,true, false),
        Question(R.string.question_asia, true,true, false))

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId


    fun moveToNext() {
        currentIndex = (currentIndex+1) % questionBank.size
    }
}
