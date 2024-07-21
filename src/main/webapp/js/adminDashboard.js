$(document).ready(function() {
    var dataTableInitialized = false;
    var userDataTable; // Variable to store the initialized DataTable

    var bloodStockDataTableInitialized = false;
    var bloodStockDataTable; // Variable to store the initialized DataTable

    var coinReportDataTableInitialized = false;
    var coinReportDataTable; // Variable to store the initialized DataTable

    var bloodReportDataTableInitialized = false;
    var bloodReportDataTable; // Variable to store the initialized DataTable

    var requestDataTableInitialized = false;
    var bloodBankRequestDataTable; // Variable to store the initialized DataTable


    // Show admin details and hide user details initially
    $('.admin-details').show();
    $('.user-details').hide();

    $('#adminDetails').click(function() {
        $('.admin-details').show();
        $('.blood-stock').hide();
        $('.blood-report').hide();
        $('.requests').hide();
        $('.coin-report').hide();
        $('.user-details').hide(); // Hide the user details table
    });

    $('#usersList').click(function() {
        $('.admin-details').hide();
        $('.blood-stock').hide();
        $('.coin-report').hide();
        $('.blood-report').hide();
        $('.requests').hide();
        $('.user-details').show(); // Show the user details table

        if (!dataTableInitialized) {
            $.ajax({
                url: '/usersList',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    console.log("Received data:", response);
                    initializeDataTable(response); // Initialize DataTable with response data
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching data:', error);
                }
            });
            dataTableInitialized = true;
        }
    });

    // Function to initialize DataTable
    function initializeDataTable(data) {

        // Define column headings dynamically
        var columns = [
            {
                data: 'id',
                title: 'ID',
                render: function(data, type, row, meta) {
                    return '<a href="#" class="editRow" data-id="' + data + '">' + data + '</a>';
                }
            },
            { data: 'name', title: 'Name' },
            { data: 'username', title: 'Username' },
            { data: 'createdBy', title: 'Created By' },
            { data: 'role', title: 'Role' },
            { data: 'dob', title: 'Date of Birth' },
            { data: 'bloodGroup', title: 'Blood Group' },
            { title: 'Actions' }
        ];

            // Initialize DataTable without data
            userDataTable = $('#userTable').DataTable({
                "columns": columns,
                "layout": {
                                topStart: {
                                    buttons: [
                                        {
                                            text: '<i class="fa fa-plus">Add Agent</i>',
                                            action: function (e, dt, node, config) {
                                                $('#addModal').modal('show');
                                            },
                                            className: 'addBtn'
                                        }
                                    ]
                                }
                            },
                "columnDefs": [{
                    "targets": -1,
                    "data": null,
                    "defaultContent": "<button class='deleteBtn'>Delete</button>"
                }],
                "order": []
            });

        // Add data to DataTable
        userDataTable.rows.add(data).draw();

        // Add event listener for delete buttons
        $('#userTable tbody').on('click', '.deleteBtn', function() {
            var row = $(this).closest('tr');
            var rowData = userDataTable.row(row).data();
            var userId = rowData.id;

            // Perform AJAX request to delete user with given userId
            $.ajax({
                url: '/deleteUser/' + userId,
                type: 'DELETE',
                success: function(response) {
                    console.log("User deleted successfully");
                    // Reload DataTable after deletion
                    userDataTable.row(row).remove().draw(false);
                    Swal.fire({
                        icon: "success",
                        title: "Successful",
                        text: "User deleted"
                    });
                },
                error: function(xhr, status, error) {
                    console.error('Error deleting user:', error);
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: xhr.responseText
                    });
                }
            });
        });

        // Add event listener using event delegation for form submission
        $('#submitAgentForm').on('click',function(event) {
            event.preventDefault();

            // Get form data
            var formData = {
                username: $('#username').val(),
                name: $('#name').val(),
                dob: $('#dob').val(),
                bloodGroup: $('#bloodGroup').val(),
                phone: $('#phone').val(),
                address: $('#address').val()
            };

            // Perform AJAX request to add agent
            $.ajax({
                url: '/addAgent',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function(response) {

                // Add new row to the table
                var newRowData = {
                    id: response.id,
                    name: response.name,
                    username: response.username,
                    createdBy: response.createdBy,
                    role: response.role,
                    dob: response.dob,
                    bloodGroup: response.bloodGroup
                };

                userDataTable.row.add(newRowData).draw();

                $('#addModal').modal('hide');
                Swal.fire({
                    icon: "success",
                    title: "Successful",
                    text: "Agent created"
                });
            },
                error: function(xhr, status, error) {
                        $('#addModal').modal('hide');
                            if(xhr.responseText === "Username is already taken"){
                                Swal.fire({
                                    icon: "error",
                                    title: "Oops...",
                                    text: xhr.responseText
                                });
                            }else{
                                Swal.fire({
                                    icon: "error",
                                    title: "Oops...",
                                    text: "Error creating agent!"
                                });
                            }
                    }
                });
            });
    }

    // Add event listener for editing rows
    $('#userTable tbody').on('click', '.editRow', function(event) {
        event.preventDefault();
        var row = $(this).closest('tr');
        var rowData = userDataTable.row(row).data();

        // Populate modal with row data for editing
        $('#editModal').modal('show');
        $('#editId').val(rowData.id);
        $('#editName').val(rowData.name);
        $('#editUsername').val(rowData.username);
        $('#editCreatedBy').val(rowData.createdBy);
        $('#editRole').val(rowData.role);
        $('#editDOB').val(rowData.dob);
        $('#editBloodGroup').val(rowData.bloodGroup);

        // Store the row reference for later use
        $('#saveChangesBtn').data('row', row);
    });

    // Add event listener for saving changes in the modal
    $(document).on('click', '#saveChangesBtn', function(event) {
        // Retrieve updated data from modal fields
        var updatedId = $('#editId').val();
        var updatedName = $('#editName').val();
        var updatedUsername = $('#editUsername').val();
        var updatedCreatedBy = $('#editCreatedBy').val();
        var updatedRole = $('#editRole').val();
        var updatedDOB = $('#editDOB').val();
        var updatedBloodGroup = $('#editBloodGroup').val();

        // Prepare data to send to the server
        var updatedData = {
            id: updatedId,
            name: updatedName,
            username: updatedUsername,
            createdBy: updatedCreatedBy,
            role: updatedRole,
            dob: updatedDOB,
            bloodGroup: updatedBloodGroup
        };

        // Perform AJAX request to update data in the database
        $.ajax({
            url: '/updateUser/' + updatedId,
            type: 'PUT',
            contentType: 'application/json',
            data: JSON.stringify(updatedData),
            dataType: 'json',
            success: function(response) {
                console.log(response);
                console.log("User updated successfully");

                var row = $('#saveChangesBtn').data('row');

                userDataTable.row(row).data(updatedData).draw();

                $('#editModal').modal('hide');
                Swal.fire({
                    icon: "success",
                    title: "Successful",
                    text: "User updated successfully"
                });
            },
            error: function(xhr, status, error) {

                console.error('Error updating user:', error);
                $('#editModal').modal('hide');
                 Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Error updating user!"
                 });
            }
        });
    });

    $('#bloodStock').click(function() {
            $('.admin-details').hide();
            $('.user-details').hide();
            $('.blood-report').hide();
            $('.coin-report').hide();
            $('.requests').hide();
            $('.blood-stock').show();

            if (!bloodStockDataTableInitialized) {
                $.ajax({
                    url: '/bloodStock',
                    type: 'GET',
                    dataType: 'json',
                    success: function (response) {
                        console.log("Received data:", response);
                        initializeBloodStockDataTable(response); // Initialize DataTable with response data
                    },
                    error: function (xhr, status, error) {
                        console.error('Error fetching data:', error);
                    }
                });
                bloodStockDataTableInitialized = true;
            }
        });

        // Function to initialize DataTable
        function initializeBloodStockDataTable(data) {
            // Define column headings dynamically
            var columns = [
                { data: 'bloodGroup', title: 'Blood Group' },
                { data: 'donateCoinsPerUnit', title: 'Donor Coins per unit' },
                { data: 'receiveCoinsPerUnit', title: 'Recipient Coins per Unit' },
                { data: 'availableUnits', title: 'Available Units' }
            ];

            // Initialize DataTable without data
            bloodStockDataTable = $('#bloodStockTable').DataTable({
                "columns":columns,
                "order":[]
            });
            bloodStockDataTable.rows.add(data).draw();
        }

        $('#coinReport').click(function() {
            $('.admin-details').hide();
            $('.user-details').hide();
            $('.blood-stock').hide();
             $('.blood-report').hide();
             $('.requests').hide();
            $('.coin-report').show();

            if (!coinReportDataTableInitialized) {
                $.ajax({
                    url: '/adminCoinReport',
                    type: 'GET',
                    dataType: 'json',
                    success: function(response) {
                        console.log("Received data:", response);
                        initializeCoinReportDataTable(response); // Initialize DataTable with response data
                    },
                    error: function(xhr, status, error) {
                        console.error('Error fetching data:', error);
                    }
                });
                coinReportDataTableInitialized = true;
            }
        });

        // Function to initialize DataTable
        function initializeCoinReportDataTable(data) {

            // Define column headings dynamically
            var columns = [
                { data: 'username', title: 'User' },
                { data: 'bloodGroup', title: 'Blood Group' },
                { data: 'userCoins', title: 'End User Coins' },
                { data: 'agentCoins', title: 'Agent Coins' },
                { data: 'adminCoins', title: 'Admin Coins' },
                { data: 'status', title: 'Status' },
                {data: 'type',title:'Request Type'}
            ];

            // Initialize DataTable without data
            coinReportDataTable = $('#coinReportTable').DataTable({
                "columns": columns,
                "order": [] // Disable initial sorting
            });
            // Add data to DataTable
            coinReportDataTable.rows.add(data).draw();
        }

        $('#bloodReport').click(function() {
                    $('.admin-details').hide();
                    $('.user-details').hide();
                    $('.blood-stock').hide();
                    $('.coin-report').hide();
                    $('.requests').hide();
                    $('.blood-report').show();

                    if (!bloodReportDataTableInitialized) {
                        $.ajax({
                            url: '/adminBloodReport',
                            type: 'GET',
                            dataType: 'json',
                            success: function(response) {
                                initializeBloodReportDataTable(response); // Initialize DataTable with response data
                            },
                            error: function(xhr, status, error) {
                                console.error('Error fetching data:', error);
                            }
                        });
                        bloodReportDataTableInitialized = true;
                    }
                });

                // Function to initialize DataTable
                function initializeBloodReportDataTable(data) {
                    // Define column headings dynamically
                    var columns = [
                        { data: 'bloodGroup', title: 'Blood Group' },
                        { data: 'coinValue', title: 'Coins Value' },
                        { data: 'units', title: 'Units' },
                        { data: 'acceptCount', title: 'Accept' },
                        { data: 'declineCount', title: 'Decline' }
                    ];

                    // Initialize DataTable without data
                    bloodReportDataTable = $('#bloodReportTable').DataTable({
                        "columns": columns,
                        "order": [] // Disable initial sorting
                    });

                    // Add data to DataTable
                    bloodReportDataTable.rows.add(data).draw();
                }

                    $('#bloodBankRequests').click(function() {
                        $('.admin-details').hide();
                        $('.user-details').hide();
                        $('.blood-stock').hide();
                        $('.blood-report').hide();
                        $('.coin-report').hide();
                        $('.requests').show();

                        if (!requestDataTableInitialized) {
                            $.ajax({
                                url: '/adminBloodBankRequests',
                                type: 'GET',
                                dataType: 'json',
                                success: function(response) {
                                    console.log("Received data:", response);
                                    initializeRequestDataTable(response); // Initialize DataTable with response data
                                },
                                error: function(xhr, status, error) {
                                    console.error('Error fetching data:', error);
                                }
                            });
                            requestDataTableInitialized = true;
                        }
                    });

                    // Function to initialize DataTable
                    function initializeRequestDataTable(data) {

                        // Define column headings dynamically
                        var columns = [
                            { data: 'id', title: 'Id' },
                            { data: 'username', title: 'Username' },
                            { data: 'agent', title: 'Agent' },
                            { data: 'status', title: 'Status', render: function(data, type, row) {
                                    var statusText = '<div class="status-text">' + data + '</div>';
                                    var acceptButton = '<button class="accept-request-btn" data-request-id="' + row.id + '">Accept</button>';
                                    var rejectButton = '<button class="reject-request-btn" data-request-id="' + row.id + '">Reject</button>';
                                    if (row.status === 'Approved' || row.status === 'Rejected') {
                                        acceptButton = '';
                                        rejectButton = '';
                                    }
                                    return '<div class="status-buttons">' + statusText + acceptButton + rejectButton + '</div>';
                            }},
                            { data: 'quantity', title: 'Quantity' },
                            { data: 'type', title: 'Type' },
                            { data: 'bloodGroup', title: 'Blood Group' },
                            {
                                data: 'remark',
                                title: 'Remark'

                            }
                        ];

                        // Initialize DataTable without data
                        bloodBankRequestDataTable = $('#bloodBankRequestsTable').DataTable({
                            "columns": columns,
                            "order": [] // Disable initial sorting
                        });
                        // Add data to DataTable
                        bloodBankRequestDataTable.rows.add(data).draw();
                    }

                // Event listener for accepting request
                $('#bloodBankRequestsTable').on('click', '.accept-request-btn', function() {
                    var requestId = $(this).data('request-id');
                    var $this = $(this); // Store reference to $(this)

                    var rowData = bloodBankRequestDataTable.row($this.closest('tr')).data(); // Get row data from DataTable
                    var quantity = rowData.quantity;
                    var bloodGroup = rowData.bloodGroup;
                    var type = rowData.type;
                    var username = rowData.username; // Retrieve username from row data

                    // AJAX call to update status to "Accepted"
                    $.ajax({
                        url: '/processRequest',
                        type: 'POST',
                        data: {
                            requestId: requestId,
                            action: 'approve',
                            quantity: quantity,
                            bloodGroup: bloodGroup,
                            type: type,
                            username: username // Include username in the request data
                        },
                        success: function(response) {
                            console.log('Request accepted successfully:', response);
                            // Update the row with the new status
                            rowData.status = 'Approved';
                            bloodBankRequestDataTable.row($this.closest('tr')).data(rowData).draw();
                            // Remove the accept and reject buttons after successful update
                            $('.status-buttons[data-request-id="' + requestId + '"]').empty();
                            Swal.fire({
                                icon: "success",
                                title: "Successful",
                                text: "Request accepted successfully"
                            });
                        },
                        error: function(xhr, status, error) {
                            console.error('Error accepting request:', error);
                            // Handle error
                            Swal.fire({
                                icon: "error",
                                title: "Oops...",
                                text: "Error accepting request"
                            });
                        }
                    });
                });

                // Event listener for rejecting request
                    $('#bloodBankRequestsTable').on('click', '.reject-request-btn', function() {
                         var requestId = $(this).data('request-id');
                         var $this = $(this); // Store reference to $(this)
                         var rowData = bloodBankRequestDataTable.row($this.closest('tr')).data(); // Get row data from DataTable
                         $('#remarkRequestId').val(requestId);
                         $('#quantity').val(rowData.quantity);
                         $('#bloodGroup').val(rowData.bloodGroup);
                         $('#type').val(rowData.type);
                         $('#username').val(rowData.username);
                        $('#remarkModal').modal('show');
                    });

                    // Handle save remark button click
                    $('#saveRemarkBtn').click(function() {
                        var requestId = $('#remarkRequestId').val();
                        var remark = $('#remark').val();
                        var quantity = $('#quantity').val();
                        var bloodGroup = $('#bloodGroup').val();
                        var type = $('#type').val();
                        var username = $('#username').val();
                        if (!remark) {
                            Swal.fire({
                                icon: "error",
                                title: "Oops...",
                                text: "Remark is required"
                            });
                            return;
                        }

                        // AJAX call to add remark
                        $.ajax({
                            url: '/addRemark',
                            type: 'POST',
                            data: {
                                requestId: requestId,
                                remark: remark
                            },
                            success: function(response) {
                                console.log('Remark added successfully:', response);
                                // Update the row with the new remark
                                var rowData = bloodBankRequestDataTable.row($('[data-request-id="' + requestId + '"]').closest('tr')).data();
                                rowData.remark = remark;
                                bloodBankRequestDataTable.row($('[data-request-id="' + requestId + '"]').closest('tr')).data(rowData).draw();

                                // Proceed to reject the request after adding remark
                                $.ajax({
                                    url: '/processRequest',
                                    type: 'POST',
                                    data: {
                                        requestId: requestId,
                                        action: 'reject',
                                        quantity: quantity,
                                        bloodGroup: bloodGroup,
                                        type: type,
                                        username: username // Include username in the request data
                                    },
                                    success: function(response) {
                                        console.log('Request rejected successfully:', response);
                                        // Update the row with the new status
                                        var rowData = bloodBankRequestDataTable.row($('[data-request-id="' + requestId + '"]').closest('tr')).data();
                                        rowData.status = 'Rejected';
                                        bloodBankRequestDataTable.row($('[data-request-id="' + requestId + '"]').closest('tr')).data(rowData).draw();
                                        // Remove the accept and reject buttons after successful update
                                        $('.status-buttons[data-request-id="' + requestId + '"]').empty();
                                        Swal.fire({
                                            icon: "success",
                                            title: "Successful",
                                            text: "Request rejected successfully"
                                        });
                                        $('#remarkModal').modal('hide');
                                    },
                                    error: function(xhr, status, error) {
                                        console.error('Error rejecting request:', error);
                                        Swal.fire({
                                            icon: "error",
                                            title: "Oops...",
                                            text: "Error rejecting request"
                                        });
                                    }
                                });
                            },
                            error: function(xhr, status, error) {
                                console.error('Error adding remark:', error);
                                Swal.fire({
                                    icon: "error",
                                    title: "Oops...",
                                    text: "Error adding remark"
                                });
                            }
                        });
                    });

});