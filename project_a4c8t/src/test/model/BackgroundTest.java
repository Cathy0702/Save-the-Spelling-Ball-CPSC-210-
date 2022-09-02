package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Test for Background class

public class BackgroundTest {

    Background background;

    @BeforeEach
    public void runBefore() {
        background = new Background();
    }

    @Test
    public void testGetHeight() {
        assertEquals(600, background.getHeight());
    }

    @Test
    public void testGetWidth() {
        assertEquals(600, background.getWidth());
    }
}


