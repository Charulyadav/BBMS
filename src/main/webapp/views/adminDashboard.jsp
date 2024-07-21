<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>

    <!-- Include DataTables CSS -->
    <link href="https://cdn.datatables.net/2.0.4/css/dataTables.dataTables.css" rel="stylesheet"/>
    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <!--   plus icon custom     -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">

    <!-- Link to external CSS file -->
    <link rel="stylesheet" type="text/css" href="../css/style.css">
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
                padding: 10px 20px;
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

            /* Admin details */
            .admin-details {
                border: 1px solid #ccc;
                padding: 20px;
                border-radius: 5px;
                background-color: #f9f9f9;
                max-width: 400px;
                margin: 0 auto;
            }

            .admin-details h3 {
                margin-top: 0;
                text-align: center;
            }

            .admin-details img {
                width: 120px;
                height: 120px;
                border-radius: 50%;
                display: block;
                margin: 0 auto 20px;
                object-fit: cover;
                border: 3px solid #fff;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            .admin-details p {
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

        </style>
</head>
<body>

 <!-- Header -->
    <header>
        <h1>Admin Dashboard<h1>
    </header>

<!-- Sidebar -->
<div class="sidebar">
    <ul>
        <li>
            <a href="#" id="adminDetails">Admin Details</a>
        </li>
        <li>
            <a href="#" id="usersList">Users List</a>
        </li>
        <li>
            <a href="#" id="bloodBankRequests">Blood Requests</a>
        </li>
        <li>
            <a href="#" id="bloodReport">Blood Report</a>
        </li>
        <li>
            <a href="#" id="bloodStock">Blood Stock</a>
        </li>
        <li>
            <a href="#" id="coinReport">Coin Report</a>
        </li>
        <li>
            <a href="logout">Logout</a>
        </li>
    </ul>
</div>

<!-- Page Content -->
<div class="content">

    <!-- Admin details can be placed here -->
    <div class="admin-details">
        <h3>Admin Details:</h3>
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

    <!-- User Details Table -->
    <div class="user-details" style="display: none;">
        <h3>User Model Table</h3>
        <table id="userTable" class="display">
            <tbody></tbody>
        </table>
    </div>

    <!-- Requests Table -->
    <div class="requests" style="display: none;">
        <h3>Blood Request Table</h3>
        <table id="bloodBankRequestsTable" class="display">
            <tbody></tbody>
        </table>
    </div>

    <!-- Blood Stock Table -->
    <div class="blood-stock" style="display: none;">
        <h3>Blood Stock Table</h3>
        <table id="bloodStockTable" class="display">
            <tbody></tbody>
        </table>
    </div>

    <!-- Blood Report Table -->
    <div class="blood-report" style="display: none;">
        <h3>Blood Report Table</h3>
        <table id="bloodReportTable" class="display">
            <tbody></tbody>
        </table>
    </div>

    <!-- Coin Report Table -->
    <div class="coin-report" style="display: none;">
        <h3>Coin Report Table</h3>
        <table id="coinReportTable" class="display">
            <tbody></tbody>
        </table>
    </div>

    <!-- Bootstrap Modal for Adding Agent -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addAgentModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addModalLabel">Add Agent</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <form id="addAgentForm">
                        <div class="form-group">
                            <label for="username">Username:</label>
                            <input type="text" id="username" name="username" required>
                        </div>

                        <div class="form-group">
                            <label for="name">Name:</label>
                            <input type="text" id="name" name="name" required>
                        </div>

                        <div class="form-group">
                            <label for="dob">Date of birth:</label>
                            <input type="date" id="dob" name="dob" required>
                        </div>

                        <div class="form-group">
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
                            <label for="phone">Phone Number:</label>
                            <input type="tel" id="phone" name="phone" pattern="[0-9]{10}" required>
                        </div>

                        <div class="form-group">
                            <label for="address">Address:</label>
                            <textarea id="address" name="address" rows="4" cols="50" required></textarea>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" id="submitAgentForm">Submit</button>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap Modal for Updating User-->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="editModalLabel">Edit User</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
            <!-- Form fields for editing user data -->
            <input type="hidden" id="editId">
            <div class="form-group">
              <label for="editName">Name</label>
              <input type="text" class="form-control" id="editName" placeholder="Enter name">
            </div>
            <div class="form-group">
              <label for="editUsername">Username</label>
              <input type="text" class="form-control" id="editUsername" placeholder="Enter username">
            </div>
            <div class="form-group">
                <label for="createdBy">Created By</label>
                <input type="text" class="form-control" id="editCreatedBy" placeholder="Enter createdBy">
            </div>
            <div class="form-group">
                <label for="role">Role</label>
                <input type="text" class="form-control" id="editRole" placeholder="Enter role">
            </div>
            <div class="form-group">
                <label for="dob">DOB</label>
                <input type="text" class="form-control" id="editDOB" placeholder="Enter dob">
            </div>
            <div class="form-group">
                <label for="bloodGroup">Blood Group</label>
                <input type="text" class="form-control" id="editBloodGroup" placeholder="Enter blood group">
            </div>

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            <button type="button" class="btn btn-primary" id="saveChangesBtn">Save changes</button>
          </div>
        </div>
      </div>
    </div>

<!-- Modal HTML for adding remark -->
<div class="modal fade" id="remarkModal" tabindex="-1" role="dialog" aria-labelledby="remarkModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="remarkModalLabel">Add Remark</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <input type="hidden" id="remarkRequestId">
        <input type="hidden" id="quantity">
        <input type="hidden" id="bloodGroup">
        <input type="hidden" id="type">
        <input type="hidden" id="username">
        <form id="remarkForm">
          <div class="form-group">
            <label for="remark">Remark</label>
            <textarea class="form-control" id="remark" name="remark" rows="3" required></textarea>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary" id="saveRemarkBtn">Save Remark</button>
      </div>
    </div>
  </div>
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
<!--   button custom  -->
<script src="https://cdn.datatables.net/buttons/3.0.2/js/dataTables.buttons.js"></script>
<!--   plus icon custom     -->
<script src="https://cdn.datatables.net/buttons/3.0.2/js/dataTables.buttons.js"></script>
<script src="../js/adminDashboard.js"></script>



</body>
</html>
