package org.mps;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.mps.mutation.*;
import org.mps.crossover.*;
import org.mps.selection.*;

/**
 * EvolutionaryAlgorithmTest
 */
public class EvolutionaryAlgorithmTest {


  @Test
  public void shouldOptimizePopulationWhenOffspringIsBetter() throws EvolutionaryAlgorithmException {
    // Arrange
    int[][] population = {
        {10, 10},
        {20, 20},
        {30, 30},
        {40, 40}
    };
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(1),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );
    // Act
    int[][] result = algorithm.optimize(population);

    // Assert
    assertNotNull(result);
  }

  @Test
  public void shouldReturnSamePopulationIfNoOffspringIsBetter() throws EvolutionaryAlgorithmException {
    // Arrange
    int[][] population = {
        {5, 5},
        {10, 10},
        {15, 15},
        {20, 20}
    };
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(1),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );

    // Act
    int[][] result = algorithm.optimize(population);

    // Assert
    assertNotNull(result);
  }
  @Test
  public void shouldThrowExceptionIfSelectionIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new EvolutionaryAlgorithm(
        null,
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    ));
  }
  @Test
  public void shouldThrowExceptionIfMutationIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        null,
        new TwoPointCrossover()
    ));
  }
  @Test
  public void shouldThrowExceptionIfCrossoverIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        null
    ));
  }

  @Test
  public void shouldThrowExceptionIfPopulationIsOdd() throws EvolutionaryAlgorithmException {
    // Arrange
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );
    int[][] population = {{5, 5},
        {10, 10},
        {15, 15}
    };
    assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(population));
  }

  @Test
  public void shouldThrowExceptionIfPopulationIsNull() throws EvolutionaryAlgorithmException {
    // Arrange
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );

    int[][] population = null;

    // Act & Assert
    assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(population));
  }

  @Test
  public void shouldThrowExceptionIfPopulationIsEmpty() throws EvolutionaryAlgorithmException {
    // Arrange
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );

    int[][] population = {};

    // Act & Assert
    assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(population));
  }
}
