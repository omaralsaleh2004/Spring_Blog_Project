package com.Omar.Spring_Blog_Project.service;


import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.model.UserPrinciple;
import com.Omar.Spring_Blog_Project.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {


    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepo.findByEmail(email);

        if(user == null) {
            System.out.println("Not found 404");
            throw  new UsernameNotFoundException("not found 404");
        }
        return new UserPrinciple(user);
    }
}
