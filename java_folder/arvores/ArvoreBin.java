package java_folder.arvores;
import java.util.ArrayList;
import java.util.List;

public class ArvoreBin {

   protected String[] nodeList;
   protected int lastNodeIndex; //guarda o index do ultimo no do array, nesse caso é um valor inclusivo a ser checado nos IFs, já que ele contem um valor válido
   public int maxNodes;
   public int nodeNumber;

   protected static int __LeftChild (int index) {
      return (index*2) +1;
   }

   protected static int __RightChild (int index) {
      return (index*2) +2;
   }

   public static void main(String[] args) {
      ArvoreBin arvo = new ArvoreBin(8);

      arvo.Insert("abaa");
      arvo.Insert("bbb");
      arvo.Insert("aaa");
        
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
               this.nodeList[i] = null;
               this.__RemoveChild(__LeftChild(i));
               this.__RemoveChild(__RightChild(i));
               break;
         }
      }
      this.__FindLastNodeIndex();
      this.nodeNumber--;
      return true;
   }

   public int Len() {
      return this.nodeNumber;
   }

   public boolean Find(final String value) {      
      return this.__FindRecursive(0, value);
   }

   //retorna uma lista de nós em ordem baseado no array da AB, não tem a indexação direta de pais e filhos
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
            int leftChildIndex = __LeftChild(i);
            int rightChildIndex = __RightChild(i);
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

   private void __RemoveChild(final int indexToRemove) {
      if (indexToRemove > this.lastNodeIndex)
         return;

      this.nodeList[indexToRemove] = null;
      this.nodeNumber--;
      this.__RemoveChild(__LeftChild(indexToRemove));
      this.__RemoveChild(__RightChild(indexToRemove));
   }

   private boolean __FindRecursive(final int curIndex, final String value){
      if (curIndex >= this.maxNodes)
        return false;
      
      String curNodeStr = this.nodeList[curIndex];
      if (curNodeStr == null) //não achou
         return false;
      
      if (curNodeStr.equals(value))
         return true;
      
      if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
         return this.__FindRecursive(__LeftChild(curIndex), value);
      } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
         return this.__FindRecursive(__RightChild(curIndex), value);
      }
}

   //apos certas operações é necessário achar de novo qual o ultimo no da árvore
   protected void __FindLastNodeIndex() {
         for (int i = this.maxNodes-1; i >= 0; i--) { 
             if (this.nodeList[i] != null){
                  this.lastNodeIndex = i;
                  return;
             }
         } 
         this.lastNodeIndex = -1; //não achamos nenhum valor diferente de null na lista, então ela esta vazia
   }

   //MÉTODOS PROTEGIDOS  
   //Usados para que as subclasses consigam manipular a heap, mas o usuaŕio final não  

   protected int _FindIndex(final int curIndex, final String value) { //acha o index certo da string para ser inserida
      if (curIndex >= this.maxNodes)
        throw new IndexOutOfBoundsException("Index fora do tam maximo do array: " + curIndex);
      
      String curNodeStr = this.nodeList[curIndex];
      if (curNodeStr == null) //achou um lugar para inserir
         return curIndex;
      
      if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
         return this._FindIndex(__LeftChild(curIndex), value);
      } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
         return this._FindIndex(__RightChild(curIndex), value);
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

      return 1 + this._CountNodes(__LeftChild(i)) + this._CountNodes(__RightChild(i));

   }

   protected int _NodeRight (final int i){
      if (i > this.lastNodeIndex)
         return -1;

      int rightChildIndex = __RightChild(i);
      if (rightChildIndex > this.lastNodeIndex)
          return -1;
      else
         return rightChildIndex;
   }

   protected int _NodeLeft (final int i){
      if (i > this.lastNodeIndex)
         return -1;

      int leftChildIndex = __LeftChild(i);
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
