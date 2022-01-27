package de.breuco.micronaut.security.token.jwt.validator;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import de.breuco.micronaut.security.authentication.FirebaseAuthentication;
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

    /**
     * @param token The token string.
     * @return Publishes {@link Authentication} with the {@link FirebaseToken} or empty if the
     *     validation fails.
     */
    @Override
    public Publisher<Authentication> validateToken(String token, HttpRequest<?> request) {

        try {
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token);

            return Flux.just(new FirebaseAuthentication(firebaseToken));
        } catch (Exception e) {
            return Flux.empty();
        }
    }
}
