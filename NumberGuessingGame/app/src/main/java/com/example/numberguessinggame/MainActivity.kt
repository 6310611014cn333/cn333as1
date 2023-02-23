package com.example.numberguessinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.numberguessinggame.ui.theme.NumberGuessingGameTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessingGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    GameScreen()
                }
            }
        }
    }
}
private fun TheHint(guess: Int, ans: Int): String {
    var hint = ""
    if (guess > ans && guess <= 1000) {
        hint = "It's lower"
    } else if (guess < ans && guess >= 1) {
        hint = "It's higher"
    } else if (guess == ans) {
        hint = "Correct!"
    } else {
        hint = "Guess the number"
    }
    return hint
}

@Composable
fun GuessingNumField(
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.your_guess)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

    )
}

@Composable
fun GameScreen() {
    var ans by remember { mutableStateOf((1..1000).random()) }

    var numInput by remember { mutableStateOf("") }
    val guess = numInput.toIntOrNull() ?: 0
    val hint = TheHint(guess, ans)
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Try to guest the number I'm thinking of 1 - 1000 " + ans, //ใส่ ans ไว้ดูว่า program ถูกต้องไหม ของจริงไม่มี
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )

        Spacer(Modifier.height(16.dp))
        GuessingNumField(value = numInput,
            onValueChange = { numInput = it })

        Spacer(Modifier.height(24.dp))
        Text(
            text = stringResource(R.string.hint, hint),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp,
        )

        Button(onClick = {  ans = (1..1000).random() }) {
            Text(stringResource(R.string.play_again))
        }

    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NumberGuessingGameTheme {
        GameScreen()
    }
}