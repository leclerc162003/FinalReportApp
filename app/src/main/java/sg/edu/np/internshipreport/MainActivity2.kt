package sg.edu.np.internshipreport

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import sg.edu.np.internshipreport.databinding.ActivityMain2Binding
import java.text.DecimalFormat


class MainActivity2 : AppCompatActivity() {

    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference

    val transactionList: ArrayList<Transaction> = ArrayList<Transaction>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMain2Binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        Firebase.auth.uid?.let {
            database.child("Users").child(it).get().addOnSuccessListener() {

                binding.welcomeName.text = "Welcome, ${it.child("name").value.toString()}"

                val formatter = DecimalFormat("#,###,###")
                binding.accountBalance.text =
                    "$${formatter.format(it.child("accountBalance").value.toString().toInt())}"

            }.addOnFailureListener {
                Log.d("firebase", it.toString())
            }
        }

        getTransactions(object : OnGetDataListener{
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    for (postSnapshot in dataSnapshot.children) {
                        val transaction = postSnapshot.getValue(Transaction::class.java)
                        if (transaction != null) {
                            transactionList.add(transaction)
                        }
                        Log.d("size", transactionList.size.toString())
                    }
                }
                Log.d("size", transactionList.size.toString())
                Log.d("item 1", transactionList.get(1).getId().toString())

            }

            override fun onStart() {
                Log.d("ONSTART", "Started");
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }


        })

        val transactionsRecyclerView = binding.transactionsView

        val transactionsAdapter = TransactionsAdapter(this, transactionList)
        val layoutManager = LinearLayoutManager(this)

        transactionsRecyclerView.layoutManager = layoutManager

        transactionsRecyclerView.adapter = transactionsAdapter



    }

    fun getTransactions(onGetDataListener: OnGetDataListener) {

        onGetDataListener.onStart()
            Firebase.auth.uid?.let {
                database.child("Users").child(it).child("transactions")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            onGetDataListener.onSuccess(snapshot);
//                            for (postSnapshot in snapshot.children) {
//                                val transaction = postSnapshot.getValue(Transaction::class.java)
//                                if (transaction != null) {
//                                    transactionList.add(transaction)
//                                }
//                                Log.d("size", transactionList.size.toString())
//                            }
//                            Log.d("size", transactionList.size.toString())

                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
            }

        }



}
