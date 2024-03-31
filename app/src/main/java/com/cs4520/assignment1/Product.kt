package com.cs4520.assignment1

sealed class Product(val name: String, val price: Int, val imageResId: Int) {
    class Equipment(name: String, price: Int) : Product(name, price, R.drawable.equipment)
    class Food(name: String, price:Int, val expiryDate: String?) : Product(name, price, R.drawable.food)
}