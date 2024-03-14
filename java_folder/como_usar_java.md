

# Criar .java

1) Colocar no topo do código: package package {nome_do_diretório};

2) Criar uma classe main com um método public static void main:


```java
class main {

    public static void main(String[] args) {
        System.out.println("teste1"); 
    }

}
```

3) Toda vez que um arquivo classe é compilado o método main é executado, então é nele que a lógica do código deve estar 


# compilar java:

1) Ir para o diretório superior ao diretório do seu package 

2) rodar o compilador:

```bash
javac package_folder/file.java
```

3) Isso vai criar um arquivo com o nome da sua Classe e .class
ex: main.class


# Rodar Java::

Também do diretório superior ao diretório do seu package 

1) Rodar o código java:
```bash
java package_folder.file

```

OBS: entre package folder e o arquivo .class vc usa um . e não um /