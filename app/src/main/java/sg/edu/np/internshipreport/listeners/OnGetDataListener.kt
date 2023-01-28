package sg.edu.np.internshipreport.listeners

import com.google.firebase.database.DataSnapshot

interface OnGetDataListener {
    fun onSuccess(dataSnapshot: DataSnapshot?)
    fun onStart()
    fun onFailure()
}