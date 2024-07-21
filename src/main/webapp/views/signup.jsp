<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blood Bank Signup</title>
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
            height: 100vh;
        }
        .container {
            background: #ffffff;
            padding: 30px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
            border-radius: 20px;
            display: flex;
            align-items: flex-start;
            max-width: 90%;
            max-height: 90%;
            overflow: auto;
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
        .hero-content {
            flex: 1;
            padding: 20px;
            display: flex;
            flex-direction: column;
        }
        .hero-content h1 {
            color: #ff5e5b;
            font-size: 2.5em;
            margin-bottom: 20px;
            font-weight: bold;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
            align-items:center;
        }
        .hero-content h2 {
            color: black;
            font-size: 1.8em;
            margin-bottom: 20px;
            font-weight: bold;
            align-items:center;
        }
        .signup-form {
            font-size: 1em;
        }
        .form-group label {
            font-size: 1em;
            color: #333;
            font-weight: 600;
        }
        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .forgot-password-link a {
            color: #ff5e5b;
            text-decoration: none;
        }
        .forgot-password-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="hero-content">
        <h1>Welcome to Blood Bank</h1>
        <form action="addUser" method="post" id="signup-form" class="signup-form">
            <h2>Sign Up</h2>
            <c:if test="${not empty usernameError}">
                <div class="error">${usernameError}</div>
            </c:if>

            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="dob">Date of birth:</label>
                <input type="date" id="dob" name="dob" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="bloodGroup">Blood Group:</label>
                <select id="bloodGroup" name="bloodGroup" class="form-control">
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
                <input type="tel" id="phone" name="phone" pattern="[0-9]{10}" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="address">Address:</label>
                <textarea id="address" name="address" rows="4" class="form-control" required></textarea>
            </div>

            <div class="form-group">
                <label for="profileUrl">Profile Photo URL:</label>
                <input type="url" id="profileUrl" name="profileUrl" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="petName">What is your pet animal's name?</label>
                <input type="text" id="petName" name="petName" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="hobby">What is your favourite hobby?</label>
                <input type="text" id="hobby" name="hobby" class="form-control" required>
            </div>

            <div class="form-group">
                <label for="dish">What is your favourite dish?</label>
                <input type="text" id="dish" name="dish" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-custom btn-block">Sign Up</button>

            <div class="forgot-password-link mt-3 text-center">
                <br>Already have an account?<a href="/"> Login</a>
            </div>
        </form>
    </div>
</div>

<!-- Include the full version of jQuery from a CDN -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="../js/validation.js"></script>
</body>
</html>
