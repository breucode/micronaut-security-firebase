package de.breuco.micronaut.security.token.jwt.validator;

import com.google.firebase.auth.FirebaseToken;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.RolesFinder;
import io.micronaut.security.token.TokenAuthenticationFactory;
import jakarta.inject.Singleton;
import java.util.Optional;

/**
 * @author breucode
 */
@Singleton
public class FirebaseAuthenticationFactory implements TokenAuthenticationFactory<FirebaseToken> {

    private final RolesFinder rolesFinder;

    public FirebaseAuthenticationFactory(RolesFinder rolesFinder) {
        this.rolesFinder = rolesFinder;
    }

    @Override
    public Optional<Authentication> createAuthentication(FirebaseToken token) {
        return Optional.of(
                Authentication.build(
                        token.getUid(),
                        rolesFinder.resolveRoles(token.getClaims()),
                        token.getClaims()));
    }
}
