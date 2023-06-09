const page = {
    url: {
        getAllCustomers: AppBase.API_CUSTOMER,
        doCreate: AppBase.API_CREATE_CUSTOMER,
        doUpdate: AppBase.API_UPDATE_CUSTOMER,
        doDelete: AppBase.API_DELETE_CUSTOMER,
        doDeposit: AppBase.API_DEPOSIT,
        doWithdraw: AppBase.API_WITHDRAW,
        doTransfer: AppBase.API_TRANSFER,
        historyDeposit: AppBase.API_HISTORY_DEPOSIT,
        historyWithdraw: AppBase.API_HISTORY_WITHDRAW,
        historyTransfer: AppBase.API_HISTORY_TRANSFER,
    },
    elements: {},
    loadData: {},
    commands: {},
    dialogs: {
        elements: {},
        commands: {},
    }
}


page.elements.tblCustomerBody = $('#tbCustomer tbody')
page.elements.tblDepositBody = $('#tbHistoryDeposit tbody')
page.elements.tblWithdrawBody = $('#tbHistoryWithdraw tbody')
page.elements.tblTransferBody = $('#tbHistoryTransfer tbody')


page.dialogs.elements.modalCreate = $('#mdCreate')
page.dialogs.elements.errorCreateArea = $('#mdCreate .error-area')
page.dialogs.elements.fullNameCre = $('#fullNameCre')
page.dialogs.elements.emailCre = $('#emailCre')
page.dialogs.elements.phoneCre = $('#phoneCre')
page.dialogs.elements.addressCre = $('#addressCre')
page.dialogs.elements.btnCreate = $('#btnCreate')

page.dialogs.elements.modalUpdate = $('#mdUpdate')
page.dialogs.elements.errorUpdateArea = $('#mdUpdate .error-area')
page.dialogs.elements.fullNameUp = $('#fullNameUp')
page.dialogs.elements.emailUp = $('#emailUp')
page.dialogs.elements.phoneUp = $('#phoneUp')
page.dialogs.elements.addressUp = $('#addressUp')
page.dialogs.elements.btnUpdate = $('#btnUpdate')

page.dialogs.elements.modalDeposit = $('#mdDeposit');
page.dialogs.elements.errorDepositArea = $('#mdDeposit .error-area')
page.dialogs.elements.fullNameDepo = $('#fullNameDepo');
page.dialogs.elements.emailDepo = $('#emailDepo');
page.dialogs.elements.phoneDepo = $('#phoneDepo');
page.dialogs.elements.addressDepo = $('#addressDepo');
page.dialogs.elements.amountDepo = $('#amountDepo');
page.dialogs.elements.transactionAmountDepo = $('#transactionAmountDepo');
page.dialogs.elements.btnDeposit = $('#btnDeposit')

page.dialogs.elements.modalWithdraw = $('#mdWithdraw');
page.dialogs.elements.errorWithdrawArea = $('#mdWithdraw .error-area')
page.dialogs.elements.fullNameWd = $('#fullNameWd');
page.dialogs.elements.emailWd = $('#emailWd');
page.dialogs.elements.phoneWd = $('#phoneWd');
page.dialogs.elements.addressWd = $('#addressWd');
page.dialogs.elements.amountWd = $('#amountWd');
page.dialogs.elements.transactionAmountWd = $('#transactionAmountWd');
page.dialogs.elements.btnWithdraw = $('#btnWithdraw')

page.dialogs.elements.modalTransfer = $('#mdTransfer');
page.dialogs.elements.errorTransferArea = $('#mdTransfer .error-area')
page.dialogs.elements.senderIdTf = $('#senderIdTf');
page.dialogs.elements.senderNameTf = $('#senderNameTf');
page.dialogs.elements.emailTf = $('#emailTf');
page.dialogs.elements.balanceTf = $('#balanceTf');
page.dialogs.elements.transfer = $('#transfer');
page.dialogs.elements.fees = $('#fees');
page.dialogs.elements.total = $('#total');
page.dialogs.elements.btnTransfer = $('#btnTransfer')
page.dialogs.elements.recipientSelect = $('#mdTransfer #recipientSelect')


