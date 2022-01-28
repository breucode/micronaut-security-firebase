package de.breuco.micronaut.security.token.jwt.validator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import io.micronaut.context.annotation.Replaces;
import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.Authentication;
import io.micronaut.security.token.jwt.validator.JwtTokenValidator;
import io.micronaut.security.token.validator.TokenValidator;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

/** @author breucode */
@Singleton
@Replaces(JwtTokenValidator.class)
public class FirebaseTokenValidator implements TokenValidator {

    private final FirebaseAuthenticationFactory firebaseAuthenticationFactory;

    /**
     * Constructor.
     *
     * @param firebaseAuthenticationFactory Utility to generate an Authentication given a JWT.
     */
    public FirebaseTokenValidator(FirebaseAuthenticationFactory firebaseAuthenticationFactory) {
        this.firebaseAuthenticationFactory = firebaseAuthenticationFactory;
    }

    /**
     * @param token The token string.
     * @return Publishes {@link Authentication} based on the {@link FirebaseToken} or empty if the
     *     validation fails.
     */
    @Override
    public Publisher<Authentication> validateToken(String token, HttpRequest<?> request) {

        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);

            return firebaseAuthenticationFactory
                    .createAuthentication(firebaseToken)
                    .map(Flux::just)
                    .orElse(Flux.empty());
        } catch (Exception e) {
            return Flux.empty();
        }
    }
}
