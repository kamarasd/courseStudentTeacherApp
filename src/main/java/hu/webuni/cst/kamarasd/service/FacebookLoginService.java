package hu.webuni.cst.kamarasd.service;

import hu.webuni.cst.kamarasd.model.CstUserDetails;
import hu.webuni.cst.kamarasd.model.Student;
import hu.webuni.cst.kamarasd.repository.CstUserRepository;
import hu.webuni.cst.kamarasd.security.CstUserDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FacebookLoginService {

    private final CstUserRepository cstUserRepository;
    private static final String BASE_URL = "https://graph.facebook.com/v13.0";

    @Data
    public static class FacebookData{
        private String email;
        private long id;
    }
    public UserDetails getUserDetailsForToken(String facebookToken) {

        FacebookData facebookData = getEmailOfFbUser(facebookToken);
        CstUserDetails cstUserDetails = FindOrCreateUser(facebookData);

        return CstUserDetailService.createUserInfo(cstUserDetails);
    }

    private CstUserDetails FindOrCreateUser(FacebookData facebookData) {
        String facebookId = String.valueOf(facebookData.getId());
        Optional<CstUserDetails> optionalFbUser = cstUserRepository.findByFacebookId(facebookId);
        if(optionalFbUser.isEmpty()) {
            return cstUserRepository.save(Student.builder()
                    .facebookId(facebookId)
                    .username(facebookData.getEmail())
                    .password("dummy")
                    .build());
        }
        return optionalFbUser.get();
    }

    private FacebookData getEmailOfFbUser(String token) {

        return WebClient.create(BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder.path("/me").queryParam("fields", "email, name")
                        .build())
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve()
                .bodyToMono(FacebookData.class)
                .block();
    }

}
