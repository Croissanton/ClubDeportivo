package clubdeportivo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoAltoRendimientoTest {
    @Test
    @DisplayName("Create ClubDeportivoAltoRendimiento with valid data")
    public void Create_ClubDeportivoAltoRendimientoWithValidData_ReturnsSuccess() {
        assertDoesNotThrow(() -> new ClubDeportivoAltoRendimiento("Club", 7, 10.0));
    }

    @ParameterizedTest
    @DisplayName("Create ClubDeportivoAltoRendimiento with invalid number of groups (<=0) or invalid incremento (<=0)")
    @CsvSource({"-1, 10.0", "7, -1.0", "-1, -1.0", "0, 10.0", "7, 0.0", "0, 0.0"})
    public void Create_ClubDeportivoAltoRendimientoWithInvalidData_ThrowsClubException(int n, double incremento) {
        assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento("Club", n, incremento));
    }

    @Test
    @DisplayName("Create ClubDeportivoAltoRendimiento with valid data, using the second constructor (with tam)")
    public void Create_ClubDeportivoAltoRendimientoWithValidData2_ReturnsSuccess() {
        assertDoesNotThrow(() -> new ClubDeportivoAltoRendimiento("Club", 7, 7, 10.0));
    }

    @ParameterizedTest
    @DisplayName("Create ClubDeportivoAltoRendimiento with invalid number of groups (<=0), invalid incremento (<=0) or invalid tam (<=0), using the second constructor")
    @CsvSource({"-1, 7, 10.0", "7, -1, 10.0", "-1, -1, 10.0", "0, 7, 10.0", "7, 0, 10.0", "0, 0, 10.0", "7, 7, -1.0", "7, 7, 0.0", "-1,-2,-3.0"})
    public void Create_ClubDeportivoAltoRendimientoWithInvalidData2_ThrowsClubException(int tam, int n, double incremento) {
        assertThrows(ClubException.class, () -> new ClubDeportivoAltoRendimiento("Club", tam, n, incremento));
    }

    @Test
    @DisplayName("AnyadirActividad with valid data")
    public void AnyadirActividadWithValidData_ReturnsSuccess() throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 7, 10.0);
        assertDoesNotThrow(() -> club.anyadirActividad(new String[]{"Actividad", "Descripcion", "5", "3", "10.0"}));
    }

    @Test
    @DisplayName("AnyadirActividad with missing data")
    public void AnyadirActividadWithMissingData_ThrowsClubException() throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 7, 10.0);
        assertThrows(ClubException.class, () -> club.anyadirActividad(new String[]{"123A", "Actividad", "5", "3"}));
    }

    @Test
    @DisplayName("AnyadirActividad with invalid number of plazas (>maximoPersonasGrupo)")
    public void AnyadirActividadWithInvalidPlazas_ThrowsClubException() throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 7, 10.0);
        club.anyadirActividad(new String[]{"123A", "Actividad", "15", "5", "10.0"});
        assertEquals(2, club.plazasLibres("Actividad"));
    }

    @ParameterizedTest
    @DisplayName("Anyadir actividad with invalid number of plazas (<=0)")
    @CsvSource({"0, 5, 10.0", "-1, 5, 10.0", "15, 10, 10.0"})
    public void AnyadirActividadWithInvalidPlazas_ThrowsClubException(int plazas, int matriculados, double tarifa) throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 7, 10.0);
        assertThrows(ClubException.class, () -> club.anyadirActividad(new String[]{"123A", "Actividad", String.valueOf(plazas), String.valueOf(matriculados), String.valueOf(tarifa)}));
    }

    @ParameterizedTest
    @DisplayName("Anyadir actividad with invalid format number")
    @CsvSource({"123A, Actividad, 5w, 3, 10.0", "123A, Actividad, 5, 3fsdfsd, 10.0", "123A, Actividad, 5, 3, 10aaa.0"})
    public void AnyadirActividadWithInvalidFormatNumber_ThrowsClubException(String codigo, String actividad, String plazas, String matriculados, String tarifa) throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 7, 10.0);
        assertThrows(ClubException.class, () -> club.anyadirActividad(new String[]{codigo, actividad, plazas, matriculados, tarifa}));
    }

    @Test
    @DisplayName("Test ingresos")
    public void TestIngresos() throws ClubException {
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 7, 10.0);
        club.anyadirActividad(new String[]{"123A", "Actividad", "5", "3", "10.0"});
        ClubDeportivo club2 = new ClubDeportivo("Club2");
        club2.anyadirActividad(new String[]{"123A", "Actividad", "5", "3", "10.0"});
        assertEquals(club2.ingresos()+club2.ingresos()*(10.0/100), club.ingresos());
    }
}
