package java_folder.codigo_teste;

import java_folder.codigo_teste.pessoa;

public class agenda {
    static final int capacidade = 100;

    private pessoa[] pessoas;

    agenda(){
        this.pessoas = new pessoa[capacidade];
    }
    
    public void armazenar_pessoa(pessoa pessoa){
        for (int i = 0; i < capacidade; i++){
            if (this.pessoas[i] == null){
                this.pessoas[i] = pessoa;
                break;
            }
        }
    }

    public pessoa achar_pessoas(String nome){
        for (int i = 0; i < capacidade; i++){
            if (this.pessoas[i].nome == nome){
                return this.pessoas[i];
            }
        }
        return null;
    }
    
}
