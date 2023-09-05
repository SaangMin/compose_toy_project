package com.skysmyoo.todolist.domain.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.skysmyoo.todolist.data.repository.TodoRepositoryImpl
import com.skysmyoo.todolist.domain.repository.TodoRepository
import com.skysmyoo.todolist.ui.main.MainViewModel

class TodoAndroidViewModelFactory(
    private val application: Application,
    private val repository: TodoRepository = TodoRepositoryImpl(application),
) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application, repository) as T
        }
        return super.create(modelClass)
    }
}