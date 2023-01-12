package sg.edu.np.internshipreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import sg.edu.np.internshipreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener(View.OnClickListener {
            if(FirebaseManager().loginUser(binding.emailAddress.text.toString(), binding.passwordInput.text.toString())){
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
        })

    }
}