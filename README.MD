# Livraria online

## Instalação

1. Modifique o arquivo WEB-INF/classes/Database.java inserindo as informações de acesso ao banco de dados.
2. Opcional: Adicione os dados de exemplo contidos no arquivo WEB-INF/dados_exemplo.sql no servidor SQL
3. Coloque a pasta do projeto no diretório webapps do Apache Tomcat
4. Compile os arquivos do java com o script build.sh, o mesmo deve ser executado da pasta raiz do projeto
5. Reinicie o servidor java
6. Abra o link http://localhost:8080/livraria

## Arquivos de Log
1. Todo o registro do sistema é enviado par ao stdout e stderr do servidor, no caso do Tomcat, esse é o arquivo logs/catalina.out
