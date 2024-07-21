$(document).ready(function() {
    var userRequestsDataTableInitialized = false;
    var userRequestsDataTable; // Variable to store the initialized DataTable

    if (!userRequestsDataTableInitialized) {
            $.ajax({
                url: '/endUserRequestStatus',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    console.log("Received data:", response);
                    initializeUserRequestsDataTable(response); // Initialize DataTable with response data
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching data:', error);
                }
            });
            userRequestsDataTableInitialized = true;
    }

     // Function to initialize DataTable
        function initializeUserRequestsDataTable(data) {
            // Define column headings dynamically
            var columns = [
                { data: null, title: "SNo.", render: function (data, type, row, meta) { return meta.row + 1; }},
                { data: 'username', title: 'Username' },
                { data: 'createdAt', title: 'Created At' },
                { data: 'agent', title: 'Agent' },
                { data: 'bloodGroup', title: 'Blood Group' },
                { data: 'quantity', title: 'Quantity' },
                { data: 'type', title: 'Type' },
                { data: 'status', title: 'Status' },
                { data: 'dob', title: 'Date of Birth' },
                { data: 'remark', title: 'Remark' }
            ];

            // Initialize DataTable without data
            userRequestsDataTable = $('#requestTable').DataTable({
                "columns": columns,
                "order": []
            });

            // Add data to DataTable
            userRequestsDataTable.rows.add(data).draw();
        }

    $('.user-details').show();
    $('.request-table').hide();
    $('.add-request').hide();
    $('.coin-transactions').hide();

    $('#userDetails').click(function() {
        $('.user-details').show();
        $('.request-table').hide();
        $('.add-request').hide();
        $('.coin-transactions').hide();
    });

    $('#userRequests').click(function() {
        $('.user-details').hide();
        $('.add-request').hide();
        $('.coin-transactions').hide();
        $('.request-table').show(); // Show the user details table
    });

    // Add event listener for "Add request" button
    $('#addRequest').click(function() {
        $('.user-details').hide();
        $('.request-table').hide();
        $('.coin-transactions').hide();
        $('.add-request').show();
    });

    $('#submitRequestForm').on('click', function(event) {
        event.preventDefault();

        // Get form data
        var formData = {
            type: $('#type').val(),
            bloodGroup: $('#bloodGroup').val(),
            quantity: $('#quantity').val()
        };

        // Perform AJAX request to submit the request
        $.ajax({
            url: '/submitRequest',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                // Check if the response contains a status property
                if (response && response.status === "Pending") {
                    // Add new row to the table
                    var newRowData = {
                        username: response.username,
                        createdAt: response.createdAt,
                        agent: response.agent,
                        bloodGroup: response.bloodGroup,
                        quantity: response.quantity,
                        type: response.type,
                        status: response.status,
                        dob: response.dob,
                        remark: response.remark
                    };

                    userRequestsDataTable.row.add(newRowData).draw();
                    // Display success message
                    Swal.fire({
                        icon: "success",
                        title: "Successful",
                        text: "Request registered successfully"
                    });
                }  else {
                    // Display error message if unexpected response
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: response.status
                    });
                }
            },
            error: function(xhr, status, error) {
            if (xhr.responseText === "Insufficient coins.") {
                                // Display insufficient coins message and show coin transactions
                                Swal.fire({
                                    icon: "warning",
                                    title: "Insufficient coins",
                                    text: "You have insufficient coins. Please add more coins."
                                }).then(function() {
                                    // Show coin transactions section
                                    $('.user-details').hide();
                                    $('.request-table').hide();
                                    $('.add-request').hide();
                                    $('.coin-transactions').show();
                                });
                            }else{
                                // Display error message
                                Swal.fire({
                                    icon: "error",
                                    itle: "Oops...",
                                    text: xhr.responseText
                                });
                            }

            }
        });
    });


    $('#submitCoinRequestForm').on('click', function(event) {
        event.preventDefault();

        // Get form data
        var formData = {
            coinType: $('#coinType').val(),
            amount: $('#amount').val()
        };

        // Perform AJAX request to submit the request
        $.ajax({
            url: '/addCoins',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function(response) {
                console.log(response);
                // Update coinRequestCount display on the form
                $('#coinRequestCount').text(response.coinRequestCount);

                // Show success message
                Swal.fire({
                    icon: "success",
                    title: "Successful",
                    text: response.message
                });
            },
            error: function(xhr, status, error) {
                // Display error message
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: xhr.responseText
                });
            }
        });
    });

});
