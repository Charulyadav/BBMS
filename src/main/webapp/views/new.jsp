<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Update Password Form</title>
        <style>
            h2 {
                text-align: center;
            }

            body {
                font-family: Arial, sans-serif;
                background-color: #f5f5f5;
            }

            .first-login-form {
                max-width: 400px;
                margin: 20px auto;
                padding: 20px;
                background-color: #fff;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }

            .form-group {
                margin-bottom: 20px;
            }

            .form-group label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }

            .form-group input[type="text"],
            .form-group input[type="password"],
             .form-group select{
                width: calc(100% - 22px);
                padding: 10px;
                border-radius: 5px;
                border: 1px solid #ccc;
                font-size: 16px;
            }

            .first-login-form button[type="submit"] {
                width: 100%;
                padding: 12px;
                border: none;
                border-radius: 5px;
                background-color: #007bff;
                color: #fff;
                font-size: 16px;
                cursor: pointer;
            }

            .first-login-form button[type="submit"]:hover {
                background-color: #0056b3;
            }
        </style>
</head>
<body>

    <form action="userFirstLogin" method="post" class="first-login-form">
        <h2>Update Password</h2>
        <p>${errorMsg}</p>
        <div class="form-group">
            <label for="username">Username:</label>
            <!-- Set the username value as the placeholder and make it readonly -->
            <input type="text" id="username" name="username" value="${username}" readonly>
        </div>
        <div class="form-group">
            <label for="password">Old Password:</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div class="form-group">
            <label for="newPassword">New Password:</label>
            <input type="password" id="newPassword" name="newPassword" required>
        </div>

        <div class="form-group">
            <label for="profileUrl">Profile Photo URL:</label>
            <input type="url" id="profileUrl" name="profileUrl" required>
        </div>

        <div class="form-group">
            <label for="petName">What is your pet animal's name?</label>
            <input type="text" id="petName" name="petName" required>
        </div>

        <div class="form-group">
            <label for="hobby">What is your favourite hobby?</label>
            <input type="text" id="hobby" name="hobby" required>
        </div>

        <div class="form-group">
            <label for="dish">What is your favourite dish?</label>
            <input type="text" id="dish" name="dish" required>
        </div>

        <button type="submit">Update</button>
    </form>

</body>
</html>


render: function(data, type, row) {
                                    var inputField = '<input type="text" class="remark-input" data-request-id="' + row.id + '" value="' + (data ? data : '') + '">';
                                    var submitButton = '<button class="submit-remark-btn" data-request-id="' + row.id + '">Submit</button>';
                                    if (row.status === 'Approved' || row.status === 'Rejected') {
                                        inputField = '<input type="text" class="remark-input" data-request-id="' + row.id + '" value="' + (data ? data : '') + '" disabled>';
                                        submitButton = '';
                                    }
                                    return '<div class="remark-container">' + inputField + submitButton + '</div>';
                                }