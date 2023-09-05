package com.skysmyoo.todolist.domain.repository

import com.skysmyoo.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun observeTodos(): Flow<List<Todo>>

    suspend fun addTodo(todo: Todo)

    suspend fun updateTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)
}