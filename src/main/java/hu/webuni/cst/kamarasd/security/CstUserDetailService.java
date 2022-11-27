package hu.webuni.cst.kamarasd.security;

import hu.webuni.cst.kamarasd.model.Course;
import hu.webuni.cst.kamarasd.model.CstUserDetails;
import hu.webuni.cst.kamarasd.model.Student;
import hu.webuni.cst.kamarasd.model.Teacher;
import hu.webuni.cst.kamarasd.repository.CstUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Set;

@Service
@AllArgsConstructor
public class CstUserDetailService implements UserDetailsService {

    private final CstUserRepository cstUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CstUserDetails cstUserDetails = cstUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        return createUserInfo(cstUserDetails);
    }

    public static UserInfo createUserInfo(CstUserDetails cstUserDetails) {
        Set<Course> courses = null;
        if(cstUserDetails instanceof Teacher) {
            courses = ((Teacher) cstUserDetails).getCourses();
        } else if(cstUserDetails instanceof Student) {
            courses = ((Student) cstUserDetails).getCourses();
        }
        return new UserInfo(cstUserDetails.getUsername(), cstUserDetails.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority(cstUserDetails.getUserType().toString())),
                courses.stream().map(Course::getId).toList());
    }
}
