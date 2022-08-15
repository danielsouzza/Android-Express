package com.danielsouzza.poobank.repository.account

import com.danielsouzza.poobank.model.account.Account


class RepositoryAccountList : RepositoryAccount {

    private val accounts = mutableListOf<Account>()


    override fun insertAccount(account: Account): Account {
        try {
            searchAccount(account.getNumber())
            throw AccountAlreadyRegistered()
        } catch (ex: AccountNotRegisteredException) {
            accounts.add(account)
        }
        return account
    }

    override fun alterAccount(account: Account) {
        //
    }

    override fun deleteAccount(account: Account) {
        if (!accounts.remove(account)) {
            throw AccountNotRegisteredException()
        }
    }

    override fun searchAccount(account: String): Account {
        accounts.forEach {
            if (it.getEmail() == account || it.getNumber() == account) {
                return it
            }
        }
        throw AccountNotRegisteredException()
    }

    override fun getAll(): List<Account> = accounts

    override fun getAll(cpf: String): List<Account> {
        accounts.forEach {
            if (it.getHolder().getCpf() == cpf) {
                return listOf(it)
            }
        }
        throw AccountNotRegisteredException()
    }
}