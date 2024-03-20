package hu.ait.AndWallet.ui.navigation

sealed class MainNavigation(val route: String) {
    object PinCodeScreen : MainNavigation("pincode")
    object MainScreen : MainNavigation("mainscreen")
    object SummaryScreen : MainNavigation(
        "summaryscreen?expense={expense}&income={income}") {
        fun createRoute(expense: Int, income: Int) : String {
            return "summaryscreen?expense=$expense&income=$income"
        }
    }
}