package com.cursojava.curso.controllers;

import com.cursojava.curso.dao.UsuarioDao;
import com.cursojava.curso.dao.UsuarioDaoImp;
import com.cursojava.curso.models.Usuario;
import com.cursojava.curso.utils.JWTUtils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable Long id){
        Usuario usuario = new Usuario();
        usuario.setID(id);
        usuario.setNombre("Enea");
        usuario.setApellido("Castro");
        usuario.setEmail("email@gmail.com");
        usuario.setTelefono("6000000");
        usuario.setPassword("Qwerty123");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization")String token){
        if (!validarToken(token)) {return null;}
        return usuarioDao.getUsuarios();
    }

    private boolean validarToken(String token){
        String usuarioID = jwtUtils.getKey(token);
        return usuarioID != null;
    }

    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody  Usuario usuario){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1, usuario.getPassword());
        usuario.setPassword(hash);

        usuarioDao.registrar(usuario);
    }

    @RequestMapping(value = "editar")
    public Usuario editar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Enea");
        usuario.setApellido("Castro");
        usuario.setEmail("email@gmail.com");
        usuario.setTelefono("6000000");
        usuario.setPassword("Qwerty123");
        return usuario;
    }

    @RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
    public void eliminar(@RequestHeader(value = "Authorization")String token,
                         @PathVariable Long id){
        if (!validarToken(token)) {return;}
        usuarioDao.eliminar(id);
    }

    @RequestMapping(value = "buscar")
    public Usuario buscar(){
        Usuario usuario = new Usuario();
        usuario.setNombre("Enea");
        usuario.setApellido("Castro");
        usuario.setEmail("email@gmail.com");
        usuario.setTelefono("6000000");
        usuario.setPassword("Qwerty123");
        return usuario;
    }

}