page.dialogs.elements.modalHistoryDeposit = $('#mdHistoryDeposit')
page.dialogs.elements.btnHistoryDeposit = $('#btnHistoryDeposit')

page.dialogs.elements.modalHistoryWithdraw = $('#mdHistoryWithdraw')
page.dialogs.elements.btnHistoryWithdraw = $('#btnHistoryWithdraw')

page.dialogs.elements.modalHistoryTransfer = $('#mdHistoryTransfer')
page.dialogs.elements.btnHistoryTransfer = $('#btnHistoryTransfer')

let customerId = 0;

page.commands.renderCustomer = (obj) => {
    return `<tr id='tr_${obj.id}'>
            <td>${obj.id}</td>
            <td>${obj.fullName}</td>
            <td>${obj.email}</td>
            <td>${obj.phone}</td>
            <td>${obj.address}</td>
            <td>${obj.balance}</td>
            <td class="text-center">
                <button class='btn btn-outline-secondary deposit' data-id='${obj.id}'>
                    <i class="fa-solid fa-circle-plus"></i>
                </button>
                <button class='btn btn-outline-success withdraw' data-id='${obj.id}'>
                    <i class="fas fa-minus-circle"></i>
                </button>
                <button class='btn btn-outline-warning transfer' data-id='${obj.id}'>
                    <i class="fas fa-exchange-alt"></i>
                </button>
                <button class='btn btn-outline-primary edit' data-id='${obj.id}'>
                    <i class="fas fa-user-edit"></i>
                </button>
                <button class='btn btn-outline-danger delete' data-id='${obj.id}'>\
                    <i class="fas fa-ban"></i>
                </button>
            </td>
        </tr>`
        ;
}

page.commands.renderDeposit = (obj) => {
    return `<tr id='trDP_${obj.id}'>
            <td>${obj.id}</td>
            <td>${obj.customer.id}</td>
            <td>${obj.customer.fullName}</td>
            <td>${obj.transactionAmount}</td>
        </tr>`
        ;
}

page.commands.renderWithdraw = (obj) => {
    return `<tr id='trDP_${obj.id}'>
            <td>${obj.id}</td>
            <td>${obj.customer.id}</td>
            <td>${obj.customer.fullName}</td>
            <td>${obj.transactionAmount}</td>
        </tr>`
        ;
}

page.commands.renderTransfer = (obj) => {
    return `<tr id='trDP_${obj.id}'>
            <td>${obj.id}</td>
            <td>${obj.sender.id}</td>
            <td>${obj.recipient.id}</td>
            <td>${obj.fees}</td>
            <td>${obj.feesAmount}</td>
            <td>${obj.transferAmount}</td>
            <td>${obj.transactionAmount}</td>
        </tr>`
        ;
}

page.commands.getAllCustomers = () => {
    $.ajax({
        type: 'GET',
        url: page.url.getAllCustomers
    })
        .done((data) => {
            $.each(data, (index, item) => {
                let str = page.commands.renderCustomer(item);
                page.elements.tblCustomerBody.append(str);
            })
            page.commands.addEventCreate();
            page.commands.addEventEdit();
            page.commands.addEventDelete();
            page.commands.addEventDeposit();
            page.commands.addEventWithdraw();
            page.commands.addEventTransfer();
            // addEventHistoryDeposit();
        })
}

page.commands.getAllDeposit = () => {
    $.ajax({
        type: 'GET',
        url: page.url.historyDeposit
    })
        .done((data) => {
            let str = '';
            $.each(data, (index, item) => {
                str += page.commands.renderDeposit(item);
            })
            page.elements.tblDepositBody.empty(str).append(str)
            page.dialogs.elements.modalHistoryDeposit.modal('show');
        })
}

