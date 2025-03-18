// Salma Boulagna Moreno
// Miguel Ángel Dorado Maldonado

package clubdeportivo;

import org.junit.jupiter.api.*;
import org.junit.platform.engine.TestTag;

import static org.junit.jupiter.api.Assertions.*;

public class GrupoTest {
	private Grupo grupo;

	@BeforeEach
	public void setup() throws ClubException {
		grupo = new Grupo("123", "Futbol", 20, 10, 22.0);
	}

	@Test
	public void GrupoNegativePlacesExpectException() {
		String code = "121";
		String activity = "Tenis";
		int nPlaces = -20;
		int initialParticipants = 5;
		double fee = 22.0;

		assertThrows(ClubException.class, () -> new Grupo(code, activity, nPlaces, initialParticipants, fee));

	}

	@Test
	public void GrupoZeroPlacesExpectException() {
		String code = "121";
		String activity = "Tenis";
		int nPlaces = 0;
		int initialParticipants = 5;
		double fee = 22.0;

		assertThrows(ClubException.class, () -> new Grupo(code, activity, nPlaces, initialParticipants, fee));

	}

	@Test
	public void GrupoNegativeEnrolledMembersExpectException() {
		String code = "121";
		String activity = "Tenis";
		int nPlaces = 20;
		int initialParticipants = -10;
		double fee = 22.0;

		assertThrows(ClubException.class, () -> new Grupo(code, activity, nPlaces, initialParticipants, fee));

	}

	@Test
	public void GrupoNegativeFeeExpectException() {
		String code = "121";
		String activity = "Tenis";
		int nPlaces = 20;
		int initialParticipants = 10;
		double fee = -22.0;

		assertThrows(ClubException.class, () -> new Grupo(code, activity, nPlaces, initialParticipants, fee));
	}

	@Test
	public void GrupoZeroFeeExpectException() {
		String code = "121";
		String activity = "Tenis";
		int nPlaces = 20;
		int initialParticipants = 10;
		double fee = 0.0;

		assertThrows(ClubException.class, () -> new Grupo(code, activity, nPlaces, initialParticipants, fee));
	}

	@Test
	public void GrupoMoreEnrolledMembersThanPlacesExpectException() {
		String code = "121";
		String activity = "Tenis";
		int nPlaces = 20;
		int initialParticipants = 30;
		double fee = 22.0;

		assertThrows(ClubException.class, () -> new Grupo(code, activity, nPlaces, initialParticipants, fee));
	}

	@Test
	public void GrupoCorrectValuesExpectNoException() throws ClubException {
		String code = "121";
		String activity = "Tenis";
		int nPlaces = 20;
		int initialParticipants = 10;
		double fee = 22.0;

		Grupo g = new Grupo(code, activity, nPlaces, initialParticipants, fee);
		assertNotNull(g);
		assertEquals(code, g.getCodigo());
		assertEquals(activity, g.getActividad());
		assertEquals(nPlaces, g.getPlazas());
		assertEquals(initialParticipants, g.getMatriculados());
		assertEquals(fee, g.getTarifa());
	}

	@Test
	public void getCodigoExpectGroupCode() throws ClubException {
		String expected = "123";
		Grupo g = new Grupo(expected, "Futbol", 20, 10, 22.0);
		String result = g.getCodigo();
		assertEquals(expected, result);
	}

	@Test
	public void getActividadExpectActivity() throws ClubException {
		String expected = "Futbol";
		Grupo g = new Grupo("123", expected, 20, 10, 22.0);
		String result = g.getActividad();
		assertEquals(expected, result);
	}

	@Test
	public void getPlazasExpectPlaces() throws ClubException {
		int expected = 20;
		Grupo g = new Grupo("123", "Futbol", expected, 10, 22.0);
		int result = g.getPlazas();
		assertEquals(expected, result);
	}

	@Test
	public void getMatriculadosExpectEnrolledMembers() throws ClubException {
		int expected = 10;
		Grupo g = new Grupo("123", "Futbol", 20, expected, 22.0);
		int result = g.getMatriculados();
		assertEquals(expected, result);
	}

	@Test
	public void getTarifaExpectFee() throws ClubException {
		double expected = 22.0;
		Grupo g = new Grupo("123", "Futbol", 20, 10, expected);
		double result = g.getTarifa();
		assertEquals(expected, result);
	}

