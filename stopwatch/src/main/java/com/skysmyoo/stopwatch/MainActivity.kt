@file:OptIn(ExperimentalMaterial3Api::class)

package com.skysmyoo.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.skysmyoo.stopwatch.ui.theme.ComposeToyProjectTheme
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

class MainViewModel : ViewModel() {

    private val _isRunning = mutableStateOf(false)
    val isRunning: State<Boolean> = _isRunning

    private val _sec = mutableStateOf<Int>(0)
    val sec: State<Int> = _sec

    private val _milli = mutableStateOf(0)
    val milli: State<Int> = _milli

    private val _lapTimes = mutableStateOf(mutableListOf<String>())
    val lapTimes: State<List<String>> = _lapTimes

    private var lap = 1
    private var time = 0
    private var timerTask: Timer? = null

    fun start() {
        _isRunning.value = true
        timerTask = timer(period = 10) {
            time++
            _sec.value = time / 100
            _milli.value = time % 100
        }
    }

    fun pause() {
        _isRunning.value = false
        timerTask?.cancel()
    }

    fun reset() {
        timerTask?.cancel()
        time = 0

        _isRunning.value = false
        _sec.value = 0
        _milli.value = 0

        _lapTimes.value.clear()
        lap = 1
    }

    fun recordLapTime() {
        _lapTimes.value.add(0, "$lap LAP : ${sec.value}.${milli.value}")
        lap++
    }
}

@Composable
fun MainScreen(
    sec: Int,
    milli: Int,
    isRunning: Boolean,
    lapTimes: List<String>,
    onReset: () -> Unit,
    onToggle: (Boolean) -> Unit,
    onLapTime: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Stop Watch") })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text("$sec", fontSize = 100.sp)
                Text("$milli")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            ) {
                lapTimes.forEach { lapTime ->
                    Text(lapTime)
                }
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = { onReset() },
                    containerColor = Color.Red,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_refresh_24),
                        contentDescription = "reset"
                    )
                }
                FloatingActionButton(
                    onClick = { onToggle(isRunning) },
                    containerColor = Color.Green,
                ) {
                    Image(
                        painter = painterResource(id = if (isRunning) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24),
                        contentDescription = "start/pause"
                    )
                }
                Button(onClick = { onLapTime() }) {
                    Text("랩 타임")
                }
            }
        }
    }
}