package org.mps.mutation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class GaussianMutationTest {

  @Test
  @DisplayName("Debería lanzar excepción si el individuo es null")
  public void shouldThrowExceptionWhenIndividualIsNull() {
    GaussianMutation mutation = new GaussianMutation(1.0, 1.0);
    int[] individual = null;
    assertThrows(EvolutionaryAlgorithmException.class, () -> mutation.mutate(individual));
  }

  @Test
  @DisplayName("Debería crearse correctamente usando el constructor por defecto")
  public void shouldCreateConstructor() {
    GaussianMutation mutation = new GaussianMutation();
    assertNotNull(mutation);
  }

  @Test
  @DisplayName("Debería mutar un array de un solo elemento si la tasa de mutación es 1")
  public void shouldMutateSingleElementArrayIfRateIsOne() throws EvolutionaryAlgorithmException {
    GaussianMutation mutation = new GaussianMutation(1.0, 1.0);
    int[] individual = {10};
    int[] result = mutation.mutate(individual);
    assertEquals(1, result.length);
  }

  @Test
  @DisplayName("No debería mutar el array si la tasa de mutación es 0")
  public void shouldNotMutateArrayWhenMutationRateIsZero() throws EvolutionaryAlgorithmException {
    GaussianMutation mutation = new GaussianMutation(0.0, 1.0);
    int[] individual = {1, 2, 3};
    int[] result = mutation.mutate(individual);
    assertArrayEquals(individual, result);
  }

  @Test
  @DisplayName("Debería lanzar excepción si se intenta mutar un array null con el constructor por defecto")
  public void shouldThrowExceptionWhenMutationRateIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new GaussianMutation().mutate(null));
  }

  @Test
  @DisplayName("Debería lanzar excepción si el array a mutar está vacío")
  public void shouldThrowExceptionWhenMutationRateIsEmpty() throws EvolutionaryAlgorithmException {
    GaussianMutation mutation = new GaussianMutation();
    int[] individual = {};
    assertThrows(EvolutionaryAlgorithmException.class, () -> mutation.mutate(individual));
  }

  @Test
  @DisplayName("Debería mutar al menos un elemento cuando la tasa de mutación es 1")
  public void shouldPossiblyMutateElementsWhenRateIsOne() throws EvolutionaryAlgorithmException {
    GaussianMutation mutation = new GaussianMutation(1.0, 1.0);
    int[] individual = {0, 0, 0};
    int[] result = mutation.mutate(individual);

    boolean hasChanged = false;
    for (int i = 0; i < individual.length; i++) {
      if (result[i] != 0) {
        hasChanged = true;
        break;
      }
    }
    assertTrue(hasChanged, "Se esperaba al menos una mutación con tasa 1");
  }
}
