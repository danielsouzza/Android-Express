package com.danielsouzza.poobank.model.account

import com.danielsouzza.poobank.model.client.Client

abstract class Account(holder: Client, email: String, password: String) {
    private var number: String = ""
    private var balance: Double = 0.0
    private var holder: Client
    private var password: String
    private var email: String

    init {
        this.balance = 0.0
        this.holder = holder
        this.password = password
        this.email = email
    }

    fun deposit(value: Double) {
        this.balance += value
    }

    fun withdraw(value: Double) {
        if (value <= this.getBalance()) {
            this.balance -= value
        } else {
            throw InsufficientFundsException()
        }

    }

    fun transfer(destination: Account, value: Double) {
        this.withdraw(value)
        destination.deposit(value)
    }

    fun getHolder(): Client = this.holder
    fun setHolder(holder: Client) {
        holder.also { this.holder = it }
    }

    fun getNumber(): String = this.number
    fun setNumber(number: String) {
        number.also { this.number = it }
    }


    fun getBalance(): Double = this.balance
    fun setBalance(value: Double) {
        value.also { this.balance = it }
    }

    fun getPassword(): String = this.password
    fun setPassword(password: String) {
        password.also { this.password = it }
    }

    fun getEmail(): String = this.email
    fun setEmail(email: String) {
        email.also { this.email = it }
    }

    abstract fun getType(): String

}