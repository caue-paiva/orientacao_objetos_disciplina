import random

class Tabuleiro:

   WHITE_SPACE = 0

   tabuleiro: list[list[int]]
   posicao_white_space: dict[str,int]
   tamanho: int

   def __init__(self, tamanho:int) -> None:
      self.tamanho = tamanho
      self.__criar_tabuleiro(tamanho)

   def __criar_tabuleiro(self,tamanho:int)-> None:     
      lista_num: list[int] = [i for i in range(tamanho*tamanho)] #cria a lista de numeros de 0 até n-1
      random.shuffle(lista_num) #embaralha os items da lista
      index_whitespace:int = lista_num.index(0) #acha o index de 0
      lista_num[index_whitespace] = self.WHITE_SPACE #zero vai virar -1/whitespace
      
      #dividir a posicao no array do index do -1 pelo tamanho do array vai dar a linha do whitespace
      #fazer o mod da posicao no array do index do -1 pelo tamanho do array vai dar a coluna do whitespace
      self.posicao_white_space= [int(index_whitespace/self.tamanho) ,  index_whitespace%self.tamanho]
     
      self.tabuleiro = [ lista_num[(i*self.tamanho) : (i*self.tamanho) + self.tamanho]  for i in range(tamanho)   ] #lista comprehension num slice do vetor para gerar o tabuleiro

   def print_tabuleiro(self)->None:
      for i in range(self.tamanho):
            print("+---" * self.tamanho + "+")
            
            for j in range(self.tamanho):
               print("|", end="")
               val = self.tabuleiro[i][j]
               print(f"{val:3d}", end="")
            
            print("|")
         
      print("+---" * self.tamanho + "+")

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
         
   def troca_posicoes(self, i1:int, j1:int, i2:int, j2:int)->bool:
      if any(x < 0 for x in[i1, j1, i2, j2]) or any(x >= self.tamanho for x in [i1, j1, i2, j2]):     
          return False

      tmp: int = self.tabuleiro[i1][j1]
      self.tabuleiro[i1][j1] = self.tabuleiro[i2][j2] 
      self.tabuleiro[i2][j2]  = tmp

      if   [i1,j1] == self.posicao_white_space:
           self.posicao_white_space = [i2,j2]
      elif [i2,j2] == self.posicao_white_space:
           self.posicao_white_space = [i1,j1]

      return True
