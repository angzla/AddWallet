package hu.ait.AndWallet.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import hu.ait.AndWallet.data.moneyItem
import hu.ait.AndWallet.data.MoneyType

class MoneyViewModel : ViewModel() {

    private var _moneyList =
        mutableStateListOf<moneyItem>()

    fun getAllitems(): List<moneyItem> {
        return _moneyList
    }

    fun getExpense(): Int { var expense = 0
        _moneyList.forEach {
            if (it.type == MoneyType.EXPENSE) { expense += it.amount
                }
                }
                return expense
        }

    fun getIncome(): Int { var income = 0
        _moneyList.forEach {
            if (it.type == MoneyType.INCOME) {
                income += it.amount }
        }
        return income }

    fun addToMoneyList(moneyItem: moneyItem) {
        _moneyList.add(moneyItem)
    }


    fun removeItem(moneyItem: moneyItem) {
        _moneyList.remove(moneyItem)
    }

    fun changeMoneyState(moneyItem: moneyItem, value: Boolean) {
        val index = _moneyList.indexOf(moneyItem)

        val newAndWallet = moneyItem.copy(
            title = moneyItem.title,
            amount = moneyItem.amount,
            type = moneyItem.type,
//            isDone = moneyItem.isDone
        )

        _moneyList[index] = newAndWallet
    }

    fun clearAllItems() {
        _moneyList.clear()
    }

}