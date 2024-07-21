$(document).ready(function() {
    var dataTableInitialized = false;
    var bloodReportDataTable; // Variable to store the initialized DataTable

    $('#bloodReport').click(function() {
        $('.admin-details').hide();
        $('.user-details').hide();
        $('.blood-stock').hide();
        $('.coin-report').hide();
        $('.requests').hide();
        $('.blood-report').show();

        if (!dataTableInitialized) {
            $.ajax({
                url: '/adminBloodReport',
                type: 'GET',
                dataType: 'json',
                success: function(response) {
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
});
