package br.edu.ifg.luziania.model.log;

import br.edu.ifg.luziania.model.entity.Log;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class LogRepository implements PanacheRepository<Log> {

    public List<Log> findAllByUsuarioId(Long usuarioId) {
        return find("usuarioId", usuarioId).list();
    }

    public List<Log> findAllByType(LogType type) {
        return find("type", type).list();
    }
}