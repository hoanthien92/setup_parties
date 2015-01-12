public class PartyException extends RuntimeException {
   public PartyException() {
       this("General Party Problem");
   }
   
   public PartyException(String s) {
       super(s);
   }
}