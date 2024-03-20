package hu.ait.AndWallet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.ait.AndWallet.ui.navigation.MainNavigation
import hu.ait.AndWallet.ui.screen.MainScreen
import hu.ait.AndWallet.ui.screen.SummaryScreen
import hu.ait.AndWallet.ui.theme.AndWalletDemoTheme
import hu.ait.AndWallet.ui.screen.PinCodeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndWalletDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AndWalletAppNavHost()
                }
            }
        }
    }
}

@Composable
fun AndWalletAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.PinCodeScreen.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {

        composable(MainNavigation.PinCodeScreen.route) {
            PinCodeScreen(navController = navController)
            }

        composable(MainNavigation.MainScreen.route) {
            MainScreen(
                onNavigateToSummary = { all, important ->
                    navController.navigate(
                        MainNavigation.SummaryScreen.createRoute(all, important))
                }
            )
        }

        composable(MainNavigation.SummaryScreen.route,
            arguments = listOf(
                navArgument("expense"){type = NavType.IntType},
                navArgument("income"){type = NavType.IntType})
            ) {
            val numexpense = it.arguments?.getInt("expense")
            val numincome = it.arguments?.getInt("income")
            if (numexpense != null && numincome != null) {
                SummaryScreen(
                    numexpense = numexpense,
                    numincome = numincome
                )
            }
        }
    }
}