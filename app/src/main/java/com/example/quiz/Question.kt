package com.example.quiz

import android.security.identity.AccessControlProfileId
import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer:Boolean)
