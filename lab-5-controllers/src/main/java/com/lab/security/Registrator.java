package com.lab.security;

import com.lab.dto.RegisterRequest;
import entities.Role;
import entities.User;
import com.lab.repository.RoleRepository;
import com.lab.repository.UserRepository;
import entities.CarMark;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
public class Registrator {
    public final UserRepository userRepository;
    public final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    public final PasswordEncoder encoder;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsById(Long.parseLong(registerRequest.getUsername()))) {
            throw new SecurityException("User already exists");
        }
        CarMark mark = modelMapper.map(registerRequest.getCarMarkDto(), CarMark.class);

        User user = new User(mark,
                encoder.encode(registerRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Role.RoleType.valueOf(registerRequest.getRole())).orElseThrow(()->new RuntimeException("Role doesn't exists")));

        user.setRoles(roles);
        userRepository.save(user);
    }
}
