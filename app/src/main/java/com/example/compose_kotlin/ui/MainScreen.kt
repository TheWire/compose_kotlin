package com.example.compose_kotlin.ui

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.compose_kotlin.logic.MessageEvent
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import com.example.compose_kotlin.R
import java.util.function.IntConsumer
import kotlin.reflect.KProperty


@Composable
fun MainScreen(eventHandler: (MessageEvent) -> Unit, viewModel: MessageViewModel) {
    // A surface container using the 'background' color from the theme
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.padding(12.dp)
    ) {
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
        Spacer(modifier = Modifier.size(10.dp))
        EntryBox(eventHandler)
    }
}

@Composable
fun ColumnScope.MessageColumn(
    eventHandler: (MessageEvent) -> Unit,
    viewModel: MessageViewModel,
) {
    var messageDisplay by remember {
        mutableStateOf(viewModel.messageDisplay, neverEqualPolicy())
    }

    viewModel.subMessageDisplay = {
        messageDisplay = it
    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .weight(1f),
    ) {
        messageDisplay.forEach {
            MessageBox(eventHandler, it)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 14.dp)
            )
        }
    }
}

@Composable
fun MessageBox(eventHandler: (MessageEvent) -> Unit, message: MessageView) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp),
            text = message.text
        )
        IconButton(
            onClick = {
                    eventHandler.invoke(MessageEvent.OnDeleteMessage(message.id))
                },
            ) {
            Icon(
                modifier = Modifier.size(20.dp),
                tint = Color.Red,
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "close"
            )
        }
    }
}


@Composable
fun EntryBox(eventHandler: (MessageEvent) -> Unit) {
    val entryText = remember { mutableStateOf(TextFieldValue()) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextField(
            value = entryText.value,
            onValueChange = { entryText.value = it },
            placeholder = { Text("enter message") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.size(10.dp))
        Button(
            onClick = {
                eventHandler.invoke(MessageEvent.OnNewMessage(entryText.value.text))
                entryText.value = TextFieldValue("")
                      },
            modifier = Modifier.then(Modifier.shadow(elevation = 6.dp))
        ) {
            Text("Add Text")
        }
    }
}


