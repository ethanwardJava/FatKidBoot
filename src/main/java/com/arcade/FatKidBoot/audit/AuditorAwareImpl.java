package com.arcade.FatKidBoot.audit;

import com.arcade.FatKidBoot.entity.User;
import com.arcade.FatKidBoot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class AuditorAwareImpl  implements AuditorAware<String> {

    private final UserRepository userRepository;

    //Optional<User> user = userRepository.findByUsernameIgnoreCase();

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}