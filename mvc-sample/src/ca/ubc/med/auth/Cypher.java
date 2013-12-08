package ca.ubc.med.auth;
import java.util.Random;

/**
 * provide static methods to encode and decode user names, which are first_name + space + last_name,
 * and banner id strings
 * Jane Yi. Langara College. 2005 May 30. 
 */
public final class Cypher {

  // a set of possible int that can distribute the 52 alphabetic characters ('A'~'Z', 'a'~'z') properly
  private static int [] DIVISORS ={8, 9, 10, 11, 12};  
  // a set of possible int that can distribute the 10 digits ('0'~'9') properly
  private static int [] NUM_DIVISORS ={3,4,5};
  // this is the value of 'z' - ' ' plus the max length of first_name and last_name allowed in Banner, and 1 for space
  private static int CHAR_VAL_MAX = 166;        // = 90 + 15 + 1 + 60;
  // this is the value of '9' - '0' plus hightest position value 8 as in a bannerid string timed by 2 
  // position value is timed by 2 so that the encoded string will be distributed better.
  private static int NUM_VAL_MAX = 25;          // = 9 + 8*2
  // a char to mark up the divisor, can be any char from 'A' to ('Z' - 11)
  private static int DIVISOR_KEY = 'D';
  private static int KEY_LENGTH = 3;

  public static void main (String[] args){
     /*  if (args.length != 2) { 
     		System.out.println("Usage: java Cypher <Number> <String>");
	    	System.exit(0);
	     }

  	 System.out.println("Number:"); 
     String encnum = Cypher.encodeNum(args[0]);
     System.out.println("   Original:   " + args[0]);
     System.out.println("   Encoded: " + encnum);
     System.out.println("   Decoded: " + decodeNum(encnum)); 

  	 System.out.println("String:"); 
     String encMsg = Cypher.encode(args[1]);
     System.out.println("   Original:   " + args[1]);
     System.out.println("   Encoded: " + encMsg);
     System.out.println("   Decoded: " + Cypher.decode(encMsg)); */

   if (args.length == 2) 
      System.out.println("Orig: " + args[0] + "  Encoded: " + encodeNum(args[0]));
   else   
     System.out.println("Orig: " + args[0] + "   Decoded: " + decodeNum(args[0])); 
  } 
  
  public Cypher () {
  }

  /** 
   * To generate a random key for encoding and decoding a user name
   * @return a string of 3 chars to be used to encoding and decoding a user name
   */
  private static String createKey(){
    Random r = new Random();
    
    // step 1: choose a divisor randomly from the array DIVISORS
    // divisor index will be a random number in the range of 0 to 4
    int divisor = DIVISORS[r.nextInt(5)];
    
    // step 2: choose a base char randomly to mark up the quotient of division
    // base chars are in the range of 'A' to 'Z', indexed from 0 to 25
    // char_div_range is the range of the chars that can be used as divBase for a given divisor    
    int char_div_range = 25 - CHAR_VAL_MAX / divisor;
    // pick a char randomly from 'A' to ('A' + char_div_range) 
    char divBase = (char)((int)'A' + r.nextInt(char_div_range + 1));

    // step 3: choose a base char randomly to mark up the remand of mod
    // base chars are in the range of 'A' to 'Z', indexed from 0 to 25
    // char_mod_range is the range of chars that can be used as modBase for a given divisor       
    int char_mod_range = 25 - divisor;  
    // pick a char randomly from 'Z' to ('Z' - (char_mod_range))
    char modBase = (char)((int)'Z' - r.nextInt(char_mod_range + 1));

    // encode the divisor with the divisor_key
    return String.valueOf(divBase) + modBase + (char)(DIVISOR_KEY + divisor);
  }

  /** 
   * To generate a random key for encoding and decoding banner id
   * @return a string of 3 chars to be used to encoding and decoding a bannerid
   */
  private static String createNumKey(){
    Random r = new Random();

    // step 1: choose a divisor randomly from the array DIVISORS
    // divisor index will be a random number in the range of 0 to 2
    int divisor = NUM_DIVISORS[r.nextInt(3)];

    // step 2: choose a base char randomly to mark up the quotient of division
    // base chars are in the range of 'A' to 'Z', indexed from 0 to 25
    // char_div_range is the range of the chars that can be used as divBase for a given divisor   
    int char_div_range = 25 - NUM_VAL_MAX / divisor;
    // pick a char randomly from 'A' to ('A' + char_div_range)
    char divBase = (char)((int)'A' + r.nextInt(char_div_range + 1));

    // step 3: choose a base char randomly to mark up the remand of mod
    // base chars are in the range of 'A' to 'Z', indexed from 0 to 25
    // char_mod_range is the range of chars that can be used as modBase for a given divisor 
    int char_mod_range = 25 - divisor;
    // pick a char randomly from 'Z' to ('Z' - (char_mod_range))
    char modBase = (char)((int)'Z' - r.nextInt(char_mod_range + 1));  
    
    return String.valueOf(divBase) + modBase + (char)(DIVISOR_KEY + divisor);  
  }


