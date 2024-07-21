package com.BloodBank.Service;

import com.BloodBank.Model.BloodRequestModel;
import com.BloodBank.Model.UserModel;
import com.BloodBank.Repository.BloodRequestRepository;
import com.BloodBank.Repository.UserRepository;
import com.BloodBank.dto.BloodRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BloodRequestService {
    @Autowired
    private BloodRequestRepository bloodRequestRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BloodStockService bloodStockService;
    @Autowired
    private HttpSession session;
    @Autowired
    private UserService userService;

    public void addRequest(BloodRequestDTO bloodRequestDTO)
    {
        BloodRequestModel bloodRequestModel =new BloodRequestModel();
        bloodRequestModel.setUserId(bloodRequestDTO.getUserId());
        bloodRequestModel.setType(bloodRequestDTO.getType());
        if(bloodRequestDTO.getType().equals("receive"))
            bloodRequestModel.setBloodGroup(bloodRequestDTO.getBloodGroup());
        else
            bloodRequestModel.setBloodGroup(((UserModel)session.getAttribute("userLoggedIn")).getBloodGroup());
        bloodRequestModel.setQuantity(bloodRequestDTO.getQuantity());
        bloodRequestModel.setCreatedAt(LocalDateTime.now());
        bloodRequestModel.setUpdatedAt(LocalDateTime.now());
        bloodRequestModel.setCreatedBy("self");
        bloodRequestModel.setUpdatedBy("admin");
        bloodRequestModel.setStatus("Pending");
        bloodRequestModel.setUsername(((UserModel)session.getAttribute("userLoggedIn")).getUsername());
        bloodRequestModel.setAgent(((UserModel)session.getAttribute("userLoggedIn")).getCreatedBy());
        if(bloodRequestModel.getAgent().equals("auto"))
            bloodRequestModel.setAgent("-");
        bloodRequestModel.setDob(((UserModel)session.getAttribute("userLoggedIn")).getDob());
        bloodRequestModel.setRemark("None");
        if(bloodRequestModel.getRemark().equals("null"))
            bloodRequestModel.setRemark("None");
        bloodRequestRepository.save(bloodRequestModel);
    }

    public List<HashMap<String, Object>> getAllRequests(){
        List<BloodRequestModel> list = bloodRequestRepository.findAll();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (BloodRequestModel entity : list) {
            HashMap<String, Object> bloodResult = new HashMap<>();

            bloodResult.put("id", entity.getRequestId());
            bloodResult.put("username", entity.getUsername());
            bloodResult.put("createdAt", entity.getCreatedAt());
            bloodResult.put("createdBy", entity.getCreatedBy());
            bloodResult.put("status", entity.getStatus());
            bloodResult.put("agent", entity.getAgent());
            bloodResult.put("type", entity.getType());
            bloodResult.put("bloodGroup", entity.getBloodGroup());
            bloodResult.put("dob", entity.getDob());
            bloodResult.put("quantity", entity.getQuantity());
            bloodResult.put("remark",entity.getRemark());
            // Age Calculation
//            Date birthDate = entity.getDob();
//            LocalDate now = LocalDate.now();
//            LocalDate birthLocalDate = birthDate.toLocalDate();
//            int age = now.getYear() - birthLocalDate.getYear();
//            if (now.getMonthValue() < birthLocalDate.getMonthValue() ||
//                    (now.getMonthValue() == birthLocalDate.getMonthValue() &&
//                            now.getDayOfMonth() < birthLocalDate.getDayOfMonth())) {
//                age--;
//            }
//            bloodResult.put("age", age);

            hashMapList.add(bloodResult);
        }
        return hashMapList;
    }

    public List<HashMap<String, Object>> getFilteredAllRequests(String startDate, String endDate, String agentUsername, String endUserUsername) {
        List<BloodRequestModel> filteredList = bloodRequestRepository.findAll();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();

        for (BloodRequestModel entity : filteredList) {
            if ((startDate == null || startDate.isEmpty() || entity.getCreatedAt().toLocalDate().compareTo(LocalDate.parse(startDate)) >= 0) &&
                    (endDate == null || endDate.isEmpty() || entity.getCreatedAt().toLocalDate().compareTo(LocalDate.parse(endDate)) <= 0) &&
                    (agentUsername == null || agentUsername.isEmpty() || entity.getUserId().getCreatedBy().equals(agentUsername)) &&
                    (endUserUsername == null || endUserUsername.isEmpty() || entity.getUserId().getUsername().equals(endUserUsername))) {

                HashMap<String, Object> bloodResult = new HashMap<>();
                bloodResult.put("id", entity.getRequestId());
                bloodResult.put("username", entity.getUserId().getUsername());
                bloodResult.put("createdAt", entity.getCreatedAt());
                bloodResult.put("createdBy", entity.getCreatedBy());
                bloodResult.put("status", entity.getStatus());
                bloodResult.put("agent", entity.getUserId().getCreatedBy());
                bloodResult.put("type", entity.getType());
                bloodResult.put("bloodGroup", entity.getBloodGroup());
                bloodResult.put("dob", entity.getUserId().getDob());
                bloodResult.put("quantity", entity.getQuantity());
                bloodResult.put("remarks", entity.getRemark());
                // Age Calculation
//                Date birthDate = entity.getUserId().getDob();
//                LocalDate now = LocalDate.now();
//                LocalDate birthLocalDate = birthDate.toLocalDate();
//                int age = now.getYear() - birthLocalDate.getYear();
//                if (now.getMonthValue() < birthLocalDate.getMonthValue() ||
//                        (now.getMonthValue() == birthLocalDate.getMonthValue() &&
//                                now.getDayOfMonth() < birthLocalDate.getDayOfMonth())) {
//                    age--;
//                }
//                bloodResult.put("age", age);
                hashMapList.add(bloodResult);
            }
        }
        return hashMapList;
    }

    public Optional<LocalDateTime> getLastDonationDate(String username)
    {
        return bloodRequestRepository.findTopByUsernameOrderByUpdatedAtDesc(username)
                .map(BloodRequestModel::getUpdatedAt);
    }

    public void addRemarkToRequest(String requestId,String remark) {
        System.out.println(requestId);
        System.out.println(remark);
        bloodRequestRepository.addRemark(requestId,remark);
    }


    public void updateStatus(String requestId, String status) {
        bloodRequestRepository.updateStatus(requestId, status);
    }


    public void updateTime(String requestId) {
        bloodRequestRepository.updateTime(requestId, LocalDateTime.now());
    }

    public List<HashMap<String, Object>> endUserRequestStatus(String username)
    {
        List<BloodRequestModel> list = bloodRequestRepository.findAll();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (BloodRequestModel entity : list) {
            HashMap<String, Object> bloodResult = new HashMap<>();
            if(username.equals(entity.getUsername()))
            {
                bloodResult.put("id", entity.getUserId());
                bloodResult.put("username", entity.getUsername());
                bloodResult.put("createdAt", entity.getCreatedAt());
                bloodResult.put("status", entity.getStatus());
                bloodResult.put("agent", entity.getAgent());
                bloodResult.put("type", entity.getType());
                bloodResult.put("bloodGroup", entity.getBloodGroup());
                bloodResult.put("dob", entity.getDob());
                bloodResult.put("quantity", entity.getQuantity());
                bloodResult.put("remark",entity.getRemark());
            }
            else
                continue;

            hashMapList.add(bloodResult);
        }
        return hashMapList;
    }

    public List<HashMap<String, Object>> filteredEURequests(String startDate, String endDate, String username) {
        List<BloodRequestModel> filteredList = bloodRequestRepository.findAll();
        // Filter by start date
        if (startDate != null && !startDate.isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(request -> request.getCreatedAt().toLocalDate().compareTo(LocalDate.parse(startDate)) >= 0)
                    .collect(Collectors.toList());
        }
        // Filter by end date
        if (endDate != null && !endDate.isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(request -> request.getCreatedAt().toLocalDate().compareTo(LocalDate.parse(endDate)) <= 0)
                    .collect(Collectors.toList());
        }
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (BloodRequestModel entity : filteredList) {
            HashMap<String, Object> bloodResult = new HashMap<>();
            if(username.equals(entity.getUserId().getUsername()))
            {
                bloodResult.put("id", entity.getRequestId());
                bloodResult.put("username", entity.getUserId().getUsername());
                bloodResult.put("createdAt", entity.getCreatedAt());
                bloodResult.put("status", entity.getStatus());
                bloodResult.put("user_createdBy", entity.getCreatedBy());
                bloodResult.put("type", entity.getType());
                bloodResult.put("bloodGroup", entity.getBloodGroup());
                bloodResult.put("quantity", entity.getQuantity());
                bloodResult.put("remark",entity.getRemark());
            }
            else
                continue;
            hashMapList.add(bloodResult);
        }
        return hashMapList;
    }

    public List<HashMap<String, Object>> agentRequestStatus(String agentUsername)
    {
        List<BloodRequestModel> list = bloodRequestRepository.findAll();
        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (BloodRequestModel entity : list) {
            HashMap<String, Object> bloodResult = new HashMap<>();
            if(agentUsername.equals(entity.getAgent()))
            {
                bloodResult.put("id", entity.getUserId());
                bloodResult.put("username", entity.getUsername());
                bloodResult.put("createdAt", entity.getCreatedAt());
                bloodResult.put("status", entity.getStatus());
                bloodResult.put("agent", entity.getAgent());
                bloodResult.put("type", entity.getType());
                bloodResult.put("bloodGroup", entity.getBloodGroup());
                bloodResult.put("dob", entity.getDob());
                bloodResult.put("quantity", entity.getQuantity());
                bloodResult.put("remark",entity.getRemark());
            }
            else
                continue;

            hashMapList.add(bloodResult);
        }
        return hashMapList;

    }

    public List<HashMap<String, Object>> filteredAgentRequests(String startDate, String endDate,String endUsername, String username) {
        List<BloodRequestModel> filteredList = bloodRequestRepository.findAll();
        // Filter by start date
        if (startDate != null && !startDate.isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(request -> request.getCreatedAt().toLocalDate().compareTo(LocalDate.parse(startDate)) >= 0)
                    .collect(Collectors.toList());
        }
        // Filter by end date
        if (endDate != null && !endDate.isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(request -> request.getCreatedAt().toLocalDate().compareTo(LocalDate.parse(endDate)) <= 0)
                    .collect(Collectors.toList());
        }

        // Filter by end user username
        if (endUsername != null && !endUsername.isEmpty()) {
            filteredList = filteredList.stream()
                    .filter(request -> request.getUserId().getUsername().equals(endUsername))
                    .collect(Collectors.toList());
        }

        List<HashMap<String, Object>> hashMapList = new ArrayList<>();
        for (BloodRequestModel entity : filteredList) {
            HashMap<String, Object> bloodResult = new HashMap<>();
            if(username.equals(entity.getUserId().getUsername()))
            {
                bloodResult.put("id", entity.getRequestId());
                bloodResult.put("username", entity.getUserId().getUsername());
                bloodResult.put("createdAt", entity.getCreatedAt());
                bloodResult.put("status", entity.getStatus());
                bloodResult.put("user_createdBy", entity.getCreatedBy());
                bloodResult.put("type", entity.getType());
                bloodResult.put("bloodGroup", entity.getBloodGroup());
                bloodResult.put("quantity", entity.getQuantity());
                bloodResult.put("remark",entity.getRemark());
            }
            else
                continue;
            hashMapList.add(bloodResult);
        }
        return hashMapList;
    }
    public List<Map<String, Object>> getAdminBloodReport() {
        List<Map<String, Object>> requestReport = new ArrayList<>();
        List<String> bloodGroups = bloodStockService.getBloodGroups();

        for (String bloodGroup : bloodGroups) {
            int coinPerUnit = bloodStockService.getCoinPerUnitByBloodGroup(bloodGroup);
            int units = bloodStockService.getUnitsByBloodGroup(bloodGroup);
            int coinValue = coinPerUnit * units;
            int acceptCount = bloodRequestRepository.countByBloodGroupAndStatus(bloodGroup, "Approved");
            int declineCount = bloodRequestRepository.countByBloodGroupAndStatus(bloodGroup, "Rejected");

            Map<String, Object> reportMap = new HashMap<>();
            reportMap.put("bloodGroup", bloodGroup);
            reportMap.put("coinValue", coinValue);
            reportMap.put("units", units);
            reportMap.put("acceptCount", acceptCount);
            reportMap.put("declineCount", declineCount);

            requestReport.add(reportMap);
        }

        return requestReport;
    }

    public List<Map<String, Object>> getAdminCoinReport() {
        List<Map<String, Object>> userCoinReport = new ArrayList<>();

        // Retrieve all requests
        List<BloodRequestModel> allRequests = bloodRequestRepository.findAll();

        // Iterate over each request
        for (BloodRequestModel request : allRequests) {
            // Get request details
            String username = request.getUsername();
            String agent = request.getAgent();
            String bloodGroup = request.getBloodGroup();
            int quantity = request.getQuantity();
            String status = request.getStatus();
            String type = request.getType();;

            // Get coin details
            int coinPerUnit = bloodStockService.getCoinPerUnitByBloodGroup(bloodGroup);
            int totalCoins = coinPerUnit * quantity;
            int userCoins = 0;
            int agentCoins = 0;
            int adminCoins = 0;


            // Check if the request is approved
            if (status.equals("Approved")) {
                userCoins=totalCoins;
                // Calculate user coins based on whether the user is created by an agent or not
                if (!agent.equals("-") ){ // If user is created by an agent
                    agentCoins = (int) (userCoins * (userRepository.findByUsername(agent).getCommission()*0.01) ); // User gets 10% of coins
                    adminCoins = userCoins - agentCoins; // Admin gets the remaining coins
                } else { // If user is not created by an agent
                    adminCoins = userCoins; // Admin gets all the coins
                }
            }

            // Construct the report map
            Map<String, Object> coinMap = new HashMap<>();
            coinMap.put("username", username);
            coinMap.put("bloodGroup", bloodGroup);
            coinMap.put("userCoins", userCoins);
            coinMap.put("agentCoins", agentCoins);
            coinMap.put("adminCoins", adminCoins);
            coinMap.put("status",request.getStatus());
            coinMap.put("type",request.getType());

            // Add the report map to the list
            userCoinReport.add(coinMap);
        }

        return userCoinReport;
    }


    public List<Map<String, Object>> getAgentBloodReport(String agentUsername) {
        List<Map<String, Object>> requestReport = new ArrayList<>();
        List<String> bloodGroups = bloodStockService.getBloodGroups();

        for (String bloodGroup : bloodGroups) {
            int totalUnits = 0;
            int totalCoinValue = 0;
            int totalAcceptCount = 0;
            int totalDeclineCount = 0;

            List<BloodRequestModel> requestsList = bloodRequestRepository.findByAgentAndBloodGroup(agentUsername, bloodGroup);

            for (BloodRequestModel request : requestsList) {
                int units = request.getQuantity();
                int coinPerUnit = bloodStockService.getCoinPerUnitByBloodGroup(bloodGroup);

                if (request.getStatus().equals("Approved")) {
                    int coinValue = coinPerUnit * units;
                    totalUnits += units;
                    totalCoinValue += coinValue;
                    totalAcceptCount++;
                } else if (request.getStatus().equals("Rejected")) {
                    totalDeclineCount++;
                }
            }

            Map<String, Object> reportMap = new HashMap<>();
            reportMap.put("bloodGroup", bloodGroup);
            reportMap.put("coinValue", totalCoinValue);
            reportMap.put("units", totalUnits);
            reportMap.put("acceptCount", totalAcceptCount);
            reportMap.put("declineCount", totalDeclineCount);

            requestReport.add(reportMap);
        }

        return requestReport;
    }

    public List<Map<String, Object>> getAgentCoinReport(String agentUsername) {
        List<Map<String, Object>> agentCoinReport = new ArrayList<>();

        // Retrieve all requests for the specified agent
        List<BloodRequestModel> agentRequests = bloodRequestRepository.findByAgent(agentUsername);

        // Iterate over each request
        for (BloodRequestModel request : agentRequests) {
            // Get request details
            String username = request.getUsername();
            String bloodGroup = request.getBloodGroup();
            int quantity = request.getQuantity();
            String status = request.getStatus();
            //UserModel agentDetails =userRepository.findByUsername(username);
            //Long commission = agentDetails.getCommission();

            // Get coin details
            int coinPerUnit = bloodStockService.getCoinPerUnitByBloodGroup(bloodGroup);
            int totalCoins = coinPerUnit * quantity;
            int userCoins = 0;
            int agentCoins = 0;
            int adminCoins = 0;

            // Check if the request is approved and created by the agent
            if (status.equals("Approved") ) {
                userCoins=totalCoins;
                // Calculate agent coins
                agentCoins = (int) (userCoins * (userRepository.findByUsername(agentUsername).getCommission()*0.01));
                // Admin gets the remaining coins
                adminCoins = userCoins - agentCoins;
            }
            // Construct the report map
            Map<String, Object> coinMap = new HashMap<>();
            coinMap.put("username", username);
            coinMap.put("bloodGroup", bloodGroup);
            coinMap.put("userCoins", userCoins);
            coinMap.put("agentCoins", agentCoins);
            coinMap.put("adminCoins", adminCoins);
            coinMap.put("status",request.getStatus());

            // Add the report map to the list
            agentCoinReport.add(coinMap);
        }

        return agentCoinReport;
    }

    public List<Map<String, Object>> getAgentCoinsByRate(String agentUsername) {
        List<Map<String, Object>> agentCoinsByRate = new ArrayList<>();
        UserModel agent = userRepository.findByUsername(agentUsername);

        // Retrieve all blood groups
        List<String> bloodGroups = bloodStockService.getBloodGroups();

        // Iterate over each blood group
        for (String bloodGroup : bloodGroups) {
            // Retrieve all requests for the specified agent and blood group
            List<BloodRequestModel> agentRequests = bloodRequestRepository.findByAgentAndBloodGroup(agentUsername, bloodGroup);

            // Calculate coins for the current blood group
            int totalUserCoins = 0;
            int totalAgentCoins = 0;
            int totalAdminCoins = 0;

            // Iterate over each request for the current blood group
            for (BloodRequestModel request : agentRequests) {
                if (request.getStatus().equals("Approved")) { // Only process approved requests
                    int quantity = request.getQuantity();
                    int coinPerUnit = bloodStockService.getCoinPerUnitByBloodGroup(bloodGroup);
                    int totalCoins = coinPerUnit * quantity;

                    Long commission = agent.getCommission();

                    // Calculate coins distribution
                    totalUserCoins += totalCoins;
                    totalAgentCoins += (int) (totalCoins * (commission*0.01));
                    totalAdminCoins += totalCoins - totalAgentCoins;
                }
            }

            // Construct the report map for the current blood group
            Map<String, Object> coinMap = new HashMap<>();
            coinMap.put("bloodGroup", bloodGroup);
            coinMap.put("userCoins", totalUserCoins);
            coinMap.put("agentCoins", totalAgentCoins);
            coinMap.put("adminCoins", totalAdminCoins);

            // Add the report map to the list
            agentCoinsByRate.add(coinMap);
        }

        return agentCoinsByRate;
    }

    public boolean hasPendingDonationRequest(String username) {
        // Assuming you have a method to find pending donation requests for a user in your repository
        List<BloodRequestModel> pendingDonationRequests = bloodRequestRepository.findByUsernameAndTypeAndStatus(username, "donate", "Pending");
        return !pendingDonationRequests.isEmpty();
    }

    public boolean hasPendingReceivingRequestForBloodGroup(String username, String bloodGroup) {
        List<BloodRequestModel> pendingRequests = bloodRequestRepository.findByUsernameAndTypeAndStatus(username, "receive", "Pending");
        for (BloodRequestModel request : pendingRequests) {
            if (request.getBloodGroup().equals(bloodGroup)) {
                return true; // There is a pending receiving request for the specified blood group
            }
        }
        return false; // No pending receiving request found for the specified blood group
    }

    public BloodRequestModel getLastRequestByUsername(String username) {
        return bloodRequestRepository.findLastRequestByUsername(username);
    }
}
