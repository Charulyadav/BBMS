package com.BloodBank.Service;

import com.BloodBank.Model.BloodRequestModel;
import com.BloodBank.Model.BloodStockModel;
import com.BloodBank.Model.UserModel;
import com.BloodBank.Repository.BloodStockRepository;
import com.BloodBank.Repository.UserRepository;
import com.BloodBank.dto.BloodRequestDTO;
import com.BloodBank.dto.CoinRequestDTO;
import com.BloodBank.dto.UserSignUpDTO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BloodStockRepository bloodStockRepository;

    public void addUser(UserSignUpDTO userSignUpDTO,String role,String createdBy) {
        UserModel userModel = new UserModel();
        userModel.setUsername(userSignUpDTO.getUsername());
        if(userSignUpDTO.getPassword()==null)
            userModel.setPassword(String.valueOf(userSignUpDTO.getDob()));
        else
            userModel.setPassword(userSignUpDTO.getPassword());
        userModel.setName(userSignUpDTO.getName());
        userModel.setPhone(userSignUpDTO.getPhone());
        userModel.setDob(userSignUpDTO.getDob());
        userModel.setRole(role);
        if(Objects.equals(role, "EU"))
        {
            userModel.setCreatedBy(createdBy);
            if(createdBy.equals("auto"))
            {
                userModel.setModifyBy("self");
                userModel.setFirstTimeLogin(false);
                userModel.setPetName(userSignUpDTO.getPetName());
                userModel.setHobby(userSignUpDTO.getHobby());
                userModel.setDish(userSignUpDTO.getDish());
                userModel.setProfileUrl(userSignUpDTO.getProfileUrl());
            }
            else
            {
                userModel.setModifyBy(createdBy);
                userModel.setPetName("None");
                userModel.setHobby("None");
                userModel.setDish("None");
                userModel.setProfileUrl("None");
            }
        }
        else if (Objects.equals(role, "AG"))
        {
            userModel.setCreatedBy(createdBy);
            userModel.setModifyBy("admin");
            userModel.setCommission(10L);
            userModel.setPetName("None");
            userModel.setHobby("None");
            userModel.setDish("None");
        }
        userModel.setCreated_date_time(LocalDateTime.now());
        userModel.setUpdated_date_time(LocalDateTime.now());
        userModel.setAddress(userSignUpDTO.getAddress());
        userModel.setBloodGroup(userSignUpDTO.getBloodGroup());
        userModel.setCoins(0);
        userModel.setWrongAttemptCount(0);
        userRepository.save(userModel);
    }

    public boolean isUsernameTaken(String username) {

        Optional<UserModel> userOptional = Optional.ofNullable(userRepository.findByUsername(username));

        // If a user with the provided username exists, return true; otherwise, return false
        return userOptional.isPresent();
    }

    public boolean isUserExists(String username) {
        UserModel user = userRepository.findByUsername(username);
        return user != null;
    }

    public void updateCoins(String username, String bloodGroup, int quantity, String type) {
        // Step 1: Retrieve the user by ID
        UserModel user = userRepository.findByUsername(username);
        UserModel admin = userRepository.findByUsername("admin");

        // Step 2: Update coins based on blood group and quantity
        int userCoins = 0;
        int adminCoins = 0;
        BloodStockModel stock = bloodStockRepository.findByBloodGroup(bloodGroup)
                .orElseThrow(() -> new NoSuchElementException("Blood group not found"));
        if (type.equals("receive"))
            userCoins += quantity * stock.getReceiveCoinsPerUnit();
        else
            userCoins += quantity * stock.getDonateCoinsPerUnit(); // update user coins

        if (!user.getCreatedBy().equals("auto")) {
            String agentUsername = user.getCreatedBy();
            UserModel agent = userRepository.findByUsername(agentUsername);

            if (agent != null) {
                double agentCommission = agent.getCommission(); // Agent's commission percentage
                int agentCoins = (int) ((agentCommission*0.01)  * userCoins); // Agent's coins equal to the percentage of its commission
                agent.setCoins(agent.getCoins() + agentCoins);
                adminCoins = userCoins - agentCoins; // Admin's coins will be the difference
                userRepository.save(agent); // Save the updated agent
            }
        } else {
            adminCoins = userCoins; // If the user is not created by any agent, all coins go to admin
        }

        // Step 3: Update coins in the UserModel
        if (type.equals("donate")) {
            user.setCoins(user.getCoins() + userCoins);
            admin.setCoins(admin.getCoins() + adminCoins);
        } else {
            user.setCoins(user.getCoins() - userCoins);
            admin.setCoins(admin.getCoins() - adminCoins);
        }

        // Save the updated admin and user
        userRepository.save(admin);
        userRepository.save(user);
    }


    public void updateUserPassword(String newPassword, String username) {
        userRepository.updatePasswordByUsername(newPassword, username);
        updateNewUserLogin(false,username);
    }

    public void updateSecurityQuestionAnswer(String username,String petName,String hobby, String dish,String profileUrl)
    {
        userRepository.updateSecurityQuestionAndAnswer(username,petName,hobby,dish,profileUrl);
    }

    public void  updateNewUserLogin(boolean firstTimeLogin, String username){
        userRepository.updateFirstTimeLoginByUsername(firstTimeLogin, username);
    }

    public void updateBlockedStatus(boolean blockedStatus,String username){
        userRepository.updateUserBlockedStatusByUsername(blockedStatus,username);
    }

    public UserModel authenticateUser(String username) {

        return userRepository.findByUsername(username);
    }

    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    private List<UserModel> filterByDateRange(List<UserModel> userList, LocalDate startDate, LocalDate endDate) {
        return userList.stream()
                .filter(user -> {
                    LocalDateTime userDateTime = user.getCreated_date_time();
                    LocalDate userDate = userDateTime.toLocalDate();
                    return !userDate.isBefore(startDate) && !userDate.isAfter(endDate);
                })
                .collect(Collectors.toList());
    }

    public void updateWrongAttemptCount(String username) {
        UserModel user = userRepository.findByUsername(username);
        Integer currentAttemptCount = user.getWrongAttemptCount();

        // Increment the wrong attempt count by 1
        int newAttemptCount = currentAttemptCount + 1;

        // Update the wrong attempt count for the user
        userRepository.updateWrongAttemptCount(username,newAttemptCount);

    }

    public boolean isValidUser(String username, String petName, String hobby,String dish) {
        UserModel user = userRepository.findByUsername(username);

        // Check if the user exists and if the security question and answer match
        return user != null && user.getPetName().equalsIgnoreCase(petName)
                && user.getHobby().equalsIgnoreCase(hobby)
                && user.getDish().equalsIgnoreCase(dish);
    }

    public int getUserCoins(String username) {
        return userRepository.findByUsername(username).getCoins();
    }

    public boolean deleteUserById(String Id) {
        if(userRepository.existsById(Id))
        {
            userRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    public UserModel getUserById(String userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        return userOptional.orElse(null); // Return null if user is not found
    }

    public UserModel saveUser(UserModel user) {
        // Save the user object using the repository
        return userRepository.save(user);
    }

    public UserModel getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserModel> getAllAgentUsers(String agent) {
        List<UserModel> allUsers = userRepository.findAll();
        List<UserModel> agentUsers = new ArrayList<>();
        for(UserModel user : allUsers)
        {
            if(user.getCreatedBy().equals(agent))
            {
                agentUsers.add(user);
            }
        }
        return agentUsers;

    }


    public boolean canReceive(String username, BloodRequestDTO bloodRequestDTO) {
        UserModel user = userRepository.findByUsername(username);
        BloodStockModel bloodGroup=bloodStockRepository.findByBloodGroup(bloodRequestDTO.getBloodGroup())
                .orElseThrow(() -> new NoSuchElementException("Blood group not found"));;
        int rate = bloodGroup.getReceiveCoinsPerUnit();
        return bloodRequestDTO.getQuantity()*rate<user.getCoins();

    }

    public void addCoins(String username, CoinRequestDTO coinRequestDTO)
    {
        UserModel user = userRepository.findByUsername(username);
        int coins = user.getCoins();
        coins+=coinRequestDTO.getAmount();
        user.setCoins(coins);
        userRepository.save(user);
    }

    public void updateCoinRequestCount(String username) {
        UserModel user = userRepository.findByUsername(username);
        Integer currentCoinRequestCount = user.getCoinRequestCount();

        // Increment the wrong attempt count by 1
        int newCoinRequestCount = currentCoinRequestCount + 1;

        // Update the wrong attempt count for the user
        userRepository.updateCoinRequestCount(username,newCoinRequestCount);

    }

    public UserModel updateUserByUserId(String username, UserModel userData) {
        UserModel user = userRepository.findByUsername(username);

        user.setName(userData.getName());
        user.setUsername(userData.getUsername());
        user.setCreatedBy(userData.getCreatedBy());
        user.setRole(userData.getRole());
        user.setDob(userData.getDob());
        user.setBloodGroup(userData.getBloodGroup());
        // Save the updated user object
        userRepository.save(user);
        return user;
    }
}
