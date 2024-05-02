import java.util.List;
import java.util.Collections;

public class ABB extends ArvoreBin{

   private static final int maxBalancingFactor = 1;

   public static void main(String[] args) {
      ABB arv = new ABB(20);

      List<String> caso9 =    List.of("y","z","e","n","r","q");
      List<String> caso7 =    List.of("s","c","o","l","n");
      List<String> caso6 =    List.of("y","x","z","c","n");

      //List<String> vals = new ArrayList<>(List.of("y","z","e","n","r","q"));
      arv.Insert("i");
     // arv.Remove("d");
      System.out.println(arv.nodeNumber);
      System.out.println(arv.toString());

   }
   
   ABB(int len){
      super(len);
   }

   //override na inserção , usando parte da lógica da inserção da classe mae mas com o balanceamento
   @Override
   public boolean Insert(String value){
      super.Insert(value);

      if (this.nodeNumber > 2) //so balancea se tiver mais de 2 nós, já que nunca vai ficar desbalanceado so com 2 nós na arvore, antes isso fazia uns casos do runcodes dar problema
         this.__CheckTreeBalancing(); //ve se a arvore precisa de balanceamento
      
      return true;
   }

   //override na inserção de lista, usando parte da lógica da inserção de lista  da classe mae mas com o balanceamento
   @Override
   public boolean InsertList(final List<String> list) {        
      for (int i = 0; i < list.size(); i++) {
            boolean insertResult = this.Insert(list.get(i)); 
           // System.out.println("inseriu o no "+ list.get(i));
            if (!insertResult)
               return false; //falha na inserção   
      }
      return true;
   }

   private boolean __InsertNoBalancing(String value) { //insere sem balanceamento usado a função da arvore mãe
      //, usado na logica de balanceamento ao inserir nós na ordem correta
      return super.Insert(value);
   }

   //insere uma lista sem balanceamento usado a função da arvore mãe
   private boolean __InsertListNoBalancing(List<String> list){ //, usado na logica de balanceamento ao inserir nós na ordem correta
      for (int i = 0; i < list.size(); i++) {
         boolean insertResult = this.__InsertNoBalancing(list.get(i)); 
         if (!insertResult)
            return false; //falha na inserção   
      }
      return true;
   }

   //override na remoção, usando parte da lógica da remoção da classe mae mas com o balanceamento
   @Override
   public boolean Remove (String value){
      super.Remove(value); //chama a funcionalidade de busca da classe parente
      
      if (this.nodeNumber > 0) //so balancea se a arvore n tiver vazia, dava erro antes sem isso
         this.__CheckTreeBalancing(); //realiza operações de balanceamento caso seja necessário
      return true;
   }

   //retorna o int que representa o fator de balanceamento da ABB
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

   //retorna true se o nó é balanceado e false se não for
   private boolean __NodeIsBalanced(final int index){
         if (index >= this.maxNodes){
               //System.out.println("index chamado na função __Isbalanced está fora do limite do array");
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

   //realiza o balanceamento da subarvore
   private boolean __BalanceSubTree(int index) {
      List<String> unbalancedNodeList = this._GetSubtreeVals(index); //pega todos os nós da subarvore desbalanceada
      Collections.sort(unbalancedNodeList);  //ordena os
      //System.out.println("lista nos desbalan ordenada " + unbalancedNodeList);

      this._RemoveNodes(unbalancedNodeList); //remove nós desbalanceados

      int unbalacedListSize = unbalancedNodeList.size(); //tamanho dos desbalanceados
      int indexMedian;
      if (unbalacedListSize % 2 == 0){ //lista tem tamanho par
         indexMedian = (unbalacedListSize/2) -1;
      }else {
         indexMedian = (unbalacedListSize/2);
      }
      String medianVal;
      medianVal = unbalancedNodeList.get(indexMedian); //pega valor da mediana
      //System.out.println("index mediana"+indexMedian +"valor mediana " + medianVal);
      this.__InsertNoBalancing(medianVal); //insere valor da mediana
      this.__InsertListNoBalancing(unbalancedNodeList); //insere os outros nós desbalanceados (não será inserido repetidos)

      this._CountNodeNum(); //vamos compensar pelas inserções colocadas para balancear
      this._FindLastNodeIndex(); //Acha index do último nó

      return true;
   }

   private void __CheckTreeBalancing(){
      this.__BalanceSubTree(0); //balancea o nó 0 sempre
      for (int i = 1; i <= this.lastNodeIndex; i++) {
            if (!this.__NodeIsBalanced(i)){
              // System.out.println("balanceamento no nó "+i);
               this.__BalanceSubTree(i);
            }
      }
   }

}
