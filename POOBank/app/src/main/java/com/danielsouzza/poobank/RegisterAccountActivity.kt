package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.SavingsAccount
import com.danielsouzza.poobank.model.client.Client
import com.danielsouzza.poobank.regex.Regx
import com.google.android.material.snackbar.Snackbar

class RegisterAccountActivity : AppCompatActivity() {

    private val regex: Regx = Regx()
    private val controller: BankController = BankController()
    private lateinit var editEmail: EditText
    private lateinit var editPassword1: EditText
    private lateinit var editPassword2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_account)

        val btnFinished: Button = findViewById(R.id.btn_finished)
        editEmail = findViewById(R.id.edit_email)
        editPassword1 = findViewById(R.id.edit_password1)
        editPassword2 = findViewById(R.id.edit_password2)

        btnFinished.setOnClickListener {
            if (!validade()) {
                Snackbar.make(it, R.string.fields_input, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.secondary)).show()
                return@setOnClickListener
            }

            // Get data from editView
            val client = Client(
                intent.extras?.getString("cpf")!!,
                intent.extras?.getString("name")!!,
                intent.extras?.getString("birth")!!,
                intent.extras?.getString("phone")!!
            )

            val account = SavingsAccount(
                client,
                editEmail.text.toString(),
                editPassword1.text.toString()
            )

            // Register user
            controller.insertClient(client)
            controller.insertAccount(account)

            val mainActivity = Intent(this, MainActivity::class.java)
            mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(mainActivity)
        }
    }

    private fun validade(): Boolean {
        return editEmail.text.toString().isNotEmpty()
                && regex.checkPattern(editEmail.text.toString(), regex.emailRegx)
                && editPassword1.text.toString().isNotEmpty()
                && regex.checkPattern(editPassword1.text.toString(), regex.passwordRegx)
                && editPassword2.text.toString().isNotEmpty()
                && regex.checkPattern(editPassword2.text.toString(), regex.passwordRegx)
                && editPassword1.text.toString() == editPassword2.text.toString()
    }
}