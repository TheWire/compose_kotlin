package com.example.compose_kotlin.logic

abstract class BaseLogic<EVENT> {
    abstract fun onEvent(event: EVENT)
}