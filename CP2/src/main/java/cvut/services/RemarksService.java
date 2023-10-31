package cvut.services;

import cvut.model.Remarks;

import java.util.Date;
import java.util.List;

public interface RemarksService {
    Remarks findById(Long remarkId);

    void makeRemarksAndSave(String text, Long critiqueId, String adminUsername);

    void update(String text, Long remarksId);

    void deleteById(Long remarksId);

    List<Remarks> findAllByCritiqueId(Long critiqueId);

    List<Remarks> findAllByDate(Date date);

    List<Remarks> findAll();
}
