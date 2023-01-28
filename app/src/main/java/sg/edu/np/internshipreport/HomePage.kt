package sg.edu.np.internshipreport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import sg.edu.np.internshipreport.accounts.AccountsActivity
import sg.edu.np.internshipreport.accounts.AccountsAdapter
import sg.edu.np.internshipreport.classes.Account
import sg.edu.np.internshipreport.classes.Transaction
import sg.edu.np.internshipreport.databinding.ActivityMain2Binding
import sg.edu.np.internshipreport.firebase.FirebaseManager
import sg.edu.np.internshipreport.listeners.OnGetDataListener
import sg.edu.np.internshipreport.otp.OTPActivity
import sg.edu.np.internshipreport.transactions.TransactionsActivity
import sg.edu.np.internshipreport.transactions.TransactionsAdapter
import java.text.DecimalFormat


class HomePage : AppCompatActivity() {

    //Firebase Database References
    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference

    //Lists and Variables
    val transactionList: ArrayList<Transaction> = ArrayList<Transaction>()
    val accountList : ArrayList<Account> = ArrayList()
    var is2FA : Boolean = false

    //View binding and Recyclerview variables
    private lateinit var binding: ActivityMain2Binding
    private lateinit var accountsAdapter: AccountsAdapter
    private lateinit var transactionsAdapter: TransactionsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        getSupportActionBar()?.hide()
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        check2FACompleted()

        Firebase.auth.uid?.let {
            database.child("Users").child(it).get().addOnSuccessListener() {

                binding.welcomeName.text = "Welcome, ${it.child("name").value.toString()}"

                if(is2FA) {
                    val formatter = DecimalFormat("#,###,###")
                    binding.accountBalance.text = "$${formatter.format(it.child("accountBalance").value.toString().toLong())}.00 SGD"
                } else {
                    binding.accountBalance.text = "$ XXXX.XX XXX"
                }

            }.addOnFailureListener {
                Log.d("firebase", it.toString())
            }
        }

        val accountRecyclerView = binding.accountsView
        accountsAdapter = AccountsAdapter(this, accountList)
        val layoutManager = LinearLayoutManager(this)
        accountRecyclerView.layoutManager = layoutManager
        accountRecyclerView.adapter = accountsAdapter

        val transactionsRecyclerView = binding.transactionsView
        transactionsAdapter = TransactionsAdapter(this, transactionList)
        val layoutManager2 = LinearLayoutManager(this)
        transactionsRecyclerView.layoutManager = layoutManager2
        transactionsRecyclerView.adapter = transactionsAdapter

        getAccounts()

        getTransactions()






        binding.seeMore.setOnClickListener {
            val i = Intent(this, TransactionsActivity::class.java)
            this.startActivity(i)
        }

        binding.seeAccounts.setOnClickListener{
            if(is2FA) {
                val i = Intent(this, AccountsActivity::class.java)
                this.startActivity(i)
            } else{
                val i = Intent(this, OTPActivity::class.java)
                this.startActivity(i)
            }
        }


    }

    private fun getAccounts() {
        FirebaseManager().getAccounts(object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    for (account in dataSnapshot.children){
                        Log.d("key", account.key.toString())

                        database.child("Users").child(Firebase.auth.uid!!).child("transactions").child(account.key.toString())
                        val accountType = account.child("accountType").value.toString()
                        val accountBalance = account.child("accountBalance").value.toString()
                        Log.d("AccountType", accountType)
                        Log.d("AccountBalance", accountBalance)
                        accountList.add(Account(account.key.toString(), accountType,accountBalance))
                    }
                    accountsAdapter.notifyDataSetChanged()
                    Log.d("Size", accountList.size.toString())
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

    private fun getTransactions() {

        FirebaseManager().getTransactions(object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    for (transaction in dataSnapshot.children){
                        Log.d("key", transaction.key.toString())

                        database.child("Users").child(Firebase.auth.uid!!).child("transactions").child(transaction.key.toString())
                        val accountTo = transaction.child("AccountTo").value.toString()
                        val date = transaction.child("transactionDate").value.toString()
                        val amount = transaction.child("transactionAmount").value.toString()
                        transactionList.add(Transaction(transaction.key.toString(), accountTo,amount, date))
                    }
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

    override fun onResume() {
        super.onResume()
        check2FACompleted()
    }

    private fun check2FACompleted(){
        FirebaseManager().check2FA(object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                     is2FA = dataSnapshot.value.toString().toBoolean()
                }
            }
            override fun onStart() {
                Log.d(",","")
            }
            override fun onFailure() {
                TODO("Not yet implemented")
            }
        })
    }


}
