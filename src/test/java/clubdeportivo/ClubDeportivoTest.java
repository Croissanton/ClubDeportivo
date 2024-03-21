//Autores: Cristian Ruiz Martín y Mikolaj Zabski

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

    @Test
    @DisplayName("AnyadirActividad with null group")
    public void AnyadirActividadWithNullGroup_ThrowsClubException() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        assertThrows(ClubException.class, () -> club.anyadirActividad((Grupo) null));
    }

    @Test
    @DisplayName("AnyadirActividad with repeated group")
    public void AnyadirActividadWithRepeatedGroup_UpdatesPlazas() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        Grupo grupo = new Grupo("1", "Actividad", 10, 5, 10.0);
        club.anyadirActividad(grupo);
        Grupo grupo2 = new Grupo("1", "Actividad", 20, 5, 10.0);
        club.anyadirActividad(grupo2);
        assertEquals(15, club.plazasLibres("Actividad"));

    }

    @Test
    @DisplayName("Matricular with valid data")
    public void MatricularWithValidData_ReturnsSuccess() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        Grupo grupo = new Grupo("1", "Actividad", 10, 5, 10.0);
        Grupo grupo2 = new Grupo("2", "Actividad2", 12, 8, 10.0);
        club.anyadirActividad(grupo);
        club.anyadirActividad(grupo2);
        assertDoesNotThrow(() -> club.matricular("Actividad", 5));
        assertDoesNotThrow(() -> club.matricular("Actividad2", 3));
    }

    @Test
    @DisplayName("Matricular with invalid data, plazas < matriculados")
    public void MatricularWithInvalidData_ThrowsClubException() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        Grupo grupo = new Grupo("1", "Actividad", 10, 5, 10.0);
        club.anyadirActividad(grupo);
        assertThrows(ClubException.class, () -> club.matricular("Actividad", 10));
    }

    @Test
    @DisplayName("Ingresos with no groups")
    public void IngresosWithNoGroups_ReturnsZero() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        assertEquals(0.0, club.ingresos());
    }

    @Test
    @DisplayName("Ingresos with one or more groups")
    public void IngresosWithOneOrMoreGroups_ReturnsSuccess() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        Grupo grupo1 = new Grupo("1", "Actividad", 10, 5, 10.0);
        Grupo grupo2 = new Grupo("2", "Actividad", 10, 5, 10.0);
        club.anyadirActividad(grupo1);
        club.anyadirActividad(grupo2);
        assertEquals(100.0, club.ingresos());
    }

    @Test
    @DisplayName("ToString with no groups")
    public void ToStringWithNoGroups_ReturnsNameString() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        assertEquals("Club --> [  ]", club.toString());
    }

    @Test
    @DisplayName("ToString with one or more groups")
    public void ToStringWithOneOrMoreGroups_ReturnsSuccess() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        Grupo grupo1 = new Grupo("1", "Actividad", 10, 5, 10.0);
        Grupo grupo2 = new Grupo("2", "Actividad", 10, 5, 10.0);
        club.anyadirActividad(grupo1);
        club.anyadirActividad(grupo2);
        assertEquals("Club --> [ " + grupo1.toString() + ", " + grupo2.toString() + " ]", club.toString());
    }

    @Test
    @DisplayName("AnyadirActividad with datos[].length<5")
    public void AnyadirActividad_WithInvalidData2_ThrowsClubException() throws ClubException {
        ClubDeportivo club = new ClubDeportivo("Club");
        String[] datos = {"1", "Actividad", "10"};
        assertThrows(ClubException.class, () -> club.anyadirActividad(datos));
    }
}