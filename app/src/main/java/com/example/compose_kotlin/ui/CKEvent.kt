package com.example.compose_kotlin.ui

sealed class CKEvent {
    data class OnAddMessage(val message: String) : CKEvent()
//    data class OnDeleteMessage() : CKEvent()
    object OnStart: CKEvent()
    object OnStop: CKEvent()
}
