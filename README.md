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
|cfip-desktop|Client JPA/REST **Java Desktop**|`client, frontend`
  
### Inicialização

- O CFIP é configurado por interface e profiles, assim você decide se vai usar a versão Core (JPA) ou Client-api(REST).
A executar a classe ` open.digytal.CfipDesktopApp` será exibida uma tela de configuração para você usar o HSQL(LOCAL) ou API(REST).
Quando selecionar a opção no diretório root do projeto será criado o arquivo `application.properties` contendo as devidas configurações da aplicação.
**NOTA:Esta versão contém a criptografia Jasypt**

- WEB SERVER: No projeto `cfip-web-api` ajuste o arquivo `application.properties` para o banco de sua preferencia
**NOTA:** Antes de iniciar o projeto desktop em profile API, é necessário subir a aplicação cfip-web-api e se preferir ver a documentação em http://localhost:8080/swagger-ui.html

### Primeiro acesso
Via aplicação desktop crie um usuário para acessar o sistema desktop e se preferir fazer a autenticação no swagger, 

![Desktop](https://github.com/digytal/cfip/blob/develop/documentacao/print/CFIP.JPG)
![Swagger](https://github.com/digytal/cfip/blob/develop/documentacao/print/Swagger.JPG)
