import java.util.Arrays;

/**
 * Introduction to Computer Science, Ariel University, Ex1 (manual Example + a Template for your solution)
 * See: https://docs.google.com/document/d/1C1BZmi_Qv6oRrL4T5oN9N2bBMFOHPzSI/edit?usp=sharing&ouid=113711744349547563645&rtpof=true&sd=true

 * Ex1 Bulls & Cows - Automatic solution.
 * **** Add a general readme text here ****
 * Add your explanation here:

 * It started with digit that is 0 and check if digit 0 gives any bulls or cows
 * If it does not give any bull or cows, its turn all the index in our guess array that have the digit 0
 * It will go up digit 1, Checks for any bulls or cows if match keep index that might be the code
 * Exp: Code=[0,2,5,7], Guess=[0,0,0,0], res=[1,0] (We got 1 bull the digit 0)
 * So any number that not have at least a single 0 digit, We already know it can not be the code
 * So the number=[1,9,6,2] can not be our code


 * **** General Solution (algorithm) ****
 * Add your explanation here:

 * The algorithm is how we eliminate as many options as possible with one guess at a time
 * Everytime we guess a number,It's bulls & cows giving us a lot of information, that can be used for our next guess
 * If everytime we guess only 1 digit as number, we can eliminate a lot of numbers
 * Exp: If the code is [0,4,9,6], our first guess is [0,0,0,0]
 * The answer is one bull and zero cows, Now need to guess number that have least one digit of zero
 * Because we have 10 digits total: from 0 to 9, the max guesses is 10
 * (This will happen when the number is only have the digit 9 in this algorithm because we start at zero)

 * **** Results ****
 * Make sure to state the average required guesses
 * for 2,3,4,5,6-digit code:

 * FOR TEST RESULTS
 * https://github.com/ShacharTs/Auto-Solve-Bull-Cow.git
 * FOR TEST RESULTS

 * Average of 100 runs

 * Average required guesses 2 Digits: ---->  7
 * Average required guesses 3 Digits: ---->  7
 * Average required guesses 4 Digits: ---->  8
 * Average required guesses 5 Digits: ---->  9
 * Average required guesses 6 Digits: ---->  9

 */
public class Ex1 {
    public static final String Title = "Ex1 demo: manual Bulls & Cows game";

    public static void main(String[] args) {
        BP_Server game = new BP_Server();   // Starting the "game-server"
        long myID = 318790169;             // Your ID should be written here
        int numOfDigits = 5;                // Number of digits [2,6]
        game.startGame(myID, numOfDigits);  // Starting a game
        System.out.println(Title + " with code of " + numOfDigits + " digits");
        autoEx1Game(game);
    }

    /**
     * Simple parsing function that gets an int and returns an array of digits
     * @param number    - a natural number (as a guess)
     * @param howManyDigits - number of digits (to handle the 00 case).
     * @return an array of digits
     */
     static int[] toArray(int number, int howManyDigits) {
        int[] tempArray = new int[howManyDigits];//Creating array with how many digits we play
        for (int i = tempArray.length - 1; i >= 0; i--) {
            tempArray[i] = number % 10; //Taking that number and mod%10 it, so we can print array with that digit
            number = number / 10; //Divide by 10 so we can print the next number
        }
        return tempArray; //Our number but in array
    }

    /**
     * This will compare bulls and cows
     * This is the key to cracking the algorithm
     * It always takes 2 arrays and try to find a match
     * @param arraySlot Booleans index slot
     * @param guessArray our guess number
     * @return how many bull and cow shared between 2 arrays
     */
    static int[] B_C_Compare(int[] arraySlot, int[] guessArray) {
        int bulls = 0; //Bull counter
        int cows = 0; // Cow counter

        boolean[] arraySlotCheck = new boolean[arraySlot.length]; //Temp array for slot bull check
        boolean[] arrayGuessCheck = new boolean[guessArray.length]; //Temp array for guess bull check
        /*
        Checks if some digits are matching in the same slots, For Bulls
        It will check if both index have the same number, same index and same number is equal to bull
        */
        for (int i = 0; i < arraySlot.length; i++) {
            if (arraySlot[i] == guessArray[i]) {
                bulls++;
                arraySlotCheck[i] = true; //If match make that index true
                arrayGuessCheck[i] = true; //If match make that index true
            }
        }
        // Checks if some digits are matching in different slots, For cows
        for (int i = 0; i < arraySlot.length; i++) {
            for (int j = 0; j < guessArray.length; j++) {
                if (!arraySlotCheck[i] && !arrayGuessCheck[j] && arraySlot[i] == guessArray[j]) {
                    cows++;
                    arraySlotCheck[i] = true; //If match make that index true
                    arrayGuessCheck[j] = true; //If match make that index true
                    break; //Stop if found cow
                }
            }
        }
        return new int[]{bulls, cows}; //how many bulls and cows
    }

