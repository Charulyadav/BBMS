<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Forgot Password</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #ff9999;
            margin: 0;
            padding: 0;
            display: flex;
            height: 100vh;
            justify-content: center;
            align-items: center;
        }
        .container {
            background: #ffffff;
            padding: 30px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
            text-align: center;
            max-width: 85%;
            border-radius: 20px;
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
            height: 100%;
        }
        .hero-image {
            flex: 2;
        }
        .hero-image img {
            margin-bottom: 50px;
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
        .forgot-password-form {
            margin-top: 20px;
            font-size: 1em;
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
                <form action="checkValidity" method="post" class="forgot-password-form">
                    <h2>Forgot Password</h2><br>
                    <p>${errorMsg}</p>
                    <div class="form-group">
                        <label for="username">Username:</label>
                        <input type="text" id="username" name="username" class="form-control" required>
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

                    <button type="submit" class="btn btn-custom btn-block">Update</button>
                    <div class="forgot-password-link mt-3">
                        <a href="/">Back to Login</a>
                    </div>
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
