package sg.edu.np.internshipreport.classes

class Account(id : String ="", accountType : String = "", accountBalance : String ="") {

    private var id : String = id
    private var accountType : String = accountType
    private var accountBalance : String = accountBalance

    fun getid() : String { return id }
    fun getaccountType() : String { return accountType }
    fun getaccountBalance() : String {return accountBalance}

    fun setId(id : String){this.id = id}
    fun setaccountType(accountType: String) {this.accountType = accountType}
    fun setaccountBalance(accountBalance: String){this.accountBalance = accountBalance}
}