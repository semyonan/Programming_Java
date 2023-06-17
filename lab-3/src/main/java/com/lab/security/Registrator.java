package com.lab.security;

import com.lab.entities.Role;
import com.lab.entities.User;
import com.lab.repository.CarMarkRepository;
import com.lab.repository.RoleRepository;
import com.lab.repository.UserRepository;
import entities.CarMark;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class Registrator {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    public final CarMarkRepository carMarkRepository;
    public final PasswordEncoder encoder;

    public void register(Long name, String password, Role.RoleType role) {
        if (userRepository.existsById(name)) {
            throw new SecurityException("User already exists");
        }
        CarMark mark = carMarkRepository.findCarMarkById(name);

        User user = new User(mark,
                encoder.encode(password));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(role).orElseThrow(()->new RuntimeException("Role doesn't exists")));

        user.setRoles(roles);
        userRepository.save(user);
    }
}
