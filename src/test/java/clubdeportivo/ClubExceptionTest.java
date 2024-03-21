package clubdeportivo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ClubExceptionTest {
    @ParameterizedTest
    @DisplayName("Create ClubException with message")
    @ValueSource(strings = {"Error message", "Another error message"})
    public void Create_ClubExceptionWithMessage_ReturnsSuccess(String errorMessage) {
        ClubException clubException = new ClubException(errorMessage);
        assert clubException.getMessage().equals(errorMessage);
    }

    @Test
    @DisplayName("Create ClubException without message")
    public void Create_ClubExceptionWithoutMessage_ReturnsSuccess() {
        ClubException clubException = new ClubException();
        assert clubException.getMessage() == null;
    }
}
