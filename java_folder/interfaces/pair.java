package java_folder.interfaces;

public class pair <T1,T2> {

   public T1 first;
   public T2 second;
   
   pair(){
      this.first = null;
      this.second = null;
   }

   pair(T1 first, T2 second){
      this.first = first;
      this.second = second;
   }

}
