package java_folder.arvores;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBin {

   protected String[] nodeList;
   protected int lastNodeIndex; //guarda o index do ultimo no do array, nesse caso é um valor inclusivo a ser checado nos IFs, já que ele contem um valor válido
   public int maxNodes;
   public int nodeNumber;


   public static void main(String[] args) {
      ArvoreBin arvo = new ArvoreBin(10);

      arvo.Insert("a");
      arvo.Insert("b");
      arvo.Insert("c");
     
      arvo.Remove("a");

      System.out.println(arvo.toString());
        
   }

   ArvoreBin(int len){
      this.nodeNumber = 0;
      this.maxNodes = len;
      this.nodeList = new String[len];
      this.lastNodeIndex = -1;
   }

   public boolean Insert(final String value) {
      if (this.nodeNumber >= this.maxNodes){ //caso o numero de nos seja maior ou igual ao máximo de nos
         return false;
      }

      if(this.Find(value)){ //caso o valor ja exista, vamos retornar true ja que não tem nenhum erro mas não vamos inserir
         return true;
      }

      int indexToInsert = this._FindIndex(0, value);
      //System.out.printf("index inserir %s é %d%n", value, indexToInsert);

      this.nodeList[indexToInsert] = value;
      this.nodeNumber++;
      if (this.lastNodeIndex < indexToInsert) //atualiza o index do ultimo no do array
         this.lastNodeIndex = indexToInsert;
      return true;
   }

   public boolean InsertList(final List<String> list) {        
      
      for (int i = 0; i < list.size(); i++) {
            boolean insertResult = this.Insert(list.get(i)); 
            if (!insertResult)
               return false; //falha na inserção   
      }
      return true;
   }

   public boolean Remove(final String value) {
      for (int i = 0; i <= this.lastNodeIndex; i++) {
         if (this.nodeList[i] == value) {
               int rightmostChildIndex = this._FindRightmostChild(i);
               
               if (rightmostChildIndex == i){ //nó não tem filhos a direita
                  int leftmostChildIndex = this._FindLeftmostChild(i); //acha  filho mais a esquerda
                  if (leftmostChildIndex != i){ //tem filho a esquerda
                     this.nodeList[i] = this.nodeList[leftmostChildIndex]; //troca valores
                     this.nodeList[leftmostChildIndex] = null;
                  } else { //n tem filho a esquerda ou dir
                     this.nodeList[i] = null;
                  }
               } else {
                  this.nodeList[i] = this.nodeList[rightmostChildIndex]; //troca valores
                  this.nodeList[rightmostChildIndex] = null;
               }
               break;
         }
      }
      this._FindLastNodeIndex();
      this.nodeNumber--;
      return true;
   }

   public int Len() {
      return this.nodeNumber;
   }

   public boolean Find(final String value) {      
      return this.__FindRecursive(0, value);
   }

   //retorna uma lista de nós em ordem baseado no array da AB, não tem a indexação direta (2*i +1 ou +2) de pais e filhos
   public List<String> ListOfNodes(){
      List<String> returnList = new ArrayList<>();
      for (int i = 0; i < this.maxNodes; i++) {
            String curVal = this.nodeList[i];
            if (curVal == null)
               continue;
            returnList.add(curVal);
      }
      return returnList;
   }

   public void PrintTree() {
      if (this.nodeNumber == 0){
         System.out.println("arvore vazia");
         return;
      }
      for (int i = 0; i <= this.lastNodeIndex; i++) {
            System.out.printf("%s%n", this.nodeList[i]);
      }
   }

   @Override
   public String toString() {
      String treeStr = "";

      for (int i = 0; i <= this.lastNodeIndex; i++) {
            int leftChildIndex = _LeftChild(i);
            int rightChildIndex = _RightChild(i);
            String curStr = this.nodeList[i];
            if (curStr == null)
               continue;
        

            if (leftChildIndex <= this.lastNodeIndex && this.nodeList[leftChildIndex] != null){ //caso o index seja menor ou igual ao ultimo no na arvore, então existe um filho
               String leftChildStr = this.nodeList[leftChildIndex];
               String formattedString = String.format( "%d %s ->  %d %s", i , curStr, leftChildIndex, leftChildStr);
               treeStr += (formattedString + "\n"); //soma string formatada com conexão entre pai e filho no resultado
              
            }
            if (rightChildIndex <= this.lastNodeIndex && this.nodeList[rightChildIndex] != null){
               String rightChildStr = this.nodeList[rightChildIndex];
               String formattedString = String.format( "%d %s ->  %d %s", i , curStr, rightChildIndex, rightChildStr);
               treeStr += (formattedString + "\n");
            }

      }
      return treeStr;
   }

   //MÉTODOS PRIVADOS

   private boolean __FindRecursive(final int curIndex, final String value){
      if (curIndex >= this.maxNodes)
        return false;
      
      String curNodeStr = this.nodeList[curIndex];
      if (curNodeStr == null) //não achou
         return false;
      
      if (curNodeStr.equals(value))
         return true;
      
      if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
         return this.__FindRecursive(_LeftChild(curIndex), value);
      } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
         return this.__FindRecursive(_RightChild(curIndex), value);
      }
}

   private int __FindRightmostRecu(int curIndex, int lastIndex){
      if (curIndex < this.maxNodes){
         String val = this.nodeList[curIndex];
         if (val == null){
            return lastIndex; //retorna ultimo valor
         }else{ 
            return this.__FindRightmostRecu(_RightChild(curIndex), curIndex);
         }
      } else {
         return lastIndex; //out of bounds, retorna ultimo valor
      }
   }

   private int __FindLeftmostRecu(int curIndex, int lastIndex){
      if (curIndex < this.maxNodes){
         String val = this.nodeList[curIndex];
         if (val == null)
            return lastIndex; //retorna ultimo valor
         else 
            return this.__FindLeftmostRecu(_LeftChild(curIndex), curIndex);
      } else {
         return lastIndex; //out of bounds, retorna ultimo valor
      }
   }

   //MÉTODOS PROTEGIDOS  
   //Usados para que as subclasses consigam manipular a heap, mas o usuaŕio final não  

   protected boolean _ReplaceLargestLeftChild(int index){
         int leftChild = _LeftChild(index);
         if (leftChild >= this.maxNodes || this.nodeList[leftChild] == null)
            return false;
         
         int largestInLeft = this._FindRightmostChild(leftChild);
         String largestLeftVal = this.nodeList[largestInLeft]; //salvar valor do maior
         this.nodeList[index] = largestLeftVal;
         this.nodeList[largestInLeft] = null;

         return true;
   }

   protected boolean _ReplaceSmallestRightChild(int index){
      int rightChild = _RightChild(index);
      if (rightChild >= this.maxNodes || this.nodeList[rightChild] == null)
         return false;
      
      int smallestInRight= this._FindLeftmostChild(rightChild);
      String largestLeftVal = this.nodeList[smallestInRight]; //salvar valor do maior
      this.nodeList[index] = largestLeftVal;
      this.nodeList[smallestInRight] = null;

      return true;
}

   protected int _FindRightmostChild(int index){
            if (index >= this.maxNodes){
               System.out.println("index fora de bounds do array");
               return -1;
            }
            return this.__FindRightmostRecu(index, index);
   }

   protected int _FindLeftmostChild(int index){
      if (index >= this.maxNodes){
         System.out.println("index fora de bounds do array");
         return -1;
      }
      return this.__FindLeftmostRecu(index, index);
   }



   //apos certas operações é necessário achar de novo qual o ultimo no da árvore
   protected void _FindLastNodeIndex() {
         for (int i = this.maxNodes-1; i >= 0; i--) { 
             if (this.nodeList[i] != null){
                  this.lastNodeIndex = i;
                  return;
             }
         } 
         this.lastNodeIndex = -1; //não achamos nenhum valor diferente de null na lista, então ela esta vazia
   }

   protected static int _LeftChild (int index) {
      return (index*2) +1;
   }

   protected static int _RightChild (int index) {
      return (index*2) +2;
   }

   //realiza uma DFS e retorna uma lista de nos da sub-arvore, essa lista tem a mesma ordenação dos nós no vetor que representa a árvore
   protected List<String> _GetSubtreeVals(int rootIndex){
      if (rootIndex > this.lastNodeIndex) {
         return new ArrayList<>(); // Retorna uma lista vazia se o index da raiz for maior que o ultimo no
      }
   
      List<String> values = new ArrayList<>(); //lista para guardar os valores da string em ordem
      Queue<Integer> queue = new LinkedList<>();//fila para percorre o index de nós
      
      queue.offer(rootIndex); //adiciona o nó raiz na fila

      while (!queue.isEmpty()){
         
         int currentIndex = queue.poll(); //tira o index atual da fila
         values.add(this.nodeList[currentIndex]); //adiciona valor atual na lista de valores

         int leftIndex = _LeftChild(currentIndex);
         int rightIndex = _RightChild(currentIndex);

         if (leftIndex <= lastNodeIndex && this.nodeList[leftIndex] != null) {
             queue.offer(leftIndex);
         }
         if (rightIndex <= lastNodeIndex && this.nodeList[rightIndex] != null) {
             queue.offer(rightIndex);
         }
      }

      return values;
   }


   //copia o array de nós da AVL mas excluindo certos valores de uma lista passada como parametro
   protected List<String> __CopyArrayExcluding(List<String> exclusionList){
      List<String>  returnList = new ArrayList<>();
         
      for (int i = 0; i < this.maxNodes; i++) {
            String curVal = this.nodeList[i];
            if (curVal == null)
               continue;
            if (!exclusionList.contains(curVal)){ //valor não esta na lista de exclusão
                returnList.add(curVal);  
            }
      }
      return returnList;
   }


   protected void _RemoveChild(final int indexToRemove) {
      if (indexToRemove > this.lastNodeIndex)
         return;

      this.nodeList[indexToRemove] = null;
      this.nodeNumber--;
      this._RemoveChild(_LeftChild(indexToRemove));
      this._RemoveChild(_RightChild(indexToRemove));
   }

   protected int _FindIndex(final int curIndex, final String value) { //acha o index certo da string para ser inserida
      if (curIndex >= this.maxNodes)
        throw new IndexOutOfBoundsException("Index fora do tam maximo do array: " + curIndex);
      
      String curNodeStr = this.nodeList[curIndex];
      if (curNodeStr == null) //achou um lugar para inserir
         return curIndex;
      
      if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
         return this._FindIndex(_LeftChild(curIndex), value);
      } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
         return this._FindIndex(_RightChild(curIndex), value);
      }
   }


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
      if (i > this.lastNodeIndex)
         return 0; //caso base da recursão 

      return 1 + this._CountNodes(_LeftChild(i)) + this._CountNodes(_RightChild(i));

   }

   protected int _NodeRight (final int i){
      if (i > this.lastNodeIndex)
         return -1;

      int rightChildIndex = _RightChild(i);
      if (rightChildIndex > this.lastNodeIndex)
          return -1;
      else
         return rightChildIndex;
   }

   protected int _NodeLeft (final int i){
      if (i > this.lastNodeIndex)
         return -1;

      int leftChildIndex = _LeftChild(i);
      if (leftChildIndex > this.lastNodeIndex)
          return -1;
      else
         return leftChildIndex;
   }

   protected void __CopyNodeList(String[] newVals){
      if (newVals.length != this.nodeList.length){
         System.out.println("WARNING: arrays são de tamanhos diferentes, operação falhou");
         return;
      }

      for (int i = 0; i < newVals.length; i++) {
         this.nodeList[i] = newVals[i]; 
      }

   }

}
