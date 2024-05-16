

class ArvoBinaDict:
   
   dict_nos: dict[str,tuple[int|None]]
   raiz: str
   num_nos: int

   def __init__(self) -> None:
      self.dict_nos = {}
      self.raiz = ""
      self.num_nos = 0

   def print_em_ordem(self)->None:
      if (self.num_nos == 0):
         return
      self.__print_ordem_recu(self.raiz)
      print() #print para começar uma nova linha, pq o print da função recursiva não tem newline
      
   def __print_ordem_recu(self,no:str)->None:
      filho_esq: str | None = self.__get_filho_esq(no)
      filho_dir: str | None = self.__get_filho_dir(no)

      if (filho_esq is not None):
         self.__print_ordem_recu(filho_esq)
        
      print(f" {no}",end=" ")
        
      if (filho_dir is not None):
         self.__print_ordem_recu(filho_dir)

   def inserir (self, valor: str)->None:
      if self.num_nos == 0: #arvore vazia
         self.dict_nos[valor] = (None,None)
         self.raiz = valor
      else:
         self.__insere_recur(self.raiz,valor)

      self.num_nos += 1

   def __insere_recur(self,chave_atual:str, valor_inserido:str)->None:
      
      if valor_inserido > chave_atual: #valor inserido é maior, coloca no filho da direita
         if (self.__get_filho_dir(chave_atual) is None):
            self.__set_filho_dir(chave_atual,valor_inserido) #o nó pai é menor que o a ser inserido e seu filho dir é nulo, coloca ele como filho dir
            self.dict_nos[valor_inserido] = (None,None) #coloca o nó  inserido no dict
         else: 
            return self.__insere_recur(self.__get_filho_dir(chave_atual),valor_inserido) #caso exista um filho direito, chama a função recursivamente nesse filho

      elif valor_inserido < chave_atual:
         if (self.__get_filho_esq(chave_atual) is None):
            self.__set_filho_esq(chave_atual,valor_inserido) #o nó é menor que o a ser inserido e seu filho dir é nulo, coloca o inserido como filho
            self.dict_nos[valor_inserido] = (None,None) #coloca o nó  inserido no dict

         else: 
            return self.__insere_recur(self.__get_filho_esq(chave_atual),valor_inserido) #caso exista um filho direito, chama a função recursivamente nesse filho    
      else:
         return
 
   def __get_filho_dir(self,chave_no:str)->str | None:
      valor_index:tuple | None = self.dict_nos.get(chave_no)
      if valor_index is None:
         return None
      else:
         return valor_index[1]
      
   def __get_filho_esq(self,chave_no:str)->str | None:
      valor_index:tuple | None = self.dict_nos.get(chave_no)
      if valor_index is None:
         return None
      else:
         return valor_index[0]
      
   def __set_filho_dir(self,chave_no:str, novo_valor:str)->bool:
      valor_index:tuple | None = self.dict_nos.get(chave_no)
      if valor_index is None:
         return False
      else:
         self.dict_nos[chave_no] = (self.dict_nos[chave_no][0], novo_valor)
         return True
      
   def __set_filho_esq(self,chave_no:str, novo_valor:str)->bool:
      valor_index:tuple | None = self.dict_nos.get(chave_no)
      if valor_index is None:
         return False
      else:
         self.dict_nos[chave_no] = (novo_valor, self.dict_nos[chave_no][1])
         return True
      
   def __str__(self) -> str:
      str_retorno: str = "Digraph {\n"
      for key, value in self.dict_nos.items():
         esq,dir  = value
         
         if esq:
            str_retorno += (str(key) + " -> " + str(esq) + "\n")
         if dir:
            str_retorno += (str(key) + " -> " + str(dir) + "\n")

      str_retorno += "}"

      return str_retorno



if __name__ == "__main__":
   arv1: ArvoBinaDict = ArvoBinaDict()

   while True:
      try:
         linha_lida:str = input()
         if linha_lida == "":
            break

         comando: str = linha_lida[0]
         valor_inserir:str = linha_lida[2:]
         arv1.inserir(valor_inserir)
      except EOFError:
         break
   
   arv1.print_em_ordem()
   print(arv1)

