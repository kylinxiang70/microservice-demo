package org.kylin.authcenter.service.impl;

import org.kylin.authcenter.constant.InfoConstant;
import org.kylin.authcenter.dto.RoleDto;
import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.exception.UserOperationException;
import org.kylin.authcenter.repository.UserRepository;
import org.kylin.authcenter.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveRole(String id, RoleDto dto) {
        String checkedId = Optional.ofNullable(id).orElseThrow(() -> new UserOperationException(
                MessageFormat.format(InfoConstant.PROPERTIES_CANNOT_BE_EMPTY_1, InfoConstant.ID)));
        User temp = userRepository.findById(checkedId)
                .orElseThrow(() -> new UserOperationException(
                        MessageFormat.format(InfoConstant.USER_IS_NOT_EXIST_2, InfoConstant.ID, checkedId)));
        if (dto.getRoles() == null || dto.getRoles().isEmpty()) {
            throw new RuntimeException(MessageFormat.format(
                    InfoConstant.PROPERTIES_CANNOT_BE_EMPTY_1, InfoConstant.ROLES));
        }
        temp.setRoles(dto.getRoles());
        userRepository.save(temp);
    }
}
