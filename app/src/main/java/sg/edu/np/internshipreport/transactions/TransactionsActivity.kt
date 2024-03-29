package sg.edu.np.internshipreport.transactions

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import sg.edu.np.internshipreport.HomePage
import sg.edu.np.internshipreport.classes.Transaction
import sg.edu.np.internshipreport.databinding.ActivityTransactionsBinding
import sg.edu.np.internshipreport.firebase.FirebaseManager
import sg.edu.np.internshipreport.listeners.OnGetDataListener

class TransactionsActivity : AppCompatActivity() {

    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference

    val transactionList: ArrayList<Transaction> = ArrayList<Transaction>()

    private lateinit var transactionsAdapter : TransactionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        getSupportActionBar()?.hide()
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityTransactionsBinding = ActivityTransactionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val transactionsRecyclerView = binding.recyclerView

        transactionsAdapter = TransactionsAdapter(this, transactionList)
        val layoutManager = LinearLayoutManager(this)

        transactionsRecyclerView.layoutManager = layoutManager

        transactionsRecyclerView.adapter = transactionsAdapter

        getTransactions()


        binding.load.setOnClickListener {
            getRemainingTransactions()

        }

        binding.backButton.setOnClickListener {
            val i = Intent(this, HomePage::class.java)
            this.startActivity(i)
        }


    }




    private fun getTransactions() {

        FirebaseManager().getTransactions(object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    for (transaction in dataSnapshot.children){

                        database.child("Users").child(Firebase.auth.uid!!).child("transactions").child(transaction.key.toString())
                        val accountTo = transaction.child("AccountTo").value.toString()
                        val date = transaction.child("transactionDate").value.toString()
                        val amount = transaction.child("transactionAmount").value.toString()
                        transactionList.add(Transaction(transaction.key.toString(), accountTo,amount, date))
                    }
                }
                Log.d("First Load", "Loaded")
                transactionsAdapter.notifyDataSetChanged()
            }

            override fun onStart() {
                Log.d("ONSTART", "Started");
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }


        })

    }

    private fun getRemainingTransactions() {

        FirebaseManager().getRemainingTransactions(object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    for (transaction in dataSnapshot.children){
                        database.child("Users").child(Firebase.auth.uid!!).child("transactions").child(transaction.key.toString())
                        val accountTo = transaction.child("AccountTo").value.toString()
                        val date = transaction.child("transactionDate").value.toString()
                        val amount = transaction.child("transactionAmount").value.toString()
                        transactionList.add(Transaction(transaction.key.toString(), accountTo,amount, date))
                    }
                    Log.d("second load", "Updated")
                    transactionsAdapter.notifyDataSetChanged()
                }

            }

            override fun onStart() {
                Log.d("ONSTART", "Started");
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }


        })

    }

}