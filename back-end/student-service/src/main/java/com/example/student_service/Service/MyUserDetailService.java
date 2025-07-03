package com.example.student_service.Service;

import com.example.student_service.Model.Student;
import com.example.student_service.Model.Userprinciple;
import com.example.student_service.Repository.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private StudentRepo studentRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Student student = studentRepo.findByEmail(email);

        if(student==null){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("user not found");
        }

        return new Userprinciple(student);
    }
}
