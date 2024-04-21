package java_folder.arvores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AVL extends ArvoreBin {

   private static final int maxBalancingFactor = 1;



   public static void main(String[] args) {
      AVL avl = new AVL(50);

      avl.Insert("f");
      avl.Insert("b");
      avl.Insert("c");
      avl.Insert("d");
      avl.Insert("a");
      avl.Insert("g");
     // avl.Insert("abaa");
     // avl.Insert("aaa");
      
      System.out.println(avl.toString());
      avl.__MakeRotation(0);
      System.out.println();
      System.out.println(avl.toString());
   }

   AVL(int len) {
      super(len);
   }

   private boolean __NodeIsBalanced(int index){
      int balancingFactor = this.__GetBalancing(index);
      
      if (Math.abs(balancingFactor) > maxBalancingFactor)
         return false;
      else
         return true;
   }

   private int __GetBalancing (int index)  {
      if (index > this.lastNodeIndex) {
         System.out.println("WARNING: Acessando a altura de um nó que não existe " + index + " retornando altura 0");
         return 0;
      }
      //System.out.println("index entrando "+ index);
      int leftHeight =   this.__GetHeightRecu(__LeftChild(index));

      int rightHeight =  this.__GetHeightRecu(__RightChild(index));
      //System.out.println("altura esq "+ leftHeight + " altura dir" + rightHeight);

      return leftHeight - rightHeight;
   }

   private int __GetHeightRecu(int index) {
         if (index > this.lastNodeIndex || this.nodeList[index] == null) 
            return 0; 
         
         int leftHeight =  1 + this.__GetHeightRecu((index*2)+1);
         int rightHeight = 1 + this.__GetHeightRecu((index*2)+2);

         if (leftHeight > rightHeight)
            return leftHeight;
         else 
            return rightHeight;
   }

   private void __MakeRotation(int index) {
      if (index > this.lastNodeIndex) {
         System.out.println("WARNING: tentando rotacionar um nó que não existe " + index);
         return;
      }

      int rootBalancingFactor = this.__GetBalancing(index);
      //System.out.println("fator balan" + rootBalancingFactor);

      if (rootBalancingFactor > 0 ){ //valor positivo, ta pendendo pra esquerda
         int leftChildBalancing =  this.__GetBalancing(__LeftChild(index));
         
         if (leftChildBalancing > 0){ //pai e filho tem o mesmo sinal no balanceamento, rotação DIR-DIR simples
            this.__RotationLogic(index, true);
         } else {
             //rotação ESQ-DIR
             this.__RotationLogic(__LeftChild(index), false); //rotação ESQ-ESQ no filho esquerdo do desbalanceado
             this.__RotationLogic(index, true); //rotação DIR-DIR no nó desbalanceado
         }

      } else { //Valor negativo, ta pendendo pra direita
         int rightChildBalancing = this.__GetBalancing(__RightChild(index));

         if (rightChildBalancing < 0 ){ //pai e filho tem o mesmo sinal no balanceamento, rotação ESQ-ESQ simples
            this.__RotationLogic(index, false);  
         } else {
            this.__RotationLogic(__RightChild(index), true); //rotação DIR_DIR no filho direito do desbalanceado
            this.__RotationLogic(index, false); //rotação ESQ-ESQ no nó desbalanceado
         }
      }

      this.__FindLastNodeIndex(); //acha o novo ultimo nó da arvore
   }

   private void __RotationLogic(int rootIndex, boolean rightRotation) {
      List<String> UnbalancedSubTreeNodes = this.__GetSubtreeVals(rootIndex);
      List<String> restOfTreeList = this.__CopyArrayExcluding(UnbalancedSubTreeNodes);

      ArvoreBin balancedPart = new ArvoreBin(this.maxNodes);
      ArvoreBin rotatePart = new ArvoreBin(this.maxNodes);
      ArvoreBin finalBalancedTree = new ArvoreBin(this.maxNodes); //arvore com o vetor final

      balancedPart.InsertList(restOfTreeList); //insera todos os nós da avl menos o da subarvore balanceada
      
      if (rightRotation)
         rotatePart.Insert(UnbalancedSubTreeNodes.get(1)); //coloca o filho esquerdo da raiz como nova raiz (rotação DIR_DIR) da sub-arvore desbalanceada
      else 
         rotatePart.Insert(UnbalancedSubTreeNodes.get(2));//coloca o filho direito da raiz como nova raiz (rotação ESQ-ESQ) da sub-arvore desbalanceada
      
      rotatePart.Insert(UnbalancedSubTreeNodes.get(0)); //coloca o nó raiz da sub-arvore desbalanceada
      rotatePart.InsertList(UnbalancedSubTreeNodes); //coloca o resto dos nós desbalanceados, os já adicionados não serão colocados dnv, ja que tem um check no método .Insert()

      finalBalancedTree.InsertList(balancedPart.ListOfNodes());
      finalBalancedTree.InsertList(rotatePart.ListOfNodes());

      this.__CopyNodeList(finalBalancedTree.nodeList);
   }

   private List<String> __GetSubtreeVals(int rootIndex){
      if (rootIndex > this.lastNodeIndex) {
         return new ArrayList<>(); // Retorna uma lista vazia se o index da raiz for maior que o ultimo no
      }
   
      List<String> values = new ArrayList<>(); //lista para guardar os valores da string em ordem
      Queue<Integer> queue = new LinkedList<>();//fila para percorre o index de nós
      
      queue.offer(rootIndex); //adiciona o nó raiz na fila

      while (!queue.isEmpty()){
         
         int currentIndex = queue.poll(); //tira o index atual da fila
         values.add(this.nodeList[currentIndex]); //adiciona valor atual na lista de valores

         int leftIndex = __LeftChild(currentIndex);
         int rightIndex = __RightChild(currentIndex);

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
   private List<String> __CopyArrayExcluding(List<String> exclusionList){
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


}
