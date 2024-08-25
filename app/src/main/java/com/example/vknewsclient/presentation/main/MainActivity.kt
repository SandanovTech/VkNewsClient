package com.example.vknewsclient.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vknewsclient.domain.entity.AuthState
import com.example.vknewsclient.presentation.NewsFeedApplication
import com.example.vknewsclient.presentation.ViewModelFactory
import com.example.vknewsclient.ui.theme.VkNewsClientTheme
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import javax.inject.Inject


class MainActivity : ComponentActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as NewsFeedApplication).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
            val authState = viewModel.stateAuthScreen.collectAsState(initial = AuthState.Initial)
            VkNewsClientTheme {
                val authLauncher =
                    rememberLauncherForActivityResult(contract = VK.getVKAuthActivityResultContract()) {
                        viewModel.performAuthResult()
                    }
                when (authState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory)
                    }
                    is AuthState.NotAuthorized -> {
                        LoginScreen {
                            authLauncher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }
                    else -> {}
                }
            }
        }
    }
}
