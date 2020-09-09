
import java.util.Scanner;
import java.util.Arrays;

public class NumberProcessor {	
	
	/** 
	    *
	    *  This method returns true if its integer argument is "Excessive", otherwise it returns false
	    *  A number is defined to be "Excessive" if the sum of its positive divisors is greater than 2 times the number itself.   
	    *  For example, 12 and 48 are "Excessive" whereas 4 and 16 are not.
	    *  
    */
	   public static boolean isExcessive(int input) {

	   		if(input <= 0)
	   		{
	   			return false;
	   		}

	   		int factorSum = 0;

	   		for(int i=1; i<= input; i++) {

	   			if(input % i == 0) {

	   				factorSum += i;
	   			}
	   		}

	   		if(factorSum > (input * 2))
	   		{
	   			return true;
	   		}
	   		return false;
	    }
	 
	   	 
	  /**  
	    * 
	    * This method returns true if its argument is "Power", false otherwise. 
	    * A number is Power if it its value is the sum of  x^y + y^x, where x and y are integers greater than 1.
	    * 
	    * 
	    */
	         public static boolean isPower(long num) {

	         	int i = 2;
	         	int j = 2;
	         	int exponentOne = 0;
	         	int exponentTwo = 0;
	         	int powerResult = 0;

	         	while(exponentTwo < num)
	         	{
	         		exponentOne = (int) Math.pow(i, j);
	         		exponentTwo = (int) Math.pow(j, i);

	         		if((powerResult = exponentOne + exponentTwo) == num)
	         		{
	         			return true;
	         		}
	         		else if((powerResult > num) || (exponentOne  > num))
	         		{
	         			i = 2;
	         			++j;
	         		}
	         		else
	         		{
	         			++i;
	         		}
	         	}
	         	return false;   
	    }

	  /** 
		    * 
		    * This method accepts an  integer and returns true if the number is "Squad", false otherwise.
		    * An even digit integer is called "Squad" , if we can factor the number using two integers (a and b), whose product give the number n and with the following characteristics:

                    * Both a and b contains half the number of digits in the integer n. For example, if the number is 2568, a and b should be a two digit numbers.
                    * n contains the digits from both a and b. For example for n= 1530, a = 30 and b= 15. a * b = n and n contains all the digits in a and b (3, 0, 1 and 5).
                    * Both a and b cannot have trailing 0 at the same time, i.e., at most one of the numbers can have trailing 0. 
		    *
	  */  
	  
	   public static boolean isSquad(long num) {

	   		String factorOne = "";
	   		String factorTwo = "";

	   		String numStr = Long.toString(num);

	   		if(numStr.length() % 2 != 0)
	   		{
	   			return false;
	   		}

	   		int start = (int) Math.pow(10, (numStr.length() / 2) - 1);
	   		int end = (int) Math.pow(10, (numStr.length() / 2));

	   		int product = 0;

	   		outerLoop:
	   		for(int i = start; i < end; i++)
	   		{
	   			for(int j = i; j < end; j++)
	   			{
	   				product = i * j;

	   				if(product == num)
	   				{
	   					factorOne = Long.toString(i);
	   					factorTwo = Long.toString(j);

	   					if((factorOne.charAt(factorOne.length() - 1) == '0') && (factorTwo.charAt(factorTwo.length() - 1) == '0'))
	   					{
	   						continue outerLoop;
	   					}

	   					for(int k=0; k< factorOne.length(); k++)
	   					{
	   						if((!numStr.contains("" + factorOne.charAt(k))) || (!numStr.contains("" + factorTwo.charAt(k))))
	   						{
	   							continue outerLoop;
	   						}
	   					}
	   					return true;
	   				}

	   				else if(product > num)
	   				{
	   					continue outerLoop;
	   				}
	   			}
	   		}

	   		return false;
	    }
	         
	         
	 /** 
	  	    * 
	  	    * Considering the sequence 
	          *           1, 6, 15, 28, 45, 66, 91, 120, 153, 190, 231, ....

	          * The method returns the nth "MaSequence" number. If n is <= 0, it returns 0
	  	    *
	  */

