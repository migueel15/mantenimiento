package clubdeportivo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoTest {
	ClubDeportivo clubDeportivo;

	@BeforeEach
	public void setup() throws ClubException {
		clubDeportivo = new ClubDeportivo("Club1");
	}

	// CONSTRUCTOR
	// n menor o igual a 0 tira excepcion
	@Test
	public void constructorNZeroOrLessExpectException() {
		assertThrows(ClubException.class, () -> new ClubDeportivo("Club1", 0));
		assertThrows(ClubException.class, () -> new ClubDeportivo("Club1", -1));
	}

	// BUSCAR
	// si no encuentra el grupo devuelve -1
	// si encuentra el grupo devuelve la posicion

	// ANYADIR ACTIVIDAD
	// si datos incorrectos tira excepcion
	// si grupo null tira excepcion
	// si el grupo no existe lo anyade
	// si el grupo ya existe actualiza las plazas

	@Test
	public void anyadirActividadNullGroupExpectException() {
		Grupo grupoNulo = null;
		assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(grupoNulo));
	}

	// @Test
	// public void anyadir

	//
	// PLAZAS LIBRES
	// si la actividad no existe retorna 0
	// si la actividad existe retorna las plazas libres
	//
	// MATRICULAR
	// si mas personas que plaza tira excepcion
	// si personas mayor a plazas en grupo asigna maximo y pasa a siguiente grupo
	// si personas menor a plazas en grupo asigna personas y termina
	//
	// INGRESOS
	// si no hay grupos retorna 0
	// si hay grupos retorna la suma de las tarifas
	//
	// TO STRING
	// si no hay grupos retorna el nombre del club -> []
	// si hay grupos retorna el nombre del club y los grupos
}