page.commands.getAllWithdraw = () => {
    $.ajax({
        type: 'GET',
        url: page.url.historyWithdraw
    })
        .done((data) => {
            let str = '';
            $.each(data, (index, item) => {
                str += page.commands.renderWithdraw(item);
            })
            page.elements.tblWithdrawBody.empty(str).append(str)
            page.dialogs.elements.modalHistoryWithdraw.modal('show');
        })
}

page.commands.getAllTransfer = () => {
    $.ajax({
        type: 'GET',
        url: page.url.historyTransfer
    })
        .done((data) => {
            let str = '';
            $.each(data, (index, item) => {
                str += page.commands.renderTransfer(item);
            })
            page.elements.tblTransferBody.empty(str).append(str)
            page.dialogs.elements.modalHistoryTransfer.modal('show');
        })

}


page.commands.getCustomerById = (customerId) => {
    // return customers.find(item => item.id == customerId);
    return $.ajax({
        type: 'GET',
        url: page.url.getAllCustomers + '/' + customerId
    })
}


page.commands.getRecipients = (customerId) => {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: 'GET',
        url: page.url.getAllCustomers,
    })
        .done((data) => {

            page.dialogs.elements.recipientSelect.empty();
            // page.dialogs.elements.recipientSelect.append($('<option>').val('').text('Select recipient'));

            $.each(data, function (index, item) {
                if (item.id !== customerId) {
                    page.dialogs.elements.recipientSelect.append($('<option>').val(item.id).text(item.id + ' - ' + item.fullName));
                }
            })

        })
        .fail((jqXHR, textStatus, errorThrown) => {
            console.log('Error: ' + textStatus + ' ' + errorThrown);
        })
}


page.commands.addEventCreate = () => {
    $('.create').on('click', function () {
        page.dialogs.elements.fullNameCre.val();
        page.dialogs.elements.emailCre.val();
        page.dialogs.elements.phoneCre.val();
        page.dialogs.elements.addressCre.val();
        page.dialogs.elements.modalCreate.modal('show');

        page.dialogs.elements.fullNameCre.val('');
        page.dialogs.elements.emailCre.val('');
        page.dialogs.elements.phoneCre.val('');
        page.dialogs.elements.addressCre.val('');
        page.dialogs.elements.errorCreateArea.empty();

    });
}

page.commands.addEventDeposit = () => {
    $('.deposit').on('click', function () {
        customerId = $(this).data('id');
        page.commands.getCustomerById(customerId).then((data) => {

            if (data !== {}) {
                page.dialogs.elements.fullNameDepo.val(data.fullName);
                page.dialogs.elements.emailDepo.val(data.email);
                page.dialogs.elements.phoneDepo.val(data.phone);
                page.dialogs.elements.addressDepo.val(data.address);
                page.dialogs.elements.amountDepo.val(data.balance);
                page.dialogs.elements.modalDeposit.modal('show');

                page.dialogs.elements.transactionAmountDepo.val('');
                page.dialogs.elements.errorDepositArea.empty();
            } else {
                alert('Customer not found');
            }
        })
    })
}

page.commands.addEventWithdraw = () => {
    $('.withdraw').on('click', function () {
        customerId = $(this).data('id');
        page.commands.getCustomerById(customerId).then((data) => {

            if (data !== {}) {
                page.dialogs.elements.fullNameWd.val(data.fullName);
                page.dialogs.elements.emailWd.val(data.email);
                page.dialogs.elements.phoneWd.val(data.phone);
                page.dialogs.elements.addressWd.val(data.address);
                page.dialogs.elements.amountWd.val(data.balance);
                page.dialogs.elements.modalWithdraw.modal('show');

                page.dialogs.elements.transactionAmountWd.val('');
                page.dialogs.elements.errorWithdrawArea.empty();
            } else {
                alert('Customer not found');
            }
        })
    })
}

