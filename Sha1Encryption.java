package com.sjsu.java.hash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sha1Encryption {

	/**
	 * Initialize the global data : that is h0, h1, h2, h3, h4
	 * There are five variables that now need to be initialized.
		h0 = 01100111010001010010001100000001
		h1 = 11101111110011011010101110001001
		h2 = 10011000101110101101110011111110
		h3 = 00010000001100100101010001110110
		h4 = 11000011110100101110000111110000
	 */
	
	public static String h0 = "01100111010001010010001100000001";
	public static String h1 = "11101111110011011010101110001001";
	public static String h2 = "10011000101110101101110011111110";
	public static String h3 = "00010000001100100101010001110110";
	public static String h4 = "11000011110100101110000111110000";

	/* Step 10 : Initialize variables*/
	public static String a = h0;
	public static String b = h1;
	public static String c = h2;
	public static String d = h3;
	public static String e = h4;
							
	public static String endH0 = null;
	public static String endH1 = null;
	public static String endH2 = null;
	public static String endH3 = null;
	public static String endH4 = null;
	
	public static int MAXLEN = 32;
	
	public static void main(String[] args) 
	{	
		/**
		 * Step 1 : Ask User to Enter the String
		 */
		String lUserInput = requestUserToInputString();
		
		/**
		 * Step2 : Break String into characters
		 */
		char[] lUserInputCharArr = convertStringToCharacters(lUserInput);
		
		/**
		 * Step 3 : Convert characters to ASCII codes
		 */
		int[] lUserInputAscii = convertToASCII(lUserInputCharArr);
		
		/**
		 * Step 4: Convert numbers into binary
		 */
		
		String[] l8bitBinArray = convertDecimalto8bitBinary(lUserInputAscii);
		
		/**
		 * Step 5: Put the Binary numbers together and Add '1' to the end
		 */
		String ljoinedBinaryString = joinBinaryStr(l8bitBinArray);
		
		/**
		 * Step 6: Append '0's' to the end and Append original message length
		 */
		StringBuffer lAppended512MultipleMsg = appendBinaryValues(ljoinedBinaryString);
		
		/**
		 * Step 7: Chunk the message
		 * Step 8: Break chunks into 16 words each 32 bits 
		 * Step 9: XOR and left rotate by 1 bit
		 */
		String chunktostr[] = chunkMsg(lAppended512MultipleMsg);	
		
		/**
		 * Step 11: 
		 */
		functionLoop(chunktostr);
		
		binToHex();
	}
	
	
	/**
	 * This API will read the User Input string from the Console 
	 * In this example I am going to use the string: 'A Test'.
	 * @return : returns the String input by the user
	 */
	private static String requestUserToInputString()
	{	
		System.out.println("Step 1: Ask the user to enter string");
		System.out.println("Enter the String to be hashed here : ");
		String lsUserInput = null;
		 
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    lsUserInput = bufferRead.readLine();
		    System.out.println(lsUserInput);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}

		return lsUserInput;
	}
	
	/**
	 * This API will convert string to a character array
	 * You must now break the string into characters.
		A
		T
		e
		s
		t
	 */
	private static char[] convertStringToCharacters(String aiUserInput)
	{
		System.out.println("Step 2: Break the string into characters");
		char arr[] = aiUserInput.toCharArray();
		for(int i = 0; i<arr.length; i++){
			System.out.println("Data at ["+i+"]="+arr[i]);
		}
		return arr;
	}

	/**
	 * Convert Characters to ASCII values
	 * Each character should now be converted from text into ASCII.
	 *  65
		32
		84
		101
		115
		116
	 */
	private static int[] convertToASCII(char[] aiUserInputArray)
	{
		System.out.println("Step 3 : Convert characters to ASCII codes");
		
		int liAsciiArr[] = new int[aiUserInputArray.length];
		
		for (int i = 0; i < aiUserInputArray.length; i++)
		{  
			liAsciiArr[i] = (int) aiUserInputArray[i];
		}
		
		for(int j = 0; j<aiUserInputArray.length; j++){
			System.out.println("Data at liAsciiArr["+j+"]="+ liAsciiArr[j]);
		}
		return liAsciiArr;	
	}

	/**
	 * Convert numbers into binary
	Binary is simply base two. All base ten numbers are now converted into 8-bit binary numbers. 
	The eight-bit part means that if they don't actually take up a full eight place values, simply 
	append zeros to the beginning so that they do.
	01000001
	00100000
	01010100
	01100101
	01110011
	01110100
	 * @param aiDecimalUserInput
	 */
	private static String[] convertDecimalto8bitBinary(int liAsciiArr[])
	{
		System.out.println("Step 4: Convert numbers into binary");

		String[] arr = new String[liAsciiArr.length];

		for(int j = 0; j< liAsciiArr.length; j++){
			System.out.println("Data at liAsciiArr["+j+"]="+ liAsciiArr[j]);
		}
		for(int i = 0; i < arr.length; i++)
		{
			String lsBin = Integer.toBinaryString(liAsciiArr[i]);
			System.out.println("Binary Conversion of : " + liAsciiArr[i] + " : "+ lsBin);
			
			int iLen = lsBin.length();

			if(iLen < 8)
			{
				System.out.println("Binary converted value lenght is < 8. Prepend 0's. Current length is : " + iLen);
				/* Prepend 0's to this */
				for (int j = iLen; j <8; j++)
				{
					lsBin = "0" + lsBin;
				}
			}			
			System.out.println(lsBin);
			arr[i] = lsBin;
		}
		
		for(int j = 0; j < arr.length; j++){
			System.out.println("Data at arr["+j+"]="+ arr[j]);
		}		
		return arr;
	}
	
	private static String joinBinaryStr(String[] a8bitBinArray)
	{
		System.out.println("Step 5: Put the Binary numbers together");

		StringBuilder builder = new StringBuilder();
		for(String s : a8bitBinArray) {
		    builder.append(s);
		}

		
		System.out.println("Appended Binary String is : " + builder.toString());
		return builder.toString();
	}
	
	/**
	 *Step 5.2 :  Add '1' to the end
	   Step 6 :	Put the numbers together:
		010000010010000001010100011001010111001101110100

		Add the number '1' to the end:
		0100000100100000010101000110010101110011011101001
	 */
	private static StringBuffer appendBinaryValues(String aJoinedBinaryString)
	{
		System.out.println("Step 6: Append 1, Append '0's' to the end and Append original message length");
		
		StringBuffer sBuffer = new StringBuffer(aJoinedBinaryString);
		System.out.println(sBuffer);
		   
		/* This API will append all the binary digits together*/
		StringBuffer sBuffer1 = new StringBuffer(Integer.toBinaryString(sBuffer.length()));
		   int i = 64-sBuffer1.length();
		   int t;		        
		   for(t=0;t<i;t++)
		   {
			   sBuffer1.insert(t,"0");
		   }
		   
		   sBuffer.append("1");
		   System.out.println(sBuffer);
		   
		   int l1= sBuffer.length();		       
		    while(l1 % 512 != 448)
		    {    	
		    		sBuffer.append("0");	
		    		l1++;
		    }
	       sBuffer.append(sBuffer1);
	       System.out.println(sBuffer); 
	       return sBuffer;
	}
	
	/**
	 * 
	 */
	public static String[] chunkMsg(StringBuffer aAppendedBuffer)
	{
		System.out.println("Step 7: Chunk the message, Step 8: Break chunks into 16 words each 32 bits , Step 9: XOR and left rotate by 1 bit");

		int len=aAppendedBuffer.length();
	    System.out.println(len);
		System.out.println(aAppendedBuffer);
		 
		 String[] chunks = new String[aAppendedBuffer.length()/ 512];
		 String[] chunktowords = new String[64 * chunks.length];
		 Long [] chunktolong = new Long[80 * chunks.length];					 
		 Integer [] chunktoint = new Integer[80 * chunks.length];
		 String chunktostr[] = new String[80 * chunks.length]; 
		 
		 if(len >= 512)
		 {
	        for (int i = 0 ; i < chunks.length ; i++)
	        {
	            chunks[i] = aAppendedBuffer.substring((512 * i), (512 * (i + 1)));
	            System.out.println(chunks[i]);
	            int chunklen=chunks[i].length();
	            System.out.println(chunklen);
	            
	            for (int j = 0 ; j < 16 ; j++) 
				{
	            	chunktowords[j] = chunks[i].substring((32 * j), (32 * (j + 1)));
	            	System.out.println(chunktowords[j]);
	            	chunktolong[j]=Long.parseLong(chunktowords[j],2);
		            chunktoint[j]=chunktolong[j].intValue(); 
               
	            }

				System.out.println("\n");

				/* Step 9.1
				 *  : XOR : Extend to 80 words*/
				
				//XOR to give 80 words
				for(int w = 16;w <= 79; w++)
				{
					chunktoint[w] =chunktoint[w-3].intValue()^chunktoint[w-8].intValue()^chunktoint[w-14].intValue()^chunktoint[w-16].intValue();
					
					/* Step 9.2 : Left rotate*/
					chunktoint[w]=Integer.rotateLeft(chunktoint[w],1);	            		  
				}
				
				System.out.println("Displaying the 80 computed words\n");

				// add 0's to make the value 32 bit and display the 80 words
				for(int e=0;e<=79;e++)
				{			           
					StringBuilder zeros=new StringBuilder();
					chunktostr[e]=Integer.toBinaryString(chunktoint[e]);
					int diff=32-chunktostr[e].length();
					for(int add=1;add<=diff;add++)
					{
						zeros.append("0");
					}
					chunktostr[e] = zeros + chunktostr[e] ;
					System.out.println(e +"\t" + (chunktostr[e]));
				}
				System.out.println("\n");
	        }
		}
		else if (512 < len) 
		{
			System.out.println("bit length should be 512\n please enter 512 bit length message");
		}
		 return chunktostr;
	}

	private static boolean bitOf(char in) {
	    return (in == '1');
	}

	private static char charOf(boolean in) {
	    return (in) ? '1' : '0';
	}

	private static char nonCharOf(boolean in) {
	    return (in) ? '0' : '1';
	}

	/**
	 * 'f' equal to: (B AND C) or (!B AND D)
	 */
	private static String computeFunction1()
	{
		/**
		 *  'f' equal to: (B AND C) or (!B AND D)
		 */
		StringBuilder sb1 = new StringBuilder();
		
		/**
		 * Step 1: B AND C
		 */
		for (int j = 0; j < b.length(); j++) 
		{
			sb1.append(charOf(bitOf(b.charAt(j)) & bitOf(c.charAt(j))));
		}
		
		String result1 = sb1.toString();
		//System.out.println(result1);
		
		/**
		 * Step 2: !B
		 */
		StringBuilder sb2 = new StringBuilder();
		for (int j = 0; j < b.length(); j++) 
		{
			sb2.append(nonCharOf(bitOf(b.charAt(j))));
		}
		
		String result2 = sb2.toString();
		//System.out.println(result2);
		
		/**
		 * Step 3 : (!B AND D)
		 */
		
		StringBuilder sb3 = new StringBuilder();
		
		for (int j = 0; j < b.length(); j++) 
		{
			sb3.append(charOf(bitOf(result2.charAt(j)) & bitOf(d.charAt(j))));
		}
		
		String result3 = sb3.toString();
		//System.out.println(result3);
		
		/**
		 * Step 4 : (B AND C) or (!B AND D)
		 */
		
		StringBuilder sb4 = new StringBuilder();
		
		for (int j = 0; j < b.length(); j++) 
		{
			sb4.append(charOf(bitOf(result1.charAt(j)) | bitOf(result3.charAt(j))));
		}
		
		String result4 = sb4.toString();
		//System.out.println(result4);
		
		return result4;
	}
	
	/**
	 * f = b xor c xor d
	 */
	private static String computeFunction2()
	{
		StringBuilder sb = new StringBuilder();
		
		for (int j = 0; j < b.length(); j++) 
		{
			sb.append(charOf(bitOf(b.charAt(j)) ^ bitOf(c.charAt(j))
	                ^ bitOf(d.charAt(j))));
		}
		
		String result = sb.toString();
		//System.out.println(result);
		return result;
	}
	
	/**
	 *  'f' equal to: (B AND C) OR (B AND D) OR (C AND D)
	 */
	private static String computeFunction3()
	{
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb4 = new StringBuilder();
		
		for (int j = 0; j < b.length(); j++) 
		{
			/* B AND C*/
			sb1.append(charOf(bitOf(b.charAt(j)) & bitOf(c.charAt(j))));
			/* B AND D*/
			sb2.append(charOf(bitOf(b.charAt(j)) & bitOf(d.charAt(j))));
			/* C AND D*/
			sb3.append(charOf(bitOf(c.charAt(j)) & bitOf(d.charAt(j))));
		}
		
		String result1 = sb1.toString();
		//System.out.println(result1);
		
		String result2 = sb2.toString();
		//System.out.println(result2);
		
		
		String result3 = sb3.toString();
		//System.out.println(result3);
		
		/*(B AND C) OR (B AND D) OR (C AND D)*/
		for (int j = 0; j < b.length(); j++) 
		{
			sb4.append(charOf(bitOf(result1.charAt(j)) | bitOf(result2.charAt(j))
	                | bitOf(result3.charAt(j))));
		}
		
		String result4 = sb4.toString();
		//System.out.println(result4);

		return result4;
	}
	
	/**
	 * 
	 */
	public static void binToHex()
	{
		String h0Hex = Long.toHexString(Long.parseLong(endH0,2));
		String h1Hex = Long.toHexString(Long.parseLong(endH1,2));
		String h2Hex = Long.toHexString(Long.parseLong(endH2,2));
		String h3Hex = Long.toHexString(Long.parseLong(endH3,2));
		String h4Hex = Long.toHexString(Long.parseLong(endH4,2));
		
		System.out.println(h0Hex);
		System.out.println(h1Hex);
		System.out.println(h2Hex);
		System.out.println(h3Hex);
		System.out.println(h4Hex);
		
		String lApendedString = h0Hex+h1Hex+h2Hex+h3Hex+h4Hex;
		
		System.out.println(lApendedString);

	}
	
	
	/**
	 * Step 11: 
	 */
	public static void functionLoop(String chunkof80Words[])
	{ 
		System.out.println("Step 11 : Main function loop");
		
		String functionValue = null;
		String keyValue = null;

		a = h0;
		b = h1;
		c = h2;
		d = h3;
		e = h4;
		
		//System.out.println("Step 11 : Value of A: " + a);
		
		
		for (int i = 0; i < 80; i++)
		{
			/**
			 * Function 1 : 
			 * f = (b and c) or ((not b) and d)
             * k = 0x5A827999
			 */
			if ((i >= 0) && (i <= 19))
			{
				functionValue = computeFunction1();	
				System.out.println("Function 1 Value : " + functionValue);
				//keyValue = "0x5A827999";
				keyValue = "01011010100000100111100110011001";
			}
			
			/**
			 * Function 2
			 * f = b xor c xor d
             * k = 0x6ED9EBA1
			 */
			else if	((i >= 20) && (i <= 39))
			{
				functionValue = computeFunction2();		
				System.out.println("Function 2 Value : " + functionValue);
				//keyValue = "0x6ED9EBA1";
				keyValue = "01101110110110011110101110100001";
			}
			/**
			 * Function 3
			 * f = (b and c) or (b and d) or (c and d) 
             * k = 0x8F1BBCDC
			 */
			else if ((i >= 40) && (i <= 59))
			{
				functionValue = computeFunction3();
				System.out.println("Function 3 Value : " + functionValue);
				//keyValue = "0x8F1BBCDC";
				keyValue = "10001111000110111011110011011100";
			}
			/**
			 * Function 4
			 * f = b xor c xor d
             * k = 0xCA62C1D6
			 */
			else if ((i >= 60) && (i <= 79))
			{
				functionValue = computeFunction2();
				System.out.println("Function 4 Value : " + functionValue);
				//keyValue = "0xCA62C1D6";
				keyValue = "11001010011000101100000111010110";
			}
	
			//System.out.println("Value of A before left roatation : " + a);
			
			String aLeftRotated = leftRotateValue(a, 5);
				
			//System.out.println("Step 11.2 : Left rotated value of A : " + aLeftRotated);
			//System.out.println("Step 11.2 : Function Value : " + functionValue);
			//System.out.println("Step 11.2 : Value of E : " + e);
			//System.out.println("Step 11.2 : Key Value : " + keyValue);
			//System.out.println("Step 11.2 : Word Value : " + chunkof80Words[i]);
			
			/**
			 *  temp = (a leftrotate 5) + f + e + k + w[i]
        		e = d
        		d = c
        		c = b leftrotate 30
        		b = a
        		a = temp
			 */
			
			String temp = binaryAdder(aLeftRotated, functionValue, e, keyValue, chunkof80Words[i]);
			System.out.println("Temp value in round " + i + "is : " + temp);
			
			int lengthOfString = temp.length();
			//System.out.println("Length of Added String : " + lengthOfString);
			
			if (lengthOfString > MAXLEN)
			{
				int truncationLength = lengthOfString - MAXLEN;
				//System.out.println("Length of Added String : " + truncationLength);	
				
				String cutString = temp.substring(truncationLength, lengthOfString);
				//System.out.println("Length of Added String : " + cutString);
				temp = cutString;
			}
			
			e = d;
			d = c;
			c = leftRotateValue(b, 30);
			b = a;
			a = temp;
		}
		
		/**
		 * h0 = h0 + A
			h1 = h1 + B
			h2 = h2 + C
			h3 = h3 + D
			h4 = h4 + E
		 */
		
		binaryAddHValues();
	}
	
	
	private static String leftRotateValue(String rotationString, int noOfPositions)
	{
		Long valueAinLong = Long.parseLong(rotationString,2);
		Integer valueAtoInt = valueAinLong.intValue(); 
        Integer valueALeftRotate = Integer.rotateLeft(valueAtoInt, noOfPositions);
        
        StringBuilder zeros=new StringBuilder();
		String aLeftRotated = Integer.toBinaryString(valueALeftRotate);
		int diff=32-aLeftRotated.length();
		for(int add=1;add<=diff;add++)
		{
			zeros.append("0");
		}
		aLeftRotated = zeros + aLeftRotated ;
			
		//System.out.println("Left rotated value is : " + aLeftRotated);
		
		return aLeftRotated;
		
	}
	
	public static void binaryAddHValues()
	{
		Long lH0 = Long.parseLong(h0, 2);
		Long lH1 = Long.parseLong(h1, 2);
		Long lH2 = Long.parseLong(h2, 2);
		Long lH3 = Long.parseLong(h3, 2);
		Long lH4 = Long.parseLong(h4, 2);
		
		Long la = Long.parseLong(a, 2);
		Long lb = Long.parseLong(b, 2);
		Long lc = Long.parseLong(c, 2);
		Long ld = Long.parseLong(d, 2);
		Long le = Long.parseLong(e, 2);
		
		endH0 = Long.toBinaryString(lH0 + la);
		endH1 = Long.toBinaryString(lH1 + lb);
		endH2 = Long.toBinaryString(lH2 + lc);
		endH3 = Long.toBinaryString(lH3 + ld);
		endH4 = Long.toBinaryString(lH4 + le);
		
		System.out.println("H0 : " + endH0);
		System.out.println("H1 : " + endH1);
		System.out.println("H2 : " + endH2);
		System.out.println("H3 : " + endH3);
		System.out.println("H4 : " + endH4);
		
		int lengthOfString = endH0.length();
		//System.out.println("Length of H0 String : " + endH0);
		
		if (lengthOfString > MAXLEN)
		{
			int truncationLength = lengthOfString - MAXLEN;
			//System.out.println("Length of Added String : " + truncationLength);	
			
			String cutString = endH0.substring(truncationLength, lengthOfString);
			//System.out.println("Length of Added String : " + cutString);
			endH0 = cutString;
		}
		
		lengthOfString = endH1.length();
		//System.out.println("Length of H1 String : " + endH1);
		
		if (lengthOfString > MAXLEN)
		{
			int truncationLength = lengthOfString - MAXLEN;
			//System.out.println("Length of Added String : " + truncationLength);	
			
			String cutString = endH1.substring(truncationLength, lengthOfString);
			//System.out.println("Length of Added String : " + cutString);
			endH1 = cutString;
		}
		
		lengthOfString = endH2.length();
		//System.out.println("Length of H2 String : " + endH2);
		
		if (lengthOfString > MAXLEN)
		{
			int truncationLength = lengthOfString - MAXLEN;
			//System.out.println("Length of Added String : " + truncationLength);	
			
			String cutString = endH2.substring(truncationLength, lengthOfString);
			//System.out.println("Length of Added String : " + cutString);
			endH2 = cutString;
		}
		
		lengthOfString = endH3.length();
		//System.out.println("Length of H3 String : " + endH3);
		
		if (lengthOfString > MAXLEN)
		{
			int truncationLength = lengthOfString - MAXLEN;
			//System.out.println("Length of Added String : " + truncationLength);	
			
			String cutString = endH3.substring(truncationLength, lengthOfString);
			//System.out.println("Length of Added String : " + cutString);
			endH3 = cutString;
		}
		
		lengthOfString = endH4.length();
		//System.out.println("Length of H4 String : " + endH4);
		
		if (lengthOfString > MAXLEN)
		{
			int truncationLength = lengthOfString - MAXLEN;
			//System.out.println("Length of Added String : " + truncationLength);	
			
			String cutString = endH4.substring(truncationLength, lengthOfString);
			//System.out.println("Length of Added String : " + cutString);
			endH4 = cutString;
		}
		
		System.out.println("Final H0 : " + endH0);
		System.out.println("Final H1 : " + endH1);
		System.out.println("Final H2 : " + endH2);
		System.out.println("Final H3 : " + endH3);
		System.out.println("Final H4 : " + endH4);
	}
	
	public static String binaryAdder(String aLeftRotated, String functionValue, String e, String keyValue, String wordAtLoc ) {
		Long aRotatedvalue = Long.parseLong(aLeftRotated, 2);
		Long fvalue = Long.parseLong(functionValue, 2);
		Long evalue = Long.parseLong(e, 2);
		Long kvalue = Long.parseLong(keyValue, 2);
		Long wordvalue = Long.parseLong(wordAtLoc, 2);
	    
	    return Long.toBinaryString(aRotatedvalue + fvalue + evalue + kvalue + wordvalue);
	}

	public static String binaryToHex(String bin){
		   return Long.toHexString(Long.parseLong(bin,2));
		}
}	
