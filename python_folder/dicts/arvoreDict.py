

class ArvoBinaDict:
   
   dict_nos: dict[int,tuple[int|None]]
   raiz: int
   num_nos: int

   def __init__(self) -> None:
      self.dict_nos = {}
      self.raiz = -1
      self.num_nos = 0

   def inserir (self, valor: int)->None:
      if self.raiz == -1: #arvore vazia
         self.dict_nos[valor] = (None,None)
         self.raiz = valor
      else:
         self.__insere_recur(self.raiz,valor)

      self.num_nos += 1

   def __insere_recur(self,chave_atual:int, valor_inserido:int)->None:
      
      if valor_inserido > chave_atual: #valor inserido é maior, coloca no filho da direita
         if (self.get_filho_dir(chave_atual) is None):
            self.set_filho_dir(chave_atual,valor_inserido) #o nó pai é menor que o a ser inserido e seu filho dir é nulo, coloca ele como filho dir
            self.dict_nos[valor_inserido] = (None,None) #coloca o nó  inserido no dict
         else: 
            return self.__insere_recur(self.get_filho_dir(chave_atual),valor_inserido) #caso exista um filho direito, chama a função recursivamente nesse filho

      elif valor_inserido < chave_atual:
         if (self.get_filho_esq(chave_atual) is None):
            self.set_filho_esq(chave_atual,valor_inserido) #o nó é menor que o a ser inserido e seu filho dir é nulo, coloca o inserido como filho
            self.dict_nos[valor_inserido] = (None,None) #coloca o nó  inserido no dict

         else: 
            return self.__insere_recur(self.get_filho_esq(chave_atual),valor_inserido) #caso exista um filho direito, chama a função recursivamente nesse filho    
      else:
         return
 
   def get_filho_dir(self,index_no:int)->int | None:
      valor_index:tuple | None = self.dict_nos.get(index_no)
      if valor_index is None:
         return None
      else:
         return valor_index[1]
      
   def get_filho_esq(self,index_no:int)->int | None:
      valor_index:tuple | None = self.dict_nos.get(index_no)
      if valor_index is None:
         return None
      else:
         return valor_index[0]
      
   def set_filho_dir(self,index_no:int, novo_valor:int)->bool:
      valor_index:tuple | None = self.dict_nos.get(index_no)
      if valor_index is None:
         return False
      else:
         self.dict_nos[index_no] = (self.dict_nos[index_no][0], novo_valor)
         return True
      
   def set_filho_esq(self,index_no:int, novo_valor:int)->bool:
      valor_index:tuple | None = self.dict_nos.get(index_no)
      if valor_index is None:
         return False
      else:
         self.dict_nos[index_no] = (novo_valor, self.dict_nos[index_no][1])
         return True
      
   def __str__(self) -> str:
      str_retorno: str = "Digraph {\n"
      for key, value in self.dict_nos.items():
         esq,dir  = value
         
         if esq:
            str_retorno += (str(key) + " ->  " + str(esq) + "\n")
         if dir:
            str_retorno += (str(key) + " ->  " + str(dir) + "\n")

         if not esq and not dir:
            str_retorno += (str(key) + " ->  " +  "None \n")

      
      str_retorno += "}"

      return str_retorno



if __name__ == "__main__":
   arv1: ArvoBinaDict = ArvoBinaDict()


   arv1.inserir(30)
   arv1.inserir(40)
   arv1.inserir(20)

   print(arv1)

