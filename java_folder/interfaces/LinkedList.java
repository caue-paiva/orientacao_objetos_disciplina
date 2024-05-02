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
      LinkedList<String> lista = new LinkedList<String>();
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
         Node<T> lastNode = this.__findLastNode();
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
         Node<T> secondFinalNode = this.__findSecondLastNode();
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

   public boolean exists(T value){
      if (this.size == 0)
         return false;
      
      Node<T> curNode = this.head;
      while (curNode != null){
         if (curNode.value == value){
            return true;
         }
         curNode = curNode.next;
      }
      return false;
   }

   public T atIndex(int index){
      Node<T> node = this.__nodeAtIndex(index);
      if (node != null){
         return node.value;
      }else{
         return null;
      }
   }

   public boolean setIndex(T value, int index){
      Node<T> node = this.__nodeAtIndex(index);
      if (node != null){
          node.value = value;
          return true;
      }else{
         return false;
      }
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

   private Node<T> __nodeAtIndex(int index){
      if (index >= this.size){
         System.out.println("index não existe na lista");
         return null;
      }

      Node<T> curNode = this.head;
      int curIndex = 0;

      while (curNode != null){
         if (curIndex == index){
            return curNode;
         }
         curNode = curNode.next;
         curIndex++;
      }

      return null;
   }

   private Node<T> __findLastNode() {
      Node<T> curNode = this.head;
      if (curNode == null)
         return null;

      while (curNode.next != null){
         curNode = curNode.next;
      } 

      return curNode;
   }

   private Node<T> __findSecondLastNode(){
      Node<T> curNode = this.head;
      if (curNode == null || curNode.next == null)
         return null;

      while (curNode.next.next != null){
         curNode = curNode.next;
      } 

      return curNode;
   }

}
