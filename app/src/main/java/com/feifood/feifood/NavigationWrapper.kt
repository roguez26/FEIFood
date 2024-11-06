package com.feifood.feifood

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.feifood.feifood.presentation.PostCreation.PostCreationScreen
import com.feifood.feifood.presentation.home.HomeScreen
import com.feifood.feifood.presentation.initial.InitialScreen
import com.feifood.feifood.presentation.login.LoginScreen
import com.feifood.feifood.presentation.signup.SignupScreen
import com.feifood.feifood.presentation.productRegistration.ProductRegistrationScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth) {
    NavHost(navController = navHostController, startDestination = "initial") {
        composable("initial") {
            InitialScreen(
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToSignup = { navHostController.navigate("signUp") }
            )
        }
        composable("logIn") {
            LoginScreen(navigateToHome = { navHostController.navigate("home") })
        }
        composable("signUp") {
            SignupScreen()
        }
        composable("home") {
            HomeScreen( navigateToPostCreation = {
                navHostController.navigate("postCreation")
            })
        }
        composable("postCreation") {
            PostCreationScreen(
                navigateToHome = { navHostController.navigate("home") },
                navigateToProductRegistration = { navHostController.navigate("productRegistration") }
            )
        }
        composable("productRegistration") {
            ProductRegistrationScreen(navigateToPostCreation = { navHostController.navigate("postCreation") })
        }
    }
}
