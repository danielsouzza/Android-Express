package com.danielsouzza.poobank.controler

import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.client.Client
import com.danielsouzza.poobank.repository.account.RepositoryAccount
import com.danielsouzza.poobank.repository.account.RepositoryAccountList
import com.danielsouzza.poobank.repository.account.RepositoryKeysPixList
import com.danielsouzza.poobank.repository.client.RepositoryClient
import com.danielsouzza.poobank.repository.client.RepositoryClientList
import com.danielsouzza.poobank.stragy.SequentialNumberStrategy

class BankController {

    companion object {
        private var repositoryClient: RepositoryClient = RepositoryClientList()
        private var repositoryAccount: RepositoryAccount = RepositoryAccountList()
        private var repositoryKeysPix: RepositoryKeysPixList = RepositoryKeysPixList()
        private var numberStrategy: SequentialNumberStrategy = SequentialNumberStrategy()
        private lateinit var userCurrent: Account

    }

    fun setUserCurrent(user: Account) {
        userCurrent = user
    }

    fun getUserCurrent(): Account {
        return userCurrent
    }

    fun insertClient(client: Client) {
        repositoryClient.insertClient(client)
    }

    fun alterClient(client: Client) {
        repositoryClient.alterClient(client)
    }

    fun searchClient(cpf: String): Client {
        return repositoryClient.searchClient(cpf)
    }

    fun deleteClient(client: Client) {
        val accountsClient = this.getAccount(client.getCpf())

        if (accountsClient.isEmpty()) {
            repositoryClient.deleteClient(client)
        } else {
            throw ControllerException("Cliente com conta ativa não pode se excuído.")
        }
    }

    fun searchKeyPix(key: String): Account {
        return repositoryKeysPix.searchKeyPix(key)
    }

    fun insertKeyPix(key: String, id: Account) {
        repositoryKeysPix.registerNewKey(key, id)
    }

    fun getAllKeys(numberAccount: String):  MutableMap<String,String> {
        return repositoryKeysPix.getKeys(numberAccount)
    }

    fun insertAccount(account: Account): Account {
        account.setNumber(numberStrategy.nextNumber())
        return repositoryAccount.insertAccount(account)
    }

    fun searchAccount(account: String): Account {
        return repositoryAccount.searchAccount(account)
    }

    fun alterAccount(account: Account) {
        repositoryAccount.alterAccount(account)
    }

    fun deleteAccount(account: Account) {
        if (account.getBalance() == 0.0) {
            repositoryAccount.deleteAccount(account)
        } else {
            throw ControllerException("Conta com saldo não pode ser excluída.")
        }
    }

    fun deposit(number: String, value: Double) {
        val account = repositoryAccount.searchAccount(number)
        account.deposit(value)
        repositoryAccount.alterAccount(account)
    }

    fun transfer(origin: String, destination: String, value: Double) {
        val accountOrigin = repositoryAccount.searchAccount(origin)
        val accountDestination = repositoryAccount.searchAccount(destination)
        accountOrigin.transfer(accountDestination, value)
    }


    fun getAccount(cpf: String): List<Account> {
        return repositoryAccount.getAll(cpf)
    }
}