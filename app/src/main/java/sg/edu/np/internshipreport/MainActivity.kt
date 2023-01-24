package sg.edu.np.internshipreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sg.edu.np.internshipreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            FirebaseManager().loginUser(binding.emailAddress.text.toString(), binding.passwordInput.text.toString())


            val auth = FirebaseAuth.getInstance();
            auth.addAuthStateListener {

                if (it.currentUser != null) {
                    val intent = Intent(this, MainActivity2::class.java)
                    startActivity(intent)
                }
            }
        })

    }
}