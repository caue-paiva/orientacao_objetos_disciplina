package java_folder.arvores;

public class ArvoreBin {

   private String[] nodeList;
   public int maxNodes;
   public int nodeNumber;

   ArvoreBin(int len){
      this.nodeNumber = 0;
      this.maxNodes = len;
      this.nodeList = new String[len];
   }

   public boolean Insert(final String value) {
      if (this.nodeNumber >= this.maxNodes){ //caso o numero de nos seja maior ou igual ao máximo de nos
         return false;
      }

      int indexToInsert = this.__InsertFindIndex(0, value);

      this.nodeList[indexToInsert] = value;
      this.nodeNumber++;
      return true;
   }

   private int __InsertFindIndex(final int curIndex, final String value) { //acha o index certo da string para ser inserida
         if (curIndex >= this.maxNodes)
           throw new IndexOutOfBoundsException("Index out of bounds: " + curIndex);
         
         String curNodeStr = this.nodeList[curIndex];
         if (curNodeStr == null) //achou um lugar para inserir
            return curIndex;
         
         if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
            return this.__InsertFindIndex((curIndex*2)+1, value);
         } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
            return this.__InsertFindIndex((curIndex*2)+2, value);
         }
   }

   public boolean Remove(final int index) {
         String value = this.nodeList[index];
         if (value == null)   //index não tem nenhum elemento
            return false;
         
         this.nodeList[index] = null; 
         this.nodeNumber--;
         return true;
   }

   public int Len() {
      return this.nodeNumber;
   }

   public boolean Find(final String valorBuscado) {      
      return this.__FindRecursive(0, valorBuscado);
   }

   private boolean __FindRecursive(final int curIndex, final String value){
         if (curIndex >= this.maxNodes)
           return false;
         
         String curNodeStr = this.nodeList[curIndex];
         if (curNodeStr == null) //achou um lugar para inserir
            return false;
         
         if (curNodeStr == value)
            return true;
         
         if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
            return this.__FindRecursive((curIndex*2)+1, value);
         } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
            return this.__FindRecursive((curIndex*2)+2, value);
         }
   }

   public void PrintTree() {
      for (int i = 0; i < this.nodeNumber; i++) {
            System.out.printf("%s%n", this.nodeList[i]);
      }
   }

   public String toString() {
      String treeStr = "";
      boolean hasChild = false;

      for (int i = 0; i < this.nodeNumber; i++) {
            int leftChildIndex = (i*2)+1;
            int rightChildIndex = (i*2)+2;
            String curStr = this.nodeList[i];
            hasChild = false;

            if (leftChildIndex < this.nodeNumber){ //caso o index seja menor que o número de nos na arvore, então existe um filho
               String leftChildStr = this.nodeList[leftChildIndex];
               String formattedString = String.format( "%d %s ->  %d %s", i , curStr, leftChildIndex, leftChildStr);
               treeStr += (formattedString + "\n"); //soma string formatada com conexão entre pai e filho no resultado
               hasChild = true;
            }
            if (rightChildIndex < this.nodeNumber){
               String rightChildStr = this.nodeList[rightChildIndex];
               String formattedString = String.format( "%d %s ->  %d %s", i , curStr, rightChildIndex, rightChildStr);
               treeStr += (formattedString + "\n");
               hasChild = true;
            }

            if (!hasChild){ //caso o no não tenha filhos, vamos printar ela apenas
               String formattedString = String.format( "%d %s -> null", i , curStr);
               treeStr += (formattedString + "\n");
            }
      }
      return treeStr;
   }


   //Métodos protegidos para que as subclasses consigam manipular a heap, mas o usuaŕio final não  

   protected String _GetNode(final int i) {
     if (i >= this.maxNodes) 
         throw new IndexOutOfBoundsException("Index out of bounds: " + i);

     return  this.nodeList[i];
   }

   protected void _SetNode(final int i, final String value){
      if (i >= this.maxNodes) 
          throw new IndexOutOfBoundsException("Index out of bounds: " + i);
      this.nodeList[i] = value;
   }

   //dado um nó raiz de sub árvore i, conta quantos nós essa sub aŕvore tem (contando com a raiz)
   protected int _CountNodes(final int i){
      if (i >= this.nodeNumber)
         return 0; //caso base da recursão 

      return 1 + this._CountNodes((i*2)+1) + this._CountNodes((i*2)+2);

   }

   protected int _NodeRight (final int i){
      if (i >= this.nodeNumber)
         return -1;

      int rightChildIndex = (i*2)+2;
      if (rightChildIndex >= this.nodeNumber)
          return -1;
      else
         return rightChildIndex;
   }

   protected int _NodeLeft (final int i){
      if (i >= this.nodeNumber)
         return -1;

      int leftChildIndex = (i*2)+1;
      if (leftChildIndex >= this.nodeNumber)
          return -1;
      else
         return leftChildIndex;
   }


}
