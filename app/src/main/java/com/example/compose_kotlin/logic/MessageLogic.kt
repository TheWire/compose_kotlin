package com.example.compose_kotlin.logic

import com.example.compose_kotlin.domain.CKMessage
import com.example.compose_kotlin.store.IMessageStorage
import com.example.compose_kotlin.store.MessageStorageResult
import com.example.compose_kotlin.ui.ICKContainer
import com.example.compose_kotlin.ui.MessageView
import com.example.compose_kotlin.ui.MessageViewModel
import com.example.compose_kotlin.ui.ViewModelEvent
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MessageLogic(
    private val container: ICKContainer,
    private val viewModel: MessageViewModel,
    private val storage: IMessageStorage,
) : BaseLogic<MessageEvent>(),
    CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onEvent(event: MessageEvent) {
        when (event) {
            MessageEvent.OnStart -> onStart()
            MessageEvent.OnStop -> onStop()
            is MessageEvent.OnDeleteMessage -> onDeleteMessage(event.id)
            is MessageEvent.OnNewMessage -> onNewMessage(event.message)
        }
    }

    private fun onNewMessage(message: String) = launch {
        val message = CKMessage(message)

        when (val result = storage.addMessage(message)) {
            is MessageStorageResult.OnComplete -> viewModel.updateMessageDisplay(
                ViewModelEvent.AddMessage(MessageView(message.id, message.message))
            )
            is MessageStorageResult.OnError -> container.displayError(
                "error adding message to storage"
            )
        }
    }

    private fun onDeleteMessage(id: Long) {
        TODO("Not yet implemented")
//        viewModel.updateMessageDisplay(
////            ViewModelEvent.DeleteMessage()
//        )
    }

    private fun onStop() {
        TODO("Not yet implemented")
    }

    private fun onStart() = launch {
        when(val storeResult = storage.getMessages()) {
            is MessageStorageResult.OnSuccess -> viewModel.updateMessageDisplay(
                ViewModelEvent.InitMessage(storeResult.messages)
            )
            is MessageStorageResult.OnError -> container.displayError(
                "error accessing stored messages"
            )
        }
    }

}