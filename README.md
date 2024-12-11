# Sistema de gerenciamento médico

Este projeto é uma API RESTful desenvolvida em Java Spring, para gerenciar médicos, pacientes e consultas. Ele fornece funcionalidades para cadastro, listagem, atualização e remoção de dados relacionados às entidades principais.

# 1. Requisitos:
- Java 17+;
- Maven 3.0+
- Banco de dados PostgreSQL 14+;
- IDE com suporte a projetos Spring Boot (IntelliJ, Eclipse, etc.);
- Plugin Lombok 1.18.36+;

# 2. Configuração
- O acesso ao banco de dados utiliza-se de variáveis de sistema, definidas em `application.properties` Desta forma, crie no seu sistema operacional as variáveis DB_USERNAME e DB_PASSWORD contendo o usuário e senha do PostgreSQL, ou se preferir adicione manualmente as credenciais neste mesmo arquivo.
- A porta que está configurada por padrão para acesso ao banco é a **5432**, caso o seu banco não esteja nela, realize a alteração no campo `spring.datasource.url=jdbc:postgresql://localhost:PORTA/gerenciamentomedico` do arquivo `application.properties`
- É necessário criar manualmente um banco de dados vazio com o nome de `gerenciamentomedico` para ser usado pela aplicação.

# 3. Executando o projeto:
- Antes de executar, certifique-se de que o banco `gerenciamentomedico` está criado, se não estiver, crie-o.
- Certifique-se de ter configurado as variáveis de acesso ao banco, ou inseridas manualmente no arquivo `application.properties`
- Realize o clone do repositório: `git clone https://github.com/raphdgg/gerenciamentomedico.git
  cd gerenciamentomedico`
- Realize a instalação das dependências: `mvn clean install`
- Execute a aplicação: `mvn spring-boot:run`
- Acesse a API em: `http://localhost:8080`


### Endpoints REST

- Acesse `http://localhost:8080/swagger-ui/index.html#/` para visualizar a documentação dos endpoints através da Swagger UI.

#### Abaixo está uma breve descrição de todos os endpoints:
##### Médicos
- `POST /medicos` - Realiza o cadastro de um novo médico;
- `GET /medicos` - Lista todos os médicos cadastrados;
- `GET /medicos/{id}` - Realiza a busca do cadastro do médico através do ID;
- `PUT /medicos/{id}` - Atualiza as informações do cadastro através do ID;
- `DELETE /medicos/{id}` - Desativa o cadastro de um médico através do ID.

##### Pacientes
- `POST /pacientes` - Realiza o cadastro de um novo paciente;
- `GET /pacientes` - Lista todos os pacientes cadastrados;
- `GET /pacientes/{id}` - Realiza a busca do cadastro do paciente através do ID;
- `PUT /pacientes/{id}` - Atualiza as informações do cadastro através do ID;
- `DELETE /pacientes/{id}` - Desativa o cadastro de um paciente através do ID.

##### Consultas
- `POST /consultas` - Realiza o cadastro de uma nova consulta;
- `GET /consultas` - Lista todas as consultas cadastradas;
- `GET /consultas/{id}` - Realiza a busca da consulta através do ID;
- `PUT /consultas/{id}` - Atualiza as informações da consulta através do ID;
- `PUT /consultas/concluir/{id}` - Conclui uma consulta através do ID;
- `DELETE /consultas/cancelar/{id}` - Cancela uma consulta através do ID.