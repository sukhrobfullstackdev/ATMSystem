package uz.sudev.atmsystem.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.atmsystem.annotations.CheckPermission;
import uz.sudev.atmsystem.entity.Role;
import uz.sudev.atmsystem.payload.Message;
import uz.sudev.atmsystem.payload.RoleDTO;
import uz.sudev.atmsystem.service.RoleService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/role")
public class RoleController {
    final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @CheckPermission(value = "VIEW_ROLES")
    @GetMapping(value = "/get")
    public ResponseEntity<Page<Role>> getRoles(@RequestParam int page, @RequestParam int size) {
        return roleService.getRoles(page, size);
    }

    @CheckPermission(value = "VIEW_ROLES")
    @GetMapping(value = "/get/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        return roleService.getRole(id);
    }

    @CheckPermission(value = "ADD_ROLE")
    @PostMapping(value = "/add")
    public ResponseEntity<Message> addRole(@Valid @RequestBody RoleDTO roleDto) {
        return roleService.addRole(roleDto);
    }

    @CheckPermission(value = "EDIT_ROLE")
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<Message> editRole(@PathVariable Long id, @Valid @RequestBody RoleDTO roleDto) {
        return roleService.editRole(id, roleDto);
    }

    @CheckPermission(value = "DELETE_ROLE")
    //@PreAuthorize("hasAuthority('DELETE_ROLE')")
    @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> deleteRole(@PathVariable Long id) {
        return roleService.deleteRole(id);
    }
}
