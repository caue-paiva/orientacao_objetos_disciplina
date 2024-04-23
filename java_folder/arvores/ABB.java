package java_folder.arvores;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class ABB extends ArvoreBin{

   private final int maxBalancingFactor = 1;

   public static void main(String[] args) {
      ABB arv = new ABB(20);

      arv.Insert("b");
      arv.Insert("a");
      arv.Insert("c");
      arv.Insert("aa");
      arv.Insert("d");
      arv.Insert("f");

      
      System.out.println(arv.toString());

   }
   
   ABB(int len){
      super(len);
   }

   @Override
   public boolean Insert(String value){
      if (this.nodeNumber >= this.maxNodes){ //caso o numero de nos seja maior ou igual ao máximo de nos
         return false;
      }

      if(this.Find(value)){ //caso o valor ja exista, vamos retornar true ja que não tem nenhum erro mas não vamos inserir
         return true;
      }

      int indexToInsert = this._FindIndex(0, value);
      this.nodeList[indexToInsert] = value; //insere o valor no indice
      this.nodeNumber++; //aumenta número de nos
      this._FindLastNodeIndex(); //atualiza o index do ultimo
      this.__CheckTreeBalancing(); //ve se a arvore precisa de balanceamento
      
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

         if (Math.abs(balanceFactor) <= this.maxBalancingFactor)
            return true;
         else
            return false;
   }

   private boolean __BalanceSubTree(int index) {
      List<String> unbalancedNodeList = this._GetSubtreeVals(index); //pega todos os nós da subarvore desbalanceada
      Collections.sort(unbalancedNodeList);
      List<String> restOfTreeList = this.__CopyArrayExcluding(unbalancedNodeList);// pega todos os nós menos os desbalanc

      int unbalacedListSize = unbalancedNodeList.size(); //tamanho dos desbalanceados
      
      String medianVal;
      medianVal = unbalancedNodeList.get(unbalacedListSize/2); //pega valor da mediana

      ArvoreBin novaArvo = new ArvoreBin(this.maxNodes); //cria arvore aux
      novaArvo.InsertList(restOfTreeList); //insere todo o resto da árvore que está balanceada
      
      novaArvo.Insert(medianVal); //insere valor da mediana
      novaArvo.InsertList(this.ListOfNodes()); //insere os outros nós desbalanceados (não será inserido repetidos)

      this.__CopyNodeList(novaArvo.nodeList); //copia lista de nós da ab aux pra a ABB

      this._FindLastNodeIndex(); //Acha index do último nó

      return true;
   }

   private void __CheckTreeBalancing(){
      for (int i = this.lastNodeIndex; i >= 0; i--) {
            System.out.println(this.nodeList[i]);
            if (!this.__NodeIsBalanced(i)){
               this.__BalanceSubTree(i);
               System.out.println("balanceamento no nó " + i);
               return;
            }
      }
   }

}
