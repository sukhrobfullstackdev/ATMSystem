package uz.sudev.atmsystem.controller;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.atmsystem.annotations.CheckPermission;
import uz.sudev.atmsystem.entity.User;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.payload.UserDTO;
import uz.sudev.atmsystem.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @CheckPermission(value = "VIEW_USERS")
    @GetMapping
    public ResponseEntity<Page<User>> getUsers(@RequestParam int page, @RequestParam int size) {
        return userService.getUsers(page, size);
    }

    @CheckPermission(value = "VIEW_USERS")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @CheckPermission(value = "ADD_USER")
    @PostMapping
    public ResponseEntity<Message> addUser(@Valid @RequestBody UserDTO userDto) {
        return userService.addUser(userDto);
    }

    @CheckPermission(value = "EDIT_USER")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDto) {
        return userService.editUser(id, userDto);
    }

    @CheckPermission(value = "DELETE_USER")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }
}
