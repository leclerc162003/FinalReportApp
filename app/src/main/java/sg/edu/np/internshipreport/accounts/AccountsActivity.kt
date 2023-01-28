package sg.edu.np.internshipreport.accounts

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
import sg.edu.np.internshipreport.classes.Account
import sg.edu.np.internshipreport.databinding.ActivityAccountsBinding
import sg.edu.np.internshipreport.firebase.FirebaseManager
import sg.edu.np.internshipreport.listeners.OnGetDataListener

class AccountsActivity : AppCompatActivity() {

    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference
    val accountList : ArrayList<Account> = ArrayList()

    private lateinit var accountsAdapter : AccountsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        getSupportActionBar()?.hide()
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityAccountsBinding = ActivityAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val accountRecyclerView = binding.accountsView
        accountsAdapter = AccountsAdapter(this, accountList)
        val layoutManager = LinearLayoutManager(this)
        accountRecyclerView.layoutManager = layoutManager
        accountRecyclerView.adapter = accountsAdapter

        getAccounts()



        binding.backButton.setOnClickListener {
            val i = Intent(this, HomePage::class.java)
            this.startActivity(i)
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
                    Log.d("Size", accountList.size.toString())
                    accountsAdapter.notifyDataSetChanged()
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