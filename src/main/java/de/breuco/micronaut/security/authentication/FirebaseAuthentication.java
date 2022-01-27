package de.breuco.micronaut.security.authentication;

import com.google.firebase.auth.FirebaseToken;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.security.authentication.Authentication;
import java.util.Collections;
import java.util.Map;

/** @author breucode */
public class FirebaseAuthentication implements Authentication {

    /**
     * Constructor.
     *
     * @param firebaseToken The {@link FirebaseToken}.
     */
    public FirebaseAuthentication(FirebaseToken firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    private final FirebaseToken firebaseToken;

    /** @return the uid of the {@link FirebaseToken}. */
    @Override
    public String getName() {
        return firebaseToken.getUid();
    }

    /** @return the claims of the {@link FirebaseToken}. */
    @Override
    @NonNull
    public Map<String, Object> getAttributes() {
        if (firebaseToken.getClaims() != null) {
            return firebaseToken.getClaims();
        } else {
            return Collections.emptyMap();
        }
    }
}
