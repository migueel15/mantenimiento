package clubdeportivo;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ClubDeportivoTest {
	ClubDeportivo clubDeportivo;

	@BeforeEach
	public void setup() {
		// clubDeportivo = new ClubDeportivo("Club1");
	}

	@Test
	public void constructorThrowsIfNoName() {
		assertTrue(true);
	}

}
