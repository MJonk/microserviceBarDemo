package com.codenomads.userservice.service;

import com.codenomads.userservice.model.User;
import com.codenomads.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with email " + email + " could not be found"
                ));
    }

    public Page<User> getAllUsersByPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User createOrUpdateUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .map(userToUpdate -> {
                    userToUpdate.setName(user.getName());
                    userToUpdate.setBirthDate(user.getBirthDate());
                    return userToUpdate;
                })
                .orElseGet(() -> userRepository.save(user));
    }

    public void deleteUserByEmail(String email) {
        User user = getUserByEmail(email);
        userRepository.delete(user);
    }

    public Page<User> getAllUsersEqualToOrOlderThan(int age) {
        return new PageImpl<>(
                userRepository.findAll()
                .stream()
                .filter(user -> user.getBirthDate().minusDays(1).isBefore(LocalDate.now().minusYears(age)))
                .toList()
        );
    }

    public Page<User> getAllUsersYoungerThan(int age) {

        return new PageImpl<>(
                userRepository.findAll()
                        .stream()
                        .filter(user -> user.getBirthDate().isAfter(LocalDate.now().minusYears(age)))
                        .toList()
        );
    }
}
