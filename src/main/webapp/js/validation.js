// validation.js

$(document).ready(function(){
    // Function to validate the form inputs
    function validateForm() {
        var username = $('#username').val();
        var password = $('#password').val();

        // Username validation
        var usernameRegex = /^[a-zA-Z_\-][a-zA-Z0-9_\-]*$/;
        if (!usernameRegex.test(username)) {
            alert('Username can only contain letters, numbers, underscores, and dashes. It cannot start with a digit.');
            return false;
        }

        // Password validation
        if (password.length < 8) {
            alert('Password must be at least 8 characters long.');
            return false;
        }

        if (!(/[a-z]/.test(password) && /[A-Z]/.test(password) && /[0-9]/.test(password) && /[^a-zA-Z0-9]/.test(password))) {
            alert('Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character.');
            return false;
        }
        return true;
    }

    // AJAX function to submit the form data
    $('#signup-form').submit(function(e) {
        e.preventDefault(); // Prevent default form submission

        // Validate the form
        if (!validateForm()) {
            return false; // If validation fails, do not proceed further
        }

        // AJAX request
        $.ajax({
            type: 'POST',
            url: 'addUser', // Specify your server endpoint
            data: $("#signup-form").serialize(),
            success: function(response) {
                // Handle success response from the server
                console.log("Form submitted successfully");
                // Optionally, redirect the user or show a success message
                window.location.href = 'login';
            },
            error: function(xhr, status, error) {
                // Handle errors
                console.error("Form submission failed:",error);
                // Optionally, display an error message to the user
            }
        });
    });
});
