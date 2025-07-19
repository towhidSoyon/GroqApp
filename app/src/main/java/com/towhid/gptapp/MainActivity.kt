package com.towhid.gptapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.towhid.gptapp.data.api.RetrofitClient
import com.towhid.gptapp.data.db.ChatDatabase
import com.towhid.gptapp.domain.ChatRepository
import com.towhid.gptapp.navigation.AppNavHost
import com.towhid.gptapp.presentation.ChatViewModel
import com.towhid.gptapp.ui.theme.GPTAPPTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(applicationContext, ChatDatabase::class.java, "chat.db").build()
        val repository = ChatRepository(RetrofitClient.api, db.chatDao(), BuildConfig.GROQ_API_KEY)
        val viewModel = ChatViewModel(repository)
        setContent {
            GPTAPPTheme {

                    val navController = rememberNavController()
                    AppNavHost(viewModel)


            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GPTAPPTheme {
        Greeting("Android")
    }
}