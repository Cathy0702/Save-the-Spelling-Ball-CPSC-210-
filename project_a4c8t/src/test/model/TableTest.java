package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

// Test for Table class

public class TableTest {

    Table table;

    @BeforeEach
    public void runBefore() {
        table = new Table();
    }

    @Test
    public void testGetHeight() {
        assertEquals(20, table.getHeight());
    }

    @Test
    public void testGetWidth() {
        assertEquals(200, table.getWidth());
    }

    @Test
    public void testGetXPos() {
        assertEquals(Background.getWidth() / 2, table.getXPos());
    }

    @Test
    public void testGetYPos() {
        assertEquals(300, table.getYPos());
    }

    @Test
    public void testGetColor() {
        assertEquals(new Color(30, 144, 194), table.getColor());
    }
}
