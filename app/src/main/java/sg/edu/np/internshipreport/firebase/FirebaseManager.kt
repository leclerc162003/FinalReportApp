package sg.edu.np.internshipreport.firebase


import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import sg.edu.np.internshipreport.classes.Transaction
import sg.edu.np.internshipreport.listeners.OnGetDataListener

class FirebaseManager {
    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference
    private val auth = Firebase.auth
    private var loginSuccessful = false

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

    fun getAccounts(onGetDataListener: OnGetDataListener) {

        onGetDataListener.onStart()

        database.child("Users").child(Firebase.auth.uid!!).child("accounts").get().addOnSuccessListener {
            onGetDataListener.onSuccess(it)
        }
    }

    fun getTransactions(onGetDataListener: OnGetDataListener) {

        onGetDataListener.onStart()

        database.child("Users").child(Firebase.auth.uid!!).child("transactions").orderByKey().limitToFirst(5).get().addOnSuccessListener {
            onGetDataListener.onSuccess(it)
        }
    }

    fun check2FA(onGetDataListener: OnGetDataListener) {

        onGetDataListener.onStart()

        database.child("OTP").child(Firebase.auth.uid!!).get().addOnSuccessListener {
            onGetDataListener.onSuccess(it)
        }
    }

    fun getRemainingTransactions(onGetDataListener: OnGetDataListener) {

        onGetDataListener.onStart()

        database.child("Users").child(Firebase.auth.uid!!).child("transactions").orderByKey().limitToLast(5).get().addOnSuccessListener {
            onGetDataListener.onSuccess(it)
        }
    }

}