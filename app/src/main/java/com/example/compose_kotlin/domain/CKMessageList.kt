package com.example.compose_kotlin.domain

import java.util.*
import kotlin.collections.LinkedHashMap

class CKMessageList() : LinkedHashMap<Int, CKMessage>() {
}

interface ICKMessage {
    val id: Long
    val text: String
}

data class CKMessage(
    val message: String,
    val id: Long = UUID.randomUUID().leastSignificantBits
)

