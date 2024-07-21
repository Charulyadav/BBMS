<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>End User Dashboard</title>
    <!-- Include DataTables CSS -->
    <link href="https://cdn.datatables.net/2.0.4/css/dataTables.dataTables.css" rel="stylesheet"/>
    <!-- Include Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Link to external CSS file -->
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <!-- Plus icon custom -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
            /* General styles */
                               body {
                                   margin: 0;
                                   font-family: Arial, sans-serif;
                                   background-color: #fff;
                               }

                               /* Header styles */
                               header {
                                   background-color: #343a40;
                                   color: white;
                                   padding: 10px 20px;
                                   text-align: center;
                                   font-size: 1.5em;
                               }

                               header h1 {
                                   margin-left: 220px; /* Align header with content */
                               }

                               /* Sidebar styles */
                               .sidebar {
                                   position: fixed;
                                   top: 0;
                                   left: 0;
                                   width: 200px;
                                   height: 100%;
                                   background-color: #404040;
                                   color: white;
                                   padding: 20px;
                                   box-sizing: border-box;
                               }

                               .sidebar ul {
                                   list-style-type: none;
                                   padding: 0;
                                   margin: 0;
                               }

                               .sidebar li {
                                   margin-bottom: 10px;
                               }

                               .sidebar a {
                                   text-decoration: none;
                                   color: white;
                                   display: block;
                                   padding: 10px 10px;
                                   border: 3px solid transparent;
                               }

                               .sidebar a:hover {
                                   background-color: #808080;
                                   border-color: #ff9999;
                               }

                               /* Content area */
                               .content {
                                   margin-left: 200px; /* Adjust based on sidebar width */
                                   padding: 20px;
                                   background-color: #fff;
                               }

                               /* User details */
                               .user-details {
                                   border: 1px solid #ccc;
                                   padding: 20px;
                                   border-radius: 5px;
                                   background-color: #f9f9f9;
                                   max-width: 400px;
                                   margin: 0 auto;
                               }

                               .user-details h3 {
                                   margin-top: 0;
                                   text-align: center;
                               }

                               .user-details img {
                                   width: 120px;
                                   height: 120px;
                                   border-radius: 50%;
                                   display: block;
                                   margin: 0 auto 20px;
                                   object-fit: cover;
                                   border: 3px solid #fff;
                                   box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                               }

                               .user-details p {
                                   margin: 10px 0;
                                   text-align: left;
                               }

                               /* Custom DataTable CSS */
                               .dataTables_wrapper .dataTables_paginate .paginate_button {
                                   padding: 0.5em 1em;
                                   margin-left: 2px;
                                   background-color: #007bff;
                                   color: white;
                                   border: none;
                                   border-radius: 3px;
                                   cursor: pointer;
                               }

                               .dataTables_wrapper .dataTables_paginate .paginate_button:hover {
                                   background-color: #0056b3;
                               }

                               .dataTables_wrapper .dataTables_length select,
                               .dataTables_wrapper .dataTables_filter input {
                                   border: 1px solid #ced4da;
                                   border-radius: 3px;
                                   padding: 0.5em;
                               }

                               .dataTables_wrapper .dataTables_info {
                                   padding-top: 0.85em;
                                   color: #495057;
                               }

                               table.dataTable thead th {
                                   background-color: #007bff;
                                   color: white;
                                   border: none;
                               }

                               table.dataTable tbody tr:hover {
                                   background-color: #f1f1f1;
                               }

                               table.dataTable tbody tr:nth-child(even) {
                                   background-color: #f9f9f9;
                               }

                               table.dataTable tbody tr:nth-child(odd) {
                                   background-color: #ffffff;
                               }

                               table.dataTable tbody td {
                                   color: #333;
                               }

                               table.dataTable thead th {
                                   color: black;
                                   background-color: #FFDBE5;
                               }


            /* Custom styles for forms */

           .form-container.add-request {
                       width: 100%;
                       max-width: 600px;
                       background-color: #fff;
                       border-radius: 5px;
                       box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                       padding: 20px;
                       margin: 0 auto; /* Center horizontally */
                   }

                   .form-container.add-request .form-title {
                       text-align: center;
                       margin-bottom: 20px;
                   }

                   .form-container.add-request .form-group {
                       margin-bottom: 20px;
                   }

                   .form-container.add-request .form-group label {
                       display: block;
                       margin-bottom: 5px;
                       font-weight: bold;
                   }

                   .form-container.add-request .form-group select,
                   .form-container.add-request .form-group input[type="number"] {
                       width: 100%;
                       padding: 10px;
                       border-radius: 5px;
                       border: 1px solid #ccc;
                       font-size: 16px;
                   }

                   .form-container.add-request .form-submit button[type="submit"] {
                       width: 100%;
                       padding: 12px;
                       border: none;
                       border-radius: 5px;
                       background-color: #007bff;
                       color: #fff;
                       font-size: 16px;
                       cursor: pointer;
                       transition: background-color 0.3s ease;
                   }

                   .form-container.add-request .form-submit button[type="submit"]:hover {
                       background-color: #0056b3;
                   }

                   .form-container.coin-transactions {
                       width: 100%;
                       max-width: 600px;
                       background-color: #fff;
                       border-radius: 5px;
                       box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                       padding: 20px;
                       margin: 20px auto; /* Center horizontally with some margin */
                   }

                   .form-container.coin-transactions .form-title {
                       text-align: center;
                       margin-bottom: 20px;
                   }

                   .form-container.coin-transactions .form-group {
                       margin-bottom: 20px;
                   }

                   .form-container.coin-transactions .form-group label {
                       display: block;
                       margin-bottom: 5px;
                       font-weight: bold;
                   }

                   .form-container.coin-transactions .form-group select,
                   .form-container.coin-transactions .form-group input[type="number"] {
                       width: 100%;
                       padding: 10px;
                       border-radius: 5px;
                       border: 1px solid #ccc;
                       font-size: 16px;
                   }

                   .form-container.coin-transactions .form-submit button[type="submit"] {
                       width: 100%;
                       padding: 12px;
                       border: none;
                       border-radius: 5px;
                       background-color: #007bff;
                       color: #fff;
                       font-size: 16px;
                       cursor: pointer;
                       transition: background-color 0.3s ease;
                   }

                   .form-container.coin-transactions .form-submit button[type="submit"]:hover {
                       background-color: #0056b3;
                   }


        </style>
