package org.mps.crossover;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

/**
 * TwoPointCrossoverTest
 */
public class TwoPointCrossoverTest {

  // constructor funcional
  @Test
  @DisplayName("Constructor correcto")
  public void twoPointCrossoverValidConstructor() {
    TwoPointCrossover tp;

    tp = new TwoPointCrossover();

    assertNotNull(tp);
  }

  @Test
  @DisplayName("Crossover parent 1 es null")
  public void twoPointCrossoverParent1NullExpectsException() {
    TwoPointCrossover tp = new TwoPointCrossover();
    int[] parent1 = null;
    int[] parent2 = { 3, 4, 6, 8, 21, 4 };

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      tp.crossover(parent1, parent2);
    });
  }

  @Test
  @DisplayName("Crossover parent 2 es null")
  public void twoPointCrossoverParent2NullExpectsException() {
    TwoPointCrossover tp = new TwoPointCrossover();
    int[] parent1 = { 3, 4, 6, 8, 21, 4 };
    int[] parent2 = null;

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      tp.crossover(parent1, parent2);
    });
  }

  @Test
  @DisplayName("Crossover parent1 length == 1")
  public void twoPointCrossoverParent1Lenght1ExpectsException() {
    TwoPointCrossover tp = new TwoPointCrossover();
    int[] parent1 = { 3 };
    int[] parent2 = { 1, 2, 5, 6, 1, 3 };

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      tp.crossover(parent1, parent2);
    });
  }

  @Test
  @DisplayName("Crossover parent2 lenght not equal to parent2")
  public void twoPointCrossoverParent2LengthNotEqualToParent1ExpectException() {
    TwoPointCrossover tp = new TwoPointCrossover();
    int[] parent1 = { 1, 2, 3, 4, 5 };
    int[] parent2 = { 1, 2, 5, 6, 1, 7 };

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      tp.crossover(parent1, parent2);
    });
  }

  @Test
  @DisplayName("Crossover correct functionality")
  public void twoPointCrossoverCorrect() throws EvolutionaryAlgorithmException {
    TwoPointCrossover tp = new TwoPointCrossover();
    int[] parent1 = { 17, 2, 32, 4, 5 };
    int[] parent2 = { 6, 2, 4, 9, 34 };

    int[][] result = tp.crossover(parent1, parent2);

    // comprobar que existan los dos arrays de cada padre
    assertNotNull(result);
    assertNotNull(result[0]);
    assertNotNull(result[1]);
  }

}