	@Test
	public void plazasLibresExpectFreePlaces() throws ClubException {
		int places = 20;
		int enrolledMembers = 10;
		int expected = places - enrolledMembers;
		Grupo g = new Grupo("123", "Futbol", places, enrolledMembers, 22.0);
		int result = g.plazasLibres();

		assertEquals(expected, result);
	}

	@Test
	public void actualizarPlazasNegativeNumberExpectException() {
		int negativeNumber = -5;
		assertThrows(ClubException.class, () -> grupo.actualizarPlazas(negativeNumber));
	}

	@Test
	public void actualizarPlazasZeroExpectException() {
		int zero = 0;
		assertThrows(ClubException.class, () -> grupo.actualizarPlazas(zero));
	}

	@Test
	public void actualizarPlazasNumberLessThanEnrolledMembersExpectException() {
		int numberLessThanEnrolledMembers = grupo.getMatriculados() - 1;
		assertThrows(ClubException.class, () -> grupo.actualizarPlazas(numberLessThanEnrolledMembers));
	}

	@Test
	public void actualizarPlazasCorrectNumberExpectNoException() throws ClubException {
		int correctNumber = 25;
		grupo.actualizarPlazas(correctNumber);
		int actualPlaces = grupo.getPlazas();
		assertEquals(correctNumber, actualPlaces);
	}

	@Test
	public void matricularNegativeNumberExpectException() {
		int negativeNumber = -5;
		assertThrows(ClubException.class, () -> grupo.matricular(negativeNumber));
	}

	@Test
	public void matricularZeroExpectException() {
		int zero = 0;
		assertThrows(ClubException.class, () -> grupo.matricular(zero));
	}

	@Test
	public void matricularMoreThanFreePlacesExpectException() {
		int moreThanFreePlaces = grupo.plazasLibres() + 1;
		assertThrows(ClubException.class, () -> grupo.matricular(moreThanFreePlaces));
	}

	@Test
	public void matricularCorrectNumberExpectNoException() throws ClubException {
		int correctNumber = 5;
		int expectedEnrolledMembers = grupo.getMatriculados() + correctNumber;
		grupo.matricular(correctNumber);
		int actualEnrolledMembers = grupo.getMatriculados();
		assertEquals(expectedEnrolledMembers, actualEnrolledMembers);
	}

	@Test
	public void toStringExpectCorrectString() {
		String expected = "(123 - Futbol - 22.0 euros - P:20 - M:10)";
		String result = grupo.toString();
		assertEquals(expected, result);
	}

	@Test
	public void equalsDifferentObjectExpectFalse() throws ClubException {
		Grupo g = new Grupo("1234", "Tenis", 20, 10, 22.0); // different object
		boolean result = grupo.equals(g);
		assertFalse(result);
	}

	@Test
	public void equalsSameObjectExpectTrue() {
		boolean result = grupo.equals(grupo);
		assertTrue(result);
	}

	@Test
	public void equalsDifferentGroupSameCodeAndActivityExpectTrue() throws ClubException {
		Grupo g = new Grupo("123", "Futbol", 27, 5, 17.0); // same code and activity
		boolean result = grupo.equals(g);
		assertTrue(result);
	}

	@Test
	public void equalsDifferentNameExpectFalse() throws ClubException {
		Grupo g = new Grupo("123", "Tenis", 20, 10, 22.0); // different name
		boolean result = grupo.equals(g);
		assertFalse(result);
	}

	@Test
	public void equalsDifferentCodeExpectFalse() throws ClubException {
		Grupo g = new Grupo("1234", "Futbol", 20, 10, 22.0); // different code
		boolean result = grupo.equals(g);
		assertFalse(result);
	}

	@Test
	public void equalsDifferentObjectTypesExpectFalse() {
		Object o = new Object();
		boolean result = grupo.equals(o);
		assertFalse(result);
	}

	@Test
	public void hashCodeExpectHashCode() throws ClubException {
		Grupo g = new Grupo("1235", "Padel", 20, 10, 22.0);
		int hashg1 = grupo.hashCode();
		int hashg2 = g.hashCode();
		assertNotEquals(hashg1, hashg2);
	}

}
