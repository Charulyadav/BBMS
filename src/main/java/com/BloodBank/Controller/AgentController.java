package com.BloodBank.Controller;

import com.BloodBank.Model.UserModel;
import com.BloodBank.Service.BloodRequestService;
import com.BloodBank.Service.BloodStockService;
import com.BloodBank.Service.UserService;
import com.BloodBank.dto.UserSignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AgentController {
    @Autowired
    private UserService userService;

    @Autowired
    private BloodStockService bloodStockService;

    @Autowired
    private BloodRequestService bloodRequestService;

    @Autowired
    private HttpSession session;

    @GetMapping("/agentUsersList")
    public ResponseEntity<List<UserModel>> usersList() {
        String agent=((UserModel) session.getAttribute("userLoggedIn")).getUsername();
        List<UserModel> list = userService.getAllAgentUsers(agent);
        System.out.println(list);
        return ResponseEntity.ok(list);
    }

    @PostMapping("/addAgentUser")
    public ResponseEntity<?> addUser(@RequestBody UserSignUpDTO user) {
        // Check if the username already exists
        if (userService.isUsernameTaken(user.getUsername())) {
            System.out.println(userService.isUsernameTaken(user.getUsername()));
            System.out.println(user.getUsername());
            // Add error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }

        // If the username is not taken, proceed with adding the user
        userService.addUser(user, "EU", ((UserModel) session.getAttribute("userLoggedIn")).getUsername());
        UserModel newUser = userService.getUserByUsername(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(newUser) ;
    }

    @GetMapping("/agentRequestStatus")
    public ResponseEntity<List<HashMap<String, Object>>> agentRequestStatus(@RequestBody(required = false) String endUsername) {


        List<HashMap<String, Object>> bloodBankRequests =  bloodRequestService.agentRequestStatus(
                    ((UserModel) session.getAttribute("userLoggedIn")).getUsername());
        return ResponseEntity.ok(bloodBankRequests);
    }


    @GetMapping("/agentBloodReport")
    public ResponseEntity<List<Map<String, Object>>> agentBloodReport()
    {
        List<Map<String, Object>> bloodReport = bloodRequestService.getAgentBloodReport(((UserModel) session.getAttribute("userLoggedIn")).getUsername());
        return ResponseEntity.ok(bloodReport);
    }

    @GetMapping("/agentCoinReport")
    public ResponseEntity<List<Map<String, Object>>> agentCoinReport()
    {
        List<Map<String, Object>> coinReport = bloodRequestService.getAgentCoinReport(((UserModel) session.getAttribute("userLoggedIn")).getUsername());
        return ResponseEntity.ok(coinReport);
    }

    @GetMapping("/agentCoinsByRate")
    public ResponseEntity<List<Map<String, Object>>> getAgentCoinsByRate()
    {
        List<Map<String, Object>> agentCoinsByRate = bloodRequestService.getAgentCoinsByRate(((UserModel)session.getAttribute("userLoggedIn")).getUsername());
        return ResponseEntity.ok(agentCoinsByRate);
    }

}
