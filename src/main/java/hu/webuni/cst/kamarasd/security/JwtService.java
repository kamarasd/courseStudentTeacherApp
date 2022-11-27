package hu.webuni.cst.kamarasd.security;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JwtService {

    public static final String COURSE_IDS = "courseIds";
    private Algorithm myAlg = Algorithm.HMAC256("secretUniversity");
    private String myIssuer = "cstApp";
    private String myAuth = "auth";
    private Integer myLogInMinutes = 1000;

    public String createJwtToken(UserDetails principal) {
        UserInfo userinfo = (UserInfo) principal;

        return JWT.create().withSubject(principal.getUsername())
                .withArrayClaim(myAuth, principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new))
                .withArrayClaim(COURSE_IDS,userinfo.getCourseIds().stream().toArray(Long[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(myLogInMinutes)))
                .withIssuer(myIssuer)
                .sign(myAlg);
    }

    public UserDetails parseJwt(String jwtToken) {
        DecodedJWT decodedJwt = JWT.require(myAlg)
                .withIssuer(myIssuer)
                .build()
                .verify(jwtToken);

        return new UserInfo(decodedJwt.getSubject(),
                "dummyUser",
                decodedJwt.getClaim(myAuth)
                        .asList(String.class)
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()),
                decodedJwt.getClaim(COURSE_IDS).asList(Long.class));
    }
}
