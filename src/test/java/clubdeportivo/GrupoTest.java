//Autores: Cristian Ruiz Martín y Mikolaj Zabski

package clubdeportivo;

import org.checkerframework.checker.signature.qual.DotSeparatedIdentifiers;
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
    @CsvSource({"123A,Kizomba,10,1,25.0",
            "456B,Pilates,8,5,50.0",
            "789C,Spinning,15,10,30.0"})
    public void CreateGrupo_WithValidData_ReturnsSuccess(String codigo, String actividad, int nplazas, int matriculados, double tarifa) throws ClubException {
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
    public void CreateGrupo_WithInvalidData_ReturnsException(String codigo, String actividad, int nplazas, int matriculados, double tarifa) throws ClubException{
        assertThrows(ClubException.class, () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa));
    }

    @ParameterizedTest
    @DisplayName("Constructor with invalid data, nplazas<matriculas")
    @CsvSource({"123A,Kizomba,10,15,25.0",
            "456B,Pilates,8,10,50.0",
            "789C,Spinning,15,20,30.0"})
    public void CreateGrupo_WithInsufficientPlazas_ReturnsException(String codigo, String actividad, int nplazas, int matriculados, double tarifa) throws ClubException{
        assertThrows(ClubException.class, () -> new Grupo(codigo, actividad, nplazas, matriculados, tarifa));
    }

    @ParameterizedTest
    @DisplayName("ActualizarPlazas with valid data")
    @ValueSource(ints = {10, 15, 20})
    public void ActualizarPlazas_WithValidData_ReturnsSuccess(int n) throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25.0);
        grupo.actualizarPlazas(n);
        assertEquals(n, grupo.getPlazas());
    }

    @ParameterizedTest
    @DisplayName("ActualizarPlazas with invalid data")
    @ValueSource(ints = {0, -5})
    public void ActualizarPlazas_WithInvalidData_ReturnsException(int n) {
        try {
            Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25.0);
            grupo.actualizarPlazas(n);
        } catch (ClubException e) {
            assertEquals("ERROR: número de plazas negativo.", e.getMessage());
        }
    }

    @Test
    @DisplayName("ActualizarPlazas with invalid data, n<matriculados")
    public void ActualizarPlazas_WithInsufficientPlazas_ThrowsException() throws ClubException{
            Grupo grupo = new Grupo("123A", "Kizomba", 10, 10, 25.0);
            assertThrows(ClubException.class, () -> grupo.actualizarPlazas(5));
    }

    @Test
    @DisplayName("PlazasLibres test")
    public void PlazasLibres_ReturnsSuccess() throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals(5, grupo.plazasLibres());
    }

    @ParameterizedTest
    @DisplayName("Matricular with valid data")
    @ValueSource(ints = {5, 3, 1})
    public void Matricular_WithValidData_ReturnsSuccess(int n) throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        grupo.matricular(n);
        assertEquals(5 + n, grupo.getMatriculados());
    }

    @ParameterizedTest
    @DisplayName("Matricular with invalid data")
    @ValueSource(ints = {500, 0, -5, -10})
    public void Matricular_WithInvalidData_ReturnsException(int n) throws ClubException{
            Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
            assertThrows(ClubException.class, () -> grupo.matricular(n));
    }

    @Test
    @DisplayName("ToString test")
    public void ToString_ReturnsSuccess() throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals("(123A - Kizomba - 25.0 euros - P:10 - M:5)", grupo.toString());
    }

    @Test
    @DisplayName("Equals test")
    public void Equals_WhenSame_ReturnsTrue() throws ClubException {
        Grupo grupo1 = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        Grupo grupo2 = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals(grupo1, grupo2);
        assertTrue(grupo1.getActividad().equalsIgnoreCase(grupo2.getActividad())&&grupo1.getCodigo().equalsIgnoreCase(grupo2.getCodigo()));
    }

    @Test
    @DisplayName("Equals test returns false")
    public void Equals_WhenDifferent_ReturnsFalse() throws ClubException {
        Grupo grupo1 = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        Grupo grupo2 = new Grupo("456B", "Pilates", 8, 5, 50.0);
        Grupo grupo3 = new Grupo("123A", "Pilates", 15, 10, 30.0);
        assertNotEquals(grupo1, grupo2);
        assertNotEquals(grupo1, grupo3);
        assertNotEquals(grupo2, grupo3);
    }

    @Test
    @DisplayName("Equals when object is not instance of Grupo")
    public void Equals_WhenObjectIsNotInstanceOfGrupo_ReturnsFalse() throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertNotEquals(grupo, new Object());
    }

    @Test
    @DisplayName("HashCode test")
    public void HashCode_ReturnsSuccess() throws ClubException {
        Grupo grupo = new Grupo("123A", "Kizomba", 10, 5, 25.0);
        assertEquals("123A".toUpperCase().hashCode()+"Kizomba".toUpperCase().hashCode(), grupo.hashCode());
    }
}
