package sg.edu.np.internshipreport

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//this is the MainRepository, which is part of the "Model" portion
//this is responsible for business logic as you can see as well as calling backend APIs
//in this case, it is calling Firebase Authentication to login a user.

class MainRepository {
    val auth = Firebase.auth
    var loggedin = false

    fun signInUser(email : String, pass : String) : Boolean{
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "Successfully signed in with email link!")
                    val result = task.result
                    loggedin = true

                } else {
                    loggedin = false
                }
            }
        return loggedin
    }
}