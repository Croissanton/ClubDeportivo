package clubdeportivo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class GrupoTest {

    //parametrized test for constructor
    @ParameterizedTest
    @DisplayName("Create Grupo with valid data")
    @CsvSource({"123A,Kizomba,10,0,25.0",
            "456B,Pilates,8,5,50.0",
            "789C,Spinning,15,10,30.0"})
    public void Create_Grupo_WithValidData_ReturnsSuccess(String codigo, String actividad, int nplazas, int matriculados, double tarifa) throws ClubException {
        Grupo grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        assertEquals(codigo, grupo.getCodigo());
        assertEquals(actividad, grupo.getActividad());
        assertEquals(nplazas, grupo.getPlazas());
        assertEquals(matriculados, grupo.getMatriculados());
        assertEquals(tarifa, grupo.getTarifa());
    }

    @ParameterizedTest
    @DisplayName("Create Grupo with invalid data")
    @CsvSource({"123A,Kizomba,0,10,25.0",
            "456B,Pilates,8,-5,50.0",
            "789C,Spinning,15,10,-30.0"})
    public void Create_Grupo_WithInvalidData_ReturnsException(String codigo, String actividad, int nplazas, int matriculados, double tarifa) {
        try {
            Grupo grupo = new Grupo(codigo, actividad, nplazas, matriculados, tarifa);
        } catch (ClubException e) {
            assertEquals("ERROR: los datos numéricos no pueden ser menores o iguales que 0.", e.getMessage());
        }
    }

    @ParameterizedTest
    @DisplayName("actualizarPlazas with valid data")
    @ValueSource(ints = {10, 15, 20})
    public void ActualizarPlazas_WithValidData_ReturnsSuccess(int n) throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25.0);
        grupo.actualizarPlazas(n);
        assertEquals(n, grupo.getPlazas());
    }

    @ParameterizedTest
    @DisplayName("actualizarPlazas with invalid data")
    @ValueSource(ints = {0, -5, -10})
    public void ActualizarPlazas_WithInvalidData_ReturnsException(int n) {
        try {
            Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25.0);
            grupo.actualizarPlazas(n);
        } catch (ClubException e) {
            assertEquals("ERROR: número de plazas negativo.", e.getMessage());
        }
    }

    @Test
    @DisplayName("plazasLibres test")
    public void PlazasLibres_ReturnsSuccess() throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals(5, grupo.plazasLibres());
    }

    @ParameterizedTest
    @DisplayName("matricular with valid data")
    @ValueSource(ints = {5, 3, 1})
    public void Matricular_WithValidData_ReturnsSuccess(int n) throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        grupo.matricular(n);
        assertEquals(5 + n, grupo.getMatriculados());
    }

    @ParameterizedTest
    @DisplayName("matricular with invalid data")
    @ValueSource(ints = {0, -5, -10})
    public void Matricular_WithInvalidData_ReturnsException(int n) {
        try {
            Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
            grupo.matricular(n);
        } catch (ClubException e) {
            assertEquals("ERROR: no hay plazas libres suficientes, plazas libre: 5 y matriculas: " + n, e.getMessage());
        }
    }

    @Test
    @DisplayName("toString test")
    public void toString_ReturnsSuccess() throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals("(123A - Kizomba - 25.0 euros - P:10 - M:5)", grupo.toString());
    }

    @Test
    @DisplayName("equals test")
    public void Equals_WhenSame_ReturnsSuccess() throws ClubException {
        Grupo grupo1 = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        Grupo grupo2 = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals(true, grupo1.equals(grupo2));
    }

    @Test
    @DisplayName("equals test returns false")
    public void Equals_WhenDifferent_ReturnsFalse() throws ClubException {
        Grupo grupo1 = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        Grupo grupo2 = new Grupo("456B", "Pilates", 8, 5, 50.0);
        assertNotEquals(grupo1, grupo2);
    }

    @Test
    @DisplayName("hashCode test")
    public void HashCode_ReturnsSuccess() throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals("123A".toUpperCase().hashCode()+"Kizomba".toUpperCase().hashCode(), grupo.hashCode());
    }




}
