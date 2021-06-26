package com.excilys.formation.java.cdb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.excilys.formation.java.cdb.daos.UserRepository;
import com.excilys.formation.java.cdb.models.User;
import com.excilys.formation.java.cdb.models.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        User user = this.userRepository.findByUsername(username);
        return new CustomUserDetails(user);
    }

}
