package com.example.numberguessinggame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
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
private fun TheHint(guess: Int, ans: Int, rounds: Int): <String, Int, String> {
    var hint = ""
    var round = rounds
    if (guess > ans && guess <= 1000) {
        hint = "It's lower"
        round++
    } else if (guess < ans && guess >= 1) {
        hint = "It's higher"
        round++
    } else if (guess == ans) {
        hint = "Correct! You won with $round round(s)"
        round = 0
    } else {
        hint = "Guess the number"
        round = 1
    }
    return hint, round
}

@Composable
fun GuessingNumField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    value: String,
    onValueChange: (String) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.your_guess)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent)
    )
}

@Composable
fun GameScreen() {
    var ans by remember { mutableStateOf((1..1000).random()) }
    var numInput by remember { mutableStateOf(1) }
    var round by remember { mutableStateOf("1") }
    val guess = numInput.toIntOrNull() ?: 0
    var hint = TheHint(guess, ans, round)
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.game_description) + " " + ans, //ใส่ ans ไว้ดูว่า program ถูกต้องไหม ของจริงไม่มี
            fontSize = 24.sp,
            fontWeight = FontWeight.W500,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 10.dp)
        )

        Spacer(Modifier.height(100.dp))
        GuessingNumField(label = R.string.your_guess,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { hint = TheHint(guess, ans, round) }
        ),
        value = numInput,
        onValueChange = { numInput = it })

        Spacer(Modifier.height(50.dp))
        Text(
            text = stringResource(R.string.hint, hint),
            style = TextStyle(fontSize = 22.sp, color = Color.Gray),
            modifier = Modifier
                .padding(vertical = 8.dp),
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