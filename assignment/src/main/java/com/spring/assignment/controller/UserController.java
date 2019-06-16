package com.spring.assignment.controller;

import com.spring.assignment.model.User;
import com.spring.assignment.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public User getAllUser(@RequestParam(required = false, defaultValue = "1") Integer pageNum) {
        List<User> allUsers = userService.getAllUsers(pageNum);
        User user;
        if (allUsers.isEmpty()) {
            user = new User();
            user.setErrMsg("No records found!");
            return user;
        }
        user = new User();
        user.setUsersDetails(allUsers);
        return user;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        if (user == null) {
            user = new User();
            user.setErrMsg("user details not found!");
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createUser(@RequestBody User user) {
        String validate = validateUser(user);
        if (validate != null) {
            return validate;
        }
        userService.createUser(user);
        return "User details created/updated successfully.";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "Successfully deleted the User details.";
    }

    @RequestMapping(value = "/search/{text}", method = RequestMethod.GET)
    public User searchUsers(@PathVariable String text) {
        List<User> allUsers = userService.searchUsers(text);
        User user;
        if (allUsers.isEmpty()) {
            user = new User();
            user.setErrMsg("No records found!");
            return user;
        }
        user = new User();
        user.setUsersDetails(allUsers);
        return user;
    }

    private String validateUser(User user) {
        if (user.getSummary() != null && !user.getSummary().matches("^[a-zA-Z0-9]{1,1000}$")) {
            return "Summary can be between 1-1000 characters";
        } else if(user.getHeadline() != null && !user.getHeadline().matches("^[a-zA-Z0-9]{1,140}$")){
            return "Headline can be between 1-1000 characters";
        }
        return null;
    }
}
