# Chat Sockets

Este é um projeto de chat em tempo real utilizando sockets. Ele é dividido em duas partes: **servidor** e **cliente**. O servidor gerencia as conexões e o cliente permite que os usuários interajam.

## Pré-requisitos

Certifique-se de ter instalado:

- [Java JDK 8+](https://www.oracle.com/java/technologies/javase-downloads.html)
- Um terminal ou prompt de comando configurado com o `javac` e `java` no PATH.

## Como executar o projeto

### Passo 1: Compile o projeto

1. Navegue até o diretório raiz do projeto no terminal:
   ```sh
   cd c:\Projetos\Pessoais\chat-sockets
   ```

2. Compile os arquivos do servidor e do cliente separadamente:

    - Para o servidor:
      ```sh
      javac -d server/bin server/src/*.java
      ```

    - Para o cliente:
      ```sh
      javac -d client/bin client/src/*.java
      ```

    - Isso compilará todos os arquivos `.java` e colocará os arquivos `.class` no diretório `bin`.

### Passo 2: Execute o servidor

1. No terminal, execute o servidor:
   ```sh
   java -cp server/bin Main
   ```

    - Certifique-se de que o servidor está rodando antes de iniciar o cliente.

### Passo 3: Execute o cliente

1. Em outro terminal, execute o cliente:
   ```sh
   java -cp client/bin Main
   ```

2. Siga as instruções no terminal para interagir com o chat.

### Comandos disponíveis no cliente

- `/send message <user> <message>`: Envia uma mensagem para um usuário.
- `/send file <user> <message>`: Envia um arquivo para um usuário (o arquivo deve estar na pasta `files/`).
- `/users`: Lista os usuários conectados.
- `/quit`: Sai da aplicação.
- `/help`: Lista os comandos disponíveis.

## Estrutura do projeto

```
chat-sockets/
├── server/
│   ├── src/          # Código-fonte do servidor
│   └── Main.java     # Classe principal do servidor
├── client/
│   ├── src/          # Código-fonte do cliente
│   └── Main.java     # Classe principal do cliente
├── bin/              # Arquivos compilados
└── README.md         # Documentação do projeto
```

## Observações

- Certifique-se de que a porta `8080` está disponível no seu computador.
- O cliente e o servidor devem ser executados na mesma máquina ou em máquinas que possam se comunicar pela rede.
- Caso haja necessidade de comunicar entre duas máquinas diferentes, a máquina que executará o client deverá alterar a informação do seridor dentro do arquivo fonte do client.

---
Desenvolvido para fins acadêmicos.