package org.example.project

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Merhaba, ${platform.name}!"
    }
}