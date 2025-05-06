package org.mps.boundedqueue;

import org.assertj.core.api.ThrowableAssert;
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
    void capacityEqualsZero_throwsException(){
      assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ArrayBoundedQueue<>(0));
    }
    @Test
    @DisplayName("si la capacidad es menor que 0, lanza excepcion")
    void capacityLessThanZero_throwsException(){
      assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ArrayBoundedQueue<>(-1));
    }
    @Test
    @DisplayName("si los valores son correctos, el tamañop inicial es 0, la cola esta vacía" +
        "y los indices iniciales estan bien")
    void constructorISOK(){
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
    void isFull_throwsException(){
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(1);
      queue.put(1);
      assertThat(queue.isFull()).isTrue();
      assertThatExceptionOfType(FullBoundedQueueException.class).isThrownBy(() -> queue.put(2));
    }
    @Test
    @DisplayName("si se le pasa a put un parametro nulo, lanza excepcion")
    void putGetsNullValue_throwsException(){
      assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ArrayBoundedQueue<>(2).put(null));
    }
    @Test
    @DisplayName("el put funciona correctamente")
    void putISOK(){
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
    void isEmpty_throwsException(){
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(2);
      assertThat(queue.isEmpty()).isTrue();
      assertThatExceptionOfType(EmptyBoundedQueueException.class).isThrownBy(() -> queue.get());
    }
    @Test
    @DisplayName("el get funciona correctamente")
    void getISOK(){
      ArrayBoundedQueue<Integer> queue = new ArrayBoundedQueue<>(2);
      queue.put(1);
      assertThat(queue.size()).isEqualTo(1);
      queue.get();
      assertThat(queue.size()).isEqualTo(0);
    }
  }
}
