package java_folder.codigo_teste;
import java_folder.codigo_teste.Pessoa;

public class agenda {
    static final int capacidade = 100;

    private Pessoa[] pessoas;

    agenda(){
        this.pessoas = new Pessoa[capacidade];
    }
    
    public void armazenar_pessoa(Pessoa pessoa){
        for (int i = 0; i < capacidade; i++){
            if (this.pessoas[i] == null){
                this.pessoas[i] = pessoa;
                break;
            }
        }
    }

    public Pessoa achar_pessoas(String nome){
        for (int i = 0; i < capacidade; i++){
            if (this.pessoas[i].nome == nome){
                return this.pessoas[i];
            }
        }
        return null;
    }
    
}
