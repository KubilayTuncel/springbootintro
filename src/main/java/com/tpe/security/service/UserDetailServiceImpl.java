package com.tpe.security.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    //!!! Bu class'ta amacim : User'lari UserDetails'e , role'leri de GrantedAuthorities e cevirmek

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //Burada biz userlarimizi userDetails e cevirdik ki spring security bizim userlarimizi taniyabilsin.
        User user = userRepository.findByUserName(username).orElseThrow(
                ()-> new ResourceNotFoundException("user not found username : " + username)
                );

        if (user != null) {
            return new org.springframework.security.core.userdetails.
                    User(user.getUserName(),
                         user.getPassword(),
                         buildGrantedAuthorities(user.getRoles()));
        }else {
            throw  new UsernameNotFoundException("user not found username : " + username);
        }


    }

    //Bu methodla rollerimizi GrantedAutorities e ceviriyoruz. Bu method önemli.
    //final yazdik cünkü gelmeden önce variable setlensin.
    private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final Set<Role> roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role:roles
             ) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
            //enum yapida calistigimiz icin role.getName() den sonra tekrar . name() dedik.
        }

        return authorities;
    }
}