page.commands.addEventTransfer = () => {

    $('.transfer').on('click', function () {
        customerId = $(this).data('id');
        page.commands.getCustomerById(customerId).then((data) => {

            if (data !== {}) {
                page.dialogs.elements.senderIdTf.val(data.id);
                page.dialogs.elements.senderNameTf.val(data.fullName);
                page.dialogs.elements.emailTf.val(data.email);
                page.dialogs.elements.balanceTf.val(data.balance);
                page.commands.getRecipients(customerId);
                page.dialogs.elements.modalTransfer.modal('show');

                page.dialogs.elements.transfer.val('');
                page.dialogs.elements.total.val('');
                page.dialogs.elements.errorTransferArea.empty();

            } else {
                alert('Customer not found');
            }
        })
    })
}


page.commands.addEventEdit = () => {
    $('.edit').on('click', function () {
        customerId = $(this).data('id');
        page.commands.getCustomerById(customerId).then((data) => {
            console.log(data);

            if (data !== {}) {
                page.dialogs.elements.fullNameUp.val(data.fullName);
                page.dialogs.elements.emailUp.val(data.email);
                page.dialogs.elements.phoneUp.val(data.phone);
                page.dialogs.elements.addressUp.val(data.address);
                page.dialogs.elements.modalUpdate.modal('show');

                page.dialogs.elements.errorUpdateArea.empty();

            } else {
                alert('Customer not found');
            }
        })
    })
}

page.commands.addEventDelete = () => {
    $('.delete').on('click', function () {
        customerId = $(this).data('id');
        Swal.fire({
            title: 'Are you sure?',
            text: "You won't be able to revert this!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085D6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
        }).then((result) => {
            if (result.isConfirmed) {
                page.dialogs.commands.doDelete(customerId);
            }
        })
    })
}
page.dialogs.commands.doDelete = (customerId) => {
    $.ajax({
        type: 'DELETE',
        //tên API
        url: page.url.doDelete + '/' + customerId,
        //xử lý khi thành công
        data: {
            deleted: true
        }
    })
        .done(() => {
            $('#tr_' + customerId).remove();
            Swal.fire({
                position: 'top-end',
                icon: 'success',
                title: 'Customer has been deleted',
                showConfirmButton: false,
                timer: 1500
            })
        })
}

page.dialogs.elements.handleCreate = () => {
    let fullName = page.dialogs.elements.fullNameCre.val();
    let email = page.dialogs.elements.emailCre.val();
    let phone = page.dialogs.elements.phoneCre.val();
    let address = page.dialogs.elements.addressCre.val();
    let customer = {
        fullName,
        email,
        phone,
        address,
    };
    page.dialogs.commands.doCreate(customer);
}

page.dialogs.commands.doCreate = (customer) => {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "POST",
        //tên API
        url: page.url.doCreate,
        //xử lý khi thành công
        data: JSON.stringify(customer)
    })
        .done((data) => {
            customer = data;
            let str = page.commands.renderCustomer(customer);
            page.elements.tblCustomerBody.append(str);
            page.dialogs.elements.modalCreate.modal('hide');

            page.dialogs.elements.fullNameCre.val('');
            page.dialogs.elements.emailCre.val('');
            page.dialogs.elements.phoneCre.val('');
            page.dialogs.elements.addressCre.val('');
            page.dialogs.elements.errorCreateArea.empty();

            page.commands.addEventEdit();
            page.commands.addEventDelete();
            page.commands.addEventDeposit();
            page.commands.addEventWithdraw();
            page.commands.addEventTransfer();
        })
        .fail((jqXHR) => {
            const responseJSON = jqXHR.responseJSON
            let str = '<ul>'
            $.each(responseJSON, (k, v) => {
                str += `<li>${v}</li>`
            })
            str += '</ul>'
            page.dialogs.elements.errorCreateArea.empty().append(str);
        })
};


