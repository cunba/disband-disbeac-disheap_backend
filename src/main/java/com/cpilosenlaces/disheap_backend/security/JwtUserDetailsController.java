package com.cpilosenlaces.disheap_backend.security;

import java.util.List;

import com.cpilosenlaces.disheap_backend.model.UserModel;
import com.cpilosenlaces.disheap_backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsController implements UserDetailsService {

    @Autowired
    private UserService us;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        final List<UserModel> user = us.findByEmail(email);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("UserDTO '" + email + "' not found");
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.get(0).getPassword())
                .authorities("ROLE_" + user.get(0).getRole())
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}