package br.edu.ifg.luziania.model.entity;

import br.edu.ifg.luziania.model.log.LogType;
import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long usuarioId;  // Aqui usamos o ID do usuário autenticado

    @Enumerated(EnumType.STRING)
    @Column
    private LogType type;

    private ZonedDateTime moment;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
        this.type = type;
    }

    public ZonedDateTime getMoment() {
        return moment;
    }

    public void setMoment(ZonedDateTime moment) {
        this.moment = moment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", userId='" + usuarioId + '\'' +
                ", type=" + type +
                ", moment=" + moment +
                ", description='" + description + '\'' +
                '}';
    }

}