page.dialogs.commands.handleDeposit = () => {
    let transactionAmount = +page.dialogs.elements.transactionAmountDepo.val();
    let depositDTO = {
        transactionAmount
    }
    page.dialogs.commands.doDeposit(depositDTO);
}
page.dialogs.commands.doDeposit = (deposit) => {
    $.ajax({
        type: 'PATCH',
        headers: {
            'accept': 'application/json',
            'content-type': 'application/json'
        },
        url: page.url.doDeposit + '/' + customerId,
        data: JSON.stringify(deposit)
    })
        .done((data) => {
            let str = page.commands.renderCustomer(data.customer);
            $('#tr_' + data.customer.id).replaceWith(str);

            page.dialogs.elements.modalDeposit.modal('hide');

            page.dialogs.elements.transactionAmountDepo.val('');
            page.dialogs.elements.errorDepositArea.empty();

            page.commands.addEventEdit();
            page.commands.addEventDelete();
            page.commands.addEventDeposit();
            page.commands.addEventWithdraw();
            page.commands.addEventTransfer();

        })
        .fail((jqXHR) => {
            const responseJSON = jqXHR.responseJSON
            let str = '<ul>'
            $.each(responseJSON, (k, v) => {
                str += `<li>${v}</li>`
            })
            str += '</ul>'
            page.dialogs.elements.errorDepositArea.empty().append(str);
        })
}

page.dialogs.commands.handleWithdraw = () => {

    let transactionAmount = +page.dialogs.elements.transactionAmountWd.val();
    let withdraw = {
        transactionAmount
    }
    page.dialogs.commands.doWithdraw(withdraw);
}
page.dialogs.commands.doWithdraw = (withdraw) => {
    $.ajax({
        type: 'PATCH',
        headers: {
            'accept': 'application/json',
            'content-type': 'application/json'
        },
        url: page.url.doWithdraw + '/' + customerId,
        data: JSON.stringify(withdraw)
    })
        .done((data) => {
            let str = page.commands.renderCustomer(data.customer);
            $('#tr_' + data.customer.id).replaceWith(str);

            page.dialogs.elements.modalWithdraw.modal('hide');

            page.dialogs.elements.transactionAmountWd.val('');
            page.dialogs.elements.errorWithdrawArea.empty();
            // addEventCreate();
            page.commands.addEventEdit();
            page.commands.addEventDelete();
            page.commands.addEventDeposit();
            page.commands.addEventWithdraw();
            page.commands.addEventTransfer();
        })
        .fail((jqXHR) => {
            const responseJSON = jqXHR.responseJSON
            let str = '<ul>'
            $.each(responseJSON, (k, v) => {
                str += `<li>${v}</li>`
            })
            str += '</ul>'
            page.dialogs.elements.errorWithdrawArea.empty().append(str);
        })
}


page.dialogs.commands.handleTransfer = () => {
    let senderId = page.dialogs.elements.senderIdTf.val();
    let recipientId = page.dialogs.elements.recipientSelect.val();
    let fees = +page.dialogs.elements.fees.val();
    let transactionAmount = +page.dialogs.elements.total.val();
    let transferAmount = +page.dialogs.elements.transfer.val();
    let feesAmount = (fees * transferAmount) / 100;

    let transfer = {
        senderId,
        recipientId,
        fees,
        feesAmount,
        transferAmount,
        transactionAmount
    }

    page.dialogs.commands.doTransfer(transfer);
}

page.dialogs.commands.doTransfer = (transfer) => {
    $.ajax({
        type: 'PATCH',
        headers: {
            'accept': 'application/json',
            'content-type': 'application/json'
        },
        url: page.url.doTransfer + '/' + customerId,
        data: JSON.stringify(transfer)
    })
        .done((data) => {
            let strSender = renderCustomer(data.sender);
            $('#tr_' + data.sender.id).replaceWith(strSender);
            let strRecipient = page.commands.renderCustomer(data.recipient);
            $('#tr_' + data.recipient.id).replaceWith(strRecipient);

            page.dialogs.elements.modalTransfer.modal('hide');

            page.dialogs.elements.transfer.val('');
            page.dialogs.elements.total.val('');
            page.dialogs.elements.errorTransferArea.empty();

            page.commands.addEventEdit();
            page.commands.addEventDelete();
            page.commands.addEventDeposit();
            page.commands.addEventWithdraw();
            page.commands.addEventTransfer();
        })
        .fail((jqXHR) => {
            const responseJSON = jqXHR.responseJSON
            let str = '<ul>'
            $.each(responseJSON, (k, v) => {
                str += `<li>${v}</li>`
            })
            str += '</ul>'
            page.dialogs.elements.errorTransferArea.empty().append(str);
        })
}

