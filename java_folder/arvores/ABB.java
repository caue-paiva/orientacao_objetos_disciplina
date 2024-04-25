package java_folder.arvores;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ABB extends ArvoreBin{

   private static final int maxBalancingFactor = 1;

   public static void main(String[] args) {
      ABB arv = new ABB(20);

      List<String> vals = new ArrayList<>(List.of("d", "b", "f", "e","c","a"));
      arv.InsertList(vals);
      arv.Remove("e");
     
      System.out.println(arv.nodeNumber);      
      System.out.println(arv.toString());

   }
   
   ABB(int len){
      super(len);
   }

   @Override
   public boolean Insert(String value){
      super.Insert(value);
      this.__CheckTreeBalancing(); //ve se a arvore precisa de balanceamento
      
      return true;
   }

   private boolean __InsertNoBalancing(String value) {
      return super.Insert(value);
   }

   private boolean __InsertListNoBalancing(List<String> list){
      for (int i = 0; i < list.size(); i++) {
         boolean insertResult = this.__InsertNoBalancing(list.get(i)); 
         if (!insertResult)
            return false; //falha na inserção   
      }
      return true;
   }

   @Override
   public boolean Remove (String value){
      super.Remove(value); //chama a funcionalidade de busca da classe parente
      this.__CheckTreeBalancing(); //realiza operações de balanceamento caso seja necessário
      return true;
   }

   private int __GetBalancing(final int index){

         int leftchild = _LeftChild(index);
         int leftChildNumNodes;
         if (leftchild < this.maxNodes && this.nodeList[leftchild] != null) //caso o no filho esquerdo exista
             leftChildNumNodes =  this._GetSubtreeVals(leftchild).size(); //pega p tamanho da lista de nós da sub-arvore
         else
            leftChildNumNodes = 0;

         int rightchild = _RightChild(index);
         int rightChildNumNodes;
         if (rightchild < this.maxNodes &&  this.nodeList[rightchild] != null) //caso o no filho esquerdo exista
             rightChildNumNodes =  this._GetSubtreeVals(rightchild).size(); //pega p tamanho da lista de nós da sub-arvore
         else
             rightChildNumNodes = 0;

         return leftChildNumNodes - rightChildNumNodes; //retorna o tamanho (num de nós) da subarvore esquerda - sub-arvore dir
   }

   private boolean __NodeIsBalanced(final int index){
         if (index >= this.maxNodes){
               System.out.println("index chamado na função __Isbalanced está fora do limite do array");
               return true; //n precisa rotacionar nesse caso de erro
         }
         if (this.nodeList[index] == null) //caso o nó não exista
            return true;

         int balanceFactor = this.__GetBalancing(index);

         if (Math.abs(balanceFactor) <= maxBalancingFactor)
            return true;
         else
            return false;
   }

   private boolean __BalanceSubTree(int index) {
      List<String> unbalancedNodeList = this._GetSubtreeVals(index); //pega todos os nós da subarvore desbalanceada
      Collections.sort(unbalancedNodeList);  //ordena os
      System.out.println(unbalancedNodeList);

      this._RemoveNodes(unbalancedNodeList); //remove nós desbalanceados

      int unbalacedListSize = unbalancedNodeList.size(); //tamanho dos desbalanceados
      
      String medianVal;
      medianVal = unbalancedNodeList.get(unbalacedListSize/2); //pega valor da mediana
      System.out.println(medianVal);

      this.__InsertNoBalancing(medianVal); //insere valor da mediana
      this.__InsertListNoBalancing(unbalancedNodeList); //insere os outros nós desbalanceados (não será inserido repetidos)

      this.nodeNumber -= unbalacedListSize; //vamos compensar pelas inserções colocadas para balancear
      this._FindLastNodeIndex(); //Acha index do último nó

      return true;
   }

   private void __CheckTreeBalancing(){
      for (int i = this.lastNodeIndex; i >= 0; i--) {
            if (!this.__NodeIsBalanced(i)){
               this.__BalanceSubTree(i);
               System.out.println("balanceamento no nó " + i);
               return;
            }
      }
   }

}
