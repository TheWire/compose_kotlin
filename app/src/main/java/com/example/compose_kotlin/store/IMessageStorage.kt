package com.example.compose_kotlin.store

import com.example.compose_kotlin.domain.CKMessage
import com.example.compose_kotlin.domain.CKMessageList
import java.lang.Exception

interface IMessageStorage {
    suspend fun getMessages(): MessageStorageResult
    suspend fun addMessage(message: CKMessage): MessageStorageResult
    suspend fun deleteMessage(id: Long): MessageStorageResult
}

sealed class MessageStorageResult {
    data class OnSuccess(val messages: CKMessageList) : MessageStorageResult()
    data class OnError(val exception: Exception): MessageStorageResult()
    object OnComplete: MessageStorageResult()
}