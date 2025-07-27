package resource.domain.service.impl;

import com.josegvasconcelos.videometadata.domain.entity.User;
import com.josegvasconcelos.videometadata.domain.entity.UserRole;
import com.josegvasconcelos.videometadata.domain.exception.InvalidCredentialsException;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import com.josegvasconcelos.videometadata.domain.service.impl.AuthorizationServiceImpl;
import de.huxhorn.sulky.ulid.ULID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorizationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthorizationServiceImpl authorizationService;

    @Test
    void shouldLoadByUsernameSuccessfully() {
        var username = "user.name";

        User user = User.builder()
                .id(new ULID().nextULID())
                .username(username)
                .password("password")
                .role(UserRole.USER)
                .build();

        when(userRepository.findByUsername(user.getUsername()))
                .thenReturn(Optional.of(user));

        UserDetails resultUser = authorizationService.loadUserByUsername(username);

        assertSame(user, resultUser);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenLoadingUnexistingUser() {
        var username = "user.name";

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(
                InvalidCredentialsException.class,
                () -> authorizationService.loadUserByUsername(username)
        );
        verify(userRepository, times(1)).findByUsername(username);
    }
}
