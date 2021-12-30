package com.sarftec.lifehacks.utils

class Event<out T>(private val value: T) {
    private var isHandled = false

    fun getIfNotHandled() : T? {
        if(isHandled) return null
        isHandled = false
        return value
    }

    fun peek() : T = value
}