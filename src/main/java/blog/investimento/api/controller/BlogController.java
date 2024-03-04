package blog.investimento.api.controller;

import blog.investimento.api.blog.Blog;
import blog.investimento.api.blog.BlogRepository;
import blog.investimento.api.blog.DadosBlog;
import blog.investimento.api.blog.DadosListagemBlog;
import blog.investimento.api.usuario.Usuario;
import blog.investimento.api.usuario.UsuarioRepository;
import jakarta.transaction.Transaction;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogRepository repository;

    @Autowired
    private UsuarioRepository user_repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosBlog dados, UriComponentsBuilder uriBuilder){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        System.out.println(usuario.getId());

        var user = user_repository.getReferenceById(usuario.getId());

        var blog = new Blog(dados, user);
            repository.save(blog);

        var uri = uriBuilder.path("/blog/{id}").buildAndExpand(blog.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosListagemBlog(blog));
    }

    @GetMapping
    public ResponseEntity<List<DadosListagemBlog>> listar() {
        List<Blog> blogs = repository.findAll();
        List<DadosListagemBlog> dadosListagemBlogs = blogs.stream().map(DadosListagemBlog::new).collect(Collectors.toList());
        return ResponseEntity.ok(dadosListagemBlogs);
    }
    /*public ResponseEntity<Page<DadosListagemBlog>> listar(@PageableDefault(size = 10, sort = {"post"}) Pageable paginacao){

        //return repository.findAll().stream().map(DadosListagemBlog::new).toList();
        var page = repository.findAll(paginacao).map(DadosListagemBlog::new);
        return ResponseEntity.ok(page);
    }*/

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosBlog dados){
        var blog = repository.getReferenceById(dados.id());
        blog.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosListagemBlog(blog));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable Long id) {
        //var blog = repository.getReferenceById(id);
        //blog.excluir();
        Optional<Blog> post = repository.findById(id);
        var id_post = post.get().getUsuario().getId();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();
        var id_user = usuario.getId();

        if(id_post != id_user){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Você não tem acesso a este recurso.");
        }else{
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }

    }
    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity detalhar(@PathVariable Long id){
            var blog = repository.getReferenceById(id);
            return ResponseEntity.ok(new DadosListagemBlog(blog));
    }

}
