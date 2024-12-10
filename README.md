# Sistema de gerenciamento médico

Este projeto é uma API RESTful desenvolvida em Java Spring, com o objetivo de gerenciar médicos, pacientes e consultas. Ele fornece funcionalidades para cadastro, listagem, atualização e remoção de dados relacionados às entidades principais.

# 1. Requisitos:
- Java 17+;
- Maven 3.0+
- Banco de dados PostgreSQL 14+;
- IDE com suporte a projetos Spring Boot (IntelliJ, Eclipse, etc.);
- Plugin Lombok 1.18.36+;

# 2. Configuração
- Para acessar o banco de dados, foi configurado no application.properties para utilizar as variáveis do sistema, portando crie no seu sistema operacional as variáveis __DB_USERNAME__ e __DB_PASSWORD__ contendo o usuário e senha do PostgreSQL, respectivamente. Ou caso prefirir, basta sustituir tais variáveis no corpo do arquivo `application.properties` pelas credenciais de acesso;
- A porta que está configurada por padrão para acesso ao banco é a **5432**, caso o seu banco não esteja nela, realize a alteração no campo `spring.datasource.url=` do arquivo `application.properties`
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

#### Médicos
- `POST /medicos` - Realiza o cadastro de um novo médico;
```json
{
  "nome": "Nome Completo",
  "crm": "000000-UF",
  "especialidade": "Especialidade",
  "email": "exemplo@email.com"
} 
```
- `GET /medicos` - Lista todos os médicos cadastrados;
- `GET /medicos/{id}` - Realiza a busca do cadastro do médico através do ID;
- `PUT /medicos/{id}` - Atualiza as informações do cadastro através do ID;
```json
  {
  "nome": "",
  "crm": "",
  "especialidade": "",
  "email": ""
  }
```
- `DELETE /medicos/{id}` - Desativa o cadastro de um médico através do ID.

#### Pacientes
- `POST /pacientes` - Realiza o cadastro de um novo paciente;
```json
{
"nome": "Nome Completo",
"dataDeNascimento": "dd-mm-aaaa",
"contato": "00 00000000",
"cpf": "000.000.000-00"
}
```
- `GET /pacientes` - Lista todos os pacientes cadastrados;
- `GET /pacientes/{id}` - Realiza a busca do cadastro do paciente através do ID;
- `PUT /pacientes/{id}` - Atualiza as informações do cadastro através do ID;
```json
{
"nome": "",
"dataDeNascimento": "",
"contato": "",
"cpf": ""
}
```
- `DELETE /pacientes/{id}` - Desativa o cadastro de um paciente através do ID.

#### Consultas
- `POST /consultas` - Realiza o cadastro de uma nova consulta;
```json
  {
  "pacienteId": "UUID",
  "medicoId": "UUID",
  "dataHoraConsulta": "dd-mm-aaaa hh:mm"
  }
```
- `GET /consultas` - Lista todas as consultas cadastradas;
- `GET /consultas/{id}` - Realiza a busca da consulta através do ID;
- `PUT /consultas/{id}` - Atualiza as informações da consulta através do ID;
```json
  {
  "pacienteId": "cb3c6818-43f6-4b7c-80fb-1d6228c752ca",
  "medicoId": "",
  "dataHoraConsulta": "26-12-2024 10:25"
  }
```
- `PUT /consultas/concluir/{id}` - Conclui uma consulta através do ID;
- `DELETE /consultas/cancelar/{id}` - Cancela uma consulta através do ID.