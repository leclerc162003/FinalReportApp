package sg.edu.np.internshipreport

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class AccountsAdapter(context : Context, accountList : ArrayList<Account>) : RecyclerView.Adapter<AccountsViewHolder>() {

    private var firebase: FirebaseDatabase =
        FirebaseDatabase.getInstance("https://internship-81576-default-rtdb.asia-southeast1.firebasedatabase.app/")
    private var database: DatabaseReference = firebase.reference

    val context = context

    val list = accountList

    private var is2FA : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountsViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.account_viewholder, parent,false)
        return AccountsViewHolder(item)
    }

    override fun onBindViewHolder(holder: AccountsViewHolder, @SuppressLint("RecyclerView") position: Int) {

        Firebase.auth.uid?.let { database.child("OTP").child(it).get() }

        getAccounts(object : OnGetDataListener {
            override fun onSuccess(dataSnapshot: DataSnapshot?) {
                if (dataSnapshot != null) {
                    is2FA =  dataSnapshot.value.toString().toBoolean()
                    val account = list.get(position)
                    if(is2FA)
                        holder.accountAmount?.text = "$${account.getaccountBalance()}.00 SGD"
                    else
                        holder.accountAmount?.text = "$ XXXX.XX SGD"

                    holder.accountType?.text = account.getaccountType()
                }
            }

            override fun onStart() {
                Log.d("yes", "yes")
            }

            override fun onFailure() {
                TODO("Not yet implemented")
            }

        })

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getAccounts(onGetDataListener: OnGetDataListener) {

        onGetDataListener.onStart()

        database.child("OTP").child(Firebase.auth.uid!!).get().addOnSuccessListener {
            onGetDataListener.onSuccess(it)
        }
    }
}