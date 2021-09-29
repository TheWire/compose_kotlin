package com.example.compose_kotlin.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.compose_kotlin.logic.MessageEvent
import com.example.compose_kotlin.logic.MessageLogic
import com.example.compose_kotlin.store.LocalMessageStorageImp
import com.example.compose_kotlin.store.messageDataStore

class MainActivity : ComponentActivity(), ICKContainer {

    private lateinit var logic: MessageLogic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = MessageViewModel()
        setContent {
            MainScreen(logic::onEvent, viewModel)
        }

        logic = MessageLogic(
            this, viewModel, LocalMessageStorageImp(applicationContext.messageDataStore)
        )
    }

    override fun onStart() {
        super.onStart()
        logic.onEvent(MessageEvent.OnStart)
    }

    override fun onStop() {
        super.onStop()
        logic.onEvent(MessageEvent.OnStop)
        finish()
    }

    override fun displayError(errorText: String) {
        error(errorText)
    }
}








//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    MainScreen()
//}
