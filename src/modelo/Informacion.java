package modelo;

import java.io.Serializable;
import java.util.Objects;

public abstract class Informacion implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;

    public Informacion() {
    }

    public Informacion(long id) {
        setId(id); // reutiliza validación
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("El ID debe ser mayor a 0");
        }
        this.id = id;
    }

    // Buenas prácticas para entidades
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Informacion that)) return false;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Informacion{id=" + id + "}";
    }

    // 💡 Aprovechar que es abstracta
    public abstract String getDescripcion();
}