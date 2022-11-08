package cvut.services;

import cvut.model.Critique;
import cvut.repository.CritiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CritiqueService {

    private final CritiqueRepository critiqueRepository;

    @Autowired
    public CritiqueService(CritiqueRepository critiqueRepository) {
        this.critiqueRepository = critiqueRepository;
    }

    @Transactional
    public Critique findById(Long id){return critiqueRepository.findById(id).get();}

    @Transactional
    public List<Critique> findByCritiqueOwnerId(Long id){
        return critiqueRepository.findAllByCritiqueOwner_Id(id);
    }

    @Transactional
    public int findQuantityOfCritiquesByCriticId(Long id){return critiqueRepository.findQuantityOfCritiquesByCriticId(id);}

    @Transactional
    public double findSumOfCritiquesByCriticId(Long id){return critiqueRepository.findSumOfCritiquesRatingByCriticId(id);}

}
