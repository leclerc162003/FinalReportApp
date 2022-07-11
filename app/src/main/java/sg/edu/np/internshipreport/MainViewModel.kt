package sg.edu.np.internshipreport

import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.view.isVisible
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter

class MainViewModel : BaseObservable(){

    //this is the ViewModel which handles some of the presentation logics
    //it is also responsible for calling the repository if needed.
    //In this case, you can see that the signIn() method calls the MainRepository
    //to sign in a user, if it fails it will set the failed object to Visible
    //which will set a textview in the View end to prompt the user login has failed.

    val repository : MainRepository = MainRepository()
    var visibility : Boolean = false

    fun signIn(email : String, pass : String){

        visibility = !repository.signInUser(email, pass)

        if(repository.signInUser(email, pass)){
            visibility = false
            notifyPropertyChanged(BR.aVisibility)
        } else{
            visibility = true
            notifyPropertyChanged(BR.aVisibility)
        }
        Log.d("show me", visibility.toString())
    }

    @Bindable
    fun getAVisibility(): Int {
        return if (visibility) {
            VISIBLE
        } else {
            GONE
        }
    }



}