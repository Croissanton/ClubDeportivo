package clubdeportivo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoTest {
    @Test
    @DisplayName("Create ClubDeportivo with a valid number of groups (>0) and a valid name (length > 0)")
    public void Create_ClubDeportivoWithValidData_ReturnsSuccess() {
        assertDoesNotThrow(() -> new ClubDeportivo("Club", 7));
    }
    @ParameterizedTest
    @DisplayName("Create ClubDeportivo with an invalid number of groups (<=0)")
    @ValueSource(ints = {0, -1})
    public void Create_ClubDeportivoWithInvalidGroups_ThrowsClubException(int n) {
        assertThrows(ClubException.class, () -> new ClubDeportivo("Club", -1));
    }

    @Test
    @DisplayName("AnyadirActividad with valid data")
    public void AnyadirActividadWithValidData_ReturnsSuccess() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        String[] datos = {"1", "Actividad", "10", "5", "10.0"};
        assertDoesNotThrow(() -> club.anyadirActividad(datos));
    }

    @ParameterizedTest
    @DisplayName("AnyadirActividad with invalid data (cannot be parsed properly)")
    @CsvSource({ "1, Actividad, -a.0, 5, 10.0", "1, Actividad, 10, we5, 0.0", "1, Actividad, 10, we5, 10w.0" })
    public void AnyadirActividadWithInvalidData_ThrowsClubException(String codigo, String actividad, String plazas, String matriculados, String tarifa) throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        String[] datos = {codigo, actividad, plazas, matriculados, tarifa};
        assertThrows(ClubException.class, () -> club.anyadirActividad(datos));
    }
}
