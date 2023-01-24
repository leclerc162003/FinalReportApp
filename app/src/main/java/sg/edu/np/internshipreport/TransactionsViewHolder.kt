package sg.edu.np.internshipreport

import android.R
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import sg.edu.np.internshipreport.databinding.TransactionViewholderBinding


class TransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding = TransactionViewholderBinding.bind(itemView)
    var transactionDate: TextView? = binding.transactionDate
    var transactionName: TextView? = binding.transactionName
    var transactionAmount: TextView? = binding.transactionAmount
}