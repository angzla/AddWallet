package hu.ait.AndWallet.data

import hu.ait.AndWallet.R

data class moneyItem(
    val title: String,
    val amount:Int,
    var type:MoneyType,
)

enum class MoneyType { EXPENSE, INCOME;
    fun getIcon(): Int {
        return if (this == INCOME) R.drawable.normal else R.drawable.important }
}

