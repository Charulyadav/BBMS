package com.BloodBank.Controller;

import com.BloodBank.Model.BloodRequestModel;
import com.BloodBank.Model.UserModel;
import com.BloodBank.Service.BloodRequestService;
import com.BloodBank.Service.BloodStockService;
import com.BloodBank.Service.UserService;
import com.BloodBank.dto.BloodRequestDTO;
import com.BloodBank.dto.CoinRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession session;

    @Autowired
    private BloodRequestService bloodRequestService;

    @Autowired
    private BloodStockService bloodStockService;

    @RequestMapping("/endUserRequestStatus")
    public ResponseEntity<List<HashMap<String, Object>>> endUserRequestStatus() {
        List<HashMap<String, Object>> bloodBankRequests = bloodRequestService.endUserRequestStatus(((UserModel) session.getAttribute("userLoggedIn")).getUsername());
        return ResponseEntity.ok(bloodBankRequests);
    }

    @PostMapping("/submitRequest")
    public ResponseEntity<?> submitRequest(@RequestBody BloodRequestDTO bloodRequestDTO) {
        UserModel user = (UserModel) session.getAttribute("userLoggedIn");
        if (user != null) {
            // Check if the user is donating blood
            if (bloodRequestDTO.getType().equalsIgnoreCase("donate")) {
                // Check if the user has a pending request for donating blood
                if (bloodRequestService.hasPendingDonationRequest(user.getUsername())) {
                    return ResponseEntity.badRequest().body("You already have a pending request for donating blood. You cannot add a new request.");
                }

                // Proceed with the 3-months check for donation requests
                Optional<LocalDateTime> lastDonationDate = bloodRequestService.getLastDonationDate(user.getUsername());
                if (lastDonationDate.isPresent()) {
                    LocalDateTime now = LocalDateTime.now();
                    if (lastDonationDate.get().plusMonths(3).isAfter(now)) {
                        return ResponseEntity.badRequest().body("You can donate blood only after 3 months of your last donation.");
                    }
                }
            } else {
                // Check if the user has sufficient coins to request receiving blood
                if (!userService.canReceive(user.getUsername(), bloodRequestDTO)) {

                    return ResponseEntity.badRequest().body("Insufficient coins.");
                }

                // Check if the user has a pending request for receiving blood of the same blood group
                if (bloodRequestService.hasPendingReceivingRequestForBloodGroup(user.getUsername(), bloodRequestDTO.getBloodGroup())) {
                    return ResponseEntity.badRequest().body("You already have a pending request for receiving blood of the same blood group. You cannot add a new request.");
                }
            }

            // Proceed with adding the request
            bloodRequestDTO.setUserId(user);
            bloodRequestService.addRequest(bloodRequestDTO);
            BloodRequestModel newRequest = bloodRequestService.getLastRequestByUsername(user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(newRequest);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }
    }

    @PostMapping("/addCoins")
    public ResponseEntity<?> addCoinRequest(@RequestBody CoinRequestDTO coinRequestDTO) {
        UserModel user = (UserModel) session.getAttribute("userLoggedIn");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in.");
        }

        int coinRequestCount = user.getCoinRequestCount();
        if (coinRequestCount >= 3) {
            return ResponseEntity.badRequest().body("You have exceeded the maximum number of coin requests.");
        }

        if (coinRequestDTO.getCoinType().equalsIgnoreCase("deposit")) {
            if (coinRequestDTO.getAmount() <= 100) {
                userService.addCoins(user.getUsername(), coinRequestDTO);
                userService.updateCoinRequestCount(user.getUsername());
                return ResponseEntity.ok().body(user);
            } else {
                return ResponseEntity.badRequest().body("Coin request can be at most 100 coins.");
            }
        } else if (coinRequestDTO.getCoinType().equalsIgnoreCase("withdrawal")) {
            int userCoins = userService.getUserCoins(user.getUsername());
            if (userCoins >= 200) {
                // coinRequestService.subtractCoins(coinRequestDTO);
                userService.updateCoinRequestCount(user.getUsername());
                System.out.println(user.getCoinRequestCount());
                return ResponseEntity.ok().body(coinRequestDTO);
            } else {
                return ResponseEntity.badRequest().body("Insufficient coins. You need at least 200 coins to withdraw.");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid coin type.");
        }
    }

}

