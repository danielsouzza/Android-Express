package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.google.android.material.snackbar.Snackbar

class DepositActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var edittextValue: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deposit)

        val userCurrent = controller.getUserCurrent()
        edittextValue = findViewById(R.id.edit_deposit)

        val btnDeposit: Button = findViewById(R.id.btn_deposit)

        btnDeposit.setOnClickListener {
            if (edittextValue.text.toString().isEmpty()) {
                Snackbar.make(it, R.string.fields_input, Snackbar.LENGTH_SHORT)
                    .setBackgroundTint(resources.getColor(R.color.secondary))
                    .show()
                return@setOnClickListener
            }

            controller.deposit(userCurrent.getNumber(), edittextValue.text.toString().toDouble())
            val endScreen = Intent(this, FinishedTransactionActivity::class.java)
            endScreen.putExtra("condition", true)
            finish()
            startActivity(endScreen)
        }
    }
}