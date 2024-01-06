package com.example.springboot.security.sevice;

import com.example.springboot.domain.Role;
import com.example.springboot.domain.User;
import com.example.springboot.exception.ResourceNotFoundException;
import com.example.springboot.repository.UserRepository;
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
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user= userRepository.findByUserName(username).orElseThrow(
               ()->new ResourceNotFoundException("user not found username : "+username));
       if (user!=null){
           return new org.springframework.security.core.userdetails.
                   User(user.getUserName(),user.getPassword(),buildGrantedAuthorities(user.getRoles()));
       }else {
           throw new UsernameNotFoundException("User not found username : "+username);
       }

    }

    //role özelliğini securitiy katmanında simpleGrantedAuthority yapısında olması gerekiyorr
    private static List<SimpleGrantedAuthority>buildGrantedAuthorities(final Set<Role> roles){
        List<SimpleGrantedAuthority>authorities=new ArrayList<>();
        for(Role role:roles){
            //role enum yapıda olduğu için getname().name() olması gerekir
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;
    }
}
