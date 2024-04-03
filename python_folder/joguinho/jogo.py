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
   
   def entra_comandos(self,comandos:str)->None:
      comandos:str = comandos.upper() #coloca os comandos em UPPERCASE
      self.tabuleiro.print_tabuleiro()
      print()
      for comando in comandos:
         self.__realizar_movimento(comando)
         self.tabuleiro.print_tabuleiro()
         print()
      
      if self.tabuleiro.condicao_vitoria():
         print("Posicao final: True")
      else:
         print("Posicao final: False")
      
   
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
            
             
numeros_str:str = input() #le numeros da tela como string
numeros_str = numeros_str.replace(" ","") #tira os espaços da string
lista_numeros:list[int] = [int(x) for x in numeros_str] #transforma chars de numeros em inteiros

comandos:str = input() #le comandos da tela


jogo1 = Jogo(lista_numeros) #instancia um jogo com a lista de números
jogo1.entra_comandos(comandos) #roda os comandos e ve se o estado final está certo