package com.example.myhabitapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform