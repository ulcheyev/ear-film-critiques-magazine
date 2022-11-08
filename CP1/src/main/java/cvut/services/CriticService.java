package cvut.services;
import cvut.model.Critic;
import cvut.repository.CriticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class CriticService extends UserService{

    private final CriticRepository criticRepository;

    @Autowired
    public CriticService(CriticRepository criticRepository) {
        this.criticRepository = criticRepository;
    }

    @Transactional
    public Critic findById(Long id){
        return (Critic) criticRepository.findById(id).get();
    }

}
