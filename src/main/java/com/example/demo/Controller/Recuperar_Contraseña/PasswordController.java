package com.example.demo.Controller.Recuperar_Contraseña;

import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.UsuarioRepository;
import com.example.demo.Service.Recuperacion_Contraseña.EmailService;
import com.example.demo.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reset/password")
public class PasswordController {

    @Autowired
    private EmailService emailService;


    @Autowired
    private UsuarioService usuarioService;

//    @PostMapping
//    public ResponseEntity<?> resetPasswordRequest(@RequestParam String email) {
//        Integer user = usuarioService.BuscarCorreo(email);
//        if (user == null) {
//            return ResponseEntity.badRequest().body("Usuario no encontrado.");
//        }
//        usuarioService.createPasswordResetTokenForUser(user);
//
//        // Aquí enviar el correo electrónico con el token...
//        return ResponseEntity.ok("Se ha enviado un correo electrónico con instrucciones para restablecer la contraseña.");
//    }
}
