package hu.ait.AndWallet.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import hu.ait.AndWallet.ui.navigation.MainNavigation
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import hu.ait.AndWallet.R


@Composable
fun PinCodeScreen(navController: NavHostController) {

    val correctPinCode = stringResource(R.string.pincode) // Hardcoded correct PIN code

    val pinCodeState = remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = pinCodeState.value,
            onValueChange = { pinCodeState.value = it },
            label = { Text(stringResource(R.string.enter_pin_code)) },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val isCorrectPin = pinCodeState.value == correctPinCode
                if (isCorrectPin) {
                    navController.navigate(MainNavigation.MainScreen.route)
                } else {
                    Toast.makeText(context,
                        context.getString(R.string.incorrect_pin), Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(stringResource(R.string.start))
        }
    }
}