	   /*
	   public static int recurMaSequence(int num) {

	   		if(num <= 0)
	   		{
	   			return 0;
	   		}

	   		if(num == 1)
	   		{
	   			return 1;
	   		}

	   		return ((5 * (num - 1)) - (num - 2)) + maSequence(--num);
	   }
	   */

	   public static int maSequence(int num){
	     // DELETE THE LINE BELOW ONCE YOU IMPLEMENT THE CALL!

	  		if(num <= 0)
	   		{
	   			return 0;
	   		}

	  		int nth = 1;

	  		for(int i = 1; i < num; i++)
	  		{
	  			nth += (5 * i) - (i - 1);
	  		}

	  		return nth;

	    }
	         
	  /** 
	         * 
	         * This method accepts a number and returns true if the number is "OneSummative", false otherwise.
	         * A positive integer is called "OneSummative" , if the repetitive sum of the square of its digits is one:
	         *
	         *         * Consider 7: 7^2 = 49; 4^2 + 9^2 = 97; 9^2 + 7^2 = 130; 1^2 + 3^2 + 0^2 = 10; 1^2 + 0^2 = 1.
	         *
	         *         *  Consider 392: 3^2 + 9^2 + 2^2 = 94; 9^2 + 4^2 = 97; 9^2+ 7^2 = 130; 1^2 + 3^2 + 0^2 = 10; 1^2 + 0^2 = 1
	   */

	   public static boolean isOneSummative(long num) {

	   		if(num <= 0)
	   		{
	   			return false;
	   		}

	   		int index = 0;
	   		int foundOccurrences = 0;

	   		String s = "";
	   		
	   		StringBuilder sb = new StringBuilder();
	   		long newNum = 0;
	   		String strNum = Long.toString(num);

	   		while(newNum != 1)
	   		{
	   			newNum = 0;

	   			for(int i=0; i< strNum.length(); i++)
	   			{
	   				newNum += (long) Math.pow(Long.parseLong(strNum.substring(i, i + 1)), 2);
	   			}

	   			if(newNum == 1)
	   			{
	   				return true;
	   			}

	   			sb.append(newNum + "-");
	   			s = sb.toString();

	   			while((index = s.indexOf(Long.toString(newNum) + "-")) != -1)
	   			{
	   				++foundOccurrences;
	   				s = s.substring(index + Long.toString(newNum).length());
	   			}

	   			if(foundOccurrences >= 2)
	   			{
	   				return false;
	   			}

	   			foundOccurrences = 0;
	   			strNum = Long.toString(newNum);
	   		}

	   		return true;
	   }
	 	        
	         
	     
      /** 
		    * 
		    * This method returns true if the array is EvenDual false otherwise. 
		    * An array is called EvenDual if it has the following properties:
	        *        - The value of the first element equals the sum of the next two elements, which is equals to the next three elements, equals to the sum of the next four elements, etc.
	        *        - It has a size of x*(x+1)/2 for some positive integer x .
	        *
	        *  For example {6, 2, 4, 2, 2, 2, 1, 5, 0, 0} isEvenDual, whereas {2, 1, 2, 3, 5, 6} is not
       */
	     
