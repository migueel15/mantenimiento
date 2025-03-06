package clubdeportivo;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoAltoRendimientoTest {
    ClubDeportivoAltoRendimiento clubDeportivoAltoRendimiento;

    /*@BeforeEach
    public void initTest{
        try {
            clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento("Club Deportivo Alto Rendimiento 1", 10, 10.0);
        } catch (ClubException e) {
            throw new RuntimeException(e);
        }
    }*/

    @Test
    @DisplayName("test que comprueba que se crea una actividad")
    public void anyadirActividad_test() throws ClubException {

        clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento("clubDeportivoAltoRendimiento", 10, 10.0);
        int plazasIniciales = clubDeportivoAltoRendimiento.plazasLibres("Actividad 1");
        String[] datos = {"Actividad 1", "Descripcion 1", "10", "5", "10.0"};
        clubDeportivoAltoRendimiento.anyadirActividad(datos);
        int plazasNuevas = clubDeportivoAltoRendimiento.plazasLibres("Actividad 1");
        assertNotEquals(plazasIniciales, plazasNuevas);
    }
}