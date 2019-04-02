package org.kylin.authcenter.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.kylin.authcenter.constant.AuthConstant;
import org.kylin.authcenter.constant.InfoConstant;
import org.kylin.authcenter.dto.AuthDto;
import org.kylin.authcenter.entity.Filter;
import org.kylin.authcenter.entity.User;
import org.kylin.authcenter.exception.UserOperationException;
import org.kylin.authcenter.repository.CommonRepository;
import org.kylin.authcenter.repository.UserRepository;
import org.kylin.authcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CommonRepository<User> commonRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CommonRepository<User>
            commonRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.commonRepository = commonRepository;
    }


    @Override
    public User save(User user) {
        checkUserCreateInfo(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User result;
        try {
            result = userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            Throwable exception = Optional.ofNullable(e.getRootCause()).orElse(e);
            if (exception.getMessage().contains(InfoConstant.DUPLICATE)) {
                log.error(InfoConstant.USER_HAS_ALREADY_EXIST);
                throw new UserOperationException(InfoConstant.USER_HAS_ALREADY_EXIST);
            } else {
                log.error(exception.getMessage());
                throw new UserOperationException(exception.getMessage());
            }

        }
        return result;
    }

    @Override
    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new UserOperationException(
                MessageFormat.format(InfoConstant.USER_IS_NOT_EXIST_2, InfoConstant.ID, id)));
    }

    @Transactional
    @Override
    public User createDefaultAuthUser(AuthDto dto) {
        User user = User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .roles(new HashSet<>(Arrays.asList(AuthConstant.ROLE_USER)))
                .build();
        checkUserCreateInfo(user);
        return userRepository.save(user);
    }

    @Override
    public List<User> getUsersByFilter(List<Filter> filters) {
        return commonRepository.getEntitiesByFilter(User.class, filters);
    }

    private void checkUserCreateInfo(User user) {
        List<String> infos = new ArrayList<>();

        if (null == user.getUsername() || "".equals(user.getUsername())) {
            infos.add(MessageFormat.format(InfoConstant.PROPERTIES_CANNOT_BE_EMPTY_1, InfoConstant.USERNAME));
        }

        if (null == user.getPassword()) {
            infos.add(MessageFormat.format(InfoConstant.PROPERTIES_CANNOT_BE_EMPTY_1, InfoConstant.PASSWORD));
        } else if (user.getPassword().length() < 6) {
            infos.add(MessageFormat.format(InfoConstant.PASSWORD_LEAST_CHAR_1, 6));
        }

        if (null == user.getRoles() || user.getRoles().isEmpty()) {
            infos.add(MessageFormat.format(InfoConstant.PROPERTIES_CANNOT_BE_EMPTY_1, InfoConstant.ROLES));
        }

        if (!infos.isEmpty()) {
            log.error(infos.toString());
            throw new UserOperationException(infos.toString());
        }
    }
}
