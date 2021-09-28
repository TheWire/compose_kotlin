package com.example.compose_kotlin.logic

sealed class MessageEvent {
    object OnStart: MessageEvent()
    object OnStop: MessageEvent()
    data class OnNewMessage(val message: String): MessageEvent()
    data class OnDeleteMessage(val id: Long): MessageEvent()
}
