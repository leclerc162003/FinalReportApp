package sg.edu.np.internshipreport.classes

import java.util.*

class Transaction(id : String ="", AccountTo : String = "", transactionAmount : String ="", transactionDate : String ="") {

    private var id : String = id
    private var AccountTo : String = AccountTo
    private var transactionAmount : String = transactionAmount
    private var transactionDate : String = transactionDate

    fun getId() : String{return id}

    fun getAccountTo() : String {return AccountTo}

    fun gettransactionAmount() : String {return transactionAmount}

    fun gettransactionDate() : String {return transactionDate
    }

    fun setAccountTo(AccountTo: String){
        this.AccountTo = AccountTo
    }

    fun settransactionAmount(transactionAmount: Long){
        this.transactionAmount = transactionAmount.toString()
    }

    fun settransactionDate(transactionDate: String){
        this.transactionDate = transactionDate
    }

    fun setId(id: String){
        this.id = id
    }

}