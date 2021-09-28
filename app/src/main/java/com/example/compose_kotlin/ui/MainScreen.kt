package com.example.compose_kotlin.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.compose_kotlin.logic.MessageEvent


@Composable
fun MainScreen(eventHandler: (MessageEvent) -> Unit, viewModel: MessageViewModel) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        MainContent(eventHandler, viewModel)
    }
}

@Composable
fun MainContent(
    eventHandler: (MessageEvent) -> Unit,
    viewModel: MessageViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MessageColumn(eventHandler, viewModel)
        EntryBox(eventHandler)
    }
}

@Composable
fun MessageColumn(
    eventHandler: (MessageEvent) -> Unit,
    viewModel: MessageViewModel
) {
    var messageDisplay by remember {
        mutableStateOf(viewModel.messageDisplay, neverEqualPolicy())
    }

    viewModel.subMessageDisplay = {
        messageDisplay = it
    }

    Column() {
        messageDisplay.forEach {
            MessageBox(it)
        }
    }
}

@Composable
fun MessageBox(message: MessageView) {
    Box(

    ) {
        Text(message.text)
    }
}


@Composable
fun EntryBox(eventHandler: (MessageEvent) -> Unit) {
    val entryText = remember { mutableStateOf(TextFieldValue()) }
    Column {
        TextField(
            value = entryText.value,
            onValueChange = { entryText.value = it },
            placeholder = { Text("enter message") },
            modifier = Modifier
                .background(Color.Red)
        )
        Button(
            onClick = {
                eventHandler.invoke(MessageEvent.OnNewMessage(entryText.value.text))
                      },
            modifier = Modifier.size(25.dp),
            colors = ButtonDefaults.textButtonColors(
                backgroundColor = Color.Blue
            )
        ) {
            Text("Add Text")
        }
    }
}


