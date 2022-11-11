package cvut.services;

import cvut.config.exception.NotFoundException;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.repository.AdminRepository;
import cvut.repository.CriticRepository;
import cvut.repository.CritiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CritiqueService {

    private final CritiqueRepository critiqueRepository;
    private final AdminRepository adminRepository;
    private final CriticRepository criticRepository;

    @Autowired
    public CritiqueService(CritiqueRepository critiqueRepository, AdminRepository adminRepository, CriticRepository criticRepository) {
        this.critiqueRepository = critiqueRepository;
        this.adminRepository = adminRepository;
        this.criticRepository = criticRepository;
    }


    @Transactional
    public void updateCritiqueState(@NonNull Long critiqueId, @NonNull CritiqueState critiqueState){
        Critique critique = critiqueRepository.findById(critiqueId).get();
        if(!critique.getId().equals(critiqueId)){
            throw new NotFoundException("Critique with specified id "+critiqueId+"does not exist");
        }
        critique.setCritiqueState(critiqueState);
        critiqueRepository.save(critique);
    }

    @Transactional
    public Critique findById(Long id){return critiqueRepository.findById(id).get();}

    @Transactional
    public List<Critique> findByCritiqueOwnerId(Long id){
        return critiqueRepository.findCritiquesByCritiqueOwner_Id(id);
    }

    @Transactional
    public int findQuantityOfCritiquesByCriticId(Long id){return critiqueRepository.findQuantityOfCritiquesByCriticId(id);}

    @Transactional
    public double findSumOfCritiquesByCriticId(Long id){return critiqueRepository.findSumOfCritiquesRatingByCriticId(id);}


}
