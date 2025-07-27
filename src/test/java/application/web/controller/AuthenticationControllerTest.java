package application.web.controller;

import com.josegvasconcelos.videometadata.application.service.AuthenticationService;
import com.josegvasconcelos.videometadata.application.web.controller.AuthenticationController;
import com.josegvasconcelos.videometadata.application.web.dto.request.LoginRequestDTO;
import com.josegvasconcelos.videometadata.domain.exception.InvalidCredentialsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Test
    void shouldLoginSuccessfully() {
        var username = "user.name";
        var password = "hashedPassword";
        var jwt = "eyJhbGciOiJIUzI1NiJ9";

        var request = new LoginRequestDTO(username, password);

        when(authenticationService.authenticate(username, password))
                .thenReturn(jwt);

        var response = authenticationController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(jwt, response.getBody().token());

        verify(authenticationService, times(1))
                .authenticate(username, password);
    }

    @Test
    void shouldPropagateExceptionWhenLoginFails() {
        var username = "user.name";
        var password = "wrongHash";

        var request = new LoginRequestDTO(username, password);

        when(authenticationService.authenticate(username, password))
                .thenThrow(new InvalidCredentialsException("Incorrect username or password"));

        assertThrows(
                InvalidCredentialsException.class,
                () -> authenticationController.login(request)
        );

        verify(authenticationService, times(1))
                .authenticate(username, password);
    }
}
