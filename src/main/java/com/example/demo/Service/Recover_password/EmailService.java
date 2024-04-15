package com.example.demo.Service.Recover_password;

import com.example.demo.Model.Recover_password.PasswordResetToken;
import com.example.demo.Model.UsuarioEntity;
import com.example.demo.Repository.Recover_password.PasswordResetTokenRepository;
import com.example.demo.Service.RegistroExistenteException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordResetEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recuperación de contraseña");
        message.setText("Para restablecer su contraseña, haga clic en el siguiente enlace: ENLAZE DEL FRONT AQUI");
        javaMailSender.send(message);
    }


    public UsuarioEntity BuscarUsuario(String token){
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RegistroExistenteException("Token Expirado"));
        return passwordResetToken.getUser();
    }

    public boolean isPasswordResetTokenExpired(String token) {
        PasswordResetToken  passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RegistroExistenteException("Token Expirado"));
        if (passwordResetToken == null) {
            return true; // o puedes lanzar una excepción
        }
        LocalDateTime expiryDate = passwordResetToken.getExpiryDate();
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(expiryDate);
    }

    @Transactional
    public String Eliminartoken(String token)throws Exception{
        PasswordResetToken  passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RegistroExistenteException("Token Expirado"));
        Optional<PasswordResetToken> optional = passwordResetTokenRepository.findById(passwordResetToken.getId());
        if (optional.isPresent()){
            passwordResetTokenRepository.delete(optional.get());
            return ":-)";
        }
        return "Token no encontrado";
    }

}
