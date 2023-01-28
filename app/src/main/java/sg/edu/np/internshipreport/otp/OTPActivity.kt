package sg.edu.np.internshipreport.otp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import sg.edu.np.internshipreport.accounts.AccountsActivity
import sg.edu.np.internshipreport.databinding.ActivityOtpactivityBinding
import java.util.*

class OTPActivity : AppCompatActivity() {

    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference

    private lateinit var OTP : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_ACTION_BAR)
        getSupportActionBar()?.hide()
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding : ActivityOtpactivityBinding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getRandomNumberString()
        Firebase.auth?.uid?.let { database.child("OTP").child(it).child("OTP KEY").setValue(OTP)}

        binding.submitButton.setOnClickListener {
            if (binding.otp.text.isEmpty()){
                val dialogBuilder = AlertDialog.Builder(this)

                dialogBuilder.setTitle("Please Enter OTP")
                dialogBuilder.setMessage("Your OTP Field is empty.")
                dialogBuilder.setPositiveButton("Enter OTP") { dialog, whichButton ->
                    dialog.dismiss()
                }
                val b = dialogBuilder.create()
                b.show()

            }
            else if(binding.otp.text.toString() == OTP){
                Firebase.auth?.uid?.let { database.child("OTP").child(it).setValue(true)}
                val i = Intent(this, AccountsActivity::class.java)
                this.startActivity(i)
            }
            else{
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setTitle("Wrong OTP")
                dialogBuilder.setMessage("Your OTP entered is wrong.")
                dialogBuilder.setPositiveButton("Enter OTP") { dialog, whichButton ->
                    dialog.dismiss()
                }
                val b = dialogBuilder.create()
                b.show()
            }
        }




    }



    fun getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        val rnd = Random()
        val number: Int = rnd.nextInt(999999)

        OTP = String.format("%06d", number)

        Log.d("OTP",OTP)

    }
}