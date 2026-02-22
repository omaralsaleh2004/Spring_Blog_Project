package com.Omar.Spring_Blog_Project.service;



import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {


    private final UserRepo userRepo;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public User getUserByEmail(String email) {
       return userRepo.findByEmail(email);
    }

    public boolean checkEmailExists(String email) {
        return userRepo.existsByEmail(email);
    }
}
