package com.example.multiplataform

import Character
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import multiplataform.composeapp.generated.resources.Res
import multiplataform.composeapp.generated.resources.compose_multiplatform

@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        var isLoading by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }
        var characters by remember { mutableStateOf<List<Character>>(emptyList()) }
        val scope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Rick and Morty", style = MaterialTheme.typography.headlineMedium)

            Button(onClick = {
                showContent = !showContent
                if (showContent && characters.isEmpty()) {
                    isLoading = true
                    errorMessage = null
                    scope.launch {
                        try {
                            characters = ApiClient.getCharacters()
                        } catch (e: Exception) {
                            errorMessage = "Error: ${e.message}"
                        } finally {
                            isLoading = false
                        }
                    }
                }
            }) {
                Text(if (showContent) "Hide Characters" else "Show Characters")
            }

            AnimatedVisibility(showContent) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val greeting = remember { Greeting().greet() }
                    Image(painterResource(Res.drawable.compose_multiplatform), contentDescription = "Logo")

                    Text("Compose: $greeting", style = MaterialTheme.typography.bodyLarge)

                    when {
                        isLoading -> Text("Loading characters...")
                        errorMessage != null -> Text(errorMessage ?: "Unknown error")
                        else -> LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            items(characters) { character ->
                                Text("${character.name} - ${character.status}")
                            }
                        }
                    }
                }
            }
        }
    }
}
