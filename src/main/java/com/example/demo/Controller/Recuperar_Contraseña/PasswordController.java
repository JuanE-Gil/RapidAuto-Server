package com.example.demo.Controller.Recuperar_Contraseña;

import com.example.demo.DTO.ResetPassword;
import com.example.demo.Model.Recuperar_Contraseña.PasswordResetToken;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.UsuarioRepository;
import com.example.demo.Service.Recuperacion_Contraseña.EmailService;
import com.example.demo.Service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/reset/password")
public class PasswordController {

    @Autowired
    private EmailService emailService;


    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;
    @PostMapping
    public ResponseEntity<?> resetPasswordRequest(@RequestParam String email) {
        Integer user = usuarioService.BuscarCorreo(email);
        if (user == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }
        // Aquí enviar el correo electrónico con el token...
        return ResponseEntity.ok("Token : " + usuarioService.createPasswordResetTokenForUser(user, email));
    }

    @PostMapping("/new")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPassword request) throws Exception {
        String token = request.getToken();
        String newPassword = request.getPassword();
        UsuarioEntity idusuario = emailService.BuscarUsuario(token);
        Integer id = idusuario.getId_usuario();
        if (token == null){
            return ResponseEntity.badRequest().body("Falta token");
        }else {
            if (emailService.isPasswordResetTokenExpired(token) == false){
                usuarioService.resetpassword(id,passwordEncoder.encode(newPassword));
                emailService.Eliminartoken(token);
                return ResponseEntity.ok("Contraseña restablecida con éxito.");
            }
        }

        return ResponseEntity.badRequest().body("Token Expirado");
    }
}