  /**
   * To encode user name string. 
   * @param msg is first_name + sp + last_name, it is of maximal length of 15 + 1 + 60 
   *            according to banner db specification and can contain any printable chars from space to 'z'
   * @return the msg encoded with a dynamicly generated key
   * 20080925 - BW - Added check for nullor zero lenght input.
   */
  public static String encode(String msg){
    String encMsg = "";
    String tail ="";
    String key = createKey();
    char DivBase = key.charAt(0), ModBase = key.charAt(1);
    int Divisor = key.charAt(2) - DIVISOR_KEY;
    
    if (msg != null && msg.length() > 0 ) {
       encMsg = String.valueOf((char)(((msg.charAt(0) - ' '))/Divisor + DivBase));
       tail = String.valueOf((char)(ModBase - ((msg.charAt(0) - ' '))%Divisor));
       for(int i=1; i<msg.length(); i++)    {
          encMsg += String.valueOf((char)(((msg.charAt(i) - ' ') + i)/Divisor + DivBase));
          tail = String.valueOf((char)(ModBase - ((msg.charAt(i) - ' ') + i)%Divisor)) + tail;
       }
    }
    return encMsg + tail + key;
  }

 /**
   * To encode banner id.
   * @param msg is a BannerId 
   * @return the msg encoded with a dynamicly generated key
   * 20080925 - BW - Added check for nullor zero lenght input.
   */
  public static String encodeNum(String msg){
    String encMsg = "";
    String key = createKey();
    char DivBase = key.charAt(0), ModBase = key.charAt(1);
    int Divisor = key.charAt(2) - DIVISOR_KEY;
   
     if (msg != null && msg.length() > 0 ) { 
        encMsg = String.valueOf((char)(((msg.charAt(0) - '0'))/Divisor + DivBase));
       encMsg += String.valueOf((char)(ModBase - ((msg.charAt(0) - '0'))%Divisor));
       for(int i=1; i<msg.length(); i++)    {
         // position value is timed by 2 so that the encoded string will be distributed better.
           encMsg += String.valueOf((char)(((msg.charAt(i) - '0') + i*2)/Divisor + DivBase));
           encMsg += String.valueOf((char)(ModBase - ((msg.charAt(i) - '0') + i*2)%Divisor));
          }
     }
    return encMsg + key;
  }

  /**
   * To decode a string encoded by Cypher.encode()
   * @param encMsg is a string of 'A' to 'Z' generated by Cypher.encode()
   * @return user name decoded from encMsg
   */
  public static String decode(String encMsg){
   String msg =null;
   if (encMsg == null) return null;
   
   // key is the last 3 chars in the encMsg
   String key = encMsg.substring(encMsg.length() - KEY_LENGTH);
   char DivBase = key.charAt(0), ModBase = key.charAt(1);
   int Divisor = key.charAt(2) - DIVISOR_KEY;
   
   encMsg = encMsg.substring(0, encMsg.length() - KEY_LENGTH);
   
   msg = String.valueOf((char)((encMsg.charAt(0) - DivBase)*Divisor + (ModBase - encMsg.charAt(encMsg.length()- 1)) + ' ' ));
   for(int i=1; i< encMsg.length()/2; i++)   {
      msg += String.valueOf((char)((encMsg.charAt(i) - DivBase)*Divisor + (ModBase - encMsg.charAt(encMsg.length()- i - 1)) - i  + ' '));
   }
    return msg;
  } 


  /**
   * To decode a string encoded by Cypher.encodeNum()
   * @param encMsg is a string of 'A' to 'Z' generated by Cypher.encodeNum()
   * @return bannerid decoded from encMsg
   */
  public static String decodeNum(String encMsg){
   String msg =null;
   if (encMsg == null) return null;
   String key = encMsg.substring(encMsg.length() - KEY_LENGTH);
   char DivBase = key.charAt(0), ModBase = key.charAt(1);
   int Divisor = key.charAt(2) - DIVISOR_KEY;

   encMsg = encMsg.substring(0, encMsg.length() - KEY_LENGTH);  
   
   msg = String.valueOf((char)((encMsg.charAt(0) - DivBase)*Divisor + (ModBase - encMsg.charAt(1)) + '0' ));
   for(int i=2; i < encMsg.length(); i=i+2)
   {
      msg += String.valueOf((char)((encMsg.charAt(i) - DivBase)*Divisor + (ModBase - encMsg.charAt(i + 1)) - i  + '0'));
   }

    return msg;
  }
  
}
