package se.lexicon.g45_todo_api.repository;

import org.springframework.data.repository.CrudRepository;
import se.lexicon.g45_todo_api.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
        Optional<Role> findByName(String name);
        List<Role> findAllByOrderByIdDesc();
}
