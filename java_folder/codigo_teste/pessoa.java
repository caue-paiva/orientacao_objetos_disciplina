package java_folder.codigo_teste;

public class pessoa {

    static final int ano_atual = 2024;

    public String nome;
    int ano_nascimento;
    public float altura;
    public int idade;
    private int cpf;
    public int peso;


    //constructor em java é uma funcao com o nome da classe
    // this = self do python
    pessoa(int cpf, int idade, float altura, int ano_nascimento, String nome){
        this.cpf = cpf;
        this.idade = idade;
        this.nome = nome;
        this.altura = altura;
        this.idade = idade;
        this.ano_nascimento = ano_nascimento;
        this.idade = ano_atual - ano_nascimento;
    }

    //para utilizar a propia instancia no metodo
    


    
}

