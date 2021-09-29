package com.example.compose_kotlin.ui

import android.view.View
import com.example.compose_kotlin.domain.CKMessageList

class MessageViewModel(){

    internal var subMessageDisplay: ((ArrayList<MessageView>) -> Unit)? = null

    internal val messageDisplay: ArrayList<MessageView> = ArrayList<MessageView>()

    private fun initScreen(messageList: CKMessageList) {
        messageList.forEach { (key, value) ->
            val messageView = MessageView(value.id, value.message)
            messageDisplay.add(messageView)
        }
    }

    fun updateMessageDisplay(event: ViewModelEvent) {
        when(event) {
            is ViewModelEvent.AddMessage -> addMessage(event.messageView)
            is ViewModelEvent.DeleteMessage -> deleteMessage(event.id)
            is ViewModelEvent.InitMessage -> initScreen(event.messageList)
        }
        subMessageDisplay?.invoke(messageDisplay)
    }

    private fun addMessage(messageView: MessageView) {
        messageDisplay.add(messageView)
    }

    private fun deleteMessage(id: Long) {
        messageDisplay.removeIf { it.id == id }
    }


}

class MessageView(
    val id: Long,
    val text: String
)

sealed class ViewModelEvent {
    data class AddMessage(val messageView: MessageView): ViewModelEvent()
    data class DeleteMessage(val id: Long): ViewModelEvent()
    data class InitMessage(val messageList: CKMessageList): ViewModelEvent()
}