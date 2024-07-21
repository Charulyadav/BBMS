package com.BloodBank.Controller;

import com.BloodBank.Model.BloodStockModel;
import com.BloodBank.Model.UserModel;
import com.BloodBank.Service.BloodRequestService;
import com.BloodBank.Service.BloodStockService;
import com.BloodBank.Service.UserService;
import com.BloodBank.dto.UserSignUpDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class AdminController {
    @Autowired
    private UserService userService;

    @Autowired
    private BloodStockService bloodStockService;

    @Autowired
    private BloodRequestService bloodRequestService;

    @PostMapping("/addAgent")
    public ResponseEntity<?> addAgent(@RequestBody UserSignUpDTO agent)
    {
         //Check if the username already exists
        if (userService.isUsernameTaken(agent.getUsername())) {
            // Add error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
        }
        userService.addUser(agent,"AG","admin");
        UserModel newAgent = userService.getUserByUsername(agent.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(newAgent) ;
    }

    @GetMapping("/usersList")
    public ResponseEntity<List<UserModel>>  usersList() {
        List<UserModel> list = userService.getAllUsers();
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        System.out.println(userId);
        boolean deleted = userService.deleteUserById(userId);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No user found with ID " + userId);
    }
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @RequestBody UserModel userData) {

        System.out.println(userId);
        UserModel user = userService.getUserById(userId);

        UserModel updatedUser = userService.updateUserByUserId(user.getUsername(),userData);
        // Retrieve the user with the given userId from the database
//        UserModel user = userService.getUserById(userId);
//
//        if (user==null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Update user data with the new values
//        user.setName(userData.getName());
//        user.setUsername(userData.getUsername());
//        user.setCreatedBy(userData.getCreatedBy());
//        user.setRole(userData.getRole());
//        user.setDob(userData.getDob());
//        user.setBloodGroup(userData.getBloodGroup());
//        // Save the updated user object
//        UserModel updatedUser = userService.saveUser(user);

        // Optionally, you can return the updated user object in the response
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/bloodStock")
    public ResponseEntity<List<BloodStockModel>> bloodStock()
    {
        List<BloodStockModel> bloodStockList = bloodStockService.getBloodStock();
        return ResponseEntity.ok(bloodStockList);
    }

    @GetMapping("/adminBloodReport")
    public ResponseEntity<List<Map<String, Object>>> adminBloodReport()
    {
        List<Map<String, Object>> bloodReport = bloodRequestService.getAdminBloodReport();
        return ResponseEntity.ok(bloodReport);
    }

    @GetMapping("/adminCoinReport")
    public ResponseEntity<List<Map<String, Object>>> adminCoinReport()
    {
        List<Map<String, Object>> coinReport = bloodRequestService.getAdminCoinReport();
        return ResponseEntity.ok(coinReport);
    }

    @GetMapping("/adminBloodBankRequests")
    public ResponseEntity<List<HashMap<String, Object>>> adminBloodBankRequests(@RequestParam(required = false) String startDate,
                                                                                @RequestParam(required = false) String endDate,
                                                                                @RequestParam(required = false) String agentUsername,
                                                                                @RequestParam(required = false) String endUsername) {
        List<HashMap<String, Object>> bloodBankRequests;
        if (startDate != null || endDate != null || agentUsername != null || endUsername != null) {
            bloodBankRequests = bloodRequestService.getFilteredAllRequests(startDate, endDate, agentUsername, endUsername);
        } else {
            bloodBankRequests = bloodRequestService.getAllRequests();
        }
        return ResponseEntity.ok(bloodBankRequests);
    }

    @PostMapping("/addRemark")
    public ResponseEntity<String> addRemark(@RequestParam String requestId, @RequestParam String remark) {
        System.out.println(requestId);
        System.out.println(remark);
        try {
            bloodRequestService.addRemarkToRequest(requestId, remark);
            return ResponseEntity.ok("Remark added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding remark: " + e.getMessage());
        }
    }

    @PostMapping("/processRequest")
    public ResponseEntity<String> processRequest(
            @RequestParam String requestId,
            @RequestParam String action,
            @RequestParam(required = false) String bloodGroup,
            @RequestParam(required = false) int quantity,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String username) {
        try {
            if ("approve".equals(action)) {
                bloodRequestService.updateStatus(requestId, "Approved");
                bloodRequestService.updateTime(requestId);

                userService.updateCoins(username, bloodGroup, quantity, type);
                System.out.println("---------------");
                bloodStockService.updateStock(bloodGroup, quantity);
            } else if ("reject".equals(action)) {
                bloodRequestService.updateStatus(requestId, "Rejected");
            }
            return ResponseEntity.ok("Request processed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request: " + e.getMessage());
        }
    }

}
