package com.example.compose_kotlin.store

class DataStoreLocator(
    val localStorage: IMessageStorage,
    val remoteStorage: IMessageStorage
) {
}