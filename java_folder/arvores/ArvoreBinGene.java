import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinGene <T extends Comparable<T> > {

   protected T[] nodeList; //index do ultimo nó e o array que contém os nós é protected
   protected int lastNodeIndex; //guarda o index do ultimo no do array, nesse caso é um valor inclusivo a ser checado nos IFs, já que ele contem um valor válido
   protected int maxNodes; //variaveis protegidas sobre status da árvore, como numero de nós e numero máximo de nos
   protected int nodeNumber;


   public static void main(String[] args) {
      ArvoreBinGene<String> arvo = new ArvoreBinGene<String>(10);

      arvo.Insert("b");
      System.out.println(arvo.toString());
        
   }

   ArvoreBinGene(int len){
      this.nodeNumber = 0;
      this.maxNodes = len;
      this.nodeList = (T[]) new Comparable[len];
      this.lastNodeIndex = -1;
   }

   //insere uma string na árvore
   public boolean Insert(final T value) {
      if (this.nodeNumber >= this.maxNodes){ //caso o numero de nos seja maior ou igual ao máximo de nos
         return false;
      }

      if(this.Find(value)){ //caso o valor ja exista, vamos retornar true ja que não tem nenhum erro mas não vamos inserir
         return true;
      }

      int indexToInsert = this._FindIndex(0, value);

      this.nodeList[indexToInsert] = value;
      this.nodeNumber++;
      this._FindLastNodeIndex();

      return true;
   }

   //insere uma lista na árvore
   public boolean InsertList(final List<T> list) {        
      for (int i = 0; i < list.size(); i++) {
            boolean insertResult = this.Insert(list.get(i)); 
            if (!insertResult)
               return false; //falha na inserção   
      }
      return true;
   }

   //remove um nó se ele existir
   public boolean Remove(final T value) {
      if (!this.Find(value)) //se o item não existir n vamos remover
         return false;

      for (int i = 0; i < this.maxNodes; i++) { //loop pelo array de nós
         T node = this.nodeList[i];
         if (node != null && node.equals(value)) {
             List<T> subTree = this._GetSubtreeVals(i); //pega valores da subarvore com raiz no no removido
            
             this._RemoveNodes(subTree); //remove nos da arvore sub-arvore removida
             subTree.remove(subTree.indexOf(value)); //remove da lista o valor que vai ser removido da arvore
             this.InsertList(subTree); //insera valores restantes da subarvore, em ordem
             break;
         } 
      }
      this._FindLastNodeIndex(); //atualiza o indice do último nó
      this.nodeNumber--;
      return true;
   }

   public int Len() {
      return this.nodeNumber;
   }

   //retorna true se um nó existe na arvore, false se não existe
   public boolean Find(final T value) {      
      return this.__FindRecursive(0, value);
   }

   //printa os valores do vetor de uma árvore
   public void PrintTree() {
      if (this.nodeNumber == 0){
         System.out.println("arvore vazia");
         return;
      }
      for (int i = 0; i <= this.lastNodeIndex; i++) {
            System.out.printf("%s%n", this.nodeList[i]);
      }
   }

   //representação da arvore numa string
   @Override
   public String toString() {
      String treeStr = "digraph {";

      if (this.nodeNumber == 1){ //caso a arvore so tenha 1 nó
         T nodeVal = this.nodeList[0].toString();
         treeStr += String.format( "\n\"%d %s\" }", 0,nodeVal);
         return treeStr;
      }else { //caso a arvore so tenha mais de um nó
         for (int i = 0; i <= this.lastNodeIndex; i++) {
               int leftChildIndex = _LeftChild(i);
               int rightChildIndex = _RightChild(i);
               String curStr = this.nodeList[i].toString();
               if (curStr == null)
                  continue;
         

               if (leftChildIndex <= this.lastNodeIndex && this.nodeList[leftChildIndex] != null){ //caso o index seja menor ou igual ao ultimo no na arvore, então existe um filho
                  String leftChildStr = this.nodeList[leftChildIndex].toString();
                  String formattedString = String.format( "\n\"%d %s\" ->\"%d %s\"", i , curStr, leftChildIndex, leftChildStr);
                  treeStr += (formattedString ); //soma string formatada com conexão entre pai e filho no resultado
               
               }
               if (rightChildIndex <= this.lastNodeIndex && this.nodeList[rightChildIndex] != null){
                  String rightChildStr = this.nodeList[rightChildIndex].toString();
                  String formattedString = String.format("\n\"%d %s\" ->\"%d %s\"", i , curStr, rightChildIndex, rightChildStr);
                  treeStr += (formattedString);
               }
      }
   }
      return treeStr + " \n}";
   }

   //MÉTODOS PRIVADOS

   //tenta achar um nó na arvore
   private boolean __FindRecursive(final int curIndex, final T value){
      if (curIndex >= this.maxNodes)
        return false;
      
      T curNodeStr = this.nodeList[curIndex];
      if (curNodeStr == null) //não achou
         return false;
      
      if (curNodeStr.equals(value))
         return true;
      
      if (value.compareTo(curNodeStr) < 0 ) { // valor a ser inserido é menor que o pai, chama filho da esquerda
         return this.__FindRecursive(_LeftChild(curIndex), value);
      } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
         return this.__FindRecursive(_RightChild(curIndex), value);
      }
   }


   //MÉTODOS PROTEGIDOS  
   //Usados para que as subclasses consigam manipular a heap, mas o usuaŕio final não  

   //conta quantos nós tem na árvore
   protected void _CountNodeNum(){
      int count = 0;
      for (int i = 0; i < this.maxNodes; i++) {
         T node = this.nodeList[i];
         if (node != null)
            count++;
      }
      this.nodeNumber = count;
   }

   //remove uma lista de nós do array da árvore
   protected void _RemoveNodes(List<T> nodesToRemove){
      for (int i = 0; i < this.maxNodes; i++) {
          T val =  this.nodeList[i];
          if (nodesToRemove.contains(val)){
               this.nodeList[i] = null;
          }
      }
      this.nodeNumber -= nodesToRemove.size(); //subtrai o numero de nos da arvore
   }


   //retorna uma lista de nós em ordem baseado no array da AB, não tem a indexação direta (2*i +1 ou +2) de pais e filhos
   protected List<T> ListOfNodes(){
      List<T> returnList = new ArrayList<>();
      for (int i = 0; i < this.maxNodes; i++) {
            T curVal = this.nodeList[i];
            if (curVal == null)
               continue;
            returnList.add(curVal);
      }
      return returnList;
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

   //funções estaticas da classe para pegar filho dir e esquerdo
   protected static int _LeftChild (int index) {
      return (index*2) +1;
   }

   protected static int _RightChild (int index) {
      return (index*2) +2;
   }

   //realiza uma BFS e retorna uma lista de nos da sub-arvore, essa lista tem a mesma ordenação dos nós no vetor que representa a árvore
   protected List<T> _GetSubtreeVals(int rootIndex){
      if (rootIndex > this.lastNodeIndex) {
         return new ArrayList<>(); // Retorna uma lista vazia se o index da raiz for maior que o ultimo no
      }
   
      List<T> values = new ArrayList<>(); //lista para guardar os valores da string em ordem
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
   protected List<T> __CopyArrayExcluding(List<T> exclusionList){
      List<T>  returnList = new ArrayList<>();
         
      for (int i = 0; i < this.maxNodes; i++) {
            T curVal = this.nodeList[i];
            if (curVal == null)
               continue;
            if (!exclusionList.contains(curVal)){ //valor não esta na lista de exclusão
                returnList.add(curVal);  
            }
      }
      return returnList;
   }

    //acha o index certo da string para ser inserida
   protected int _FindIndex(final int curIndex, final T value) {
      if (curIndex >= this.maxNodes)
        throw new IndexOutOfBoundsException("Index fora do tam maximo do array: " + curIndex);
      
      T curNodeStr = this.nodeList[curIndex];
      if (curNodeStr == null) //achou um lugar para inserir
         return curIndex;
      
      if (value.compareTo(curNodeStr) < 0 ) { // string a ser inserida é menor que o pai, chama filho da esquerda
         return this._FindIndex(_LeftChild(curIndex), value);
      } else { // string a ser inserida é maior/igual que o pai, chama filho da direita
         return this._FindIndex(_RightChild(curIndex), value);
      }
   }


   protected T _GetNode(final int i) {
     if (i >= this.maxNodes) 
         throw new IndexOutOfBoundsException("Index out of bounds: " + i);

     return  this.nodeList[i];
   }

   protected void _SetNode(final int i, final T value){
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

}
