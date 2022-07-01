package uz.sudev.atmsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.sudev.atmsystem.entity.enums.PermissionName;
import uz.sudev.atmsystem.entity.template.AbstractEntity;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "users")
public class User extends AbstractEntity implements UserDetails {
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Double salary;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Role role;
    private boolean enabled;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (PermissionName permission : this.role.getPermissions()) {
            grantedAuthorities.add((GrantedAuthority) permission::name);
        }
        return grantedAuthorities;
    }

    public User(String firstName, String lastName, String username, String password, Double salary, Role role, boolean enabled) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.salary = salary;
        this.role = role;
        this.enabled = enabled;
    }
}
