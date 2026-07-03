package com.ammar.studentdesk.sdui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ammar.studentdesk.auth.ui.AuthRoute
import com.ammar.studentdesk.designsystems.StudentDeskTheme
import com.ammar.studentdesk.network.utils.TokenManager
import com.ammar.studentdesk.onboarding.ui.OnboardingScreen
import com.ammar.studentdesk.screenhost.ui.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var tokenManager: TokenManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val initialDestination = runBlocking {
            val token = tokenManager.getToken().firstOrNull()
            if (!token.isNullOrBlank()) "home" else "onboarding"
        }

        setContent {
            StudentDeskTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                val bottomNavItems = listOf(
                    Triple("home", "Beranda", Icons.Default.Home),
                    Triple("jadwal", "Jadwal", Icons.Default.DateRange),
                    Triple("profile", "Profil", Icons.Default.Person)
                )

                val showBottomNav = currentRoute in listOf("home", "jadwal", "profile")
                Scaffold(
                    bottomBar = {
                        if (showBottomNav) {
                            NavigationBar {
                                bottomNavItems.forEach { (route, label, icon) ->
                                    NavigationBarItem(
                                        icon = { Icon(icon, contentDescription = label) },
                                        label = { Text(label) },
                                        selected = currentRoute == route,
                                        onClick = {
                                            navController.navigate(route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = initialDestination,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(
                                onFinishOnboarding = {
                                    navController.navigate("auth") {
                                        popUpTo("onboarding") {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }

                        composable(
                            route = "auth",
                            enterTransition = {
                                slideIntoContainer(
                                    AnimatedContentTransitionScope.SlideDirection.Up,
                                    animationSpec = tween(500)
                                ) + fadeIn(animationSpec = tween(500))
                            },
                            exitTransition = {
                                fadeOut(animationSpec = tween(300))
                            }
                        ) {
                            AuthRoute(
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("auth") { inclusive = true }
                                    }
                                }
                            )
                        }
                        
                        val handleLogout: () -> Unit = {
                            lifecycleScope.launch {
                                tokenManager.clearToken()
                                navController.navigate("auth") {
                                    popUpTo(navController.graph.id) {
                                        inclusive = true
                                    }
                                }
                            }
                        }

                        composable("home") {
                            HomeScreen(
                                screenId = "home",
                                onNavigate = { destination ->
                                    val encoded = java.net.URLEncoder.encode(destination, "UTF-8")
                                    navController.navigate("sdui/$encoded")
                                },
                                onLogout = handleLogout
                            )
                        }
                        composable("jadwal") {
                            HomeScreen(
                                screenId = "jadwal",
                                onNavigate = { destination ->
                                    val encoded = java.net.URLEncoder.encode(destination, "UTF-8")
                                    navController.navigate("sdui/$encoded")
                                },
                                onLogout = handleLogout
                            )
                        }
                        composable("profile") {
                            HomeScreen(
                                screenId = "profile",
                                onNavigate = { destination ->
                                    val encoded = java.net.URLEncoder.encode(destination, "UTF-8")
                                    navController.navigate("sdui/$encoded")
                                },
                                onLogout = handleLogout
                            )
                        }

                        composable("sdui/{screenId}") { backStackEntry ->
                            val screenId = backStackEntry.arguments?.getString("screenId") ?: "home"
                            HomeScreen(
                                screenId = screenId,
                                onNavigate = { destination ->
                                    val encoded = java.net.URLEncoder.encode(destination, "UTF-8")
                                    navController.navigate("sdui/$encoded")
                                },
                                onLogout = handleLogout
                            )
                        }
                    }
                }
            }
        }
    }
}