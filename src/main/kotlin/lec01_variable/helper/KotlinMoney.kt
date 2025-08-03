package org.example.lec01_variable_type.helper

data class KotlinMoney(
    val amount: Long
) {

    operator fun plus(other: KotlinMoney): KotlinMoney {
        return KotlinMoney(this.amount + other.amount)
    }
}
