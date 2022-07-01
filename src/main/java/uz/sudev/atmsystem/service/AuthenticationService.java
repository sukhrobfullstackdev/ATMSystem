package uz.sudev.atmsystem.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sudev.atmsystem.entity.User;
import uz.sudev.atmsystem.repository.AuthenticationRepository;
import uz.sudev.atmsystem.repository.RoleRepository;
import uz.sudev.atmsystem.security.JWTProvider;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {
    final AuthenticationRepository authenticationRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;
    final AuthenticationManager authenticationManager;
    final JWTProvider jwtProvider;

    public AuthenticationService(JWTProvider jwtProvider, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository roleRepository, AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = authenticationRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(username + " is not found!");
        }
    }
}
