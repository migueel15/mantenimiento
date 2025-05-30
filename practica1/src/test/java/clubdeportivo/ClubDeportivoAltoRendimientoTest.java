// Salma Boulagna Moreno
// Miguel Ángel Dorado Maldonado

package clubdeportivo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoAltoRendimientoTest {
	ClubDeportivoAltoRendimiento clubDeportivoAltoRendimiento;

	@Test
	public void anyadirActividad_test() throws ClubException {

		clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento("clubDeportivoAltoRendimiento", 10, 10.0);
		int plazasIniciales = clubDeportivoAltoRendimiento.plazasLibres("Actividad 1");
		String[] datos = { "abc", "Actividad 1", "10", "5", "10.0" };
		clubDeportivoAltoRendimiento.anyadirActividad(datos);
		int plazasNuevas = clubDeportivoAltoRendimiento.plazasLibres("Actividad 1");
		System.out.println(plazasIniciales + " " + plazasNuevas);
		assertNotEquals(plazasIniciales, plazasNuevas);
	}

	@Test
	public void ClubDeportivoAltoRendimientoMaxNegativeExpectException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = -10;
		double inc = 10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoMaxZeroExpectException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 0;
		double inc = 10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoIncNegativeExpectException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = -10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoIncZeroExpectException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 0;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoOKValuesNoReturnException() {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 10;
		assertDoesNotThrow(() -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoTamNegativeExpectException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 10;
		int tam = -10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, tam, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoTamZeroExpectException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 10;
		int tam = 0;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, tam, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoOKValuesTamNoReturnException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 10;
		int tam = 10;
		assertDoesNotThrow(() -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, tam, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoWTamMaxNegativeNoReturnException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = -10;
		double inc = 10;
		int tam = 10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, tam, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoWTamMaxZeroNoReturnException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 0;
		double inc = 10;
		int tam = 10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, tam, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoWTamIncNegativeNoReturnException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = -10;
		int tam = 10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, tam, max, inc);
		});
	}

	@Test
	public void ClubDeportivoAltoRendimientoWTamIncZeroNoReturnException() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 0;
		int tam = 10;
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, tam, max, inc);
		});
	}

	@Test
	public void anyadirActividadLessRangeExpectException() throws ClubException {
		clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento("clubDeportivoAltoRendimiento", 10, 10.0);
		String[] datos = { "abc", "Actividad 1", "10", "5" };
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento.anyadirActividad(datos);
		});
	}

	@Test
	public void anyadirActividadWrongNumberFormatExpectException() throws ClubException {
		clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento("clubDeportivoAltoRendimiento", 10, 10.0);
		String codigo = "abc";
		String actividad = "Actividad 1";
		String plazas = "wrong format";
		int matriculados = 2;
		double tarifa = 10.0;
		String[] datos = { codigo, actividad, plazas, Integer.toString(matriculados), Double.toString(tarifa) };
		assertThrows(ClubException.class, () -> {
			clubDeportivoAltoRendimiento.anyadirActividad(datos);
		});
	}

	@Test
	public void anyadirActividadOKRangeNoExpectException() throws ClubException {
		clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento("clubDeportivoAltoRendimiento", 10, 10.0);
		String[] datos = { "abc", "Actividad 1", "10", "5", "10.0" };
		assertDoesNotThrow(() -> {
			clubDeportivoAltoRendimiento.anyadirActividad(datos);
		});

	}

	@Test
	public void anyadirActividadPlazasOK() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 10;
		clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		String codigo = "abc";
		String actividad = "Actividad 1";
		int plazas = 8;
		int matriculados = 2;
		double tarifa = 10.0;
		String[] datos = { codigo, actividad, Integer.toString(plazas), Integer.toString(matriculados),
				Double.toString(tarifa) };
		clubDeportivoAltoRendimiento.anyadirActividad(datos);
		int nplazas = clubDeportivoAltoRendimiento.plazasLibres("Actividad 1");
		int expected = 6;
		assertEquals(expected, nplazas);
	}

	@Test
	public void anyadirActividadPlazasEqualsMax() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 10;
		clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		String codigo = "abc";
		String actividad = "Actividad 1";
		int plazas = 15;
		int matriculados = 2;
		double tarifa = 10.0;
		String[] datos = { codigo, actividad, Integer.toString(plazas), Integer.toString(matriculados),
				Double.toString(tarifa) };
		clubDeportivoAltoRendimiento.anyadirActividad(datos);
		int nplazas = clubDeportivoAltoRendimiento.plazasLibres(actividad);
		int expected = max - matriculados;
		assertEquals(expected, nplazas);
	}

	@Test
	public void ingresosZeroGroupsOK() throws ClubException {
		String nombre = "clubDeportivoAltoRendimiento";
		int max = 10;
		double inc = 10;
		clubDeportivoAltoRendimiento = new ClubDeportivoAltoRendimiento(nombre, max, inc);
		double expected = 0.0;
		double current = clubDeportivoAltoRendimiento.ingresos();
		assertEquals(expected, current);
	}
}
