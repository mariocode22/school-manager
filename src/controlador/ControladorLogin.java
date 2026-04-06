package controlador;

import java.util.Map;

public class ControladorLogin {

    // 🔥 Simulación de base de datos en memoria
    private final Map<String, String> usuarios = Map.of(
            "Trabajo", "final",
            "admin", "1234"
    );

    public boolean login(String usuario, String contrasena) {

        if (usuario == null || contrasena == null) {
            return false;
        }

        String passGuardada = usuarios.get(usuario);

        return passGuardada != null && passGuardada.equals(contrasena);
    }
}