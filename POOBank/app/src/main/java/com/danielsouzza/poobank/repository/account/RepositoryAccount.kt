package com.danielsouzza.poobank.repository.account

import com.danielsouzza.poobank.model.account.Account

interface RepositoryAccount {
    fun insertAccount(account: Account): Account
    fun alterAccount(account: Account)
    fun deleteAccount(account: Account)
    fun searchAccount(number: String): Account
    fun getAll(): List<Account>
    fun getAll(cpf: String): List<Account>

}