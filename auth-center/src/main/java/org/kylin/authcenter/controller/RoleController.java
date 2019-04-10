package org.kylin.authcenter.controller;

import org.kylin.authcenter.dto.RoleDto;
import org.kylin.authcenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/users/{id}/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * Save and update role
     *
     * @param id role uuid
     * @param dto roles
     * @return if success ok, else http status code 500
     */
    @PostMapping
    public ResponseEntity<Void> saveRole(@PathVariable String id, @RequestBody RoleDto dto) {
        roleService.saveRole(id, dto);
        return ok().build();
    }
}
