from tabuleiro import Tabuleiro
import math

class Jogo:

   MOVIMENTOS_POSSIVEIS = "UDLR"

   tamanho_tabul:int
   tabuleiro: Tabuleiro

   def __init__(self, tamanho_ou_lista:int|list[int]) -> None:
       #overloading the constructors no python pode ser implementado checando o tipo do argumento
      if isinstance(tamanho_ou_lista,int): # se ele for um inteiro (tamanho do tabuleiro)    
         self.tamanho_tabul = tamanho_ou_lista 
         self.tabuleiro = Tabuleiro(tamanho_ou_lista) #cria tabuleiro com apenas o tamanho de argumento do constructor
      elif isinstance(tamanho_ou_lista, list): #se ele for uma lista de numeros
         len_lista:int = len(tamanho_ou_lista)
         self.tamanho_tabul = int(math.sqrt(len_lista))
         self.tabuleiro = Tabuleiro(tamanho_ou_lista) #cria tabuleiro com uma lista de argumento do constructor
      print(self.tamanho_tabul)

   def jogar(self)->None:
      print("Vamos comecar o jogo!!")

      while(not self.tabuleiro.condicao_vitoria()):
          self.__rodada()
      
      print("Parabéns, voce venceu")
   
   def __rodada(self)->None:
      self.tabuleiro.print_tabuleiro()
      movimento: str = self.__user_input()
      if not movimento:
         return
      self.__realizar_movimento(movimento)
      
   def __user_input(self)->str:
      movimento:str = input("Qual o proximo movimento? U/D/L/R ?: ").upper()
      if movimento not in self.MOVIMENTOS_POSSIVEIS:
         print("Movimento invalido")
         return ""
      return movimento
   
   def __realizar_movimento(self,movimento:str)->None:
      linha_whitespace:int = self.tabuleiro.posicao_white_space[0]
      coluna_whitespace:int = self.tabuleiro.posicao_white_space[1]
      #print(f"linha {linha_whitespace} , coluna {coluna_whitespace}")

      match(movimento):
         case "U":
           self.tabuleiro.troca_posicoes(linha_whitespace,coluna_whitespace,linha_whitespace+1,coluna_whitespace)
         case "D":
            self.tabuleiro.troca_posicoes(linha_whitespace,coluna_whitespace,linha_whitespace-1,coluna_whitespace)
         case "R":
            self.tabuleiro.troca_posicoes(linha_whitespace,coluna_whitespace,linha_whitespace,coluna_whitespace-1)
         case "L":
            self.tabuleiro.troca_posicoes(linha_whitespace,coluna_whitespace,linha_whitespace,coluna_whitespace+1)
            
             


jogo1 = Jogo([1 ,4 ,15, 14 ,10 ,2 ,12, 3, 0, 5, 8, 6, 9, 7, 11, 13])

jogo1.jogar()