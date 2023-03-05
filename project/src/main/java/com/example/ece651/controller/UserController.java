package com.example.ece651.controller;


import com.example.ece651.domain.User;
import com.example.ece651.service.UserServiceImpl;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Map<String,String> user){
        User user1 = new User();

        String email = user.get("email");
        String username = user.get("username");
        String fullname = user.get("fullname");
        String password = user.get("password");

        if (username == "" || password == "" || fullname == "" || email == "") {
            return new ResponseEntity<>("Error NUll value", HttpStatus.UNAUTHORIZED);
        }

        List<User> userlist1 = userService.FindUserByUsername(username);
        if(userlist1.size() != 0){
            return new ResponseEntity<>("username duplicate", HttpStatus.UNAUTHORIZED);
        }
        List<User> userlist2 = userService.FindUserByEmail(email);
        if(userlist2.size() != 0){
            return new ResponseEntity<>("email duplicate", HttpStatus.UNAUTHORIZED);
        }
        //user1.setId(user.get("id"));
        user1.setEmail(email);
        user1.setUsername(username);
        user1.setPassword(password);
        user1.setFullname(fullname);
        userService.AddUser(user1);
        return new ResponseEntity<>("register successful", HttpStatus.OK);
    }

    @PostMapping("/login" )
    public ResponseEntity<String> loginUser(@RequestBody Map<String,String> user){
        String username = user.get("username");
        String password = user.get("password");
        if (username == null || password == null) {
            return new ResponseEntity<>("Error NUll value", HttpStatus.UNAUTHORIZED);
        }
        List<User> userlist = userService.FindUserByUsername(username);
        for(int i=0;i<userlist.size();i++){
            User current_user = userlist.get(i);
            String cur_username = current_user.getUsername();
            String cur_password = current_user.getPassword();
            //System.out.println(password);
            //System.out.println(cur_username+" "+cur_password);
            if (cur_password.equals(password)){
                return new ResponseEntity<>("login successful by username", HttpStatus.OK);
            }
        }

        List<User> userlist1 = userService.FindUserByEmail(username);
        for(int i=0;i<userlist1.size();i++){
            User current_user = userlist1.get(i);
            //String cur_username = current_user.getUsername();
            String cur_password = current_user.getPassword();
            //System.out.println(password);
            //System.out.println(cur_username+" "+cur_password);
            if (cur_password.equals(password)){
                return new ResponseEntity<>("login successful by email", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Not found this user in the system", HttpStatus.UNAUTHORIZED);
    }




}
