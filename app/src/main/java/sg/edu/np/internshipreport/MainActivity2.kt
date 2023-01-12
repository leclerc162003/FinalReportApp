package sg.edu.np.internshipreport


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity2 : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        database = Firebase.database.reference

        database.child("Users").get().addOnSuccessListener {
            Log.d("firebase", "value ${it.value}")
        } .addOnFailureListener{
            Log.d("firebase", it.toString())
        }


    }
}