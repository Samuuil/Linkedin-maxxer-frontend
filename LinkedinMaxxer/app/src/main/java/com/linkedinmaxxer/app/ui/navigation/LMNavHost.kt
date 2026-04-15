package com.linkedinmaxxer.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.linkedinmaxxer.app.data.session.SessionManager
import com.linkedinmaxxer.app.ui.feature.auth.LoginScreen
import com.linkedinmaxxer.app.ui.feature.auth.LoginViewModel
import com.linkedinmaxxer.app.ui.feature.auth.RegisterScreen
import com.linkedinmaxxer.app.ui.feature.auth.RegisterViewModel
import com.linkedinmaxxer.app.ui.feature.home.HomeScreen
import com.linkedinmaxxer.app.ui.feature.home.HomeViewModel
import com.linkedinmaxxer.app.ui.feature.hints.HintsScreen
import com.linkedinmaxxer.app.ui.feature.hints.HintsViewModel
import com.linkedinmaxxer.app.ui.feature.hints.SuggestionReviewScreen
import com.linkedinmaxxer.app.ui.feature.hints.SuggestionReviewViewModel
import com.linkedinmaxxer.app.ui.feature.posts.PostsScreen
import com.linkedinmaxxer.app.ui.feature.posts.PostsViewModel
import com.linkedinmaxxer.app.ui.feature.posts.create.CreatePostScreen
import com.linkedinmaxxer.app.ui.feature.posts.create.CreatePostViewModel
import com.linkedinmaxxer.app.ui.feature.settings.SettingsScreen
import com.linkedinmaxxer.app.ui.feature.settings.SettingsViewModel
import com.linkedinmaxxer.app.ui.feature.setup.SetupScreen
import com.linkedinmaxxer.app.ui.feature.setup.SetupViewModel
import com.linkedinmaxxer.app.ui.feature.subscriptions.SubscriptionsScreen
import com.linkedinmaxxer.app.ui.feature.subscriptions.SubscriptionsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun LMNavHost(
    navController: NavHostController,
    initialSuggestionId: String? = null,
    onSuggestionIdConsumed: () -> Unit = {},
) {
    val startDestination = if (SessionManager.getToken().isNotBlank()) {
        Screens.HOME_SCREEN
    } else {
        Screens.LOGIN_SCREEN
    }

    LaunchedEffect(initialSuggestionId) {
        if (initialSuggestionId != null) {
            navController.navigate("${Screens.SUGGESTION_REVIEW_SCREEN}/$initialSuggestionId")
            onSuggestionIdConsumed()
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
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
                onOpenSubscriptions = { navController.navigate(Screens.SUBSCRIPTIONS_SCREEN) },
                onOpenHints = { navController.navigate(Screens.HINTS_SCREEN) },
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
                onOpenSubscriptions = { navController.navigate(Screens.SUBSCRIPTIONS_SCREEN) },
                onOpenHints = { navController.navigate(Screens.HINTS_SCREEN) },
                onOpenSettings = { navController.navigate(Screens.SETTINGS_SCREEN) },
            )
        }

        composable(Screens.SUBSCRIPTIONS_SCREEN) {
            val viewModel = koinViewModel<SubscriptionsViewModel>()
            val state by viewModel.state.collectAsState()
            SubscriptionsScreen(
                data = state,
                onAction = viewModel::onAction,
                onOpenHome = { navController.navigate(Screens.HOME_SCREEN) },
                onOpenPosts = { navController.navigate(Screens.POSTS_SCREEN) },
                onOpenHints = { navController.navigate(Screens.HINTS_SCREEN) },
                onOpenSettings = { navController.navigate(Screens.SETTINGS_SCREEN) },
            )
        }

        composable(Screens.HINTS_SCREEN) {
            val viewModel = koinViewModel<HintsViewModel>()
            val state by viewModel.state.collectAsState()
            HintsScreen(
                data = state,
                onAction = viewModel::onAction,
                onOpenSuggestion = { suggestionId ->
                    navController.navigate("${Screens.SUGGESTION_REVIEW_SCREEN}/$suggestionId")
                },
                onOpenHome = { navController.navigate(Screens.HOME_SCREEN) },
                onOpenPosts = { navController.navigate(Screens.POSTS_SCREEN) },
                onOpenSubs = { navController.navigate(Screens.SUBSCRIPTIONS_SCREEN) },
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
                onOpenHome = { navController.navigate(Screens.HOME_SCREEN) },
                onOpenPosts = { navController.navigate(Screens.POSTS_SCREEN) },
                onOpenSubscriptions = { navController.navigate(Screens.SUBSCRIPTIONS_SCREEN) },
                onOpenHints = { navController.navigate(Screens.HINTS_SCREEN) },
                onOpenSettings = { navController.navigate(Screens.SETTINGS_SCREEN) },
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
                onOpenSubscriptions = { navController.navigate(Screens.SUBSCRIPTIONS_SCREEN) },
                onOpenHints = { navController.navigate(Screens.HINTS_SCREEN) },
            )
        }

        composable(
            route = Screens.SUGGESTION_REVIEW_ROUTE,
            arguments = listOf(navArgument(Screens.SUGGESTION_ID_ARG) { type = NavType.StringType }),
        ) { backStackEntry ->
            val suggestionId = backStackEntry.arguments?.getString(Screens.SUGGESTION_ID_ARG).orEmpty()
            val viewModel = koinViewModel<SuggestionReviewViewModel>()
            val state by viewModel.state.collectAsState()
            SuggestionReviewScreen(
                suggestionId = suggestionId,
                data = state,
                onAction = viewModel::onAction,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
