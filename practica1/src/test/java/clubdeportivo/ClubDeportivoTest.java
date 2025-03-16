package clubdeportivo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoTest {
	ClubDeportivo clubDeportivo;

	@BeforeEach
	public void setup() throws ClubException {
		clubDeportivo = new ClubDeportivo("Club1", 5);
	}

	@Test
	public void ClubDeportivoNZeroOrLessExpectException() {
		assertThrows(ClubException.class, () -> new ClubDeportivo("Club1", 0));
		assertThrows(ClubException.class, () -> new ClubDeportivo("Club1", -1));
	}

	@Test
	public void anyadirActividadIncorrectDataExpectException() {
		String[] grupoIncorrecto = { "122", "12", "Club", "2", "diez" };
		assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(grupoIncorrecto));
	}

	@Test
	public void anyadirActividadLowerDataLenghtExpectClubException() {
		String[] grupoIncorrecto = { "122", "Futbol", "10", "0" };
		assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(grupoIncorrecto));
	}

	@Test
	public void anyadirActividadGreaterDataLenghtExpectCorrectBehaviour() throws ClubException {
		String places = "10";
		String actividad = "Futbol";
		String[] grupoIncorrecto = { "122", actividad, places, "0", "20.0", "trash_data" };

		clubDeportivo.anyadirActividad(grupoIncorrecto);
		int currentPlaces = clubDeportivo.plazasLibres(actividad); // trash data never used no exception

		assertEquals(Integer.parseInt(places), currentPlaces);
	}

	@Test
	public void anyadirActividadNullGroupExpectException() {
		Grupo grupoNulo = null;
		assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(grupoNulo));
	}

	@Test
	public void anyadirActividadNewGroupExpectToBeCorrectlyAdded() throws ClubException {
		int places = 10;
		Grupo newGroup = new Grupo("123", "Futbol", places, 0, 20.0);
		clubDeportivo.anyadirActividad(newGroup);
		int currPlaces = clubDeportivo.plazasLibres("Futbol");

		assertEquals(places, currPlaces);
	}

	@Test
	public void anyadirActividadAlreadyExistingGroupExpectUpdatePlaces() throws ClubException {
		String codigo = "123";
		int initialPlaces = 10;
		int newPlaces = 5;
		String actividad = "Futbol";
		Grupo initialGroup = new Grupo(codigo, actividad, initialPlaces, 0, 20.0);
		Grupo newGroup = new Grupo(codigo, actividad, newPlaces, 0, 20.0);

		clubDeportivo.anyadirActividad(initialGroup);
		clubDeportivo.anyadirActividad(newGroup);
		int updatedActivityPlaces = clubDeportivo.plazasLibres(actividad);

		assertEquals(newPlaces, updatedActivityPlaces);
	}

	@Test
	public void anyadirActividadReachMaxActivitiesExpectException() throws ClubException {
		// club initialized with 5 activities max
		Grupo g1 = new Grupo("1", "Futbol", 10, 0, 20.0);
		Grupo g2 = new Grupo("2", "Baloncesto", 10, 0, 20.0);
		Grupo g3 = new Grupo("3", "Tenis", 10, 0, 20.0);
		Grupo g4 = new Grupo("4", "Padel", 10, 0, 20.0);
		Grupo g5 = new Grupo("5", "Natacion", 10, 0, 20.0);
		Grupo g6 = new Grupo("6", "Atletismo", 10, 0, 20.0);

		clubDeportivo.anyadirActividad(g1);
		clubDeportivo.anyadirActividad(g2);
		clubDeportivo.anyadirActividad(g3);
		clubDeportivo.anyadirActividad(g4);
		clubDeportivo.anyadirActividad(g5);

		assertThrows(ClubException.class, () -> clubDeportivo.anyadirActividad(g6));
	}

	@Test
	public void plazasLibresNonExistingActivityExpectZero() {
		String fakeActivity = "Futbol";
		int currentPlaces = clubDeportivo.plazasLibres(fakeActivity);

		assertEquals(0, currentPlaces);
	}

	@Test
	public void plazasLibresExistingActivityZeroParticipantsExpectCorrectFreePlaces() throws ClubException {
		String actividad = "Futbol";
		int initialFreePlaces = 22;
		int initialParticipants = 0;
		Grupo g1 = new Grupo("123", actividad, initialFreePlaces, initialParticipants, 20.0);
		clubDeportivo.anyadirActividad(g1);
		int expectedFreePlaces = initialFreePlaces;
		int result = clubDeportivo.plazasLibres(actividad);

		assertEquals(expectedFreePlaces, result);
	}

	@Test
	public void plazasLibresExistingActivityNonZeroParticipantsExpectCorrectFreePlaces() throws ClubException {
		String actividad = "Futbol";
		int initialFreePlaces = 22;
		int initialParticipants = 5;
		Grupo g1 = new Grupo("123", actividad, initialFreePlaces, initialParticipants, 20.0);
		clubDeportivo.anyadirActividad(g1);
		int expectedFreePlaces = initialFreePlaces - initialParticipants;
		int result = clubDeportivo.plazasLibres(actividad);

		assertEquals(expectedFreePlaces, result);
	}

	@Test
	public void matricularPersonToNonExistingActivityExpectException() throws ClubException {
		String fakeActivity = "Tenis";
		int npersons = 2;
		assertThrows(ClubException.class, () -> clubDeportivo.matricular(fakeActivity, npersons));
	}

	@Test
	public void matricularMoreParticipantsThanPlacesExpectException() throws ClubException {
		String actividad = "Futbol";
		int initialPlaces = 10;
		int initialParticipants = 5;
		int newParticipants = initialPlaces - initialParticipants + 1;
		Grupo grupo = new Grupo("123", actividad, initialPlaces, initialParticipants, 20.0);
		clubDeportivo.anyadirActividad(grupo);
		assertThrows(ClubException.class, () -> clubDeportivo.matricular(actividad, newParticipants));
	}

	@Test
	public void matricularValidNumberOfParticipantsExpectCorrectAction() throws ClubException {
		String actividad = "Futbol";
		int initialPlaces = 10;
		int initialParticipants = 5;
		int initialFreePlaces = initialPlaces - initialParticipants;
		int newValidParticipants = initialFreePlaces;

		Grupo grupo = new Grupo("123", actividad, initialPlaces, initialParticipants, 20.0);
		clubDeportivo.anyadirActividad(grupo);
		int prevFreePlaces = clubDeportivo.plazasLibres(actividad);
		clubDeportivo.matricular(actividad, newValidParticipants);
		int currentFreePlaces = clubDeportivo.plazasLibres(actividad);

		int expectedFreePlaces = prevFreePlaces - newValidParticipants;

		assertEquals(expectedFreePlaces, currentFreePlaces);
	}

	@Test
	public void matricularZeroParticipantsToActivityExpectSameFreePlaces() throws ClubException {
		String actividad = "Futbol";
		int initialPlaces = 10;
		int initialParticipants = 5;
		int newParticipants = 0;

		Grupo grupo = new Grupo("123", actividad, initialPlaces, initialParticipants, 20.0);
		clubDeportivo.anyadirActividad(grupo);
		int prevFreePlaces = clubDeportivo.plazasLibres(actividad);
		clubDeportivo.matricular(actividad, newParticipants);
		int currentFreePlaces = clubDeportivo.plazasLibres(actividad);

		assertEquals(prevFreePlaces, currentFreePlaces);
	}

	@Test
	public void ingresosZeroGroupsExpectZero() {
		double expected = 0.0;
		double current = clubDeportivo.ingresos();

		assertEquals(expected, current);
	}

	@Test
	public void ingresosReturnCorrectRevenues() throws ClubException {
		String actividad1 = "Futbol";
		String actividad2 = "Baloncesto";
		String actividad3 = "Tenis";
		Grupo grupo1 = new Grupo("1", actividad1, 10, 0, 20.0);
		Grupo grupo2 = new Grupo("2", actividad2, 10, 0, 30.0);
		Grupo grupo3 = new Grupo("3", actividad3, 10, 0, 40.0);
		clubDeportivo.anyadirActividad(grupo1);
		clubDeportivo.anyadirActividad(grupo2);
		clubDeportivo.anyadirActividad(grupo3);
		clubDeportivo.matricular(actividad1, 5);
		clubDeportivo.matricular(actividad2, 5);
		clubDeportivo.matricular(actividad3, 1);
		double expected = grupo1.getTarifa() * grupo1.getMatriculados() + grupo2.getTarifa() * grupo2.getMatriculados()
				+ grupo3.getTarifa() * grupo3.getMatriculados();

		double current = clubDeportivo.ingresos();

		assertEquals(expected, current);
	}

	@Test
	public void toStringZeroGroupsExpectClubName() {
		String expected = "Club1 --> [  ]";
		String current = clubDeportivo.toString();

		assertEquals(expected, current);
	}

	@Test
	public void toStringReturnClubNameAndGroups() throws ClubException {
		String actividad1 = "Futbol";
		String actividad2 = "Baloncesto";
		String actividad3 = "Tenis";
		Grupo grupo1 = new Grupo("1", actividad1, 10, 0, 20.0);
		Grupo grupo2 = new Grupo("2", actividad2, 10, 0, 30.0);
		Grupo grupo3 = new Grupo("3", actividad3, 10, 0, 40.0);
		clubDeportivo.anyadirActividad(grupo1);
		clubDeportivo.anyadirActividad(grupo2);
		clubDeportivo.anyadirActividad(grupo3);
		String expected = "Club1 --> [ (1 - Futbol - 20.0 euros - P:10 - M:0), (2 - Baloncesto - 30.0 euros - P:10 - M:0), (3 - Tenis - 40.0 euros - P:10 - M:0) ]";
		String current = clubDeportivo.toString();

		assertEquals(expected, current);
	}
}
