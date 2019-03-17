# CFIP - Controle Financeiro Pessoal

Projeto open source para controle financeiro desenvolvido com Java, Spring Boot, Hibernate, Spring Web - REST, JWT e Java Swing. 
Certifique-se de baixar a branch **cfip-v1**. 
Abaixo temos os 05 projetos que compõem a aplicação:
|Projeto         |Descrição                      |Grupo                        |
|----------------|-------------------------------|-----------------------------|
|cfip-api|Contém as classes de modelo, entidades, interfaces de serviços e classe            |`api, backend`            |
|cfip-core|Acesso a base de dados,classes **repository** e **controller**|`core, backend`            |
|cfip-web-api|Recursos **HTTP Rest**, **JWT** e **Swagger**|`web-api, backend`            |
|cfip-client-api|Client dos recursos Rest utilizando **Spring RestTemplate**|`client-api`
|cfip-desktop|Client JPA/HTTP **Java Desktop**|`client, frontend`
  
### Inicialização

- JPA: Configure as dependências do `pom.xml` do projeto `cfip-desktop` incluindo a dependência  `cfip-core` e ajuste o arquivo `application.properties` para o banco de sua referencia.
- REST: No projeto `cfip-web-api` ajuste o arquivo `application.properties` para o banco de sua referencia e no projeto `cfip-desktop` utilize a dependência `cfip-client-api`. **NOTA:** Antes de iniciar o projeto desktop, é necessário subir a aplicação cfip-web-api e se preferir ver a documentação em http://localhost:8080/swagger-ui.html

### Primeiro acesso
Via aplicação desktop crie um usuário para acessar o sistema desktop e se preferir fazer a autenticação no swagger, 
