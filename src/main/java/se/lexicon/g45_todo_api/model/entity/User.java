package se.lexicon.g45_todo_api.model.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.lexicon.g45_todo_api.exception.DataDuplicateException;
import se.lexicon.g45_todo_api.exception.DataNotFoundException;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(updatable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean expired;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "USERNAME")},
            inverseJoinColumns = {@JoinColumn(name = "ROLE_ID")}
    )
    private Set<Role> roles = new HashSet<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addRole(Role role) {
        if (roles.contains(role)) {
            throw new DataDuplicateException("duplicate error");
        }
        roles.add(role);
    }

    public void removeRole(Role role) {
        if (!roles.contains(role)) {
            throw new DataNotFoundException("not found error");
        }
        roles.remove(role);
    }


}
