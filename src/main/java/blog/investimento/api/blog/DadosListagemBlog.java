package blog.investimento.api.blog;

public record DadosListagemBlog(Long id, String post, String comentario) {

    public DadosListagemBlog(Blog dados){
        this(dados.getId(), dados.getPost(), dados.getComentario());
    }
}
