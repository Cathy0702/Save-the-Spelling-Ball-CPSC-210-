package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

// Test for Ball class

class BallTest {

    Ball ball;

    @BeforeEach
    public void runBefore() {
        ball = new Ball();
    }

    @Test
    public void testReadyToFallInitial() {
        assertFalse(ball.readyToFall());
    }

    @Test
    public void testReadyToFallBeforeTableEdge() {
        ball.moveHorizontally();

        assertFalse(ball.readyToFall());
    }

    @Test
    public void testReadyToFallOnTableEdge() {
        for(int i = 0; i < 5; i++) {
            ball.moveHorizontally();
        }
        assertTrue(ball.readyToFall());
    }

    @Test
    public void testMoveHorizontallyInitial() {
        ball.moveHorizontally();
        assertEquals(Background.getWidth() / 2 + ball.getDx(), ball.getXCood());
        assertFalse(ball.readyToFall());

    }

    @Test
    public void testMoveHorizontallyBeforeTableEdge() {
        ball.moveHorizontally();
        ball.moveHorizontally();
        ball.moveHorizontally();

        assertEquals(Background.getWidth() / 2 + 3 * ball.getDx(),
                ball.getXCood());
        assertFalse(ball.readyToFall());

    }

    @Test
    public void testMoveHorizontallyOnTableEdge() {
        for(int i = 0; i < 5; i++) {
            ball.moveHorizontally();
        }

        assertEquals(ball.getXIni() + 5 * ball.getDx(),
                ball.getXCood());
        assertTrue(ball.readyToFall());

    }

    @Test
    public void testMoveHorizontallyReadyToFall() {
        for(int i = 0; i < 5; i++) {
            ball.moveHorizontally();
        }
        ball.moveHorizontally();

        assertEquals(ball.getXIni() + 5 * ball.getDx(),
                ball.getXCood());
        assertTrue(ball.readyToFall());

    }

    @Test
    public void testMoveVerticallyInitial() {
        ball.moveVertically();

        assertEquals(ball.getYIni() + ball.getDy(),
                ball.getYCood());
    }

    @Test
    public void testMoveVerticallyDuringFalling() {
        ball.moveVertically();
        ball.moveVertically();
        ball.moveVertically();

        assertEquals(ball.getYIni() + 3 * ball.getDy(),
                ball.getYCood());
    }

    @Test
    public void testMoveVerticallyOutOfBound() {
        int i;
        for (i = 0; i <= 20; i++) {
            ball.moveVertically();
        }

        assertEquals(ball.getYIni() + i * ball.getDy(),
                ball.getYCood());
    }

    @Test
    public void testBallFall() {
        // meet REQUIRES CLAUS
        for(int i = 0; i < 5; i++) {
            ball.moveHorizontally();
        }
        assertTrue(ball.readyToFall());

        ball.ballFall();
        assertEquals(ball.getFallingEdge() + ball.getRadius(), ball.getXCood());
        assertTrue(ball.getYCood() <= 0 - ball.getRadius());

    }

    @Test
    public void testSetIniXOriginal() {
        ball.setIniX();

        assertEquals(ball.getXIni(), ball.getXCood());
    }

    @Test
    public void testSetIniXNotOriginal() {
        ball.moveHorizontally();
        ball.moveHorizontally();
        ball.setIniX();

        assertEquals(ball.getXIni(), ball.getXCood());
    }

    @Test
    public void testChanceLeftOriginal() {
        assertEquals(5, ball.chanceLeft());
    }

    @Test
    public void testChanceLeftInMiddle() {
        ball.moveHorizontally();
        ball.moveHorizontally();

        assertEquals(3, ball.chanceLeft());
    }

    @Test
    public void testChanceLeftAtEdge() {
        for (int i = 0; i < 5; i++) {
            ball.moveHorizontally();
        }

        assertEquals(0, ball.chanceLeft());
    }

    @Test
    public void testGetColor() {
        assertEquals(new Color(238, 0, 0), ball.getColor());
    }
}