package sg.edu.np.internshipreport.transactions

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sg.edu.np.internshipreport.R
import sg.edu.np.internshipreport.classes.Transaction
import java.util.ArrayList

class TransactionsAdapter(context : Context, transactionList : ArrayList<Transaction>) : RecyclerView.Adapter<TransactionsViewHolder>() {

    val context = context

    val list = transactionList


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.transaction_viewholder, parent,false)
        return TransactionsViewHolder(item)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val transaction = list.get(position)
        holder.transactionAmount?.text = "${transaction.gettransactionAmount()}.00 SGD"
        holder.transactionName?.text = transaction.getAccountTo()
        holder.transactionDate?.text = transaction.gettransactionDate().toString()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}