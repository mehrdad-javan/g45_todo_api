package se.lexicon.g45_todo_api.service;

import se.lexicon.g45_todo_api.model.dto.RoleDto;

import java.util.List;

public interface RoleService {

    List<RoleDto> getAll();

    RoleDto findById(Integer roleId);

    RoleDto create(RoleDto roleDto);

    void update(RoleDto roleDto);

    void delete(Integer roleId);


}
