package se.lexicon.g45_todo_api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import se.lexicon.g45_todo_api.model.dto.UserDto;
import se.lexicon.g45_todo_api.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired UserService userService;


    //POST  http://localhost:8080/api/v1/user/
    //request body { username: user, password: 123456, rolses: {id: 1, name: ADMIN,...}}
    //@RequestMapping(path = "/", method = RequestMethod.POST)

    @PostMapping("/")
    public ResponseEntity<UserDto> signup(@RequestBody UserDto dto){
        System.out.println("USERNAME: " + dto.getUsername());
       UserDto serviceResult = userService.register(dto);
       return ResponseEntity.status(HttpStatus.CREATED).body(serviceResult);
    }


    //GET  http://localhost:8080/api/v1/user/admin
    //   /{username}    search - path variable - GET
    // todo:
    @GetMapping("/{username}")
    public ResponseEntity<Map<String, Object>> findByUsername(@PathVariable("username") String username){
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }



    //   /{username}    disable user - path variable - PUT
    // todo:
    @PutMapping("/disable")
    public ResponseEntity<Void> disableUserByUsername(@RequestParam("username") String username){
        userService.disableUserByUsername(username);
        return ResponseEntity.noContent().build();
    }


}
