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

   //override na inserção , usando parte da lógica da inserção da classe mae mas com o balanceamento
   @Override
   public boolean Insert(String value){
      super.Insert(value); //chama o método da classe pai
      this.__CheckNodeListBalance(); //ve se a arvore precisa de balanceamento
      return true;
   }

   //override na remoção , usando parte da lógica da remoção da classe mae mas com o balanceamento
   @Override
   public boolean Remove (String value){
      super.Remove(value); //remoção igual ao metodo da classe pai
      this.__CheckNodeListBalance(); //realiza operações de balanceamento caso seja necessário
      return true;
   }


   //retorna true se o nó é balanceado e false se não for
   private boolean __NodeIsBalanced(int index){
      int balancingFactor = this.__GetBalancing(index);
      
      if (Math.abs(balancingFactor) > maxBalancingFactor)
         return false;
      else
         return true;
   }

   //retorna o int que representa o fator de balanceamento da ABB
   private int __GetBalancing (int index)  {
      if (index > this.lastNodeIndex) {
         System.out.println("WARNING: Acessando a altura de um nó que não existe " + index + " retornando altura 0");
         return 0;
      }
      int leftHeight =   this.__GetHeightRecu(_LeftChild(index));
      int rightHeight =  this.__GetHeightRecu(_RightChild(index));
      
      return leftHeight - rightHeight;
   }

   //acha a altura de um nó
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

   //lógica para achar qual o tipo de rotação é necessário fazer
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

   //função para realizar a rotação em si, com uma flag de rotação dir ou esquerda
   private void __RotationLogic(int rootIndex, boolean rightRotation) {
      List<String> UnbalancedSubTreeNodes = this._GetSubtreeVals(rootIndex); //nós da subarvore desbalanceada
      int leftChildIndex = this._NodeLeft(rootIndex); //filhos esq e dir do nó raiz da subarvore
      int rightChildIndex = this._NodeRight(rootIndex);
      String leftSon = null;
      String rightSon = null;
      
      if (leftChildIndex > -1 && leftChildIndex < this.maxNodes)
         leftSon = this.nodeList[leftChildIndex];
      if (rightChildIndex > -1 && rightChildIndex < this.maxNodes)
         rightSon = this.nodeList[rightChildIndex];

      this._RemoveNodes(UnbalancedSubTreeNodes); //remove nós desbalanceados da lista 
      
      if (rightRotation && leftSon != null){
         this.__InsertNoBalancing(leftSon); //coloca o filho esquerdo da raiz como nova raiz (rotação DIR_DIR) da sub-arvore desbalanceada
      }else if (rightSon != null) {
         this.__InsertNoBalancing(rightSon);//coloca o filho direito da raiz como nova raiz (rotação ESQ-ESQ) da sub-arvore desbalanceada
      }

      this.__InsertNoBalancing(UnbalancedSubTreeNodes.get(0)); //coloca o nó raiz da sub-arvore desbalanceada
      this.__InsertListNoBalancing(UnbalancedSubTreeNodes); //coloca o resto dos nós desbalanceados, os já adicionados não serão colocados dnv, ja que tem um check no método .Insert()
      this.nodeNumber -= UnbalancedSubTreeNodes.size(); //subtrair pelo tamanho do array de desbalanceados 
     
   }


   private boolean __InsertNoBalancing(String value) { //insere sem balanceamento usado a função da arvore mãe
      //, usado na logica de balanceamento ao inserir nós na ordem correta
      return super.Insert(value);
   }

   private boolean __InsertListNoBalancing(List<String> list){//insere uma lista sem balanceamento usado a função da arvore mãe
      //, usado na logica de balanceamento ao inserir nós na ordem correta
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
