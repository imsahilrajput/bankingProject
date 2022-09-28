package com.perfios.banking.controller;


import com.perfios.banking.domain.Account;
import com.perfios.banking.domain.Branch;
import com.perfios.banking.domain.Card;
import com.perfios.banking.domain.User;
import com.perfios.banking.dto.*;
import com.perfios.banking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


@CrossOrigin
@RestController
public class RestApiController {
    @Autowired
    BranchService branchService;
    @Autowired
    UserService userService;
    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    CardService cardService;

    @PostMapping("/bank")
    public void addBranch(@RequestBody BranchDTO branchDTO) {
        Branch branch = new Branch(branchDTO.getBranchName(), branchDTO.getBranchAddress(), branchDTO.getIfsc());
        this.branchService.createBranch(branch);
    }

    @GetMapping("/bank")
    public List<Branch> getBranches() {
        return this.branchService.getBranches();
    }

    @GetMapping("/bank/{branchId}")
    public Branch getBranch(@PathVariable Integer branchId) {
        return this.branchService.getBranch(branchId);
    }


    @PutMapping("/bank")
    public Branch updateBranch(@RequestBody UpdateBranchDTO updateBranchDTO) {

        return this.branchService.updateBranch(updateBranchDTO);
    }

    @CrossOrigin
    @PostMapping("/user")
    public UserDTOResponse addUser(@RequestParam("file") MultipartFile file, @RequestParam("userName") String userName, @RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName, @RequestParam("password") String password, @RequestParam("address") String address, @RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("role") String role, @RequestParam("dob") String dob, @RequestParam("cibilScore") Integer cibilScore, @RequestParam("monthlySalary") Double monthlySalary) throws IOException, ParseException {
//        User user = new User(userDTO.getUserName(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword(), userDTO.getAddress(), userDTO.getEmail(), userDTO.getPhoneNumber());
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date dob1 = originalFormat.parse(dob);
        UserDTO userDTO = new UserDTO(userName,firstName,lastName,password,address,email,phoneNumber,role,dob1,cibilScore,monthlySalary);
        return this.userService.addUser(userDTO,file);
    }


    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable Integer userId) {
        return userService.getUser(userId);
    }


    @CrossOrigin
    @PutMapping("/user")
    public UpdateUserDTO updateUser(@RequestBody User user) {
        return this.userService.updateUser(user);
    }

    @PutMapping("/user/changePassword")
    public User updatePassword(@RequestBody UserPasswordDTO userPasswordDTO) {
        return this.userService.updatePassword(userPasswordDTO);
    }

    @PostMapping("/account")
    public Account createAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @PutMapping("/account")
    public Account changeBranch(@RequestBody ChangeBranchDTO changeBranchDTO) {
        return this.accountService.changeBranch(changeBranchDTO);
    }

    @CrossOrigin
    @PostMapping("/transaction")
    public TransactionResponseDTO addTransaction(@RequestBody TransactionDTO transactionDTO) {
        return this.transactionService.transaction(transactionDTO);
    }

    @CrossOrigin
    @PostMapping("/withdraw")
    public Map<String, String> debit(@RequestBody WithdrawDTO withdrawDTO) {
        this.transactionService.withdraw(withdrawDTO);
        Map<String, String> map = new HashMap<>();
        map.put("Message", "Successfully withdrawn");
        return map;
    }

    @CrossOrigin
    @PostMapping("/deposit")
    public DepositResponseDTO credit(@RequestBody DepositDTO depositDTO) {
        return this.transactionService.deposit((depositDTO));
    }

    @GetMapping("/transaction")
    public List<GetTansactionDTO> getTransactions() {
        return this.transactionService.getTransactions();
    }

    @GetMapping("/transaction/{accountNumber}")
    public List<GetTansactionDTO> getTansactionsByAccNumber(@PathVariable Integer accountNumber) {
        return this.transactionService.getTransactionsById(accountNumber);
    }

    @GetMapping("account/{userId}")
    public List<GetAccountDTO> getAccountsOfAUser(@PathVariable Integer userId) {
        return this.accountService.getAccountOfAUser(userId);
    }

    @PutMapping("/user/changeName")
    public User updateUsersName(@RequestBody UpdateNameDTO updateNameDTO) {
        return this.userService.updateName(updateNameDTO);
    }

    @PutMapping("user/changeUserName")
    public User updateUserName(@RequestBody UpdateUserNameDTO updateUserNameDTO) {
        return this.userService.updateUserName(updateUserNameDTO);
    }

    @PutMapping("user/changeAddress")
    public User updateAddress(@RequestBody UpdateAddressDTO updateAddressDTO) {
        return this.userService.updateAddress(updateAddressDTO);
    }

    @PutMapping("user/changeEmail")
    public User updateAddress(@RequestBody UpdateEmailDTO updateEmailDTO) {
        return this.userService.updateEmailId(updateEmailDTO);
    }

    @PutMapping("user/changePhoneNumber")
    public User updatePhoneNumber(@RequestBody UpdatePhoneNumberDTO updatePhoneNumberDTO) {
        return this.userService.updatePhoneNumber(updatePhoneNumberDTO);
    }

    @CrossOrigin
    @PostMapping("user/login")
    public LoginResponseDTO login(@RequestBody LoginDTO loginDTO) {
        return this.userService.login(loginDTO);
    }

    @GetMapping("transaction/user/{userId}")
    public List<GetTansactionDTO> getTransactionsOfAUser(@PathVariable Integer userId) {
        return this.transactionService.getTansactionDTOListOfAUser(userId);
    }


    @PostMapping("bank/card")
    public CardDTOResponse createCard(@RequestBody AddCardDTO addCardDTO) {
        return this.cardService.addCard(addCardDTO);
    }

    @PutMapping("bank/approve")
    public CardDTOResponse approveCard(@RequestBody Integer cardNumber) {
        return this.cardService.approveCard(cardNumber);
    }


    @CrossOrigin
    @GetMapping("bank/cards/{userId}")
    public List<Card> getAllCards(@PathVariable Integer userId){
        return this.cardService.checkForCards(userId);

    }



//    @PostMapping("/upload")
//    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file,Integer userId) {
//        String message = "";
//        try {
//            userService.store(file,userId);
//
//            message = "Uploaded the file successfully: " + file.getOriginalFilename();
//            return ResponseEntity.status(HttpStatus.OK).body("");
//        } catch (Exception e) {
//            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
//            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("");
//        }
//    }

//    @GetMapping("/files")
//    public ResponseEntity<List<ResponseFile>> getListFiles() {
//        List<ResponseFile> files = userService.getAllFiles().map(dbFile -> {
//            String fileDownloadUri = ServletUriComponentsBuilder
//                    .fromCurrentContextPath()
//                    .path("/files/")
//                    .path(dbFile.getUserId().toString())
//                    .toUriString();
//
//            return new ResponseFile(
//                    dbFile.getDocumentName(),
//                    fileDownloadUri,
//                    dbFile.getDocumentType(),
//                    dbFile.getDocumentData().length);
//        }).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(files);
//    }

    @GetMapping("/user/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable Integer id) {
        User fileDB = userService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getDocumentName() + "\"")
                .body(fileDB.getDocumentData());
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<byte[]> getFiles(@PathVariable Integer id) {
        byte[] fileDB = userService.getFiles(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB+ "\"")
                .body(fileDB);
    }
}


