package org.example;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class NumberUtilsTest {

    /**
     * Test for all null inputs
     *
     *      Specification: "Null returns Null"
     *
     * - null, [1]  --> null
     * - [1],  null --> null
     * - null, null --> null
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing for each null input")
    void testWhenInputIsNull(){
        //Arrange
        List<Integer> notNullList = new ArrayList<>(Arrays.asList(1));

        //Act & Assert
        assertThat(NumberUtils.add(null, notNullList)).isNull();
        assertThat(NumberUtils.add(notNullList, null)).isNull();
    }

    /**
     * Test for all empty list inputs
     *
     *      Specification: "empty list ([]) behaves as 0"
     *
     * - [ ],[1] --> [1]
     * - [1],[ ] --> [1]
     * - [ ],[ ] --> [0]
     * - [1],[1] --> [2] (neither empty)
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing for each empty input")
    void testWhenInputIsEmpty(){
        //Arrange
        List<Integer> notEmpty = new ArrayList<>(Arrays.asList(1));
        List<Integer> empty = new ArrayList<>(Arrays.asList());
        List<Integer> result = new ArrayList<>(Arrays.asList(2));

        //Act & Assert
        assertThat(NumberUtils.add(empty, notEmpty)).isEqualTo(notEmpty);
        assertThat(NumberUtils.add(notEmpty, empty)).isEqualTo(notEmpty);
        assertThat(NumberUtils.add(empty, empty)).isEqualTo(empty);
        assertThat(NumberUtils.add(notEmpty, notEmpty)).isEqualTo(result);
    }

    /**
     * Test for all inputs that are just on the boundary (valid/true/on-point)
     * & inputs that are just outside boundaries (invalid/false/off-point)
     *
     *      Specification:
     *      "Each element in the left and right lists should be a number from [0-9].
     *       An IllegalArgumentException is thrown in case this pre-condition does not hold."
     *
     * - [0], [0]  --> [0] (redundant; in empty list test)
     * - [9], [9]  --> [1,8]
     * - [-1],[0]  --> IllegalArgumentException
     * - [0], [-1] --> IllegalArgumentException
     * - [10],[9]  --> IllegalArgumentException
     * - [9], [10] --> IllegalArgumentException
     * - [10],[10] --> IllegalArgumentException (redundant)
     * - [-1],[-1] --> IllegalArgumentException (redundant)
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing for each input on the boundary")
    void testInputBoundaries(){
        //Arrange
        List<Integer> upperLimit = new ArrayList<>(Arrays.asList(9));
        List<Integer> lowerLimit = new ArrayList<>(Arrays.asList(0));
        List<Integer> aboveUpper = new ArrayList<>(Arrays.asList(10));
        List<Integer> belowLower = new ArrayList<>(Arrays.asList(-1));

        List<Integer> result = new ArrayList<>(Arrays.asList(1,8));

        //Act & Assert
        //assertThat(NumberUtils.add(0, 0)).isEqualTo(EMPTY LIST); redundant
        assertThat(NumberUtils.add(upperLimit, upperLimit)).isEqualTo(result);
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.add(belowLower, lowerLimit));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.add(lowerLimit, belowLower));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.add(aboveUpper, upperLimit));
        assertThrows(IllegalArgumentException.class, () -> NumberUtils.add(upperLimit, aboveUpper));
        //assertThrows(IllegalArgumentException.class, () -> NumberUtils.add(aboveUpper, aboveUpper)); redundant
        //assertThrows(IllegalArgumentException.class, () -> NumberUtils.add(belowLower, belowLower)); redundant
    }

    /**
     * Tests if the ones place carries over to tens place
     * - [1,8], [0,7] --> [2,5]
     *    18  +  07    =   25
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing for carry over")
    void testCarryOverOnes(){
        //Arrange
        List<Integer> left = new ArrayList<>(Arrays.asList(1,8));
        List<Integer> right = new ArrayList<>(Arrays.asList(0,7));

        List<Integer> expected = new ArrayList<>(Arrays.asList(2,5));

        //Act
        List<Integer> actual = NumberUtils.add(left,right);

        //Assert
        assertEquals(expected, actual);
    }

    /**
     * Tests if adding 1 carries over multiple nines
     * - [1,9,9], [0,0,1] --> [2,0,0]
     *     199   +   001   =    200
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing if carry over ripples across multiple nines")
    void testCarryAcrossNines(){
        //Arrange
        List<Integer> left = new ArrayList<>(Arrays.asList(1,9,9));
        List<Integer> right = new ArrayList<>(Arrays.asList(0,0,1));

        List<Integer> expected = new ArrayList<>(Arrays.asList(2,0,0));

        //Act
        List<Integer> actual = NumberUtils.add(left,right);

        //Assert
        assertEquals(expected, actual);
    }

    /**
     * Tests if a new most significant digit is added when carrying over
     * - [9,9,9], [0,0,1] --> [1,0,0,0]
     *     999   +  001   =    1000
     */
    @Test
    @Tag("specification")
    // This test is redundant? (Significant digit already tested in testInputBoundaries 2nd case)
    @DisplayName("Testing if carry over properly adds new most significant digit")
    void testCarryOverAddsMostSignificantDigit(){
        //Arrange
        List<Integer> left = new ArrayList<>(Arrays.asList(9,9,9));
        List<Integer> right = new ArrayList<>(Arrays.asList(0,0,1));

        List<Integer> expected = new ArrayList<>(Arrays.asList(1,0,0,0));

        //Act
        List<Integer> actual = NumberUtils.add(left,right);

        //Assert
        assertEquals(expected, actual);
    }

    /**
     * Tests if carry over works in multiple place digits
     * - [2,7], [9,5] --> [1,2,2]
     *    27  +  95   =    122
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing carry over in multiple digits")
    void testCarryOverInMultipleDigits(){
        //Arrange
        List<Integer> left = new ArrayList<>(Arrays.asList(2,7));
        List<Integer> right = new ArrayList<>(Arrays.asList(9,5));

        List<Integer> expected = new ArrayList<>(Arrays.asList(1,2,2));

        //Act
        List<Integer> actual = NumberUtils.add(left,right);

        //Assert
        assertEquals(expected, actual);
    }

    /**
     * Tests if it adds the same list object properly
     * Assume List<Integer> number = [1,4]
     * - number, number --> 28
     *    14   +  14     =  28
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing if same list object sums to 2x itself")
    void testWhenSameListObjectIsBothParams(){
        //Arrange
        List<Integer> number = new ArrayList<>(Arrays.asList(1,4));

        List<Integer> expected = new ArrayList<>(Arrays.asList(2,8));

        //Act
        List<Integer> actual = NumberUtils.add(number,number);

        //Assert
        assertEquals(expected, actual);
    }

    /**
     * Tests if immutable list objects add properly
     *
     *      Specification:
     *      "This method receives two numbers, `left` and `right`, both represented as a list of digits.
     *       It adds these numbers and returns the result also as a list of digits."
     *
     *       Spec never mentions 'mutating' the inputs and only mentions "list of digits";
     *       No specification for mutable or immutable lists
     *
     * Assume List<Integer> imList1 = [1,4]
     *        List<Integer> imList2 = [0,5] are both immutable
     *
     *    and List<Integer> mList = [0,5] is mutable
     *
     * - imList1, mList   --> [1,9]
     * - mList,   imList1 --> [1,9]
     * - imList1, imList2 --> [1,9] (redundant)
     * - neither immutable is redundant
     */
    @Test
    @Tag("specification")
    @DisplayName("Testing if immutable lists properly add")
    void testWhenInputIsImmutable(){
        //Arrange
        List<Integer> imList1 = List.of(1,4);
        //List<Integer> imList2 = List.of(0,5);
        List<Integer> mList = new ArrayList<>(Arrays.asList(0,5));

        List<Integer> expected = new ArrayList<>(Arrays.asList(1,9));

        //Act & Assert
        assertEquals(expected, NumberUtils.add(imList1, mList));
        assertEquals(expected, NumberUtils.add(mList, imList1));
        //assertEquals(expected, NumberUtils.add(imList1, imList2)); (redundant)

    }


    @Test
    @Tag("structural")
    @DisplayName("test for inputs with leading 0's")
    void testWhenInputHasLeadingZeros(){
        //Arrange
        List<Integer> left = new ArrayList<>(Arrays.asList(0,1));
        List<Integer> right = new ArrayList<>(Arrays.asList(0,1));

        List<Integer> expected = new ArrayList<>(Arrays.asList(2));

        //Act
        List<Integer> actual = NumberUtils.add(left,right);

        //Assert
        assertEquals(expected, actual);

    }
}
