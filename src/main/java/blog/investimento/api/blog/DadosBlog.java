package blog.investimento.api.blog;


import jakarta.validation.constraints.NotBlank;

public record DadosBlog(


        Long id,

        @NotBlank
        String post,

        @NotBlank
        String comentario) {
}
