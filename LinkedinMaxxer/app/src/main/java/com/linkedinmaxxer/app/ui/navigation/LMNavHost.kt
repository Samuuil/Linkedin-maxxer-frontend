package com.linkedinmaxxer.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.linkedinmaxxer.app.ui.feature.auth.LoginScreen
import com.linkedinmaxxer.app.ui.feature.auth.LoginViewModel
import com.linkedinmaxxer.app.ui.feature.auth.RegisterScreen
import com.linkedinmaxxer.app.ui.feature.auth.RegisterViewModel
import com.linkedinmaxxer.app.ui.feature.home.HomeScreen
import com.linkedinmaxxer.app.ui.feature.home.HomeViewModel
import com.linkedinmaxxer.app.ui.feature.posts.PostsScreen
import com.linkedinmaxxer.app.ui.feature.posts.PostsViewModel
import com.linkedinmaxxer.app.ui.feature.posts.create.CreatePostScreen
import com.linkedinmaxxer.app.ui.feature.posts.create.CreatePostViewModel
import com.linkedinmaxxer.app.ui.feature.settings.SettingsScreen
import com.linkedinmaxxer.app.ui.feature.settings.SettingsViewModel
import com.linkedinmaxxer.app.ui.feature.setup.SetupScreen
import com.linkedinmaxxer.app.ui.feature.setup.SetupViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LMNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.LOGIN_SCREEN,
    ) {
        composable(Screens.LOGIN_SCREEN) {
            val viewModel = koinViewModel<LoginViewModel>()
            val state by viewModel.state.collectAsState()
            LoginScreen(
                data = state,
                onAction = viewModel::onAction,
                onNavigateToRegister = { navController.navigate(Screens.REGISTER_SCREEN) },
                onLoginSuccess = { navigateAndPopBackstack(Screens.HOME_SCREEN, navController) },
            )
        }

        composable(Screens.REGISTER_SCREEN) {
            val viewModel = koinViewModel<RegisterViewModel>()
            val state by viewModel.state.collectAsState()
            RegisterScreen(
                data = state,
                onAction = viewModel::onAction,
                onNavigateToLogin = { navController.popBackStack() },
                onRegisterSuccess = { navigateAndPopBackstack(Screens.HOME_SCREEN, navController) },
            )
        }

        composable(Screens.SETUP_SCREEN) {
            val viewModel = koinViewModel<SetupViewModel>()
            val state by viewModel.state.collectAsState()
            SetupScreen(
                data = state,
                onAction = viewModel::onAction,
                onContinue = { navigateAndPopBackstack(Screens.HOME_SCREEN, navController) },
            )
        }

        composable(Screens.HOME_SCREEN) {
            val viewModel = koinViewModel<HomeViewModel>()
            val state by viewModel.state.collectAsState()
            HomeScreen(
                data = state,
                onAction = viewModel::onAction,
                onOpenSettings = { navController.navigate(Screens.SETTINGS_SCREEN) },
                onOpenPosts = { navController.navigate(Screens.POSTS_SCREEN) },
                onOpenCreatePost = { navController.navigate(Screens.CREATE_POST_SCREEN) },
            )
        }

        composable(Screens.POSTS_SCREEN) {
            val viewModel = koinViewModel<PostsViewModel>()
            val state by viewModel.state.collectAsState()
            PostsScreen(
                data = state,
                onAction = viewModel::onAction,
                onOpenCreatePost = { navController.navigate(Screens.CREATE_POST_SCREEN) },
                onOpenHome = { navigateAndPopBackstack(Screens.HOME_SCREEN, navController) },
                onOpenSettings = { navController.navigate(Screens.SETTINGS_SCREEN) },
            )
        }

        composable(Screens.CREATE_POST_SCREEN) {
            val viewModel = koinViewModel<CreatePostViewModel>()
            val state by viewModel.state.collectAsState()
            CreatePostScreen(
                data = state,
                onAction = viewModel::onAction,
                onPublished = { navigateAndPopBackstack(Screens.POSTS_SCREEN, navController) },
            )
        }

        composable(Screens.SETTINGS_SCREEN) {
            val viewModel = koinViewModel<SettingsViewModel>()
            val state by viewModel.state.collectAsState()
            SettingsScreen(
                data = state,
                onAction = viewModel::onAction,
                onLoggedOut = { navigateAndPopBackstack(Screens.LOGIN_SCREEN, navController) },
                onOpenHome = { navController.navigate(Screens.HOME_SCREEN) },
                onOpenSetup = { navController.navigate(Screens.SETUP_SCREEN) },
                onOpenPosts = { navController.navigate(Screens.POSTS_SCREEN) },
            )
        }
    }
}
