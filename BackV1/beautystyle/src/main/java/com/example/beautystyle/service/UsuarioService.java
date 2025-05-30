package com.example.beautystyle.service;

import org.springframework.stereotype.Service;
import com.example.beautystyle.repository.UsuarioRepository;
import com.example.beautystyle.model.Usuario;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService{
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Usuario salvarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
}







/* 
package com.example.beautystyle.service; 
 
import org.springframework.stereotype.Service; 
import com.example.beautystyle.repository.UsuarioRepository; 
import com.example.beautystyle.model.Usuario; 
 
import java.util.List; 
import java.util.Optional; 
 
@Service 
public class UsuarioService{ 
    private final UsuarioRepository usuarioRepository; 
 
    public UsuarioService(UsuarioRepository usuarioRepository){ 
        this.usuarioRepository = usuarioRepository; 
    } 
 
    public List<Usuario> listarUsuarios(){ 
        return usuarioRepository.findAll(); 
    } 
 
    public Optional<Usuario> buscarPorId(Long id){ 
        return usuarioRepository.findById(id); 
 
    } 
 
    public Usuario salvarUsuario(Usuario usuario){ 
        return usuarioRepository.save(usuario); 
    } 
 
    public void deletarUsuario(Long id){ 
        usuarioRepository.deleteById(id); 
    } 
} 

*/