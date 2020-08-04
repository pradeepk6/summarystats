package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SmallCodeSnippetsTest {

    @Test
    public void shouldThrowIndexOutOfBoundsException() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            SmallCodeSnippets.generateArrayIndexOutOfBoundException();
        });
    }
    @Test
    public void shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            SmallCodeSnippets.generateNullPointerException();
        });
    }
    @Test
    public void shouldReverseString() {
        assertEquals("ROCIN",SmallCodeSnippets.reverseString("NICOR"));
    }
    @Test
    public void shouldRemoveWhiteSpaces() {
        assertEquals("NationalInstituteForCardiovascularOutcomesResearch",SmallCodeSnippets.removeWhiteSpaces("National Institute For Cardiovascular Outcomes Research"));
    }
}

