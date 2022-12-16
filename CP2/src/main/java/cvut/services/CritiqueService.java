package cvut.services;

import cvut.model.dto.CritiqueDTO;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.repository.CritiqueSearchCriteria;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface CritiqueService {
    Critique findById(Long id);
    List<Critique> findAll();
    List<Critique> findByCriteria(CritiqueSearchCriteria critiqueSearchCriteria);
    List<Critique> findByFilmName(String filmName);
    List<Critique> findByRating(double rating);
    List<Critique> findAllByCritiqueState(CritiqueState critiqueState);
    List<Critique> findByCritiqueOwnerId(Long id);
    int findQuantityOfCritiquesByCriticId(Long id);
    double findSumOfCritiquesByCriticId(Long id);
    List<Critique> findByCriticsLastnameAndFirstname(String firstname, String lastname);
    List<Critique> findByDateOfAcceptance(Date date);
    List<Critique> findAllOrderByDateDesc();
    void updateCritiqueState(Long critiqueId, CritiqueState critiqueState);
    void updateCritique(Long critiqueId, CritiqueDTO critiqueDTO);
    String readPdf(MultipartFile multipartFile) throws IOException;
    void correctCritiqueAfterCreateRemarks(Long critiqueId, String title, String text);
    void save(Critique critique);
    Critique save(CritiqueDTO critique);
    void deleteById(Long id);
}
