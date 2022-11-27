package hu.webuni.cst.kamarasd.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CourseChatGuard {

    public boolean checkCourseId(Authentication authentication, Long courseId) {
        UserInfo userInfo = (UserInfo) authentication.getPrincipal();
        return userInfo.getCourseIds().contains(courseId);

    }
}
