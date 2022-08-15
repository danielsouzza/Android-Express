package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.SavingsAccount
import com.danielsouzza.poobank.model.client.Client
import com.danielsouzza.poobank.regex.Regx
import com.danielsouzza.poobank.repository.account.AccountNotRegisteredException
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private val controller = BankController()
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    val regx = Regx()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Input data
        editEmail = findViewById(R.id.text_email)
        editPassword = findViewById(R.id.text_password)

        // Data test
        val clientTest1: Client = Client("1", "DLS", "20/03/2003", "99122-2222")
        controller.insertClient(clientTest1)
        val accountTest1: Account = SavingsAccount(clientTest1, "Daniel@gmail.com", "12345")
        accountTest1.deposit(200.0)
        controller.insertAccount(accountTest1)

        val clientTest2: Client = Client("2", "Kalvin", "19/04/2003", "99122-2222")
        controller.insertClient(clientTest2)
        val accountTest2: Account = SavingsAccount(clientTest2, "kalvin@gmail.com", "12345")
        accountTest2.deposit(150.0)
        controller.insertKeyPix("kalvin@gmail.com", accountTest2)
        controller.insertKeyPix("(93) 99177-4499", accountTest2)
        controller.insertAccount(accountTest2)

        // Buttons
        val btnEnter: Button = findViewById(R.id.btn_enter)
        val btnOpenAccount: Button = findViewById(R.id.btn_open_account)

        //logical part
        btnEnter.setOnClickListener {
            // Validate credentials
            if (!validate()) {
                Snackbar.make(it, R.string.fields_login, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.secondary))
                    .show()
                return@setOnClickListener
            }

            // User current
            controller.setUserCurrent(controller.searchAccount(editEmail.text.toString()))

            // Start activity
            val home = Intent(this, HomeActivity::class.java)
            startActivity(home)
        }

        // OnClick
        btnOpenAccount.setOnClickListener {
            val openAccount = Intent(this, RegisterClientActivity::class.java)
            startActivity(openAccount)
        }
    }

    private fun validate(): Boolean {
        return editEmail.text.toString().isNotEmpty()
                && regx.checkPattern(editEmail.text.toString(), regx.emailRegx)
                && editPassword.text.toString().isNotEmpty()
                && editPassword.text.toString().length <= 8
                && checkLogin()
    }

    private fun checkLogin(): Boolean {
        return try {
            val account = controller.searchAccount(editEmail.text.toString())
            if (account.getPassword() != editPassword.text.toString()) {
                return false
            }
            true
        } catch (ex: AccountNotRegisteredException) {
            false
        }
    }
}