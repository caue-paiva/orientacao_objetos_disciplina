import java.util.ArrayList;
import java.util.List;

public class AVL extends ArvoreBin {
   
   private static final int maxBalancingFactor = 1;

   public static void main(String[] args) {
      AVL avl = new AVL(50);


      List<String> vals = new ArrayList<>(List.of("d","c","f","g","b","bb"));
      avl.InsertList(vals);
    
      System.out.println(avl.toString());
   }

   AVL(int len) {
      super(len);
   }

   @Override
   public boolean Insert(String value){
      super.Insert(value); //chama o método da classe pai
      this.__CheckNodeListBalance(); //ve se a arvore precisa de balanceamento
      return true;
   }

   @Override
   public boolean Remove (String value){
      super.Remove(value); //remoção igual ao metodo da classe pai
      this.__CheckNodeListBalance(); //realiza operações de balanceamento caso seja necessário
      return true;
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
      int leftHeight =   this.__GetHeightRecu(_LeftChild(index));
      int rightHeight =  this.__GetHeightRecu(_RightChild(index));
      
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
         int leftChildBalancing =  this.__GetBalancing(_LeftChild(index));
         
         if (leftChildBalancing > 0){ //pai e filho tem o mesmo sinal no balanceamento, rotação DIR-DIR simples
            this.__RotationLogic(index, true);
         } else {
             //rotação ESQ-DIR
             this.__RotationLogic(_LeftChild(index), false); //rotação ESQ-ESQ no filho esquerdo do desbalanceado
             this._FindLastNodeIndex(); //acha o novo ultimo nó da arvore entre as rotações
             this.__RotationLogic(index, true); //rotação DIR-DIR no nó desbalanceado
         }

      } else { //Valor negativo, ta pendendo pra direita
         int rightChildBalancing = this.__GetBalancing(_RightChild(index));

         if (rightChildBalancing < 0 ){ //pai e filho tem o mesmo sinal no balanceamento, rotação ESQ-ESQ simples
            this.__RotationLogic(index, false);  
         } else {
            this.__RotationLogic(_RightChild(index), true); //rotação DIR_DIR no filho direito do desbalanceado
            this._FindLastNodeIndex(); //acha o novo ultimo nó da arvore entre as rotações
            this.__RotationLogic(index, false); //rotação ESQ-ESQ no nó desbalanceado
         }
      }

      this._FindLastNodeIndex(); //acha o novo ultimo nó da arvore
   }

   private void __RotationLogic(int rootIndex, boolean rightRotation) {
      List<String> UnbalancedSubTreeNodes = this._GetSubtreeVals(rootIndex);
      String leftSon = this.nodeList[this._NodeLeft(rootIndex)];
      String rightSon = this.nodeList[this._NodeRight(rootIndex)];

      this._RemoveNodes(UnbalancedSubTreeNodes); //remove nós desbalanceados da lista 
      
      if (rightRotation){
         this.__InsertNoBalancing(leftSon); //coloca o filho esquerdo da raiz como nova raiz (rotação DIR_DIR) da sub-arvore desbalanceada
      }else {
         this.__InsertNoBalancing(rightSon);//coloca o filho direito da raiz como nova raiz (rotação ESQ-ESQ) da sub-arvore desbalanceada
      }

      this.__InsertNoBalancing(UnbalancedSubTreeNodes.get(0)); //coloca o nó raiz da sub-arvore desbalanceada
      this.__InsertListNoBalancing(UnbalancedSubTreeNodes); //coloca o resto dos nós desbalanceados, os já adicionados não serão colocados dnv, ja que tem um check no método .Insert()
      this.nodeNumber -= UnbalancedSubTreeNodes.size(); //subtrair pelo tamanho do array de desbalanceados 
     
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

   //retorna true se uma rotação foi feita e false senão
   private boolean __CheckNodeListBalance() {
         for (int i = this.lastNodeIndex; i >= 0; i--) {
            if (!this.__NodeIsBalanced(i)){
                this.__MakeRotation(i);
                return true;
            }
         }

         return false;
   }

}
