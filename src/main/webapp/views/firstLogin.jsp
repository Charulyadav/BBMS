<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blood Bank Update Password</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #ff9999;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh; /* Ensure minimum height of viewport */
        }
        .container {
            background: #ffffff;
            padding: 30px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
            text-align: center;
            width: 100%; /* Set width to 100% */
            max-width: 1200px; /* Increased maximum width */
            border-radius: 20px;
            margin: 50px auto; /* Adjusted margin for space around the container */

        }
        .btn-custom {
            background: #ff5e5b;
            color: #fff;
            border: none;
            transition: background 0.3s;
        }
        .btn-custom:hover {
            background: #e04b4b;
        }
        .forgot-password-link {
            margin-top: 20px;
            font-size: 1em;
        }
        .forgot-password-link a {
            color: #ff5e5b;
            text-decoration: none;
        }
        .forgot-password-link a:hover {
            text-decoration: underline;
        }
        .hero-section {
            display: flex;
            align-items: center;
        }
        .hero-image {
            flex: 2;
            padding: 20px; /* Add padding for better spacing */
        }
        .hero-image img {
            width: 100%;
            border-radius: 15px;
        }
        .hero-content {
            flex: 1;
            padding: 20px;
        }
        .hero-content h1 {
            color: #ff5e5b;
            font-size: 3.5em;
            margin-bottom: 20px;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
        }
        .hero-content p {
            color: #666666;
            font-size: 1.1em;
            margin-bottom: 20px;
        }
        .first-login-form {
            margin-top: 20px;
            font-size: 1em;
        }
        .first-login-form .form-group label {
            text-align: left;
            display: block;
            margin-bottom: 5px;
        }
        .first-login-form .form-group input {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        .first-login-form button {
            width: 100%;
            padding: 10px;
            background: #ff5e5b;
            color: #fff;
            border: none;
            border-radius: 5px;
            transition: background 0.3s;
        }
        .first-login-form button:hover {
            background: #e04b4b;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="hero-section">
        <div class="hero-image">
            <img src="https://media1.thehungryjpeg.com/thumbs/800_3904033_v8kcna7d201vk5mhikikb4wdxf82gcazj36y1iiw.jpg" alt="Blood Donation Illustration" class="img-fluid">
        </div>
        <div class="hero-content">
            <h1>Welcome to Blood Bank</h1>
            <div>
                <form action="userFirstLogin" method="post" class="first-login-form">
                    <h2>Update Password</h2><br>
                    <p>${errorMsg}</p>
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <!-- Set the username value as the placeholder and make it readonly -->
                        <input type="text" id="username" name="username" value="${username}" readonly class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="password">Old Password:</label>
                        <input type="password" id="password" name="password" required class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="newPassword">New Password:</label>
                        <input type="password" id="newPassword" name="newPassword" required class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="profileUrl">Profile Photo URL:</label>
                        <input type="url" id="profileUrl" name="profileUrl" required class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="petName">What is your pet animal's name?</label>
                        <input type="text" id="petName" name="petName" required class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="hobby">What is your favourite hobby?</label>
                        <input type="text" id="hobby" name="hobby" required class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="dish">What is your favourite dish?</label>
                        <input type="text" id="dish" name="dish" required class="form-control">
                    </div>
                    <button type="submit" class="btn btn-custom btn-block">Update</button>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