	   public static  boolean isEvenDual(int array[]) {
	     // DELETE THE LINE BELOW ONCE YOU IMPLEMENT THE CALL!

	   		int len = array.length;

	   		if(len < 3)
	   		{
	   			return false;
	   		}

	   		int offset = 1;
	   		int currentPos = 0;
	   		int initialSum = array[0];
	   		int upperIndex = 2;
	   		int currentSum = 0;

	   		while(currentPos != len)
	   		{
	   			if(currentPos > len)
	   			{
	   				return false;
	   			}

	   			currentPos += offset;
	   			++offset;
	   		}
	   		
	   		offset = 2;

	   		for(int i = 1; i < len; i++)
	   		{
	   			currentSum += array[i];

	   			if(i == upperIndex)
	   			{
	   				if(currentSum != initialSum)
	   				{
	   					return false;
	   				}
	   				currentSum = 0;
	   				++offset;
	   				upperIndex += offset;
	   			}
	   		}

	   		return true;
	    }

	 
	    
	 
	  /** 
	      * 
	      * This method  accepts a positive n and returns corresponding "IncrementalArray". 
	      * An array is called "IncrementalArray" if for the given positive integer n, it produces an array with incremental pattern.<p>
	      *           *if n = 1, it will produce {1}
	      *           * if n= 2. it produces {1, 1, 2}
	      *           * if n= 4. it produces {1, 1, 2, 1, 2, 3, 1, 2, 3, 4}
	      *           * if n= 6. it produces {1, 1, 2, 1, 2, 3, 1, 2, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6}
	      *          
	  */
	     
	  public static int[] incrementalArray(int n) {
	     // DELETE THE LINE BELOW ONCE YOU IMPLEMENT THE CALL!

	        if(n <= 0)
	        {
	        	return new int[] {};
	        }

	        if(n == 1)
	        {
	        	return new int[] {1};
	        }

	        if(n == 2)
	        {
	        	return new int[] {1, 1, 2};
	        }


	        int arrSize = 0;
	        int currentNumber = 1;
	        int maxNumber = 3;
	        int [] arr;

	        for(int i = 1; i <= n; i++)
	        {
	        	arrSize += i;
	        }

	        arr = new int[arrSize];
	        arr[0] = 1;
	        arr[1] = 1;
	        arr[2] = 2;

	        for(int j = 3; j < arrSize; j++)
	        {
	        	if(currentNumber > maxNumber)
	        	{
	        		currentNumber = 1;
	        		arr[j] = currentNumber;
	        		++currentNumber;
	        		++maxNumber;
	        		continue;
	        	}
	        	arr[j] = currentNumber;
	        	++currentNumber;
	        }

	        return arr;
	    }

	   
	 
	  
	  /** 
	    * 
	    * This method returns true if the array is Divisible, false otherwise.
	    * An array is called "Divisible" if it can be divided into two non-empty sub arrays, 
	    * where the sum of elements of the first sub array equals the sum of elements of the second sub array. 
	    *
	    * For example, {6, 2, 4, 2, 2, 2, 1, 5, 0, 0}	is Divisible as it can give the sub arrays {6,2,4} and {2,2,2,1,5,0,0}
	    *               {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, -2, -1}	is also Divisible as it gives sub arrays {0,0,0,0,0,0,0,0,0,0} and {1,1,1,-2,-1})
	    *
	  */
	   private static int calculateSum(String array []) {
	   		
	   		int sum = 0;

	   		for(int i = 0; i < array.length; i++)
	   		{
	   			sum += Integer.parseInt(array[i]);
	   		}

	   		return sum;
	   }
	   public static boolean isDivisible(int array []) {
	     // DELETE THE LINE BELOW ONCE YOU IMPLEMENT THE CALL!
	        
	        if(array.length == 0)
	        {
	        	return false;
	        }

	        StringBuilder firstSubArray = new StringBuilder();
	        StringBuilder secondSubArray = new StringBuilder();

	        String[] firstSubSet;
	        String[] secondSubSet;

	        for(int i = 0; i < array.length; i++)
	        {
	        	if(i != 0)
	        	{
	        		firstSubArray.append(",");
	        	}
	        	firstSubArray.append(array[i]);

	        	for(int j = i + 1; j < array.length; j++)
	        	{
	        		if(j != i + 1)
	        		{
	        			secondSubArray.append(",");
	        		}
	        		secondSubArray.append(array[j]);
	        	}

	        	if((secondSubArray.toString().length() == 0) || (firstSubArray.toString().length() == 0))
	        	{
	        		return false;
	        	}

	        	firstSubSet = firstSubArray.toString().split(",", array.length);
	        	//System.out.println(Arrays.toString(firstSubSet));
	        	secondSubSet = secondSubArray.toString().split(",", array.length);
	        	//System.out.println(Arrays.toString(secondSubSet));

	        	if(calculateSum(firstSubSet) == calculateSum(secondSubSet))
	        	{
	        		return true;
	        	}
	        	secondSubArray.setLength(0);
	        }

	        return false;

	    }
	
	   
	/**
	 * An array is called ConsecutiveDual if it contains consecutive elements (greater than 1 element) of same value.
	 *  For example, the array {1, 2, 3, 3, 4, 5} and { 4, 4 , 4 , 4, 4 } are "ConsecutiveDual" arrays 
	 *  where as {10, 9, 8, 7, 8, 9} and {0,1,0,1,0,1} are not.
	 */
		  

