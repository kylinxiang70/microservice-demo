package org.kylin.authcenter.service;

import org.kylin.authcenter.dto.RoleDto;

public interface RoleService {
    void saveRole(String id, RoleDto dto);
}
