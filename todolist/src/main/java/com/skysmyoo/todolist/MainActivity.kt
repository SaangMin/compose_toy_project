package com.skysmyoo.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skysmyoo.todolist.domain.util.TodoAndroidViewModelFactory
import com.skysmyoo.todolist.ui.main.MainScreen
import com.skysmyoo.todolist.ui.main.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel(
                factory = TodoAndroidViewModelFactory(application),
            )

            MainScreen(viewModel = viewModel)
        }
    }
}