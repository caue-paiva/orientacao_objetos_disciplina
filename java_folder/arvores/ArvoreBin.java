package java_folder.arvores;

import javax.lang.model.type.NullType;

class No {
   public int valor;
   public No dir;
   public No esq;
}

public class ArvoreBin {

   private String[] nodeList;
   public int maxNodes;
   public int nodeNumber;

   ArvoreBin(int len){
      this.nodeNumber = 0;
      this.maxNodes = len;
      this.nodeList = new String[len];
   }

   public boolean insert(String value) {
      if (this.nodeNumber >= this.maxNodes){ //caso o numero de nos seja maior ou igual ao máximo de nos
         return false;
      }

      int indexToInsert = this.insertFindIndex(0, value);

      this.nodeList[indexToInsert] = value;
      this.nodeNumber++;
      return true;
   }

   private int insertFindIndex(int curIndex, String value) { //acha o index certo da string para ser inserida
         if (curIndex >= this.maxNodes)
           throw new IndexOutOfBoundsException("Index out of bounds: " + curIndex);
         
         String curNodeStr = this.nodeList[curIndex];
         if (curNodeStr == null) //achou um lugar para inserir
            return curIndex;
         
         if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
            return this.insertFindIndex((curIndex*2)+1, value);
         } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
            return this.insertFindIndex((curIndex*2)+2, value);
         }
   }

   public boolean remove(int index) {
         String value = this.nodeList[index];
         if (value == null)   //index não tem nenhum elemento
            return false;
         
         this.nodeList[index] = null; 
         this.nodeNumber--;
         return true;
   }

   public int len() {
      return this.nodeNumber;
   }

   public boolean find(String valorBuscado) {      
      return this.findRecursive(0, valorBuscado);
   }

   private boolean findRecursive(int curIndex, String value){
         if (curIndex >= this.maxNodes)
           return false;
         
         String curNodeStr = this.nodeList[curIndex];
         if (curNodeStr == null) //achou um lugar para inserir
            return false;
         
         if (curNodeStr == value)
            return true;
         
         if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
            return this.findRecursive((curIndex*2)+1, value);
         } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
            return this.findRecursive((curIndex*2)+2, value);
         }

   }

   public String toString() {
      String result = "";
      for (int i = 0; i < this.nodeNumber; i++) {
            int leftChildIndex = (i*2)+1;
            int rightChildIndex = (i*2)+2;

            if (leftChildIndex < this.nodeNumber){
               

            }
            if (rightChildIndex < this.nodeNumber){

            }

      }
   }

   //protected colocar métodos protegidos para que as subclasses consigam manipular a heap, mas o usuaŕio 
   //final não  

   protected String getNode(int i) {
     if (i >= this.maxNodes) 
         throw new IndexOutOfBoundsException("Index out of bounds: " + i);

     return  this.nodeList[i];
   }

   protected void setNode(int i, String value){
      if (i >= this.maxNodes) 
          throw new IndexOutOfBoundsException("Index out of bounds: " + i);
      this.nodeList[i] = value;
   }




}
