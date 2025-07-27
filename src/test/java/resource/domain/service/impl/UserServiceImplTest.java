package resource.domain.service.impl;

import com.josegvasconcelos.videometadata.domain.entity.User;
import com.josegvasconcelos.videometadata.domain.entity.UserRole;
import com.josegvasconcelos.videometadata.domain.exception.UserNotFoundException;
import com.josegvasconcelos.videometadata.domain.repository.UserRepository;
import com.josegvasconcelos.videometadata.domain.service.impl.UserServiceImpl;
import de.huxhorn.sulky.ulid.ULID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldFindByUsernameSuccessfully() {
        var username = "user.name";

        User user = User.builder()
                .id(new ULID().nextULID())
                .username(username)
                .password("password")
                .role(UserRole.USER)
                .build();

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.of(user));

        User resultUser = userService.findByUsername(username);

        assertSame(user, resultUser);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenLoadingUnexistingUsername() {
        var username = "user.name";

        when(userRepository.findByUsername(username))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.findByUsername(username)
        );
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldFindByIdSuccessfully() {
        var userId = new ULID().nextULID();

        User user = User.builder()
                .id(userId)
                .username("user.name")
                .password("password")
                .role(UserRole.USER)
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        User resultUser = userService.findById(userId);

        assertSame(user, resultUser);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenLoadingUnexistingUserId() {
        var userId = new ULID().nextULID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> userService.findById(userId)
        );
        verify(userRepository, times(1)).findById(userId);
    }
}
