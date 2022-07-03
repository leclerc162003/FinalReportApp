package sg.edu.np.internshipreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import sg.edu.np.internshipreport.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //this is the activity, this is the "view" part of the Model-View-ViewModel (MVVM) part
        //of the architecture.

        val binding : ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val viewModel = MainViewModel()
        setContentView(binding.root)
        binding.viewModel = viewModel

        //it uses databinding to bind with the XML file (activity_main.xml) unlike the traditional way
        //traditional way
        var textView : TextView = findViewById(R.id.textView)
        //new way
        var textView2 : TextView = binding.textView

        binding.button.setOnClickListener(View.OnClickListener {
            viewModel.signIn(binding.editTextTextEmailAddress.text.toString(), binding.editTextTextPassword.text.toString())
        })

        //as you can see this activity is kept as simple as possible for easier testing
        //compared to traditional architectures, this view will be filled with other logics and methods
        //like to the backend services
    }
}