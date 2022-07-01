package uz.sudev.atmsystem.configuration;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.sudev.atmsystem.entity.User;

import java.util.Optional;


public class SpringSecurityAuditAwareImpl implements AuditorAware<Integer> {
    @Override
    public Optional<Integer> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) { // authentication.isAuthenticated()- authenticate bo'ganmi shuni tekshirvoti
            User user = (User) authentication.getPrincipal();
            return Optional.of(user.getId());
        } else {
            return Optional.empty();
        }
    }
}
