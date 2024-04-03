from tabuleiro import Tabuleiro


class Jogo:

   MOVIMENTOS_POSSIVEIS = "UDLR"

   tamanho_tabul:int
   tabuleiro: Tabuleiro

   def __init__(self, tamanho:int) -> None:
      self.tamanho_tabul = tamanho
      self.tabuleiro = Tabuleiro(tamanho)

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
            
             


jogo1 = Jogo(3)

jogo1.jogar()