package se.lexicon.g45_todo_api.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.lexicon.g45_todo_api.exception.DataDuplicateException;
import se.lexicon.g45_todo_api.exception.DataNotFoundException;
import se.lexicon.g45_todo_api.model.dto.RoleDto;
import se.lexicon.g45_todo_api.model.dto.UserDto;
import se.lexicon.g45_todo_api.model.entity.User;
import se.lexicon.g45_todo_api.repository.RoleRepository;
import se.lexicon.g45_todo_api.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    RoleRepository roleRepository;
    UserRepository userRepository;
    ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(RoleRepository roleRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public UserDto register(UserDto dto) {
        // step1: check the methods params
        if (dto == null) throw new IllegalArgumentException("UserDto data was null");
        if (dto.getUsername() == null || dto.getPassword() == null)
            throw new IllegalArgumentException("username or password was null");
        if (dto.getRoles() == null || dto.getRoles().size() == 0)
            throw new IllegalArgumentException("role data was null");

        // step2: check the roles data
        for (RoleDto element : dto.getRoles())
            roleRepository.findById(element.getId()).orElseThrow(() -> new DataNotFoundException("role id was not valid"));

        // step3: check the username that should not be duplicated
        if (userRepository.existsByUsername(dto.getUsername()))
            throw new DataDuplicateException("duplicate username error");

        // step4: convert the dto to entity
        User convertedToEntity = modelMapper.map(dto, User.class);

        // step5: execute the save method of UserRepository
        User createdEntity = userRepository.save(convertedToEntity);

        // step6: convert the created entity to dto
        UserDto convertedToDto = modelMapper.map(createdEntity, UserDto.class);

        return convertedToDto;
    }

    @Override
    public Map<String, Object> findByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username was null");
        User user = userRepository.findByUsername(username).orElseThrow(() -> new DataNotFoundException("Username not found error"));

        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("roles", user.getRoles());
        map.put("expired", user.isExpired());
        return map;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void disableUserByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username was null");
        if (!userRepository.existsByUsername(username)) throw new DataNotFoundException("username was not valid");
        userRepository.updateExpiredByUsername(username, true);
    }
}
