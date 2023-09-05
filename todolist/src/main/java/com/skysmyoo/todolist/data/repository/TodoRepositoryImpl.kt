package com.skysmyoo.todolist.data.repository

import android.app.Application
import androidx.room.Room
import com.skysmyoo.todolist.data.source.TodoDatabase
import com.skysmyoo.todolist.domain.model.Todo
import com.skysmyoo.todolist.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(application: Application) : TodoRepository {
    private val db = Room.databaseBuilder(
        application,
        TodoDatabase::class.java,
        "todo-db",
    ).build()

    override fun observeTodos(): Flow<List<Todo>> {
        return db.todoDao().todos()
    }

    override suspend fun addTodo(todo: Todo) {
        return db.todoDao().insert(todo)
    }

    override suspend fun updateTodo(todo: Todo) {
        return db.todoDao().update(todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        return db.todoDao().delete(todo)
    }
}