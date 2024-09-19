package br.edu.ifg.luziania.model.log;

import br.edu.ifg.luziania.model.entity.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@ApplicationScoped
public class LogService {

    @Inject
    LogRepository logRepository;

    public List<Log> findAllByUsuarioId(Long usuarioId) {
        return logRepository.findAllByUsuarioId(usuarioId);
    }

    public List<Log> findAllByType(LogType type) {
        return logRepository.findAllByType(type);
    }

    @Transactional
    public void registerLog(Long usuarioId, LogType type, String description) {
        Log log = new Log();
        log.setUsuarioId(usuarioId);  // Passando o ID do usuário autenticado
        log.setType(type);
        log.setMoment(ZonedDateTime.now());
        log.setDescription(description);
        logRepository.persist(log);
    }
}
