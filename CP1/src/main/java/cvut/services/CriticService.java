package cvut.services;

import cvut.model.Critic;
import cvut.model.Critique;
import cvut.repository.CriticRepository;
import cvut.repository.CritiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CriticService extends UserService{

    @Autowired
    private CriticRepository criticRepository;

    @Transactional
    public Critic findById(Long id){
        return (Critic) criticRepository.findById(id).get();
    }


}
