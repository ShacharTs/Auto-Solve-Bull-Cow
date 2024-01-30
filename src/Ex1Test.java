import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

public class Ex1Test {
    @Test
    public void TestArrayMaker() {
        int numOfDigits = 2;
        boolean[] test = Ex1.ArrayMaker(numOfDigits);
        for (boolean b : test) {
            Assert.assertTrue(b);
        }
        System.out.println("Test for ArrayMaker for 2 Digits ");//I don't want to print 10000
        // so I will print 100 for testing
        //It Does work for any Number
        System.out.print(Arrays.toString(test));
        System.out.println();
        System.out.println("Test was correct");
    }

    /**
     * This test only work, if you got least one true somewhere
     */
    @Test
    public void TestisGuessIsAllow() {
        boolean[] test = {false, false, false, false, true, false, false};
        int counter = 0;
        int ansTest;
        ansTest = Ex1.isGuessIsAllow(test);
        for (int i = 0; i < test.length - 1; i++) {
            if (!test[i]) {
                counter++;
            } else {
                break;
            }
        }


        //ansTest=Ex1.isGuessIsAllow(test);
        //If you want to check if there are a digit that allow
        Assertions.assertEquals(counter, ansTest);

        //If you want to check if there are no digits that allow
        //Maybe the filter missed
        //Assertions.assertNotEquals(counter,ansTest);
    }

    @Test
    void TestnoBullsAndCows() {
        int numOfDigits = 4;
        boolean[] test = Ex1.ArrayMaker(numOfDigits);
        int[] guess = {9, 9, 9, 9};
        for (int i = 0; i < test.length; i++) {
            int[] nextDigitArray = Ex1.toArray(i, numOfDigits);
            int[] tempRes = Ex1.B_C_Compare(nextDigitArray, guess);
            int totalTempRes = tempRes[0] + tempRes[1];
            if (totalTempRes > 0) {
                Assertions.assertTrue(test[i]);
                test[i] = false;
            }
        }
    }

    @Test
    void TestguessFiltere() {
        int num = 2;
        boolean[] test;
        test = Ex1.ArrayMaker(num);
        int[] guess = {8, 0};
        int[] resG = {0, 1};
        Ex1.guessFilter(test, guess, resG, num);
        Assertions.assertTrue(test[18]);
    }

    @Test
    void TestB_C_Compare() {
        int[] guess = {1, 2};
        int[] test = {2, 4};
        int[] res = {0, 1};
        int[] altTestRes = Ex1.B_C_Compare(guess, test);
        Assertions.assertArrayEquals(altTestRes, res);
    }

    private static final int NUM_RUNS = 100;

    @Test
    public void TestautoEx1Game() {
        for (int i = 0; i < 100; i++) {
            System.out.println("Testing Automatic Game - Run " + (i + 1) + ":");
            BP_Server newGameOfBullAndCows = new BP_Server();
            newGameOfBullAndCows.startGame(318790169, 2);
            Ex1.countGuess += 0;
            Ex1.autoEx1Game(newGameOfBullAndCows);
        }
        System.out.println("Total average of guesses with " + Ex1.howManyDigits + " Digits");
        System.out.println("The average of the guesses is " + Ex1.countGuess / NUM_RUNS);
    }
}

