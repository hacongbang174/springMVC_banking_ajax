class AppBase {
    static DOMAIN_SERVER = 'http://localhost:8090'
    static API_SERVER = this.DOMAIN_SERVER + '/api'

    static API_CUSTOMER = this.API_SERVER + '/customers'
    static API_CREATE_CUSTOMER = this.API_CUSTOMER + '/create'
    static API_UPDATE_CUSTOMER = this.API_CUSTOMER + '/edit'
    static API_DELETE_CUSTOMER = this.API_CUSTOMER + '/delete'

    static API_DEPOSIT = this.API_CUSTOMER + '/deposits'
    static API_WITHDRAW = this.API_CUSTOMER + '/withdraws'
    static API_TRANSFER = this.API_CUSTOMER + '/transfers'

    static API_HISTORY_DEPOSIT = this.API_CUSTOMER + '/historyDeposits'
    static API_HISTORY_WITHDRAW = this.API_CUSTOMER + '/historyWithdraws'
    static API_HISTORY_TRANSFER = this.API_CUSTOMER + '/historyTransfers'
}