package org.mps.boundedqueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayBoundedQueueTest
 */
public class ArrayBoundedQueueTest {
  @Nested
  class ArrayBoundedQueueConstructor {
    @Test
    @DisplayName("si la capacidad es 0, lanza excepcion")
    void capacityEqualsZero_throwsException() {
      assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ArrayBoundedQueue<>(0));
    }

    @Test
    @DisplayName("si la capacidad es menor que 0, lanza excepcion")
    void capacityLessThanZero_throwsException() {
      assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ArrayBoundedQueue<>(-1));
    }

    @Test
    @DisplayName("si los valores son correctos, el tamañop inicial es 0, la cola esta vacía" +
        "y los indices iniciales estan bien")
    void constructorISOK() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(4);
      assertThat(queue.size()).isEqualTo(0);
      assertThat(queue.isEmpty()).isTrue();
      assertThat(queue.getFirst()).isEqualTo(0);
      assertThat(queue.getLast()).isEqualTo(0);
    }
  }

  @Nested
  class ArrayBoundedQueuePut {
    @Test
    @DisplayName("si esta lleno, lanza excepcion")
    void isFull_throwsException() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(1);
      queue.put(1);
      assertThat(queue.isFull()).isTrue();
      assertThatExceptionOfType(FullBoundedQueueException.class).isThrownBy(() -> queue.put(2));
    }

    @Test
    @DisplayName("si se le pasa a put un parametro nulo, lanza excepcion")
    void putGetsNullValue_throwsException() {
      assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ArrayBoundedQueue<>(2).put(null));
    }

    @Test
    @DisplayName("el put funciona correctamente")
    void putISOK() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(2);
      queue.put(1);
      assertThat(queue.size()).isEqualTo(1);
      assertThat(queue.getFirst()).isEqualTo(0);
      assertThat(queue.getLast()).isEqualTo(1);
    }
  }

  @Nested
  class ArrayBoundedQueueGet {
    @Test
    @DisplayName("si esta vacio, lanza excepcion")
    void isEmpty_throwsException() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(2);
      assertThat(queue.isEmpty()).isTrue();
      assertThatExceptionOfType(EmptyBoundedQueueException.class).isThrownBy(() -> queue.get());
    }

    @Test
    @DisplayName("el get funciona correctamente")
    void getISOK() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(2);
      queue.put(1);
      assertThat(queue.size()).isEqualTo(1);
      queue.get();
      assertThat(queue.size()).isEqualTo(0);
    }
  }

  @Nested
  public class ArrayBoundedQueueSize {
    @Test
    @DisplayName("Test size con cola vacía")
    public void sizeWithEmptyQueueReturns0() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(10);
      assertThat(queue.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test size con cola llena")
    public void sizeWithFullQueueReturnsQueueSize() {
      int size = 5;
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(size);
      for (int i = 0; i < size; i++) {
        queue.put(i);
      }

      assertThat(queue.size()).isEqualTo(size);
    }

    @Test
    @DisplayName("Test size con cola parcialmente llena")
    public void sizeWithPartiallyFullQueueReturnsCorrectSize() {
      int size = 5;
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(size);
      for (int i = 0; i < size - 1; i++) {
        queue.put(i);
      }

      assertThat(queue.size()).isEqualTo(size - 1);
    }

    @Test
    @DisplayName("Test size con cola llena y elementos sacados")
    public void sizeWithFullQueueAndElementsRemovedReturnsCorrectSize() {
      int size = 5;
      int elementsToRemove = 2;

      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(size);
      for (int i = 0; i < size; i++) {
        queue.put(i);
      }
      for (int i = 0; i < elementsToRemove; i++) {
        queue.get();
      }

      assertThat(queue.size()).isEqualTo(size - elementsToRemove);
    }
  }

  @Nested
  public class ArrayBoundedQueueGetFirst {
    @Test
    @DisplayName("Test getFirst con cola vacía")
    public void getFirstWithEmptyQueueReturnIndex0() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(10);
      assertThat(queue.size()).isEqualTo(0);
      assertThat(queue.getFirst()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test getFirst con cola llena")
    public void getFirstWithFullQueueReturnIndex0() {
      int size = 5;
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(size);
      for (int i = 0; i < size; i++) {
        queue.put(i);
      }

      assertThat(queue.size()).isEqualTo(size);
      assertThat(queue.getFirst()).isEqualTo(0);
    }
  }

  @Nested
  public class ArrayBoundedQueueGetLast {
    @Test
    @DisplayName("Test getLast con cola vacía")
    public void getLastWithEmptyQueueReturnIndex0() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(10);
      assertThat(queue.size()).isEqualTo(0);
      assertThat(queue.getLast()).isEqualTo(0);
    }

    @Test
    @DisplayName("Test getLast con cola parcialmente llena")
    public void getLastWithPartiallyFullQueueReturnIndex0() {
      int size = 5;
      int elements = 2;
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(size);
      for (int i = 0; i < elements; i++) {
        queue.put(i);
      }

      assertThat(queue.size()).isEqualTo(elements);
      assertThat(queue.getLast()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test getLast con cola llena")
    public void getLastWithFullQueueReturnIndex0() {
      int size = 5;
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(size);
      for (int i = 0; i < size; i++) {
        queue.put(i);
      }

      assertThat(queue.size()).isEqualTo(size);
      assertThat(queue.getLast()).isEqualTo(0);
    }
  }

  @Nested
  public class ArrayBoundedQueueIterator {
    @Test
    @DisplayName("Test iterator con cola vacía no tiene siguiente")
    public void hasNextWithEmptyQueueReturnsFalse() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(10);
      Iterator<Integer> iterator = queue.iterator();
      assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    @DisplayName("Test iterator con cola vacía no puede avanzar")
    public void nextWithEmptyQueueThrowsNoSuchElementException() {
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(10);
      Iterator<Integer> iterator = queue.iterator();

      assertThat(iterator.hasNext()).isFalse();
      assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> {
        iterator.next();
      });
    }

    @Test
    @DisplayName("Test iterator correcto")
    public void iteratorWithFullQueueExpectsCorrectValues() {
      int size = 5;
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(size);
      for (int i = 0; i < size; i++) {
        queue.put(i);
      }

      Iterator<Integer> iterator = queue.iterator();

      for (int i = 0; i < size; i++) {
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(i);
      }
      assertThat(iterator.hasNext()).isFalse();
    }
  }
}
