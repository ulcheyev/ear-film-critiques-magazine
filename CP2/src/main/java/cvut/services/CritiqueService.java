package cvut.services;

import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.Critique;
import cvut.model.CritiqueSearchCriteria;
import cvut.model.CritiqueState;
import cvut.repository.AdminRepository;
import cvut.repository.CriticRepository;
import cvut.model.CritiqueCriteriaRepository;
import cvut.repository.CritiqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CritiqueService {

    private final CritiqueRepository critiqueRepository;
    private final CritiqueCriteriaRepository critiqueCriteriaRepository;
    private static final int TEXT_LENGTH_MAX = 3000;
    private static final int TEXT_LENGTH_MIN = 300;
    private static final int TITLE_LENGTH_MAX = 150;
    private static final int TITLE_LENGTH_MIN = 15;

    @Autowired
    public CritiqueService(CritiqueRepository critiqueRepository, AdminRepository adminRepository, CriticRepository criticRepository, CritiqueCriteriaRepository critiqueCriteriaRepository) {
        this.critiqueRepository = critiqueRepository;
        this.critiqueCriteriaRepository = critiqueCriteriaRepository;
    }

    public Critique findById(Long id) {
        Optional<Critique> byId = critiqueRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ValidationException("Critique with id " + id + " does not exist");
        }
        return byId.get();
    }

    public List<Critique> getAll(CritiqueSearchCriteria critiqueSearchCriteria) {
        List<Critique> all = critiqueCriteriaRepository.findAllByFilters(critiqueSearchCriteria);
        if(all.isEmpty()){
            throw new ValidationException("Critiques with specified parameters does not exist");
        }
        return all;
    }

    public List<Critique> findByFilm(String filmName) {
        List<Critique> allByFilm_nameLike = critiqueRepository.findAllByFilm_NameLike(filmName);
        if(allByFilm_nameLike.isEmpty()){
            throw new NotFoundException("At the request \" "+filmName+" \" there was no critiques");
        }
        return allByFilm_nameLike;
    }

    public List<Critique> findByRating(double rating) {
        List<Critique> allByRating = critiqueRepository
                .findAllByRating(rating);
        if(allByRating.isEmpty()) {
            throw new NotFoundException("Critiques not found with rating " + rating);
        }
        return allByRating;
    }

    public List<Critique> findAllByCritiqueState(CritiqueState critiqueState){
        List<Critique> allByCritiqueState = critiqueRepository.findAllByCritiqueState(critiqueState);
        if(allByCritiqueState.isEmpty()){
            throw new NotFoundException("Critiques not found");
        }
        return allByCritiqueState;
    }


    public List<Critique> findByCritiqueOwnerId(Long id) {
        List<Critique> critiquesByCritiqueOwner_id = critiqueRepository.findCritiquesByCritiqueOwner_Id(id);
        if (critiquesByCritiqueOwner_id.isEmpty()) {
            throw new NotFoundException("Critique with critique owner id " + id + " does not exist");
        }
        return critiquesByCritiqueOwner_id;
    }

    public int findQuantityOfCritiquesByCriticId(Long id) {
        Optional<Integer> quantityOfCritiquesByCriticId = critiqueRepository.findQuantityOfCritiquesByCriticId(id);
        if (quantityOfCritiquesByCriticId.isEmpty()) {
            return 0;
        }
        return quantityOfCritiquesByCriticId.get();
    }

    public double findSumOfCritiquesByCriticId(Long id) {
        Optional<Double> sumOfCritiquesRatingByCriticId = critiqueRepository.findSumOfCritiquesRatingByCriticId(id);
        if (sumOfCritiquesRatingByCriticId.isEmpty()) {
            return 0;
        }
        return sumOfCritiquesRatingByCriticId.get();
    }

    public List<Critique> findByCriticsLastnameAndFirstname(String firstname, String lastname){
        List<Critique> allByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike
                = critiqueRepository.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike(lastname, firstname);
        if(allByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike.isEmpty()){
            throw  new NotFoundException("Critiques with specified critic not found ");
        }
        return allByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike;
    }

    public List<Critique> findByDateOfAcceptance(Date date){
        List<Critique> allByDateOfAcceptance = critiqueRepository.findAllByDateOfAcceptance(date);
        if(allByDateOfAcceptance.isEmpty()){
            throw new NotFoundException("Critiques with specified date not found ");
        }
        return allByDateOfAcceptance;
    }

    public List<Critique> getAllOrderByDateDesc(){
        List<Critique> byOrderByDateOfAcceptanceDesc = critiqueRepository.findByOrderByDateOfAcceptanceDesc();
        if(byOrderByDateOfAcceptanceDesc.isEmpty()){
            throw new NotFoundException("Critiques not found ");
        }
        return byOrderByDateOfAcceptanceDesc;
    }

    public List<Critique> getAllOrderByDateAsc(){
        List<Critique> byOrderByDateOfAcceptanceAsc = critiqueRepository.findByOrderByDateOfAcceptanceAsc();
        if(byOrderByDateOfAcceptanceAsc.isEmpty()){
            throw new  NotFoundException("Critiques not found ");
        }
        return byOrderByDateOfAcceptanceAsc;
    }

    @Transactional
    public void updateCritiqueState(@NonNull Long critiqueId, @NonNull CritiqueState critiqueState) {
        Critique crtq = critiqueRepository.findById(critiqueId)
                .orElseThrow(() -> new NotFoundException("Critique with specified id " + critiqueId + " does not exist"));

        crtq.setCritiqueState(critiqueState);
    }

    @Transactional
    public void updateCritique(@NonNull Long critiqueId, @NonNull Critique critique) {
        Critique toChange = critiqueRepository.findById(critiqueId).orElseThrow(
                () -> new NotFoundException("Critique with id " + critiqueId + " does not found")
        );
        String text = critique.getText();
        String title = critique.getTitle();

        if(title.length() > TITLE_LENGTH_MAX ||
                title.length() < TITLE_LENGTH_MIN) {
            throw new ValidationException("Max title length " +
                    TITLE_LENGTH_MAX + ", Min title length " + TITLE_LENGTH_MIN +
                    ", but was " + title.length());
        }

        if(text.length() > TEXT_LENGTH_MAX ||
                text.length() < TEXT_LENGTH_MIN ){
            throw new ValidationException( "Max text length " + TEXT_LENGTH_MAX + ", Min text length "+
                    TEXT_LENGTH_MIN + ", but was " + text.length());
        }

        if (!toChange.getText().equals(text)
                && text.length() > TEXT_LENGTH_MIN
                && text.length() < TEXT_LENGTH_MAX)
        {
            toChange.setText(text);
        }

        if(!toChange.getTitle().equals(title)
            && title.length() > TITLE_LENGTH_MIN
            && title.length() < TITLE_LENGTH_MAX)
        {
            toChange.setTitle(title);
        }
    }

    public void correctCritiqueAfterCreateRemarks(@NonNull Long critiqueId, String title, String text) {
        Critique critique = critiqueRepository.findById(critiqueId).orElseThrow(
                ()-> new NotFoundException("Critique with id "+critiqueId+" does not found")
        );
        if(critique.getCritiqueState() != CritiqueState.SENT_FOR_CORRECTIONS){
            throw new ValidationException("Critique does not have status" + CritiqueState.SENT_FOR_CORRECTIONS.name());
        }
        if(text != null && !critique.getText().equals(text)){
            critique.setText(text);
        }
        if(title != null && !critique.getTitle().equals(text)){
            critique.setTitle(title);
        }
        save(critique);
        critique.setCritiqueState(CritiqueState.CORRECTED);
    }

    public void save(Critique critique){


        if(critique.getText().length() > TEXT_LENGTH_MAX || critique.getText().length() < TEXT_LENGTH_MIN){
            throw new ValidationException("Text length must be less than 3000 symbols and greater than 300 symbols");
        }

        if(critique.getTitle().length() > TITLE_LENGTH_MAX || critique.getTitle().length() < TITLE_LENGTH_MIN){
            throw new ValidationException("Title length must be less than 150 symbols and greater than 15 symbols");
        }

        critiqueRepository.save(critique);
    }

    public void deleteById(Long id){
        if(!critiqueRepository.existsById(id)){
            throw new NotFoundException("Can not delete critique with id "+id+" . Critique does not exist.");
        }
        critiqueRepository.deleteById(id);

    }
}


