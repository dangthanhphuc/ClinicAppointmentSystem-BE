package com.example.main.services.role;

import com.example.main.entities.Role;
import com.example.main.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
