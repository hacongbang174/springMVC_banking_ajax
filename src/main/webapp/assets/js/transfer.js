class Transfer {
    constructor (id, fees, feesAmount, transactionAmount, transferAmount, senderId, recipientId, deleted) {
        this.id = id;
        this.fees = fees;
        this.feesAmount = feesAmount;
        this.transactionAmount = transactionAmount;
        this.transferAmount = transferAmount;
        this.senderId = senderId;
        this. recipientId = recipientId;
        this.deleted = deleted;
    }
}