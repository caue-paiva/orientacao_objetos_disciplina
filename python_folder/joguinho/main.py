from jogo import Jogo

def main(): 
   numeros_str:str = input() #le numeros da tela como string
   numeros_str = numeros_str.split()
   lista_numeros:list[int] = [int(x) for x in numeros_str] #transforma chars de numeros em inteiros
   comandos:str = input() #le comandos da tela


   jogo1 = Jogo(lista_numeros) #instancia um jogo com a lista de números
   jogo1.entra_comandos(comandos) #roda os comandos e ve se o estado final está certo

if __name__ == "__main__":
   main()