// Miguel Angel Dorado Maldonado
// Salma Boulgna Moreno
package org.mps.ronqi2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mps.dispositivo.Dispositivo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ronQI2SilverTest {

  /*
   * Analiza con los caminos base qué pruebas se han de realizar para comprobar
   * que al inicializar funciona como debe ser.
   * El funcionamiento correcto es que si es posible conectar ambos sensores y
   * configurarlos,
   * el método inicializar de ronQI2 o sus subclases,
   * debería devolver true. En cualquier otro caso false. Se deja programado un
   * ejemplo.
   */

  private RonQI2Silver ronQI2Silver;
  private Dispositivo mockDispositivo;

  @BeforeEach
  void setUp() {
    ronQI2Silver = new RonQI2Silver();
    mockDispositivo = mock(Dispositivo.class);
    ronQI2Silver.anyadirDispositivo(mockDispositivo);
  }

  @Nested
  @DisplayName("Inicializar tests")
  public class InicializarTests {

    @DisplayName("Disp preasure sensor not connected")
    @Test
    public void inicializarPreasureSensorNotConnectedExpectsFalse() {
      when(mockDispositivo.conectarSensorPresion()).thenReturn(false);

      boolean result = ronQI2Silver.inicializar();
      assertFalse(result);
    }

    @DisplayName("Disp sound sensor not connected")
    @Test
    public void inicializarSoundSensorNotConnectedExpectsFalse() {
      when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
      when(mockDispositivo.configurarSensorPresion()).thenReturn(true);
      when(mockDispositivo.conectarSensorSonido()).thenReturn(false);

      boolean result = ronQI2Silver.inicializar();
      assertFalse(result);
    }

    @DisplayName("Disp set preasure sensor fails")
    @Test
    public void inicializarPreasureSensorConfigurationFailsExpectsFalse() {
      when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
      when(mockDispositivo.conectarSensorSonido()).thenReturn(true);
      when(mockDispositivo.configurarSensorPresion()).thenReturn(false);
      when(mockDispositivo.configurarSensorSonido()).thenReturn(true);

      boolean result = ronQI2Silver.inicializar();
      assertFalse(result);
    }

    @DisplayName("Disp set sound sensor fails")
    @Test
    public void inicializarSoundSensorConfigurationFailsExpectsFalse() {
      when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
      when(mockDispositivo.conectarSensorSonido()).thenReturn(true);
      when(mockDispositivo.configurarSensorPresion()).thenReturn(true);
      when(mockDispositivo.configurarSensorSonido()).thenReturn(false);

      boolean result = ronQI2Silver.inicializar();
      assertFalse(result);
    }

    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se
     * inicializa de forma correcta (el conectar es true),
     * se llama una sola vez al configurar de cada sensor.
     */

    @DisplayName("Disp correct execution")
    @Test
    public void inicializarCorrectValuesExpectsTrue() {
      when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
      when(mockDispositivo.conectarSensorSonido()).thenReturn(true);
      when(mockDispositivo.configurarSensorPresion()).thenReturn(true);
      when(mockDispositivo.configurarSensorSonido()).thenReturn(true);

      boolean result = ronQI2Silver.inicializar();

      assertTrue(result);
      verify(mockDispositivo).configurarSensorPresion();
      verify(mockDispositivo).configurarSensorSonido();
    }

    @DisplayName("Disp not defined")
    @Test
    public void inicializarWithNullDispExpectsException() {
      ronQI2Silver.disp = null;
      assertThrows(RuntimeException.class, () -> {
        ronQI2Silver.inicializar();
      });
    }

  }

  /*
   * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta
   * ambos y devuelve true si ambos han sido conectados.
   * Genera las pruebas que estimes oportunas para comprobar su correcto
   * funcionamiento.
   * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que
   * deben ser llamados.
   */

  @Nested
  @DisplayName("reconectar test when device disconected")
  class WhenDeviceIsDisconnected {

    @Test
    void shouldReconnectSuccessfullyIfBothSensorsConnect() {
      when(mockDispositivo.estaConectado()).thenReturn(false);
      when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
      when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

      boolean result = ronQI2Silver.reconectar();

      assertTrue(result);
      verify(mockDispositivo).conectarSensorPresion();
      verify(mockDispositivo).conectarSensorSonido();
    }

    @Test
    void shouldFailToReconnectIfPressureSensorFails() {
      when(mockDispositivo.estaConectado()).thenReturn(false);
      when(mockDispositivo.conectarSensorPresion()).thenReturn(false);
      when(mockDispositivo.conectarSensorSonido()).thenReturn(true);

      boolean result = ronQI2Silver.reconectar();

      assertFalse(result);
      verify(mockDispositivo).conectarSensorPresion();
      verify(mockDispositivo, never()).conectarSensorSonido();
    }

    @Test
    void shouldFailToReconnectIfSoundSensorFails() {
      when(mockDispositivo.estaConectado()).thenReturn(false);
      when(mockDispositivo.conectarSensorPresion()).thenReturn(true);
      when(mockDispositivo.conectarSensorSonido()).thenReturn(false);

      boolean result = ronQI2Silver.reconectar();

      assertFalse(result);
      verify(mockDispositivo).conectarSensorPresion();
      verify(mockDispositivo).conectarSensorSonido();
    }
  }

  @Nested
  @DisplayName("reconectar test when device is already conected")
  class WhenDeviceIsAlreadyConnected {

    @Test
    void shouldNotAttemptToReconnect() {
      when(mockDispositivo.estaConectado()).thenReturn(true);

      boolean result = ronQI2Silver.reconectar();

      assertFalse(result);
      verify(mockDispositivo, never()).conectarSensorPresion();
      verify(mockDispositivo, never()).conectarSensorSonido();
    }
  }

  /*
   * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con
   * obtenerNuevaLectura(),
   * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP
   * = 20.0f y thresholdS = 30.0f;,
   * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también
   * debería realizar la media.
   * /
   * 
   * /* Realiza un primer test para ver que funciona bien independientemente del
   * número de lecturas.
   * Usa el ParameterizedTest para realizar un número de lecturas previas a
   * calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
   * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-
   * parameterized-tests
   */

  @Nested
  @DisplayName("evaluarApneaSuenyo test")
  class EvaluateSleepApnea {

    @BeforeEach
    void setUpApneaTest() {
      ronQI2Silver.anyadirDispositivo(mockDispositivo);
    }

    @Test
    void shouldReturnFalseWhenBothAveragesAreBelowThresholds() {
      when(mockDispositivo.leerSensorPresion()).thenReturn(10.0f);
      when(mockDispositivo.leerSensorSonido()).thenReturn(20.0f);

      for (int i = 0; i < 5; i++) {
        ronQI2Silver.obtenerNuevaLectura();
      }

      boolean result = ronQI2Silver.evaluarApneaSuenyo();
      assertFalse(result);
    }

    @Test
    void shouldReturnTrueWhenBothAveragesAreAboveThresholds() {
      when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
      when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);

      for (int i = 0; i < 5; i++) {
        ronQI2Silver.obtenerNuevaLectura();
      }

      boolean result = ronQI2Silver.evaluarApneaSuenyo();
      assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenOnlyPressureAboveThresholdAndSoundBelow() {
      when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
      when(mockDispositivo.leerSensorSonido()).thenReturn(10.0f);

      for (int i = 0; i < 5; i++) {
        ronQI2Silver.obtenerNuevaLectura();
      }

      boolean result = ronQI2Silver.evaluarApneaSuenyo();
      assertFalse(result);
    }

    @ParameterizedTest
    @ValueSource(ints = { 4, 5, 10 })
    public void checkCorrectUsageForDifferentNumberOfLectures(int lectures) {
      ronQI2Silver = new RonQI2Silver();
      mockDispositivo = mock(Dispositivo.class);
      ronQI2Silver.anyadirDispositivo(mockDispositivo);

      when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
      when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);
      for (int i = 0; i < lectures; i++) {
        ronQI2Silver.obtenerNuevaLectura();
      }
      boolean result = ronQI2Silver.evaluarApneaSuenyo();
      assertTrue(result);
    }
  }

  @Nested
  @DisplayName("estaConectado test")
  class CheckConnectionStatus {

    @Test
    void shouldReturnTrueWhenDeviceIsConnected() {
      when(mockDispositivo.estaConectado()).thenReturn(true);
      ronQI2Silver.anyadirDispositivo(mockDispositivo);
      assertTrue(ronQI2Silver.estaConectado());
      verify(mockDispositivo).estaConectado();
    }

    @Test
    void shouldReturnFalseWhenDeviceIsDisconnected() {
      when(mockDispositivo.estaConectado()).thenReturn(false);
      ronQI2Silver.anyadirDispositivo(mockDispositivo);

      assertFalse(ronQI2Silver.estaConectado());
      verify(mockDispositivo).estaConectado();
    }
  }

  @Nested
  @DisplayName("anyadirDispositivo test")
  class AddDevice {

    @Test
    void shouldAssignDeviceCorrectly() {
      when(mockDispositivo.estaConectado()).thenReturn(true);

      ronQI2Silver.anyadirDispositivo(mockDispositivo);
      boolean result = ronQI2Silver.estaConectado();

      assertTrue(result);
      verify(mockDispositivo).estaConectado();
    }
  }

}
