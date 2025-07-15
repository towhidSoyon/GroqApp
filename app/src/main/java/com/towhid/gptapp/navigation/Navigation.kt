package com.towhid.gptapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.towhid.gptapp.presentation.ChatScreen
import com.towhid.gptapp.presentation.ChatViewModel

@Composable
fun AppNavHost(viewModel: ChatViewModel) {
    NavHost(navController = rememberNavController(), startDestination = "chat") {
        composable("chat") {
            ChatScreen(viewModel)
        }
    }
}

