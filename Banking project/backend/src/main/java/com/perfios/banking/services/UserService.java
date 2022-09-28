package com.perfios.banking.services;


import com.perfios.banking.domain.User;
import com.perfios.banking.dto.*;
import com.perfios.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public UserDTOResponse addUser(UserDTO userDTO,MultipartFile file)  throws IOException{
        List<User> users = userRepository.findAll();
        for (User eachUser : users) {
            if (eachUser.getUserName().equals(userDTO.getUserName())) {
                throw new EntityExistsException("User with user name: " + userDTO.getUserName() + " already exists.");
            }
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        User user = new User(userDTO.getUserName(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getPassword(), userDTO.getAddress(), userDTO.getEmail(), userDTO.getPhoneNumber(),userDTO.getRole(),userDTO.getDob(),userDTO.getCibilScore(),userDTO.getMonthlySalary(),fileName, file.getContentType(), file.getBytes());

         userRepository.save(user);
         UserDTOResponse userDTOResponse = new UserDTOResponse();
         userDTOResponse.setMessage("Successful");
         userDTOResponse.setUser(user);

         return userDTOResponse;


    }
//    public User store(MultipartFile file,Integer userId) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        User FileDB = new User(userId,fileName, file.getContentType(), file.getBytes());
//
//        return userRepository.save(FileDB);
//    }
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(int userId){
        User user;
        try{
            user=userRepository.findById(userId).get();
        }catch (NoSuchElementException e){
            throw new EntityNotFoundException("No user with id " + userId + " found.");
        }
        return user;
    }

    public UpdateUserDTO updateUser(User user){
        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        User userOld;
        try {
            userOld=userRepository.findById(user.getUserId()).get();
        }catch (NoSuchElementException e){
            throw new EntityNotFoundException("No user with user id "+user.getUserId()+" found.");
        }

        userOld.setUserName(user.getUserName());
        userOld.setFirstName(user.getFirstName());
        userOld.setLastName(user.getLastName());
        userOld.setEmail(user.getEmail());
        userOld.setAddress(user.getEmail());
        userOld.setPhoneNumber(user.getPhoneNumber());
        userOld.setPassword(user.getPassword());
        userOld.setRole(user.getRole());
        userOld.setDob(user.getDob());
        userOld.setCibilScore(user.getCibilScore());
        userOld.setMonthlySalary(user.getMonthlySalary());
        userRepository.save(userOld);
        updateUserDTO.setMessage("Successful");
        updateUserDTO.setUser(userOld);
        return  updateUserDTO;

    }


    public User updatePassword(UserPasswordDTO userPasswordDTO){
        User userOld;
        try {
            userOld=userRepository.findByUserName(userPasswordDTO.getUserName());
        }catch (NoSuchElementException e){
            throw new EntityNotFoundException("No user with user id "+ userPasswordDTO.getUserName()+" found.");
        }
        userOld.setPassword(userPasswordDTO.getPassword());
        userRepository.save(userOld);
        return userOld;
    }

    public User updateName(UpdateNameDTO updateNameDTO){
        User user;
        try{
            user=userRepository.findById(updateNameDTO.getUserid()).get();
        }catch (NoSuchElementException e){
            throw new EntityNotFoundException("No user with user id "+ updateNameDTO.getUserid()+" found.");
        }
        user.setFirstName(updateNameDTO.getFirstName());
        user.setLastName(updateNameDTO.getLastName());
        return userRepository.save(user);

    }

    public User updateUserName(UpdateUserNameDTO updateUserNameDTO){
         User user;
         try{
             user= userRepository.findById(updateUserNameDTO.getUserId()).get();
         }catch (NoSuchElementException ex){
             throw new EntityNotFoundException("No user with user id "+ updateUserNameDTO.getUserId()+" found.");
         }
         user.setUserName(updateUserNameDTO.getUserName());
         return userRepository.save(user);

    }

    public User updateAddress(UpdateAddressDTO updateAddressDTO){
        User user;
        try{
            user= userRepository.findById(updateAddressDTO.getUserId()).get();
        }catch (NoSuchElementException ex){
            throw new EntityNotFoundException("No user with user id "+ updateAddressDTO.getUserId()+" found.");
        }
        user.setAddress(updateAddressDTO.getAddress());
        return userRepository.save(user);

    }

    public User updateEmailId(UpdateEmailDTO updateEmailDTO){
        User user;
        try{
            user= userRepository.findById(updateEmailDTO.getUserId()).get();
        }catch (NoSuchElementException ex){
            throw new EntityNotFoundException("No user with user id "+ updateEmailDTO.getUserId()+" found.");
        }
        user.setEmail(updateEmailDTO.getEmailId());
        return userRepository.save(user);


    }

    public User updatePhoneNumber(UpdatePhoneNumberDTO updatePhoneNumberDTO){
        User user;
        try{
            user= userRepository.findById(updatePhoneNumberDTO.getUserId()).get();
        }catch (NoSuchElementException ex){
            throw new EntityNotFoundException("No user with user id "+ updatePhoneNumberDTO.getUserId()+" found.");
        }
        user.setPhoneNumber(updatePhoneNumberDTO.getPhoneNumber());
        return userRepository.save(user);


    }

    public LoginResponseDTO login(LoginDTO loginDTO){
        User user;

        LoginResponseDTO loginResponseDTO =new LoginResponseDTO();
        try{
            user = userRepository.findByUserNameAndPassword(loginDTO.getUserName(),loginDTO.getPassword());
            user.getUserName();
        }catch (NullPointerException ex){
            throw new EntityNotFoundException("Invalid Credentials");
        }

        loginResponseDTO.setUserId(user.getUserId());
        loginResponseDTO.setMessage("successful");
        loginResponseDTO.setRole(user.getRole());

        return loginResponseDTO;
    }




    public User getFile(Integer id) {
        return userRepository.findById(id).get();
    }
    public byte[] getFiles(Integer id) {
        return userRepository.findById(id).get().getDocumentData();
    }


    public Stream<User> getAllFiles() {
        return userRepository.findAll().stream();
    }




}