    /**
     * This will turn false all the booleans index that gave us 0 bulls and 0 cows
     * @param guessArray Our guess
     * @param booleanArrays Our boolean array
     * @param numOfDigits How many digits we play
     * @return Our boolean array with fewer options to guess
     */
    static boolean[] noBullsAndCows(int[] guessArray, boolean[] booleanArrays, int numOfDigits){
        for (int i=0;i<booleanArrays.length; i++){
            int []nextDigitArray= toArray(i, numOfDigits);
            int[] tempRes= B_C_Compare(nextDigitArray,guessArray);
            int totalTempRes=tempRes[0]+tempRes[1];
            if(totalTempRes>0){
                booleanArrays[i]=false;
            }
        }
        return booleanArrays;
    }

    /**
     * This will create array of booleans and at the start change all of them to true
     * This will only RUN ONCE at the start of the game
     * @param numOfDigits Is equal how many digits we choose
     * @return Array of booleans all equal to true
     */
    static boolean[] ArrayMaker(int numOfDigits){
        boolean[]arr;
        double arraySize=Math.pow(10,numOfDigits);
        arr=new boolean[(int)arraySize];

        Arrays.fill(arr, true);
        return arr;
    }

    /**
     * This will check if the index of boolean slot is true or false
     * If we filter guesses we do not want to even try guess numbers that are not correct
     * So this will check if the index is true, if true we will pick that number and guess
     * If index is equal to false, we know It cannot be a possible guess
     * So we skip that number and check the number after it
     * @param arr Array of booleans
     * @return that digit we want to start guessing
     */
    static int isGuessIsAllow(boolean[] arr){
        int Digit=0; //We start with the Digit 0
        for(int i=0;i<arr.length;i++){
            if (arr[i]){ //If at slot I is true
                Digit=i;//Digit = to I slot index
                break;
            }
        }
        return Digit; //Our guess number
    }

    /**
     * This will filter other numbers that can not be our next guess
     * Its take array of booleans, our guess, our res of bull & cow and the number of digits we play
     * If the index in our boolean array is true, we will take our guess and remove all other incorrect guesses
     * Exp: If our guess [0,0,0,0] gave us res of [1,0]
     * Now the filter will compare all the other guesses and make them false if theirs Bull & Cow res is not match
     * Exp: So [0,0,0,0] will compare [0,0,0,1] gave us res of [3,0]
     * [1,0] is not equal to [3,0], So [0,0,0,1] can not be our next guess so [0,0,0,1] will change to false
     * @param BooleanArrays Array of booleans
     * @param myGuess Our Guess
     * @param myGuessRep Our rep of our guess
     * @param numberOfDigits How many digits we play
     */
    static void guessFilter(boolean[]BooleanArrays, int[] myGuess, int[] myGuessRep, int numberOfDigits){
        for (int i=0; i<BooleanArrays.length;i++){
            if(BooleanArrays[i]){ //If index number true
                int[] arrayForEveryGuess= toArray(i,numberOfDigits);
                int[] tempRes= B_C_Compare(arrayForEveryGuess,myGuess);
                if(tempRes[0]!=myGuessRep[0] || tempRes[1]!=myGuessRep[1]){
                    BooleanArrays[i]=false;
                }
            }
        }
    }
    /**
     * This will count for test avg
     */
    public static int countGuess =0;
    public static int howManyDigits =0;


    /**
     * This is the auto solver of the game
     * @param game Bull and Cows game code generator
     */
    public static void autoEx1Game(BP_Server game) {
        howManyDigits= game.getNumOfDigits();//This is for testing
        boolean[] GameBooleanArrays;//Our boolean array
        int guess;// Our guess number (Always start from 0 digits)
        int[]  guessArray;//Start array for our guess numbers
        int[] res;// Start array for bull & cow array
        GameBooleanArrays=ArrayMaker(game.getNumOfDigits());//Make array of booleans equal to how many digits we play

        while (game.isRunning()) {
            guess= isGuessIsAllow(GameBooleanArrays);//Our guess
            guessArray=toArray(guess, game.getNumOfDigits());//Our guess number ---> to array
            res= game.play(guessArray);//How many bulls & cows in our guess
            countGuess++; //This is only for testing how many guess it took, for cal avg for tester
            if(res[0]==0 && res[1]==0){//If no bulls & no cows it will turn all the index that have that numbers to FALSE
                GameBooleanArrays= noBullsAndCows(guessArray,GameBooleanArrays, game.getNumOfDigits());
            }
            else {
                //If we got some bulls or cows we will filter any other number(in our boolean array)
                //that not have the same amount of bulls and cows
                guessFilter(GameBooleanArrays, guessArray, res, game.getNumOfDigits());
            }
        }
        System.out.println(game.getStatus());// Print our guesses,the code,how long took us
    }
}