page.dialogs.commands.handleUpdate = () => {
    let fullName = page.dialogs.elements.fullNameUp.val();
    let email = page.dialogs.elements.emailUp.val();
    let phone = page.dialogs.elements.phoneUp.val();
    let address = page.dialogs.elements.addressUp.val()
    let customer = {
        fullName,
        email,
        phone,
        address
    }
    page.dialogs.commands.doUpdate(customer);
}

page.dialogs.commands.doUpdate = (customer) => {
    $.ajax({
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        type: "PATCH",
        //tên API
        url: page.url.doUpdate + '/' + customerId,
        //xử lý khi thành công
        data: JSON.stringify(customer)
    })
        .done((data) => {
            customer = data;

            page.dialogs.elements.modalUpdate.modal('hide');
            page.dialogs.elements.errorUpdateArea.empty();
            console.log(data)
            $('#tr_' + customerId).replaceWith(page.commands.renderCustomer(customer));

            // addEventCreate();
            page.commands.addEventEdit();
            page.commands.addEventDelete();
            page.commands.addEventDeposit();
            page.commands.addEventWithdraw();
            page.commands.addEventTransfer();
        })
        .fail((jqXHR) => {
            const responseJSON = jqXHR.responseJSON
            let str = '<ul>'
            $.each(responseJSON, (k, v) => {
                str += `<li>${v}</li>`
            })
            str += '</ul>'
            page.dialogs.elements.errorUpdateArea.empty().append(str);
        })
}

page.dialogs.commands.handleHistoryDeposit = () => {
    page.commands.getAllDeposit();
}

page.dialogs.commands.handleHistoryWithdraw = () => {
    page.commands.getAllWithdraw();
}

page.dialogs.commands.handleHistoryTransfer = () => {
    page.commands.getAllTransfer();
}


page.initializeControlEvent = () => {
    page.dialogs.elements.btnCreate.on('click', () => {
        page.dialogs.commands.handleCreate();
    })

    page.dialogs.elements.btnUpdate.on('click', () => {
        page.dialogs.commands.handleUpdate();
    })
    // page.dialogs.elements.btnDelete.on('click', () => {
    //     page.dialogs.commands.handleDelete();
    // })

    page.dialogs.elements.btnDeposit.on('click', () => {
        page.dialogs.commands.handleDeposit();
    })
    page.dialogs.elements.btnWithdraw.on('click', () => {
        page.dialogs.commands.handleWithdraw();
    })

    page.dialogs.elements.btnTransfer.on('click', () => {
        page.dialogs.commands.handleTransfer();
    })

    page.dialogs.elements.btnHistoryDeposit.on('click', () => {
        page.dialogs.commands.handleHistoryDeposit();
    })
    page.dialogs.elements.btnHistoryWithdraw.on('click', () => {
        page.dialogs.commands.handleHistoryWithdraw();
    })
    page.dialogs.elements.btnHistoryTransfer.on('click', () => {
        page.dialogs.commands.handleHistoryTransfer();
    })

}


page.loadData = () => {
    page.commands.getAllCustomers();

}

$(() => {
    page.loadData();

    page.initializeControlEvent();
})

document.addEventListener("input", () => {
    let fee = +page.dialogs.elements.fees.val();
    let transferAmount = +page.dialogs.elements.transfer.val();
    let transactionFee = transferAmount * fee / 100;
    let transactionAmount = Math.round(transactionFee + transferAmount);
    page.dialogs.elements.total.val(transactionAmount);
})




