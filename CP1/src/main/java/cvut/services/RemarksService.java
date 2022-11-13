package cvut.services;

import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Admin;
import cvut.model.Critique;
import cvut.model.CritiqueState;
import cvut.model.Remarks;
import cvut.repository.AdminRepository;
import cvut.repository.CritiqueRepository;
import cvut.repository.RemarksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class RemarksService {

    private RemarksRepository remarksRepository;
    private CritiqueRepository critiqueRepository;
    private AdminRepository adminRepository;

    private final static int REMARKS_MIN_TEXT_LENGTH = 5;
    private final static int REMARKS_MAX_TEXT_LENGTH = 5;

    @Autowired
    public RemarksService(RemarksRepository remarksRepository, CritiqueRepository critiqueRepository, AdminRepository adminRepository) {
        this.remarksRepository = remarksRepository;
        this.critiqueRepository = critiqueRepository;
        this.adminRepository = adminRepository;
    }


    @Transactional
    public void makeRemarksAndSave(@NonNull String text, @NonNull Long critiqueId, @NonNull Long adminId){

        if(text.length() > REMARKS_MAX_TEXT_LENGTH || text.length() < REMARKS_MIN_TEXT_LENGTH){
            throw new ValidationException("Invalid remarks text length. Max "
                    +REMARKS_MAX_TEXT_LENGTH+ " Min "+REMARKS_MIN_TEXT_LENGTH+
                    " but was " + text.length());
        }

        Critique critique = critiqueRepository.findById(critiqueId).orElseThrow(
                ()->new NotFoundException("Critique with id " + critiqueId + " does not found" )
        );

        Admin admin = adminRepository.findById(adminId).orElseThrow(
                ()->new NotFoundException("Admin with id " + adminId + " does not found")
        );

        Remarks remarks = new Remarks(admin, text, critique, new Date());
        critique.setCritiqueState(CritiqueState.SENT_FOR_CORRECTIONS);
        remarksRepository.save(remarks);
    }

    @Transactional
    public void update(@NonNull String text, @NonNull Long remarksId) {

        if(text.length() > REMARKS_MAX_TEXT_LENGTH || text.length() < REMARKS_MIN_TEXT_LENGTH){
            throw new ValidationException("Invalid remarks text length. Max "
                    +REMARKS_MAX_TEXT_LENGTH+ " Min "+REMARKS_MIN_TEXT_LENGTH+
                    " but was " + text.length());
        }

        Remarks remarks = remarksRepository.findById(remarksId).orElseThrow(
                () -> new NotFoundException("Remarks with id "+remarksId+" does not exist")
        );

        remarks.setText(text);
    }

    public void deleteById(@NonNull Long remarksId){
        Remarks remarks = remarksRepository.findById(remarksId).orElseThrow(
                () -> new NotFoundException("Remarks with id "+remarksId+" does not exist")
        );
        remarksRepository.deleteById(remarksId);
    }

    public List<Remarks> getAllByCritiqueId(@NonNull Long critiqueId){
        List<Remarks> allByCritique_id = remarksRepository.findAllByCritique_Id(critiqueId);
        if(allByCritique_id.isEmpty()){
            throw new NotFoundException("Remarks on critique with id "+critiqueId+" not found");
        }
        return allByCritique_id;
    }

    public List<Remarks> getAllByDate(@NonNull Date date){
        List<Remarks> allByRemarksMakeDay = remarksRepository.findAllByRemarksMakeDay(date);
        if(allByRemarksMakeDay.isEmpty()){
            throw new NotFoundException("Remarks maked in date "+date+" not found");
        }
        return allByRemarksMakeDay;
    }
}
