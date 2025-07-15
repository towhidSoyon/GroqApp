package com.towhid.gptapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.towhid.gptapp.presentation.ChatScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "chat") {
        composable("chat") {
            ChatScreen(viewModel = viewModel())
        }
    }
}
