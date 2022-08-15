package com.danielsouzza.poobank

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.repository.account.KeyPixNotRegisteredException

class RegisterKeyPixActivity : AppCompatActivity() {

    private lateinit var btnConfirm: Button
    private lateinit var editTextKey: EditText
    private lateinit var spinnerKey: Spinner
    private val controller = BankController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_key_pix)

        val currentUser = controller.getUserCurrent() // User current


        try {
            spinnerKey = findViewById(R.id.key_spinner)
            editTextKey = findViewById(R.id.edit_key)

            var selected: String
            val keys = controller.getAllKeys(currentUser.getNumber()).keys // Get all key related to user
            val options = resources.getStringArray(R.array.type_key).filter {
                it !in keys
            }

            // Adapter Spinner
            spinnerKey.adapter = ArrayAdapter<String>(this, R.layout.spinner_item, options)
            spinnerKey.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if(options[position] == getString(R.string.text_random)){
                        editTextKey.isEnabled = false
                        editTextKey.setText(getString(R.string.key_random,"1234thaioy4hhqr4"))
                    }else{
                        editTextKey.setText("")
                        editTextKey.isEnabled = true
                    }
                    selected = options[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                   selected = options[0]
                }

            }
            btnConfirm = findViewById(R.id.btn_confirm_key) //Button finish

        }catch (ex: KeyPixNotRegisteredException){

        }
    }
}