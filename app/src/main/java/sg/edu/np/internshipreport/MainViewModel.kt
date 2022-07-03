package sg.edu.np.internshipreport

import android.util.Log
import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData

class MainViewModel : BaseObservable(){

    //this is the ViewModel which handles some of the presentation logics
    //it is also responsible for calling the repository if needed.
    //In this case, you can see that the signIn() method calls the MainRepository
    //to sign in a user, if it fails it will set the failed object to Visible
    //which will set a textview in the View end to prompt the user login has failed.

    val repository : MainRepository = MainRepository()
    val failed = MutableLiveData<User>( User(View.INVISIBLE, 0))

    fun signIn(email : String, pass : String){
        if(repository.signInUser(email, pass)){
            failed.value = User(View.INVISIBLE, 0)
            //notifyPropertyChanged(BR.obj)
        }
        else {
            failed.value = User(View.VISIBLE, 0)
        }
        notifyPropertyChanged(BR.obj)
        notifyPropertyChanged(BR.viewModel)
        Log.d("show me", failed.value!!.loginSuccess.toString())
    }


}