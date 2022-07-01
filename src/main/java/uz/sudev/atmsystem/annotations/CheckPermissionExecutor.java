package uz.sudev.atmsystem.annotations;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.sudev.atmsystem.entity.User;
import uz.sudev.atmsystem.exceptions.ForbiddenException;

@Aspect
@Component
public class CheckPermissionExecutor {
    @Before(value = "@annotation(checkPermission)")
    public void checkUserPermission(CheckPermission checkPermission) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean exists = false;
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals(checkPermission.value())) {
                exists = true;
                break;
            }
        }
        if (!exists) {
            throw new ForbiddenException(checkPermission.value(), "This action is not allowed!");
        }
//        checkPermission.value().
    }
}
