package org.mps.mutation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.*;
import org.mps.EvolutionaryAlgorithmException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * GaussianMutationTest
 */
public class GaussianMutationTest {
  @Test
  public void shouldThrowExceptionWhenIndividualIsNull() {
    // Arrange
    GaussianMutation mutation = new GaussianMutation(1.0, 1.0);
    int[] individual = null;

    // Act & Assert
    assertThrows(EvolutionaryAlgorithmException.class, () -> mutation.mutate(individual));
  }
  @Test
  public void shouldCreateConstructor(){
    GaussianMutation mutation = new GaussianMutation();
    assertNotNull(mutation);
  }
  @Test
  public void shouldMutateSingleElementArrayIfRateIsOne() throws EvolutionaryAlgorithmException {
    // Arrange
    GaussianMutation mutation = new GaussianMutation(1.0, 1.0);
    int[] individual = {10};

    // Act
    int[] result = mutation.mutate(individual);

    // Assert
    assertEquals(1, result.length);
    // El valor puede cambiar, no podemos asegurar el número, pero sí que no lance error
  }

  @Test
  public void shouldNotMutateArrayWhenMutationRateIsZero() throws EvolutionaryAlgorithmException {
    // Arrange
    GaussianMutation mutation = new GaussianMutation(0.0, 1.0);
    int[] individual = {1, 2, 3};

    // Act
    int[] result = mutation.mutate(individual);

    // Assert
    assertArrayEquals(individual, result);
  }
  @Test
  public void shouldThrowExceptionWhenMutationRateIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new GaussianMutation().mutate(null));
  }
  @Test
  public void shouldThrowExceptionWhenMutationRateIsEmpty() throws EvolutionaryAlgorithmException {
    GaussianMutation mutation = new GaussianMutation();
    int[] individual = {};
    assertThrows(EvolutionaryAlgorithmException.class, () -> mutation.mutate(individual));
  }
  @Test
  public void shouldPossiblyMutateElementsWhenRateIsOne() throws EvolutionaryAlgorithmException {
    // Arrange
    GaussianMutation mutation = new GaussianMutation(1.0, 1.0);
    int[] individual = {0, 0, 0};

    // Act
    int[] result = mutation.mutate(individual);

    // Assert
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
