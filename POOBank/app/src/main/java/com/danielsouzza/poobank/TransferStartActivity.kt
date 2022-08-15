package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.repository.account.AccountNotRegisteredException
import com.google.android.material.snackbar.Snackbar
import java.util.*

class TransferStartActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var option: Spinner
    private lateinit var userCurrent: Account
    private lateinit var inputValue: EditText
    private lateinit var buttonNext: Button
    private lateinit var textBalance: TextView
    private lateinit var accountNumber: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_start)

        // User current
        userCurrent = controller.getUserCurrent()

        // Elements
        option = findViewById(R.id.spinner)
        buttonNext = findViewById(R.id.btn_transfer_next)
        accountNumber = findViewById(R.id.account_number)
        inputValue = findViewById(R.id.input_value)
        textBalance = findViewById(R.id.trf_text_balance)

        // Item of spinner
        val options = resources.getStringArray(R.array.type_account)
        var tipo: String = options[0]

        // Adapter Spinner
        option.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, options)
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                tipo = options[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Show balance
        textBalance.text = userCurrent.getBalance().toString()

        buttonNext.setOnClickListener {
            when {
                !validate() -> {
                    Snackbar.make(it, R.string.fields_input, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(resources.getColor(R.color.secondary))
                        .show()
                    return@setOnClickListener
                }
                !checkAccount(tipo) -> {
                    Snackbar.make(it, R.string.fields_account, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(resources.getColor(R.color.secondary))
                        .show()
                    return@setOnClickListener
                }
            }

            // Go to finish transfer Activity
            val transferGetValueActivity = Intent(this, TransferEndActivity::class.java)
            transferGetValueActivity.putExtra("typeAccount", tipo)
            transferGetValueActivity.putExtra("numberAccount", accountNumber.text.toString())
            transferGetValueActivity.putExtra("value", inputValue.text.toString())
            finish()
            startActivity(transferGetValueActivity)
        }
    }

    private fun validate(): Boolean {
        return inputValue.text.toString().isNotEmpty()
                && accountNumber.text.toString().length == 1
    }

    private fun checkAccount(type: String): Boolean {
        return try {
            val account = controller.searchAccount(accountNumber.text.toString())
            if (account.getType() != type) {
                return false
            }
            true
        } catch (ex: AccountNotRegisteredException) {
            false
        }
    }
}