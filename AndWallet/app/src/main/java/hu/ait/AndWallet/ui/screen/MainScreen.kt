package hu.ait.AndWallet.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import hu.ait.AndWallet.data.moneyItem
import hu.ait.AndWallet.data.MoneyType
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hu.ait.AndWallet.R


@Composable
fun MainScreen(
    moneyViewModel: MoneyViewModel = viewModel(),
    onNavigateToSummary: (Int, Int) -> Unit
) {
    var title by rememberSaveable {
        mutableStateOf("")
    }
    var amount by rememberSaveable {
        mutableStateOf("")
    }
    var income by rememberSaveable {
        mutableStateOf(false)
    }
    var titleErrorState by rememberSaveable { mutableStateOf(false) }
    var amountErrorState by rememberSaveable { mutableStateOf(false) }
    var amountTextErrorState by rememberSaveable { mutableStateOf(false) }

    fun validate() {
        titleErrorState = title.isEmpty()
        amountErrorState = amount.isEmpty()
        amountTextErrorState = !amount.all { it.isDigit() }
    }

    Column {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it
                    validate()},
                label = { Text(text = stringResource(R.string.title)) },
                isError = titleErrorState
            )
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it
                    validate()},
                label = { Text(text = stringResource(R.string.amount_in)) },
                isError = amountErrorState || amountTextErrorState
            )

        val inputErrorState = titleErrorState || amountErrorState || amountTextErrorState

        if (inputErrorState) {
            Text(
                text = when {
                    titleErrorState -> stringResource(R.string.title_cannot_be_empty)
                    amountErrorState -> stringResource(R.string.amount_cannot_be_empty)
                    amountTextErrorState -> stringResource(R.string.amount_must_be_a_number)
                    else -> ""
                },
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        Row() {
            Checkbox(checked = income, onCheckedChange = { income = it })
            Text(text = "Income")
        }
        Row {

            Button(
                enabled = !inputErrorState,
                onClick = {
                    if (title.isNotEmpty() && amount.isNotEmpty() && amount.all { it.isDigit() }) {
                        moneyViewModel.addToMoneyList(
                            moneyItem(
                                title = title,
                                amount = amount.toInt(),
                                type = if (income) MoneyType.INCOME else MoneyType.EXPENSE
                            )
                        )
                    }}) {
                Text(text = stringResource(R.string.save))
            }

            Button(onClick = {
                moneyViewModel.clearAllItems()
            }) {
                Text(text = stringResource(R.string.delete_all))
            }

            Button(onClick = {
                onNavigateToSummary(
                    moneyViewModel.getExpense(),
                    moneyViewModel.getIncome()
                )
            }) {
                Text(text = stringResource(R.string.sum))
            }
        }

        // show MoneyItems from the ViewModel in a LazyColumn
        if (moneyViewModel.getAllitems().isEmpty()) {
            Text(text = stringResource(R.string.no_items))
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(moneyViewModel.getAllitems()) {
                    MoneyCard(it,
                        onMoneyCheckChange = { checkValue ->
                            moneyViewModel.changeMoneyState(it, checkValue)
                        },
                        onRemoveItem = { moneyViewModel.removeItem(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun MoneyCard(
    moneyItem: moneyItem,
    onMoneyCheckChange: (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = moneyItem.type.getIcon()),
                contentDescription = "Type",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )

            Column{
                Text(moneyItem.title, modifier = Modifier.fillMaxWidth(0.2f))
                Text(moneyItem.amount.toString(), modifier = Modifier.fillMaxWidth(0.2f))
            }
            Spacer(modifier = Modifier.fillMaxSize(0.55f))
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = "Delete",
                modifier = Modifier.clickable {
                    onRemoveItem()
                },
                tint = Color.Red
            )
        }
    }
}

