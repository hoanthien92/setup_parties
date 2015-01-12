import java.util.Comparator;
/**
 *  Utility class to provide sorting algorithms and simple date invalidation.
 *  Thien Lai
 */
public class Utilities <T>
{


   /**
    * Sorts the specified array of Comparable objects using the selection
    * sort algorithm. <br>
    * The entire array is sorted.<br>  
    */
   public static <T> void selectionSort(T[] sort,Comparator<T> comp)
   {
      if (sort != null)
      {
         selectionSort(sort, comp, sort.length);
      }
   }

   /**
    * Sorts the specified array of Comparable objects using the selection
    * sort algorithm. <br>  
    * The number of items sorted (starting at the beginning of the array) is n.
    */
   public static <T> void selectionSort (T[] sort, Comparator<T> comp, int n)
   {
      //should check n to make sure that it doesn't exceed the array parameters
      int min;
      T temp;

      if (n > sort.length || n <= 0)
      {
         n = sort.length;
      }

      for (int index = 0; index < n - 1; index++)
      {
         min = index;
         for (int scan = index+1; scan < n; scan++)
         {
            if (comp.compare(sort[scan],sort[min]) < 0)
            {
                 min = scan;
            }
         }

         // Swap the values
         temp = sort[min];
         sort[min] = sort[index];
         sort[index] = temp;
      }
   }


   /**
    * Sorts the specified array of Comparable objects using the insertion
    * sort algorithm. <br>
    * The entire array is sorted.<br>  
    */
   public static <T> void insertionSort(T[] sort,Comparator<T> comp)
   {
      if (sort != null)
      {
         insertionSort(sort,comp, sort.length);
      }
   }

   /**
    * Sorts the specified array of Comparable objects using the insertion
    * sort algorithm. <br>  
    * The number of items sorted (starting at the beginning of the array) is n.
    */
   public static <T> void insertionSort (T[] sort,Comparator<T> comp, int n)
   {
      T temp;
      int position;

      if (n>sort.length || n<=0)
      {
         n=sort.length;
      }

      for (int index = 1; index < n; index++)
      {
         temp = sort[index];
         position = index;

         // shift larger values to the right
         while (position > 0 && comp.compare(sort[position-1],temp) > 0)
         {
            sort[position] = sort[position-1];
            position--;
         }
            
         sort[position] = temp;
      }
      
   }
   /**
    * Method to check the date input from users.
    * Precondition: a string of date
    * Postcondition: return true if the date is invalid and return false if it is correct date
    */
   public static boolean invalidDate(String date)
    {
        String[] parts = date.split("/");
        if (parts.length == 3 && parts[0].length() == 2 &&
            parts[1].length() == 2 && parts[2].length() == 2)
        {
            int year, month, day;
            try
            {
                year = Integer.parseInt(parts[0]);
                month = Integer.parseInt(parts[1]);
                day = Integer.parseInt(parts[2]);
            }
            catch (NumberFormatException e)
            {
                return true;
            }
            int latestDay = 28;
            if (year >= 0 && year <= 99)
            {
                switch (month)
                {
                    case 2:
                        if (year % 4 == 0)
                            latestDay = 29;
                        break;
                    case 4: case 6: case 9: case 11:
                        latestDay = 30;
                    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                        latestDay = 31;
                        break;
                    default:
                        return true;
                }
                if (day >= 1 && day <= latestDay)
                    return false;
            }
        }
        return true;
    }

}