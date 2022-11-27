package hu.webuni.cst.kamarasd.security;

import hu.webuni.cst.kamarasd.dto.LoginDto;
import hu.webuni.cst.kamarasd.service.FacebookLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtLoginController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    @Autowired
    FacebookLoginService facebookLoginService;

    @PostMapping("/api/login")
    public String login(@RequestBody LoginDto loginDto) {

        UserDetails userDetails = null;
        String facebookToken = loginDto.getFacebookToken();

        if(ObjectUtils.isEmpty(facebookToken)) {
            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            facebookLoginService.getUserDetailsForToken(facebookToken);
        }
        return "\"" + jwtService.createJwtToken(userDetails) + "\"";

    }




}
