package org.mps;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.mutation.*;
import org.mps.crossover.*;
import org.mps.selection.*;

public class EvolutionaryAlgorithmTest {

  @Test
  @DisplayName("Debería optimizar la población si la descendencia es mejor")
  public void shouldOptimizePopulationWhenOffspringIsBetter() throws EvolutionaryAlgorithmException {
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
    int[][] result = algorithm.optimize(population);
    assertNotNull(result);
  }

  @Test
  @DisplayName("Debería devolver la misma población si ninguna descendencia es mejor")
  public void shouldReturnSamePopulationIfNoOffspringIsBetter() throws EvolutionaryAlgorithmException {
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
    int[][] result = algorithm.optimize(population);
    assertNotNull(result);
  }

  @Test
  @DisplayName("Debería lanzar excepción si el operador de selección es null")
  public void shouldThrowExceptionIfSelectionIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new EvolutionaryAlgorithm(
        null,
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    ));
  }

  @Test
  @DisplayName("Debería lanzar excepción si el operador de mutación es null")
  public void shouldThrowExceptionIfMutationIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        null,
        new TwoPointCrossover()
    ));
  }

  @Test
  @DisplayName("Debería lanzar excepción si el operador de cruce es null")
  public void shouldThrowExceptionIfCrossoverIsNull() throws EvolutionaryAlgorithmException {
    assertThrows(EvolutionaryAlgorithmException.class, () -> new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        null
    ));
  }

  @Test
  @DisplayName("Debería lanzar excepción si la población tiene un tamaño impar")
  public void shouldThrowExceptionIfPopulationIsOdd() throws EvolutionaryAlgorithmException {
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );
    int[][] population = {
        {5, 5},
        {10, 10},
        {15, 15}
    };
    assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(population));
  }

  @Test
  @DisplayName("Debería lanzar excepción si la población es null")
  public void shouldThrowExceptionIfPopulationIsNull() throws EvolutionaryAlgorithmException {
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );
    int[][] population = null;
    assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(population));
  }

  @Test
  @DisplayName("Debería lanzar excepción si la población está vacía")
  public void shouldThrowExceptionIfPopulationIsEmpty() throws EvolutionaryAlgorithmException {
    EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(
        new TournamentSelection(2),
        new GaussianMutation(0.5, 1),
        new TwoPointCrossover()
    );
    int[][] population = {};
    assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(population));
  }
}
