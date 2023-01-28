package sg.edu.np.internshipreport.accounts

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sg.edu.np.internshipreport.databinding.AccountViewholderBinding
import sg.edu.np.internshipreport.databinding.TransactionViewholderBinding

class AccountsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding = AccountViewholderBinding.bind(itemView)
    var accountType: TextView? = binding.accountType
    var accountAmount: TextView? = binding.amount
}