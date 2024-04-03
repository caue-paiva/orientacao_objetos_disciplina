import random , math

class Tabuleiro:

   WHITE_SPACE = 0

   tabuleiro: list[list[int]]
   posicao_white_space: tuple[int]
   tamanho: int

   def __init__(self, tamanho_ou_lista:int |list[int]) -> None:
      #overloading the constructors no python pode ser implementado checando o tipo do argumento
      if isinstance(tamanho_ou_lista,int): # se ele for um inteiro (tamanho do tabuleiro)      
         self.tamanho = tamanho_ou_lista 
         self.__criar_tabuleiro_tam(tamanho_ou_lista)
      elif isinstance(tamanho_ou_lista, list): #se ele for uma lista de numeros
         tamanho_lista:int = len(tamanho_ou_lista)
         print(tamanho_lista)
         self.tamanho = int(math.sqrt(tamanho_lista))
         self.__preenche_tabuleiro_lista(tamanho_ou_lista)
      
      
         
   def __criar_tabuleiro_tam(self,tamanho:int)-> None:     
      lista_num: list[int] = [i for i in range(tamanho*tamanho)] #cria a lista de numeros de 0 até n-1
      random.shuffle(lista_num) #embaralha os items da lista
      index_whitespace:int = lista_num.index(0) #acha o index de 0
      lista_num[index_whitespace] = self.WHITE_SPACE #zero vai virar -1/whitespace
      
      #dividir a posicao no array do index do -1 pelo tamanho do array vai dar a linha do whitespace
      #fazer o mod da posicao no array do index do -1 pelo tamanho do array vai dar a coluna do whitespace
      self.posicao_white_space= (int(index_whitespace/self.tamanho) ,  index_whitespace%self.tamanho)
     
      self.tabuleiro = [ lista_num[(i*self.tamanho) : (i*self.tamanho) + self.tamanho]  for i in range(tamanho)   ] #lista comprehension num slice do vetor para gerar o tabuleiro

   def __preenche_tabuleiro_lista(self,list_numeros:int)->None:
      self.tabuleiro = [[0] * self.tamanho for _ in range(self.tamanho)]
      index_atual_list: int = 0
      for i in range(self.tamanho):
         for j in range(self.tamanho):
            numero_atual: int  = list_numeros[index_atual_list]
            self.tabuleiro[i][j] = numero_atual
            index_atual_list += 1
            if numero_atual == self.WHITE_SPACE:
               self.posicao_white_space = (i,j)

   def print_tabuleiro(self)->None:
      for i in range(self.tamanho):
        print("+------" * self.tamanho + "+")
        
        for j in range(self.tamanho):
            val = self.tabuleiro[i][j]
            if val == self.WHITE_SPACE:
                print("|      " , end="")
            else:
                if val >= 10:
                    print("|  %d  " % val, end="")
                else:
                    print("|   %d  " % val, end="")
        
        print("|")
    
      print("+------" * self.tamanho + "+")

   def condicao_vitoria(self)->bool:
      if self.tabuleiro[0][0] != self.WHITE_SPACE:
         return False
      
      cur_value: int = 1
      for i in range(self.tamanho):
         for j in range(self.tamanho):
             if i == 0 and j == 0:
                continue
             if self.tabuleiro[i][j] != cur_value:
                return False
             cur_value+=1
      return True
         
   def troca_posicoes(self, i1:int, j1:int, i2:int, j2:int)->bool:
      if any(x < 0 for x in[i1, j1, i2, j2]) or any(x >= self.tamanho for x in [i1, j1, i2, j2]):     
          return False

      tmp: int = self.tabuleiro[i1][j1]
      self.tabuleiro[i1][j1] = self.tabuleiro[i2][j2] 
      self.tabuleiro[i2][j2]  = tmp

      if  (i1,j1) == self.posicao_white_space:
           self.posicao_white_space = (i2,j2)
      elif (i2,j2) == self.posicao_white_space:
           self.posicao_white_space = (i1,j1)

      return True
