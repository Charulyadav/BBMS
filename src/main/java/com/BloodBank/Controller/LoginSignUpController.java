package com.BloodBank.Controller;

import com.BloodBank.Model.UserModel;
import com.BloodBank.Service.BloodRequestService;
import com.BloodBank.Service.BloodStockService;
import com.BloodBank.Service.UserService;
import com.BloodBank.dto.UserLoginDTO;
import com.BloodBank.dto.UserSignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class LoginSignUpController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private BloodRequestService bloodRequestService;

    @Autowired
    private BloodStockService bloodStockService;

    @RequestMapping("/backToDashboard")
    public String back(Model model)
    {
        String role=((UserModel)session.getAttribute("userLoggedIn")).getRole();
        model.addAttribute("username",((UserModel) session.getAttribute("userLoggedIn")).getUsername());
        switch(role)
        {
            case "AD": return "adminDashboard";

            case "AG": return "agentDashboard";

            default: return "endUserDashboard";
        }
    }

    @PostMapping("/addUser")
    public String addUser(UserSignUpDTO userSignUpDTO, Model model) {
        // Check if the username already exists
        if (userService.isUsernameTaken(userSignUpDTO.getUsername())) {
            // Add error message to the model
            model.addAttribute("usernameError", "Username is already taken. Please choose a different username.");
            // Return the same view with the error message
            return "signup";
        }

        // If the username is not taken, proceed with adding the user
        userService.addUser(userSignUpDTO, "EU", "auto");
        model.addAttribute("successMsg", "Successfully registered.\nYou can Login Now!");
        return "homePage";
    }

    @PostMapping("/loginUser")
    public String loginUser(UserLoginDTO userLoginDTO, Model model,@RequestParam(required = false, defaultValue = "id") String sortBy,@RequestParam(required = false,defaultValue ="") String filterBy) {
        UserModel userModel=userService.authenticateUser(userLoginDTO.getUsername());
        if(userModel==null){
            model.addAttribute("errorMsg", "Username does not exist");
            return "homePage";
        }
        if(userLoginDTO.getPassword().equals(userModel.getPassword())){
            session.setAttribute("userLoggedIn",userModel);
            if (userModel.isFirstTimeLogin()) {
                model.addAttribute("username",((UserModel) session.getAttribute("userLoggedIn")).getUsername());
                return "firstLogin";
            } else if (userModel.getBlockedStatus()) {
                model.addAttribute("errorMsg", "USER BLOCKED");
                return "homePage";
            } else{
                session.setAttribute("userModel", userModel); // Set user model in session
                switch (userModel.getRole()) {
                    case "AD":
                        List<UserModel> userModelList = userService.getAllUsers();
                        model.addAttribute("userModelList", userModelList);
                        return "adminDashboard";
                    case "EU":
                        return "endUserDashboard";
                    case "AG":
                        return "agentDashboard";
                    default:
                        return "signup";
                }
            }
        }
        else {
            int attemptCount = userModel.getWrongAttemptCount();
            userService.updateWrongAttemptCount(userModel.getUsername());

            int maxAttempts = 2;
            int attemptsLeft = maxAttempts - attemptCount;

            if (attemptCount < maxAttempts) {
                model.addAttribute("errorMsg", "Invalid Password. " + attemptsLeft + " attempt(s) left.");
            } else {
                userService.updateBlockedStatus(true, userModel.getUsername());
                model.addAttribute("errorMsg", "You are Blocked!!");
            }
            return "homePage";
        }
    }

    @RequestMapping("/logout")
    public String logout() {
        session.invalidate();
        return "homePage";
    }
    @GetMapping("/forgotPassword")
    public String showForgotPasswordForm() {
        return "forgotPasswordForm"; // Assuming you have a corresponding HTML file for the forgot password form
    }

    @PostMapping("/checkValidity")
    public String checkValidity(@RequestParam String username,
                                @RequestParam String petName,
                                @RequestParam String hobby,
                                @RequestParam String dish,
                                Model model) {

        // Check if the username exists
        boolean isUserExists = userService.isUserExists(username);

        if (!isUserExists) {
            // If username does not exist, return to the forgot password form with an error message
            model.addAttribute("errorMsg", "Username does not exist.");
            return "forgotPasswordForm";
        }

        // Check if the security question and answer match
        boolean isValidUser = userService.isValidUser(username,petName,hobby,dish);

        if (isValidUser) {
            // If user is valid, return the reset password view
            model.addAttribute("username", username);
            return "resetPasswordForm";
        } else {
            // If user is not valid, return to the forgot password form with an error message
            model.addAttribute("errorMsg", "Invalid username or security question/answer.");
            return "forgotPasswordForm";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestParam String username,
                                @RequestParam String newPassword,
                                Model model)
    {
        userService.updateUserPassword(newPassword, username);
        userService.updateBlockedStatus(false,username);
        model.addAttribute("successMsg", "Password reset successful");
        return "homePage";
    }

    @PostMapping("/userFirstLogin")
    public String updatePassword(@RequestParam String username,
                                 @RequestParam String newPassword,
                                 @RequestParam String petName,
                                 @RequestParam String hobby,
                                 @RequestParam String dish,
                                 @RequestParam String profileUrl,
                                 Model model) {

        userService.updateUserPassword(newPassword, username);
        userService.updateSecurityQuestionAnswer(username,petName,hobby,dish,profileUrl);
        model.addAttribute("successMsg", "Password updated successfully");
        return "homePage";
    }

}