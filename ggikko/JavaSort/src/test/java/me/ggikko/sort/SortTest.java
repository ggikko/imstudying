package me.ggikko.sort;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


public class SortTest {

    @Test
    public void testInsertionSort() {
        //given
        Insertion insertion = new Insertion();
        //when
        int[] input = {1, 6, 2, 3, 4, 9, 5};
        //then
        int[] expected = {1, 2, 3, 4, 5, 6, 9};
        assertArrayEquals(insertion.sort(input), expected);
    }
}
