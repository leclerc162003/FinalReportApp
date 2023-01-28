package sg.edu.np.internshipreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import sg.edu.np.internshipreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        getSupportActionBar()?.hide()
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            FirebaseManager().loginUser(binding.emailAddress.text.toString(), binding.passwordInput.text.toString())


            val auth = FirebaseAuth.getInstance();
            auth.addAuthStateListener {

                if (it.currentUser != null) {
                    Firebase.auth?.uid?.let { database.child("OTP").child(it).setValue(false)}
                    val intent = Intent(this, HomePage::class.java)
                    startActivity(intent)
                }
            }
        })

    }
}