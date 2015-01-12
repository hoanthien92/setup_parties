/**
 *  A class to simplify keyboard input.
 *  Builds on the Scanner class provided in the Java SDK.
 */
public class Keyboard
{
   /** The SDK provided Scanner object, used to obtain keyboard input */
   private static java.util.Scanner scan = new java.util.Scanner(System.in);
   
   /**
    *  Reads an integer from the keyboard and returns it. <br>
    *  Uses the provided prompt to request an integer from the user.
    */
   public static int readInt(String prompt)
   {
      System.out.print(prompt);
      int num = 0;

      try
      {
         num = scan.nextInt();
         Keyboard.readString("");  //clear the buffer
      }
      catch (java.util.InputMismatchException ime)  //wrong type inputted
      {
         Keyboard.readString("");  //clear the buffer
         num = 0;
      }
      catch (java.util.NoSuchElementException nsee)  //break out of program generates an exception
      {
         Keyboard.readString("");  //clear the buffer
         num = 0;
      }
      return num;
   }

   /**
    *  Reads a double from the keyboard and returns it. <br>
    *  Uses the provided prompt to request a double from the user.
    */
   public static double readDouble(String prompt)
   {
      System.out.print(prompt);
      double num = 0.0;

      try
      {
         num = scan.nextDouble();
         Keyboard.readString("");  //clear the buffer
      }
      catch (java.util.InputMismatchException ime)
      {
         Keyboard.readString("");  //clear the buffer
         num = 0;
      }
      catch (java.util.NoSuchElementException nsee)
      {
         Keyboard.readString("");  //clear the buffer
         num = 0;
      }

      return num;
   }

   /**
    *  Reads a line of text from the keyboard and returns it as a String. <br>
    *  Uses the provided prompt to request a line of text from the user.
    */
   public static String readString(String prompt)
   {
      System.out.print(prompt);
      String str = "";

      try
      {
         str = scan.nextLine();
      }
      catch (java.util.NoSuchElementException nsee)
      {
         Keyboard.readString("");  //clear the buffer
         str = "";
      }

      return str;
   }

}
