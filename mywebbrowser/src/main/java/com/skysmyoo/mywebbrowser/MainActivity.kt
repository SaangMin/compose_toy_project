@file:OptIn(ExperimentalMaterial3Api::class)

package com.skysmyoo.mywebbrowser

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.skysmyoo.mywebbrowser.ui.theme.ComposeToyProjectTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = viewModel<MainViewModel>()
            HomeScreen(viewModel)
        }
    }
}

@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val focusManager = LocalFocusManager.current
    val (inputUrl, setUrl) = rememberSaveable {
        mutableStateOf("https://www.google.com")
    }
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "나만의 웹 브라우저") },
                actions = {
                    IconButton(onClick = {
                        viewModel.undo()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                    IconButton(onClick = {
                        viewModel.redo()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "forward",
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = inputUrl,
                onValueChange = setUrl,
                label = { Text("https://") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    viewModel.url.value = inputUrl
                    focusManager.clearFocus()
                })
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyWebView(viewModel, snackbarHostState)
        }
    }
}

@Composable
fun MyWebView(viewModel: MainViewModel, snackbarHostState: SnackbarHostState) {
    val webView = rememberWebView()

    LaunchedEffect(Unit) {
        viewModel.undoSharedFlow.collectLatest {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                snackbarHostState.showSnackbar("초기 화면 입니다.")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.redoSharedFlow.collectLatest {
            if (webView.canGoForward()) {
                webView.goForward()
            } else {
                snackbarHostState.showSnackbar("마지막 화면 입니다.")
            }
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { webView },
        update = {
            it.loadUrl(viewModel.url.value)
        }
    )
}

@Composable
fun rememberWebView(): WebView {
    val context = LocalContext.current
    val webView = remember {
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl("https://google.com")
        }
    }
    return webView
}