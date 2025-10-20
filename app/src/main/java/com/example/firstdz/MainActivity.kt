package com.example.firstdz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserProfileScreen()
                }
            }
        }
    }
}

@Composable
fun MyAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen() {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf(25f) }
    var gender by remember { mutableStateOf("") }
    var subscribed by remember { mutableStateOf(false) }
    var showSummary by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Поле ввода имени
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Введите имя") },
            modifier = Modifier.fillMaxWidth()
        )

        // Выбор возраста
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Возраст: ${age.toInt()}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Slider(
                value = age,
                onValueChange = { age = it },
                valueRange = 1f..100f,
                steps = 98,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Выбор пола
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Пол",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "male",
                        onClick = { gender = "male" }
                    )
                    Text("Мужской")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = gender == "female",
                        onClick = { gender = "female" }
                    )
                    Text("Женский")
                }
            }
        }

        // Подписка на рассылку
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = subscribed,
                onCheckedChange = { subscribed = it }
            )
            Text(
                text = "Хочу получать новости",
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Кнопка отправки
        Button(
            onClick = { showSummary = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = name.isNotBlank() && gender.isNotBlank()
        ) {
            Text(text = "Отправить")
        }

        // Сводка данных
        if (showSummary) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Имя: $name")
                    Text("Возраст: ${age.toInt()}")
                    Text("Пол: ${getGenderText(gender)}")
                    Text("Подписка: ${if (subscribed) "да ✅" else "нет"}")
                }
            }
        }
    }
}

@Composable
private fun getGenderText(gender: String): String {
    return when (gender) {
        "male" -> "Мужской"
        "female" -> "Женский"
        else -> ""
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    MyAppTheme {
        UserProfileScreen()
    }
}