     public static boolean isConsecutiveDual(int[] array) {

     		if(array.length < 2)
     		{
     			return false;
     		}

     		int prevNumber = array[0];

     		for(int i = 1; i < array.length; i++)
     		{
     			if(prevNumber == array[i])
     			{
     				return true;
     			}
     			else
     			{
     				prevNumber = array[i];
     			}
     		}
     		return false;

          }


	
    /** 
           * 
           * This method returns true if the array is "PairArray", false otherwise.
           * An array is called "PairArray" if exactly one pair of its elements sum to 10. 
           * For example, {4,11,14, 6} is PairArray as only 4 and 6 sum to 10
           * The array {10,3,0,15,7} is not PairArray as more than one pair (10,0) and (3,7) sum to 10. 
           * {4,1,11} is not also PairArray as no pair sums to 10
            *
        *
      */
      public static boolean isPairArray(int array[]) {
        // DELETE THE LINE BELOW ONCE YOU IMPLEMENT THE CALL!
            
           	int firstNum = 0;
           	int secondNum = 0;
           	int numOfPairs = 0;

            for(int i = 0; i < array.length; i++)
            {
            	firstNum = array[i];
            	for(int j = 0; j < array.length; j++)
            	{
            		if(j == i)
            		{
            			continue;
            		}
            		secondNum = array[j];

            		if((firstNum + secondNum) == 10)
            		{
            			++numOfPairs;
            		}
            	}
            }
            
            return numOfPairs == 2;
          }
//##############################################Honors Section##############################
 /*	
  * 
  * 
  * /* 
   * 
   * This part only applies to Honors section students 
   *  if you are Honors section student, you have to uncomment this part 
   *  
   *  
   */
  
/**
 * 
 *  Given an array of integers, find the consecutive elements with the largest sum. 
 *  For example, if the array is {-2, 11, -4, , 13, -5, 2} the maximum sum is 20 which is the sum of the subarray that contains 11, -4, 13.
 *
 */
     public static int maxSum(int array[]) { 
		 // DELETE THE LINE BELOW ONCE YOU IMPLEMENT THE CALL!
        
     	return 0;
     }
	
	
	
	/**
	 * 
	 *  Given an array of integers, find the sub array with the largest sum. 
	 *  For example, if the array is {-2, 11, -4, , 13, -5, 2} the maximum sum is 20 which is the sum of the subarray {11, -4, 13}.
	 *
	 */
/*	
	public static int[] maxSubArray(int array[]) {
		// DELETE THE LINE BELOW ONCE YOU IMPLEMENT THE CALL!
        throw new RuntimeException("not implemented!");  
    }
*/

