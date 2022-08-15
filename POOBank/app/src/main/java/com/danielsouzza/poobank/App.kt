package com.danielsouzza.poobank

import android.app.Application
import android.content.res.Resources

class App : Application() {
    companion object{
        private lateinit var instance: App
        private lateinit var res: Resources

        fun getInstance() : App = instance
        fun getRes(): Resources = res
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources
    }
}