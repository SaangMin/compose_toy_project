package com.skysmyoo.xylophone

import android.app.Application
import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val soundPool = SoundPool.Builder().setMaxStreams(8).build()
    private val sounds = listOf(
        soundPool.load(application.applicationContext, R.raw.do1, 1),
        soundPool.load(application.applicationContext, R.raw.re, 1),
        soundPool.load(application.applicationContext, R.raw.mi, 1),
        soundPool.load(application.applicationContext, R.raw.fa, 1),
        soundPool.load(application.applicationContext, R.raw.sol, 1),
        soundPool.load(application.applicationContext, R.raw.la, 1),
        soundPool.load(application.applicationContext, R.raw.si, 1),
        soundPool.load(application.applicationContext, R.raw.do2, 1),
    )

    fun playSound(index: Int) {
        soundPool.play(sounds[index], 1f, 1f, 0, 0, 1f)
    }

    override fun onCleared() {
        soundPool.release()
        super.onCleared()
    }
}

@Composable
fun XylophoneScreen() {
    val keys = listOf(
        Pair("도", Color.Red),
        Pair("레", Color(0xFFFF9800)),
        Pair("미", Color(0xFFFFC107)),
        Pair("파", Color(0xFF8BC34A)),
        Pair("솔", Color(0xFF2196F3)),
        Pair("라", Color(0xFF3F51B5)),
        Pair("시", Color(0xFF673AB7)),
        Pair("도", Color.Red),
    )

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        keys.forEachIndexed { index, key ->
            val padding = (index + 2) * 8
            Keyboard(
                modifier = Modifier
                    .padding(top = padding.dp, bottom = padding.dp),
                text = key.first,
                color = key.second,
            )
        }
    }
}

@Composable
fun Keyboard(
    modifier: Modifier,
    text: String,
    color: Color
) {
    Box(
        modifier = modifier
            .width(50.dp)
            .fillMaxHeight()
            .background(color = color),
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontSize = 20.sp,
            ),
            modifier = Modifier.align(Alignment.Center),
        )
    }
}