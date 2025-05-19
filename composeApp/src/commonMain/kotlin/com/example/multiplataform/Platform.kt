package com.example.multiplataform

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform