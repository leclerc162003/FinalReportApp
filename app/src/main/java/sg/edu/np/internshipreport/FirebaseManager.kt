package sg.edu.np.internshipreport


import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirebaseManager {


    val auth = Firebase.auth

    fun loginUser(email : String, password : String) : Boolean {
        var loginSuccessful = false
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

}