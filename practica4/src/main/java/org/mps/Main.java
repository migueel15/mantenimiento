package org.mps;

import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.TwoPointCrossover;
import org.mps.mutation.GaussianMutation;
import org.mps.mutation.MutationOperator;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

/**
 * Main
 */
public class Main {
  public static void main(String[] args) {
    try {
      SelectionOperator selectionOperator = new TournamentSelection(4);
      MutationOperator mutationOperator = new GaussianMutation(100, 0.5);
      CrossoverOperator crossoverOperator = new TwoPointCrossover();
      EvolutionaryAlgorithm ev = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);

      int[][] population = new int[10][5];
      for (int i = 0; i < population.length; i++) {
        for (int j = 0; j < population[i].length; j++) {
          population[i][j] = (int) (Math.random() * 100);
        }
      }
      System.out.println("Población inicial:");
      for (int i = 0; i < population.length; i++) {
        for (int j = 0; j < population[i].length; j++) {
          System.out.print(population[i][j] + " ");
        }
        System.out.println();
      }

      System.out.println("Población nueva:");
      int[][] newValues = ev.optimize(population);
      for (int i = 0; i < newValues.length; i++) {
        for (int j = 0; j < newValues[i].length; j++) {
          System.out.print(newValues[i][j] + " ");
        }
        System.out.println();
      }

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
