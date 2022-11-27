package hu.webuni.cst.kamarasd.repository;

import hu.webuni.cst.kamarasd.model.CstUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CstUserRepository extends JpaRepository<CstUserDetails, Long> {

    Optional<CstUserDetails> findByUsername(String username);
    Optional<CstUserDetails> findByFacebookId(String fbId);
}
