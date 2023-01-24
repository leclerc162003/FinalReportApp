package sg.edu.np.internshipreport


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class FirebaseManager {
    private lateinit var firebase : FirebaseDatabase
    private val auth = Firebase.auth
    private var loginSuccessful = false
    private lateinit var database : DatabaseReference

    fun loginUser(email : String, password : String) : Boolean {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    loginSuccessful = true
                    Log.d("Login", "Successful")
                }
                else{
                    loginSuccessful = false
                    Log.d("Login", "unSuccessful")
                }
            }

        return loginSuccessful
    }

    fun getTransactions() : ArrayList<Transaction>{

        firebase = FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")

        database = firebase.reference

        val transactionList : ArrayList<Transaction> = ArrayList<Transaction>()

        database.child("Users").child(Firebase.auth.uid!!).child("transactions").orderByKey().limitToFirst(5).get().addOnSuccessListener {
            for (transaction in it.children){
                Log.d("key", transaction.key.toString())




                database.child("Users").child(Firebase.auth.uid!!).child("transactions").child(transaction.key.toString())
                val accountTo = transaction.child("AccountTo").value.toString()
                val date = transaction.child("transactionAmount").value.toString()
                val amount = transaction.child("transactionDate").value.toString()
                Log.d("accountTo", accountTo)
                Log.d("Date", date)
                Log.d("amount", amount)
                transactionList.add(Transaction(transaction.key.toString(), accountTo,amount, date))
            }
        }

        Log.d("size", transactionList.size.toString())

        return transactionList
    }





}