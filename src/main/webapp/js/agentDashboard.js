$(document).ready(function() {
    var userDataTableInitialized = false;
    var userDataTable; // Variable to store the initialized DataTable

    var requestStatusDataTableInitialized = false;
    var requestStatusDataTable; // Variable to store the initialized DataTable

    var bloodReportDataTableInitialized = false;
    var bloodReportDataTable; // Variable to store the initialized DataTable

    var coinReportDataTableInitialized = false;
    var coinReportDataTable; // Variable to store the initialized DataTable

    // Show admin details and hide user details initially
    $('.agent-details').show();

    $('#agentDetails').click(function() {
        $('.agent-details').show();
        $('.blood-report').hide();
        $('.coin-report').hide();
        $('.request-status').hide();
        $('.user-details').hide(); // Hide the user details table
    });

    $('#usersList').click(function() {
        $('.agent-details').hide();
        $('.blood-report').hide();
        $('.coin-report').hide();
        $('.request-status').hide();
        $('.user-details').show(); // Show the user details table

        if (!userDataTableInitialized) {
            $.ajax({
                url: '/agentUsersList',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    console.log("Received data:", response);
                    initializeUserDataTable(response); // Initialize DataTable with response data
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching data:', error);
                }
            });
            userDataTableInitialized = true;
        }
    });

    // Function to initialize DataTable
    function initializeUserDataTable(data) {
        // Define column headings dynamically
        var columns = [
            { data: null, title: "SNo.", render: function (data, type, row, meta) { return meta.row + 1; } },
            { data: 'id', title: 'ID' },
            { data: 'name', title: 'Name' },
            { data: 'username', title: 'Username' },
            { data: 'createdBy', title: 'Created By' },
            { data: 'role', title: 'Role' },
            { data: 'dob', title: 'Date of Birth' },
            { data: 'bloodGroup', title: 'Blood Group' }
        ];

        // Initialize DataTable without data
        userDataTable = $('#userTable').DataTable({
            "columns": columns,
            layout: {
                topStart: {
                    buttons: [
                        {
                            text: '<i class="fa fa-plus">Add User</i>',
                            action: function (e, dt, node, config) {
                                $('#addModal').modal('show');
                            },
                            className: 'addBtn'
                        }
                    ]
                }
            },
            "order": []
        });

        // Add data to DataTable
        userDataTable.rows.add(data).draw();

        // Add event listener using event delegation for form submission
        $('#submitUserForm').on('click', function(event) {
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

            // Perform AJAX request to add user
            $.ajax({
                url: '/addAgentUser',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(formData),
                dataType: 'json',
                success: function(response) {
                    Swal.fire({
                        icon: "success",
                        text: "User created successfully"
                    });

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
                        text: "User created"
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

    $('#addRequestBtn').click(function() {
        $('#addModal').modal('show');
    });

    $('#usersRequestStatus').click(function() {
            $('.agent-details').hide();
            $('.user-details').hide();
            $('.coin-report').hide();
            $('.blood-report').hide();
            $('.request-status').show();

            if (!requestStatusDataTableInitialized) {
                $.ajax({
                    url: '/agentRequestStatus',
                    type: 'GET',
                    dataType: 'json',
                    success: function(response) {
                        console.log("Received data:", response);
                        initializeRequestStatusDataTable(response); // Initialize DataTable with response data
                    },
                    error: function(xhr, status, error) {
                        console.error('Error fetching data:', error);
                    }
                });
                requestStatusDataTableInitialized = true;
            }
        });

        // Function to initialize DataTable
        function initializeRequestStatusDataTable(data) {
            // Define column headings dynamically
            var columns = [
                { data: 'id.id', title: 'Id' },
                { data: 'username', title: 'Username' },
                { data: 'createdAt', title: 'created At' },
                { data: 'status', title: 'Status'},
                { data: 'quantity', title: 'Quantity' },
                { data: 'type', title: 'Type' },
                { data: 'bloodGroup', title: 'Blood Group' },
                { data: 'remark', title: 'Remark' }
            ];

            // Initialize DataTable without data
            requestStatusDataTable = $('#requestStatusTable').DataTable({
                "columns": columns,
                "order": [] // Disable initial sorting
            });

            // Add data to DataTable
            requestStatusDataTable.rows.add(data).draw();
        }

    $('#bloodReport').click(function() {
        $('.agent-details').hide();
        $('.user-details').hide();
        $('.coin-report').hide();
        $('.request-status').hide();
        $('.blood-report').show();

        if (!bloodReportDataTableInitialized) {
            $.ajax({
                url: '/agentBloodReport',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    console.log("Received data:", response);
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
            "columnDefs": [{ // Define delete button column
                "targets": -1,
                "data": null
            }],
            "order": [] // Disable initial sorting
        });

        // Add data to DataTable
        bloodReportDataTable.rows.add(data).draw();
    }

    $('#coinReport').click(function() {
        $('.agent-details').hide();
        $('.user-details').hide();
        $('.blood-report').hide();
        $('.request-status').hide();
        $('.coin-report').show();

        if (!coinReportDataTableInitialized) {
            $.ajax({
                url: '/agentCoinReport',
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
            { data: 'status', title: 'Status' }
        ];

        // Initialize DataTable without data
        coinReportDataTable = $('#coinReportTable').DataTable({
            "columns": columns,
            "data": null,
            "order": [] // Disable initial sorting
        });

        // Add data to DataTable
        coinReportDataTable.rows.add(data).draw();
        }

});
