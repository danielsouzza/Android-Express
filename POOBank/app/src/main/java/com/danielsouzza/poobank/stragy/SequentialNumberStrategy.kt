package com.danielsouzza.poobank.stragy

class SequentialNumberStrategy {
    private var nextNumberAccount: Int = 1

    fun nextNumber(): String {
        return nextNumberAccount++.toString()
    }
}