package com.JobPortal.JobPortal.auth;

import com.JobPortal.JobPortal.exception.DuplicateResourceException;
import com.JobPortal.JobPortal.user.UserInfo;
import com.JobPortal.JobPortal.user.UserInfoDetails;
import com.JobPortal.JobPortal.user.UserInfoRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepo repository;
    private final PasswordEncoder encoder;

    public UserInfoService(UserInfoRepo repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(RegisterRequestDTO dto) {
        if (repository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email is already registered: " + dto.getEmail());
        }

        UserInfo user = new UserInfo();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRoles(dto.getRoles());

        repository.save(user);
        return "User registered successfully!";
    }
}
