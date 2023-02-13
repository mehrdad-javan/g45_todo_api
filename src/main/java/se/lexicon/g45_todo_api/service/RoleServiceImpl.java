package se.lexicon.g45_todo_api.service;

import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.g45_todo_api.exception.DataDuplicateException;
import se.lexicon.g45_todo_api.exception.DataNotFoundException;
import se.lexicon.g45_todo_api.model.dto.RoleDto;
import se.lexicon.g45_todo_api.model.entity.Role;
import se.lexicon.g45_todo_api.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// import the model mapper class
import org.modelmapper.ModelMapper;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public List<RoleDto> getAll() {
        List<Role> roleList = roleRepository.findAllByOrderByIdDesc();
        /*return roleList.stream()
                .map(role -> new RoleDto(role.getId(), role.getName()))
                .collect(Collectors.toList());*/

        return modelMapper.map(roleList, new TypeToken<List<RoleDto>>() {
        }.getType());
    }

    @Override
    public RoleDto findById(Integer roleId) {
        if (roleId == null) throw new IllegalArgumentException("role id was null");
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (!optionalRole.isPresent()) throw new DataNotFoundException("role id was not valid");
        Role entity = optionalRole.get();
        return modelMapper.map(entity, RoleDto.class);
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        if (roleDto == null) throw new IllegalArgumentException("role data was null");
        if (roleDto.getId() != 0) throw new IllegalArgumentException("role id should be null or zero");
        // todo: check the name that should not exist in the db...

        Role createdEntity = roleRepository.save(modelMapper.map(roleDto, Role.class));
        return modelMapper.map(createdEntity, RoleDto.class);
    }

    @Override
    public void update(RoleDto roleDto) {
        if (roleDto == null) throw new IllegalArgumentException("role data was null");
        if (roleDto.getId() == 0) throw new IllegalArgumentException("role id should not be zero");

        if (!roleRepository.findById(roleDto.getId()).isPresent())
            throw new DataNotFoundException("data not found error");

        if (roleRepository.findByName(roleDto.getName()).isPresent())
            throw new DataDuplicateException("duplicate error");
        roleRepository.save(modelMapper.map(roleDto, Role.class));

    }

    @Override
    public void delete(Integer roleId) {

        RoleDto roleDto = findById(roleId);
        if (roleDto == null) throw new DataNotFoundException("id was not valid");
        roleRepository.deleteById(roleId);
    }


}
