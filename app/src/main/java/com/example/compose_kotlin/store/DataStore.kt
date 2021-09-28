package com.example.compose_kotlin.store

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.compose_kotlin.ProtoCKMessageStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

internal val Context.messageDataStore: DataStore<ProtoCKMessageStore> by dataStore (
            fileName = "messages.pb",
            serializer = MessageSerializer
        )

private object MessageSerializer : Serializer<ProtoCKMessageStore> {
    override val defaultValue: ProtoCKMessageStore
        get() = ProtoCKMessageStore.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): ProtoCKMessageStore {
        try {
            return ProtoCKMessageStore.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto", exception)
        }
    }

    override suspend fun writeTo(t: ProtoCKMessageStore, output: OutputStream) = t.writeTo(output)
}