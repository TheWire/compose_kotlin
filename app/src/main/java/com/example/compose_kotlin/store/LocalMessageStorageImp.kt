package com.example.compose_kotlin.store

import android.util.Log
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.datastore.core.DataStore
import com.example.compose_kotlin.ProtoCKMessageStore
import com.example.compose_kotlin.ProtoCKMessage
import com.example.compose_kotlin.domain.CKMessage
import com.example.compose_kotlin.domain.CKMessageList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class LocalMessageStorageImp(
    private val dataStore: DataStore<ProtoCKMessageStore>
) : IMessageStorage {
    override suspend fun getMessages(): MessageStorageResult =
        withContext(Dispatchers.IO) {
            try {
                val messages = dataStore.data.first()
                MessageStorageResult.OnSuccess(messages.toMessages)
            } catch (e: Exception) {
                MessageStorageResult.OnError(e)
            }

    }



    override suspend fun addMessage(message: CKMessage): MessageStorageResult =
        withContext(Dispatchers.IO) {
            try {
                dataStore.updateData { messages ->
                    messages.toBuilder().addMessages(message.ProtoCKMessage()).build()
                }
                MessageStorageResult.OnComplete
            } catch (e: Exception) {
                MessageStorageResult.OnError(e)
            }
    }

    override suspend fun deleteMessage(id: Long): MessageStorageResult =
        withContext(Dispatchers.IO) {
            try {
                dataStore.updateData { messages ->
                    val ret = findMessageById(messages.messagesList, id) {
                        messages.toBuilder().removeMessages(it)
                    } as ProtoCKMessageStore.Builder? ?: throw Exception("message does not exist")
                    ret.build()
                }
                MessageStorageResult.OnComplete
            } catch (e: Exception) {
                MessageStorageResult.OnError(e)
            }
    }

    private fun findMessageById(messages: List<ProtoCKMessage>, id: Long, cb: (Int) -> Any?): Any? {
        for ((index, protoCKMessage) in messages.withIndex()) {
            if(protoCKMessage.messageID == id) {
                return cb(index)
            }
        }
        return null
    }
}

private val ProtoCKMessageStore.toMessages: CKMessageList
    get() {
        val messageList = CKMessageList()
        for ((index, message) in this.messagesList.withIndex()) {
            messageList[index] = CKMessage(message.message, message.messageID)
        }
        return messageList
    }

fun CKMessage.ProtoCKMessage(): ProtoCKMessage {
    val protoBuild = ProtoCKMessage.newBuilder()
    protoBuild.message = this.message
    protoBuild.messageID = this.id
    return protoBuild.build()
}