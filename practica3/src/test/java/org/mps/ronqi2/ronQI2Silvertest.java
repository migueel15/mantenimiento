package org.mps.ronqi2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.dispositivo.Dispositivo;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ronQI2Silvertest {

  /*
   * Analiza con los caminos base qué pruebas se han de realizar para comprobar
   * que al inicializar funciona como debe ser.
   * El funcionamiento correcto es que si es posible conectar ambos sensores y
   * configurarlos,
   * el método inicializar de ronQI2 o sus subclases,
   * debería devolver true. En cualquier otro caso false. Se deja programado un
   * ejemplo.
   */

  /*
   * Un inicializar debe configurar ambos sensores, comprueba que cuando se
   * inicializa de forma correcta (el conectar es true),
   * se llama una sola vez al configurar de cada sensor.
   */

  /*
   * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta
   * ambos y devuelve true si ambos han sido conectados.
   * Genera las pruebas que estimes oportunas para comprobar su correcto
   * funcionamiento.
   * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que
   * deben ser llamados.
   */

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

    private RonQI2Silver ronQI2Silver;
    private Dispositivo mockDispositivo;

    @BeforeEach
    void setUp() {
        ronQI2Silver = new RonQI2Silver();
        mockDispositivo = mock(Dispositivo.class);
        ronQI2Silver.anyadirDispositivo(mockDispositivo);
    }

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

    @Nested
    @DisplayName("evaluarApneaSuenyo test")
    class EvaluateSleepApnea {

        @BeforeEach
        void setUpApneaTest() {
            ronQI2Silver.anyadirDispositivo(mockDispositivo);
        }

        @Test
        void shouldReturnTrueWhenBothAveragesAreBelowThresholds() {
            when(mockDispositivo.leerSensorPresion()).thenReturn(10.0f);
            when(mockDispositivo.leerSensorSonido()).thenReturn(20.0f);

            for (int i = 0; i < 5; i++) {
                ronQI2Silver.obtenerNuevaLectura();
            }

            boolean result = ronQI2Silver.evaluarApneaSuenyo();
            assertTrue(result);
        }

        @Test
        void shouldReturnFalseWhenBothAveragesAreAboveThresholds() {
            when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
            when(mockDispositivo.leerSensorSonido()).thenReturn(35.0f);

            for (int i = 0; i < 5; i++) {
                ronQI2Silver.obtenerNuevaLectura();
            }

            boolean result = ronQI2Silver.evaluarApneaSuenyo();
            assertFalse(result);
        }

        @Test
        void shouldReturnTrueWhenOnlyPressureAboveThresholdAndSoundBelow() {
            when(mockDispositivo.leerSensorPresion()).thenReturn(25.0f);
            when(mockDispositivo.leerSensorSonido()).thenReturn(10.0f);

            for (int i = 0; i < 5; i++) {
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
