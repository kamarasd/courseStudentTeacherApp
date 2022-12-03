package hu.webuni.cst.kamarasd.service;

import hu.webuni.cst.kamarasd.model.CstUserDetails;
import hu.webuni.cst.kamarasd.model.Student;
import hu.webuni.cst.kamarasd.repository.CstUserRepository;
import hu.webuni.cst.kamarasd.security.CstUserDetailService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleLoginService {

    private final CstUserRepository cstUserRepository;
    private static final String BASE_URL = "https://oauth2.googleapis.com";
    private static final String CLIENT_ID = "244592059674-gj0c3ic97g015a00kuph1d53nalg42mn.apps.googleusercontent.com";

    @Data
    public static class GoogleData{
        private String email;
        private String sub;
        private String email_verified;
        private String name;
        private String given_name;
        private String family_name;
        private String at_hash;
        private String aud;
    }

    @Transactional
    public UserDetails getUserDetailsForToken(String googleToken) {

        GoogleData googleData = getGoogleDataToken(googleToken);
        if(!CLIENT_ID.equals(googleData.getAud())) {
            throw new BadCredentialsException("Google aud claim match error");
        }
        CstUserDetails cstUserDetails = FindOrCreateUser(googleData);

        return CstUserDetailService.createUserInfo(cstUserDetails);
    }

    private CstUserDetails FindOrCreateUser(GoogleData token) {
        String googleId = String.valueOf(token.getSub());
        Optional<CstUserDetails> optionalGoogleUser = cstUserRepository.findByGoogleId(googleId);
        if(optionalGoogleUser.isEmpty()) {
            return cstUserRepository.save(Student.builder()
                    .facebookId(googleId)
                    .username(token.getEmail())
                    .password("dummy")
                    .build());
        }
        return optionalGoogleUser.get();
    }

    private GoogleData getGoogleDataToken(String token) {

        return WebClient.create(BASE_URL)
                .get()
                .uri(uriBuilder -> uriBuilder.path("/tokeninfo").queryParam("id_token", token)
                        .build())
                .retrieve()
                .bodyToMono(GoogleData.class)
                .block();
    }

}