</head>
<body>
 <!-- Header -->
    <header>
        <h1>End User Dashboard<h1>
    </header>
    <!-- Sidebar -->
        <div class="sidebar">
            <div class="sidebar-heading">User Dashboard</div>
            <ul>
                <li>
                    <a href="#" id="userDetails">User Details</a>
                </li>
                <li>
                    <a href="#" id="addRequest">Add Request</a>
                </li>
                <li>
                    <a href="#" id="userRequests">Request status</a>
                </li>
                <li>
                    <a href="logout">Logout</a>
                </li>
            </ul>
        </div>
     </div>
    <!-- Page Content -->
    <div class="content">
        <!-- Admin details can be placed here -->
        <div class="user-details">
            <h3>User Details:</h3>
            <img src="${userModel.profileUrl}" alt="Profile Photo">
           <p>Username: ${userModel.username}</p>
                   <p>Name: ${userModel.name}</p>
                   <p>Date of Birth: ${userModel.dob}</p>
                   <p>BloodGroup:${userModel.bloodGroup}</p>
                   <p>Role: ${userModel.role}</p>
                   <p>Created Date and time: ${userModel.created_date_time}</p>
                   <p>Phone Number: ${userModel.phone}</p>
                   <p>Coins:${userModel.coins}</p>
        </div>

        <!-- Request Form -->
        <div class="form-container add-request">
            <form id="requestForm">
                <h2 class="form-title">Add Request</h2>
                <div class="form-group">
                    <label for="type">Type:</label>
                    <select id="type" name="type" onchange="toggleBloodGroupField()">
                        <option value="donate">Donate Blood</option>
                        <option value="receive">Request Blood</option>
                    </select>
                </div>
                <!-- Blood Group field initially hidden -->
                <div class="form-group" id="bloodGroupField" style="display: none;">
                    <label for="bloodGroup">Blood Group:</label>
                    <select id="bloodGroup" name="bloodGroup">
                        <option value="A+">A+</option>
                        <option value="A-">A-</option>
                        <option value="B+">B+</option>
                        <option value="B-">B-</option>
                        <option value="AB+">AB+</option>
                        <option value="AB-">AB-</option>
                        <option value="O+">O+</option>
                        <option value="O-">O-</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="quantity">Quantity:</label>
                    <input type="number" id="quantity" name="quantity" min="1" required>
                </div>
                <div class="form-submit">
                    <button id="submitRequestForm" type="submit">Submit</button>
                </div>
            </form>
        </div>

        <!-- Request Table -->
        <div class="request-table" style="display: none;">
            <h3>Request Table</h3>

            <table id="requestTable" class="display">
                <tbody></tbody>
            </table>
        </div>

        <!-- Coin Request Form -->
        <div class="form-container coin-transactions">
            <form id="coinRequestForm">
                <h2 class="form-title">Coin Request Form</h2>
                <div class="form-group">
                    <label for="coinRequestCount">Coin Request Count:</label>
                    <span id="coinRequestCount">${userModel.coinRequestCount}</span>
                </div>
                <div class="form-group">
                    <label for="coinType">Type:</label>
                    <select id="coinType" name="coinType">
                        <option value="deposit">Deposit</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="amount">Coin:</label>
                    <input type="number" id="amount" name="amount" min="1" required>
                </div>

                <div class="form-submit">
                    <button id="submitCoinRequestForm" type="submit">Submit</button>
                </div>
            </form>
        </div>

    </div>
        <!-- Include jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Include Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- Include DataTables JS -->
        <script src="https://cdn.datatables.net/2.0.4/js/dataTables.js"></script>
        <!-- Include Sweet alert JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.0/sweetalert.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
        <!-- Button custom -->
        <script src="https://cdn.datatables.net/buttons/3.0.2/js/dataTables.buttons.js"></script>
        <script src="../js/endUserDashboard.js"></script>

    <script>
        function toggleBloodGroupField() {
                    var type = document.getElementById("type").value;
                    var bloodGroupField = document.getElementById("bloodGroupField");

                    // Show blood group field if type is "receive", otherwise hide it
                    if (type === "receive") {
                        bloodGroupField.style.display = "block";
                    } else {
                        bloodGroupField.style.display = "none";
                    }
                }
    </script>
</body>
</html>
