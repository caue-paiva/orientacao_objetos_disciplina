package java_folder.interfaces;


class Node <T> {
   public T value;
   public Node<T> next;



   Node (T value){
      this.value = value;
      this.next = null;
   }
}

public class LinkedList <T> {
   
   public int size;
   private Node<T> head;

   public static void main(String[] args) {
      LinkedList<String> lista = new LinkedList();
      lista.appendEnd("aaa");
      lista.appendEnd("ddd");
      lista.appendEnd("hhh");

      System.out.println(lista.toString());



   }



   LinkedList () {
      this.size = 0;
      this.head = null;
   }

   public void appendEnd(T value){
      Node<T> newNode  = new Node<T>(value);
      
      if (this.head == null){
         this.head = newNode;
      } else {
         Node<T> lastNode = this.findLastNode();
         lastNode.next = newNode;
      }
      this.size++;
   }

   public void appendStart(T value){
      Node<T> newNode  = new Node<T>(value);
     
      if (this.head == null){
         this.head = newNode;
      }else {
         newNode.next = this.head;
         this.head = newNode;
      }
      this.size++;
   }

   public boolean removeEnd() {
      if (this.size < 1)
         return false;

      if (this.size == 1){
         this.head = null;
      }else if (this.size == 2){
         this.head.next = null;
      } else {
         Node<T> secondFinalNode = this.findSecondLastNode();
         secondFinalNode.next = null;
      }

      this.size--;
      return true;
   }

   public boolean removeStart() {
         if (this.size < 1)
            return false;
         this.head = this.head.next;
         this.size--;
         return true;
   }

   @Override
   public String toString(){
      String returnStr = "";

      Node<T> curNode = this.head;
      while (curNode != null){
         returnStr += (curNode.value.toString() + " ");
         curNode = curNode.next;
      }

      return returnStr;
   }

   private Node<T> findLastNode() {
      Node<T> curNode = this.head;
      if (curNode == null)
         return null;

      while (curNode.next != null){
         curNode = curNode.next;
      } 

      return curNode;
   }

   private Node<T> findSecondLastNode(){
      Node<T> curNode = this.head;
      if (curNode == null || curNode.next == null)
         return null;

      while (curNode.next.next != null){
         curNode = curNode.next;
      } 

      return curNode;
   }

}
