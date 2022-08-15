package com.danielsouzza.poobank

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danielsouzza.poobank.controler.BankController
import com.danielsouzza.poobank.model.account.Account
import com.danielsouzza.poobank.model.account.InsufficientFundsException
import com.google.android.material.snackbar.Snackbar

class TransferEndActivity : AppCompatActivity() {

    private val controller = BankController()
    private lateinit var origin: Account
    private lateinit var trfValue: TextView
    private lateinit var destination: Account
    private lateinit var rvTransfer: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transfer_end)
        // Button finish
        val finishedButton: Button = findViewById(R.id.btn_transfer_finish)

        // Data transaction
        val numberDestine = intent.extras?.getString("numberAccount")!!
        val type = intent.extras?.getString("typeAccount")!!

        // List of object TrfItem
        val itemInfo = mutableListOf<TrfItem>()
        val adapter = TransferAdapter(itemInfo)

        // Destination and Origin
        origin = controller.getUserCurrent()
        destination = controller.searchAccount(numberDestine)

        // items to recycle view
        itemInfo.add(
            TrfItem(id = 1, textStringId = getString(R.string.text_name))
        )

        itemInfo.add(
            TrfItem(
                id = 2,
                textStringId = getString(
                    R.string.name_current_user,
                    destination.getHolder().getName()
                )
            )
        )

        itemInfo.add(
            TrfItem(id = 3, textStringId = getString(R.string.text_cpf))
        )

        itemInfo.add(
            TrfItem(
                id = 4,
                textStringId = getString(R.string.trf_cpf, destination.getHolder().getCpf())
            )
        )

        itemInfo.add(
            TrfItem(id = 5, textStringId = getString(R.string.trf_inst))
        )

        itemInfo.add(
            TrfItem(id = 6, textStringId = getString(R.string.app_name))
        )

        itemInfo.add(
            TrfItem(id = 7, textStringId = getString(R.string.trf_account, type))
        )

        itemInfo.add(
            TrfItem(id = 8, textStringId = getString(R.string.trf_account_number, numberDestine))
        )

        //Recycle View
        rvTransfer = findViewById(R.id.rv_trf)
        rvTransfer.adapter = adapter
        rvTransfer.layoutManager = GridLayoutManager(this, 2)

        // View statics
        val value = intent.extras?.getString("value")!! // value sent by previous view
        showInfo(value)

        // Transfer confirmation
        finishedButton.setOnClickListener {

            // Create/start and get elements of dialog
            val view = this.showDialogTrf(R.layout.dialog_password)
            val editPassword: EditText = view.findViewById(R.id.trfPassword)
            val btnContinue: Button = view.findViewById(R.id.trfBtnFinish)

            btnContinue.setOnClickListener {
                if (!checkPassword(editPassword.text.toString())) {
                    Snackbar.make(it, R.string.fields_password, Snackbar.LENGTH_SHORT)
                        .setBackgroundTint(resources.getColor(R.color.secondary))
                        .show()
                    return@setOnClickListener
                }

                // screen final
                val endScreen = Intent(this, FinishedTransactionActivity::class.java)

                try {
                    // transaction
                    controller.transfer(
                        origin.getNumber(),
                        destination.getNumber(),
                        value.toDouble()
                    )
                    endScreen.putExtra("condition", true)
                } catch (ex: InsufficientFundsException) {
                    endScreen.putExtra("condition", false)
                }

                // Regardless of what happens the final screen will start
                finish()
                startActivity(endScreen)
            }
        }
    }

    // criar pasta util para isso
    private fun checkPassword(password: String): Boolean {
        return password == origin.getPassword()
    }

    // create dialog
    private fun showDialogTrf(custom: Int): View {
        val view = layoutInflater.inflate(custom, null)
        val builder = AlertDialog.Builder(this, R.style.ThemeCustomDialog).setView(view)
        val alertDialog: AlertDialog = builder.create()
        alertDialog.window?.setGravity(Gravity.BOTTOM)
        alertDialog.show()
        return view
    }

    // criar pasta util para isso
    private fun showInfo(value: String) {
        trfValue = findViewById(R.id.trf_value)
        trfValue.text = getString(R.string.text_balance, value)
    }

    // Adapter of Recycle
    private inner class TransferAdapter(
        private val trfItem: List<TrfItem>
    ) : RecyclerView.Adapter<TransferViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransferViewHolder {
            val view = layoutInflater.inflate(R.layout.transfer_item, parent, false)
            return TransferViewHolder(view)
        }

        override fun onBindViewHolder(holder: TransferViewHolder, position: Int) {
            holder.bind(trfItem[position])
        }

        override fun getItemCount(): Int {
            return trfItem.size
        }
    }

    private inner class TransferViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: TrfItem) {
            val name: TextView = itemView.findViewById(R.id.trf_item)
            name.text = item.textStringId
        }
    }
}