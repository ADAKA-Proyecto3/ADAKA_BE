package com.cenfotec.adaka.app.service;

import com.cenfotec.adaka.app.domain.Role;
import com.cenfotec.adaka.app.domain.Status;
import com.cenfotec.adaka.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<com.cenfotec.adaka.app.domain.User> optionalUser = userRepository.getUserByEmail(username);

        if (!optionalUser.isPresent()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        com.cenfotec.adaka.app.domain.User user = optionalUser.orElseThrow();
        Map authMap = new HashMap<String, Role>();
        authMap.put(user.getRole().toString(),user.getRole());

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));

        boolean credentialNonLocked = true;
        if(user.getStatus().equals(Status.INACTIVE) ){
            credentialNonLocked = false;
        }

        return new User(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                credentialNonLocked,
                authorities);

    }

}
