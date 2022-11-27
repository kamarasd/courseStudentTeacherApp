package hu.webuni.cst.kamarasd.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

public class UserInfo extends User {

    @Getter
    private List<Long> courseIds;

    public UserInfo(String username, String password, Collection<? extends GrantedAuthority> authorities, List<Long> courseIds) {
        super(username, password, authorities);
        this.courseIds = courseIds;
    }
}