    public static void main(String argv[]) {

    	
    	/*
    	Scanner sc = new Scanner(System.in);
    	int input = 0;

    	while(true)
    	{
    		System.out.println("<<<Please enter in an integer.");
    		input = sc.nextInt();
    		System.out.println(isOneSummative(input));
    	}
    	*/

    	//isExcessive test cases

    	assert(isExcessive(-1) == false);
    	assert(isExcessive(0) == false);
    	assert(isExcessive(11) == false);
    	assert(isExcessive(24) == true);
    	assert(isExcessive(33) == false);
    	assert(isExcessive(44) == false);
    	assert(isExcessive(56) == true);
    	assert(isExcessive(66) == true);
    	assert(isExcessive(95) == false);
    	assert(isExcessive(101) == false);
    	assert(isExcessive(102) == true);
    	assert(isExcessive(945) == true);

    	//isPower test cases
    	assert(isPower(6) == false);
		assert(isPower(8) == true);
		assert(isPower(10) == false);
		assert(isPower(17) == true);
		assert(isPower(34) == false);
		assert(isPower(100) == true);
		assert(isPower(150) == false);
		assert(isPower(593) == true);
		assert(isPower(1125) == false);

		//isSquad test cases

		assert(isSquad(1530) ==	true);
		assert(isSquad(109) == false);
		assert(isSquad(1002) == false);
		assert(isSquad(1395) == true);
		assert(isSquad(2187) == true);
		assert(isSquad(126000) == false);
		assert(isSquad(150300) == true);

		//maSequence(10);

		
		assert(maSequence(-3) == 0);
		assert(maSequence(1) == 1);
		assert(maSequence(2) == 6);
		assert(maSequence(3) == 15);
		assert(maSequence(4) == 28);
		assert(maSequence(5) == 45);
		assert(maSequence(6) == 66);
		assert(maSequence(7) == 91);

		assert(isOneSummative(7) == true);
		assert(isOneSummative(5) == false);
		assert(isOneSummative(392) == true);
		
		
		assert(isEvenDual(new int[] {6, 2, 4, 2, 2, 2, 1, 5, 0, 0}) == true);
		assert(isEvenDual(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, -2, -1}) == true);
		assert(isEvenDual(new int[] {2, 1, 2, 3, 5, 6}) == false);
		assert(isEvenDual(new int[] {}) == false);
		assert(isEvenDual(new int[] {0,0}) == false);
		assert(isEvenDual(new int[] {1,0,1,0,1,0,1,0}) == false);

		System.out.println(Arrays.equals(incrementalArray(1), new int[] {1}));
		System.out.println(Arrays.equals(incrementalArray(2), new int[] {1, 1, 2}));
		System.out.println(Arrays.equals(incrementalArray(4), new int[] {1, 1, 2, 1, 2, 3, 1, 2, 3, 4}));
		System.out.println(Arrays.equals(incrementalArray(6), new int[] {1, 1, 2, 1, 2, 3, 1, 2, 3, 4, 1, 2, 3, 4, 5, 1, 2, 3, 4, 5, 6}));

		assert(isDivisible(new int[] {6, 2, 4, 2, 2, 2, 1, 5, 0, 0}) == true);
		assert(isDivisible(new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, -2, -1}) == true);
		assert(isDivisible(new int[] {2, 1, 2, 3, 5, 6}) == false);
		assert(isDivisible(new int[] {}) == false);
		assert(isDivisible(new int[] {0,0}) == true);
		assert(isDivisible(new int[] {1,0,1,0,1,0,0,0}) == false);

		
		assert(isConsecutiveDual(new int[] {1, 2, 3, 3, 4, 5}) == true);
		assert(isConsecutiveDual(new int[] { 4, 4 , 4 , 4, 4 }) == true);
		assert(isConsecutiveDual(new int[] {10, 9, 8, 7, 8, 9}) == false);
		assert(isConsecutiveDual(new int[] {0,1,0,1,0,1}) == false);

		assert(isPairArray(new int[] {4,10,14, 0}) == true);
		assert(isPairArray(new int[] {10,3,0,15,7}) == false);
		assert(isPairArray(new int[] {4,1,11}) == false);







		/* (30 * 15)
109	false(odd number of digits)
1002	false
1395	true (93 * 15)
2187	true (27 * 81)
126000	false(does not meet the 3rd condition)
150300	true (300 * 501)
*/

    }
}