package com.example.myhabitapp.navigation

import kotlinx.serialization.Serializable

sealed class NavRoutes {
    @Serializable object Main: NavRoutes()
    @Serializable object Create: NavRoutes()
    @Serializable data class Edit(val habitId: Int): NavRoutes()
}
