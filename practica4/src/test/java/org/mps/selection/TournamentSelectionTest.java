package org.mps.selection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

/**
 * TournamentSelectionTest
 */
public class TournamentSelectionTest {
  @Test
  @DisplayName("TournamentSelection tournament size less than 1")
  public void tournamentSelectionConstructorWithInvalidSizeExpectsException() {
    int tournamentSize = -1;

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      new TournamentSelection(tournamentSize);
    });
  }

  @Test
  @DisplayName("TournamentSelection tournament size greater than 0")
  public void tournamentSelectionConstructorWithValidSizeCorrectExecution() throws EvolutionaryAlgorithmException {
    int tournamentSize = 10;

    TournamentSelection ts = new TournamentSelection(tournamentSize);

    assertNotNull(ts);
  }

  @Test
  @DisplayName("Select population null")
  public void tournamentSelectionNullPopulationExpectsException() throws EvolutionaryAlgorithmException {
    int tournamentSize = 10;
    TournamentSelection ts = new TournamentSelection(tournamentSize);
    int[] population = null;

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      ts.select(population);
    });
  }

  @Test
  @DisplayName("Select population less than 1")
  public void tournamentSelectionPopulationSizeLessThan1ExpectsException() throws EvolutionaryAlgorithmException {
    int tournamentSize = 5;
    TournamentSelection ts = new TournamentSelection(tournamentSize);
    int[] population = {};

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      ts.select(population);
    });
  }

  @Test
  @DisplayName("Select population less than tournamentSize")
  public void tournamentSelectionPopulationSizeLessThanTournamentSize() throws EvolutionaryAlgorithmException {
    int tournamentSize = 5;
    TournamentSelection ts = new TournamentSelection(tournamentSize);
    int[] population = { 1, 2, 3 };

    assertThrows(EvolutionaryAlgorithmException.class, () -> {
      ts.select(population);
    });
  }

  @Test
  @DisplayName("Select array size expected")
  public void tournamentSelectionGivenPopulationReturnSameSize() throws EvolutionaryAlgorithmException {
    int tournamentSize = 5;
    TournamentSelection ts = new TournamentSelection(tournamentSize);
    int[] population = { 1, 2, 3, 4, 5, 6 };

    int[] newSelection = ts.select(population);

    assertEquals(population.length, newSelection.length);
  }

  @Test
  @DisplayName("Select returned genes exist in initial array")
  public void tournamentSelectionReturnedGenesExpextToBeInInitalArray() throws EvolutionaryAlgorithmException {
    int tournamentSize = 5;
    TournamentSelection ts = new TournamentSelection(tournamentSize);
    int[] population = { 1, 2, 3, 4, 5, 6 };

    int[] selected = ts.select(population);

    Set<Integer> validGenes = new HashSet<>();
    for (int gene : population) {
      validGenes.add(gene);
    }

    for (int gene : selected) {
      assertTrue(validGenes.contains(gene));
    }
  }

}
