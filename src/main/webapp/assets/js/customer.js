class Customer {
    constructor (id, fullName, email, phone, address, balance, deleted) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.balance = balance; 
        this.deleted = deleted;
    }
    setId = function(id) {
        this.id = id;
    }
    getId = function() {
        return this.id;
    }
    setFullName = function(fullName) {
        this.fullName = fullName;
    }
    getFullName = function() {
        return this.fullName;
    }
    setEmail = function(email) {
        this.email = email;
    }
    getEmail = function() {
        return this.email;
    }
    setPhone = function(phone) {
        this.phone = phone;
    }
    getPhone = function() {
        return this.phone;
    }
    setAddress = function(address) {
        this.address = address;
    }
    getAddress = function() {
        return this.address;
    }
    setBalance = function(balance) {
        this.balance = balance;
    }
    getBalance = function() {
        return this.balance;
    }
    setDeleted = function(deleted) {
        this.deleted = deleted;
    }
    getDeleted = function() {
        return this.deleted;
    }
}