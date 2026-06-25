# API DO TOOLS JAVA CHALLENGE👋

O projeto tem como intuito disponibilizar os endpoints de acesso às funcionalidades do Tools Java Challenge.

Nas próximas seções serão apresentados os requisitos e o procedimento para realizar o setup da aplicação.

## Requisitos
Para montar o ambiente do projeto é necessário:
* Docker
* Docker Compose
* Java 26
* Maven

## Configuração do Back-End

O projeto trabalha com o gerenciado de dependências 'Maven'.

Depois da instalação das dependências é necessário subir o container docker.

Navegue até a pasta **containers** e execute o seguinte comando. Obs: e necessário ter o docker configurado corretamente.
    
    docker compose -f docker-compose-postgres.yaml  up -d --build

O comando acima irá iniciar um container na porta 5432. Para acessar o banco de dados, basta utilizar as seguintes credências:

    $ url: localhost
    $ port: 5432
    $ usuário: postgres
    $ senha: postgres
    $ banco: api-tools-challenge

Os demais detalhes de configurações fica a critério de cada IDE utilizada.

Para subir a aplicação precisamos configurar as seguintes variáveis:

As variáveis podem ser configurada no seu OS ou direto na IDE utilizada.

* DATABASE_HOST=localhost
* DATABASE_PORT=5432
* DATABASE_USERNAME=postgres
* DATABASE_PASSWORD=postgres
* DATABASE_NAME=api-tools-challenge

Agora basta subir a aplicação na IDE utilizada.

## A aplicação será executada na porta 8080.

## Para acessa a documentação da API basta acessar a seguinte URL.
    http://localhost:8080/swagger-ui/index.html#/

## A aplicação trabalha com internacionalização i18n. Segue exemplo de requisição.

linguagens aceitas :
* **padrão** - Português (src/main/resources/messages.properties)

  $ http://localhost:8080

## Plugins da aplicação
**SPOTLESS:**  Garante que todos os desenvolvedores sigam o mesmo padrão de indentação, chaves e espaçamento, eliminando discussões de estilo. Para utilizar basta rodar o comando abaixo.

    mvn spotless:apply

**Jacoco:** Mede a cobertura de código (code coverage) em projetos Java.

    mvn clean test
    http://localhost:63342/toolsChallenge/target/site/jacoco/index.html

## Rodando a aplicação fora da IDE
* Git clone https://github.com/seu-repo.git.
* Entrar na pasta do projeto. 

      ./mvnw spring-boot:run

## Arquitetura Futura e Escalabilidade
Como o critério de autorização não foi mapeado no enunciado, foi implementada uma regra de negócio onde transações com valor igual ou superior a R$ 1.000,00 são automaticamente salvas com o status 'NEGADO', e valores inferiores são 'AUTORIZADO'.

A critério de aprendizado foi criado um novo status 'REVERSED' que será utilizado validar o fluxo de estorno da transação.