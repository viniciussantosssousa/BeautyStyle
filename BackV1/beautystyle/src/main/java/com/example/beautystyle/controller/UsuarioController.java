package com.example.beautystyle.controller;

import java.util.List;  
import org.springframework.http.ResponseEntity;  
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;  
import org.springframework.web.bind.annotation.RequestBody;   

import com.example.beautystyle.service.UsuarioService;
import com.example.beautystyle.model.Usuario;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController{
    
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listarUsuarios(){
        return usuarioService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id){
        return usuarioService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario criarUsuario(@RequestBody Usuario usuario){
        return usuarioService.salvarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}







/*package com.example.beautystyle.controller; 
 
import java.util.List;   
import org.springframework.http.ResponseEntity;   
import org.springframework.web.bind.annotation.RestController; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.DeleteMapping; 
import org.springframework.web.bind.annotation.PathVariable;   
import org.springframework.web.bind.annotation.RequestBody;    
 
import com.example.beautystyle.service.UsuarioService; 
import com.example.beautystyle.model.Usuario; 
 
@RestController 
@RequestMapping("/api/usuarios") 
public class UsuarioController{ 
     
    private final UsuarioService usuarioService; 
 
 
    public UsuarioController(UsuarioService usuarioService){ 
        this.usuarioService = usuarioService; 
    } 
 
    @GetMapping 
    public List<Usuario> listarUsuarios(){ 
        return usuarioService.listarUsuarios(); 
    } 
 
    @GetMapping("/{id}") 
    public ResponseEntity<Usuario> buscarUsuario(@PathVariable Long id){ 
        return usuarioService.buscarPorId(id) 
            .map(ResponseEntity::ok) 
            .orElse(ResponseEntity.notFound().build()); 
    } 
 
    @PostMapping 
    public Usuario criarUsuario(@RequestBody Usuario usuario){ 
        return usuarioService.salvarUsuario(usuario); 
    } 
 
    @DeleteMapping("/{id}") 
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id){ 
        usuarioService.deletarUsuario(id); 
        return ResponseEntity.noContent().build(); 
    } 
} 

*/