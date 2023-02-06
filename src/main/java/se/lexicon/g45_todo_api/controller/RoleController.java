package se.lexicon.g45_todo_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.lexicon.g45_todo_api.model.dto.RoleDto;
import se.lexicon.g45_todo_api.model.entity.Role;
import se.lexicon.g45_todo_api.service.RoleService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;


    // http://localhost:8080/api/v1/role/
    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> getAll() {
        //return ResponseEntity.ok(roleService.getAll()); // 200
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDto> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(roleService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Integer id){
        roleService.delete(id);
        //return ResponseEntity.noContent().build(); // 204
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/")
    public ResponseEntity<RoleDto> create(@RequestBody RoleDto dto){
        RoleDto createdROleDto = roleService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdROleDto); // 201
    }


    @PutMapping("/")
    public ResponseEntity<Void> update(@RequestBody RoleDto dto){
        roleService.update(dto);
        return ResponseEntity.noContent().build();
    }


}

