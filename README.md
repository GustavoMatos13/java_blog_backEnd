Sistema com Spring Boot Framework
Este é um sistema construído com o framework Spring Boot, contendo dois campos de postagem e comentário na classe Blog. Para rodar este sistema, siga as instruções abaixo:

Pré-requisitos
Instale um banco de dados. No meu caso, foi utilizado o PostgreSQL.
Utilize uma IDE para codificar e rodar o projeto em Java. No exemplo, foi utilizado o IntelliJ IDEA.
Configuração e Execução
Clone o repositório em seu ambiente local.
Aguarde o download das dependências Maven.
Configure o arquivo application.properties com as informações de conexão com seu banco de dados.
Utilize alguma ferramenta como o PostgreSQL para enviar a requisição de login e autenticar o JWT.
Envie a requisição para a URL http://localhost:8080/login com o seguinte corpo:
{
    "login": "seu_email@exemplo.com",
    "senha": "sua_senha"
}
Substitua o e-mail e senha pelas informações cadastradas na tabela de "Usuarios" do banco de dados. Lembre-se de salvar a senha com criptografia usando "BCryptPasswordEncoder" para evitar armazenar a senha visível no banco.
Após enviar a requisição, você receberá o código do JWT. Abra a aba "Authorization" e selecione "Bearer Token", coloque o código que foi devolvido.
Agora você pode enviar um POST para o banco de dados adicionando postagens com comentários através da URL http://localhost:8080/blog com o seguinte corpo na requisição:
{
    "post": "teste_post",
    "comentario": "comentarios"
}
Apenas o usuário que cadastrou as postagens tem permissão para deletá-las.
