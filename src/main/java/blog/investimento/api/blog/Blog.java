package blog.investimento.api.blog;
import blog.investimento.api.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "Blog")
@Entity(name = "Blog")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Blog {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String post;
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Blog(DadosBlog dados, Usuario user) {
        this.post = dados.post();
        this.comentario = dados.comentario();
        this.usuario = user;
    }

    public void atualizarInformacoes(DadosBlog dados){
        if(dados.post() != null) {
            this.post = dados.post();
        }
        if(dados.post() != null) {
            this.comentario = dados.comentario();
        }
    }
}
