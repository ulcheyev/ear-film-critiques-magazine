package cvut.services;

import cvut.config.security_utils.AuthenticationFacade;
import cvut.model.dto.CritiqueCreationDTO;
import cvut.exception.BadRequestException;
import cvut.exception.NotFoundException;
import cvut.exception.ValidationException;
import cvut.model.*;
import cvut.model.dto.CritiqueDTO;
import cvut.repository.CritiqueCriteriaRepository;
import cvut.repository.CritiqueRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CritiqueServiceImpl implements CritiqueService{

    private final CritiqueRepository critiqueRepository;
    private final CritiqueCriteriaRepository critiqueCriteriaRepository;
    private final FilmService filmService;
    private final AppUserService appUserService;
    private final AuthenticationFacade authenticationFacade;

    private static final int TEXT_LENGTH_MAX = 3000;
    private static final int TEXT_LENGTH_MIN = 300;
    private static final int TITLE_LENGTH_MAX = 150;
    private static final int TITLE_LENGTH_MIN = 15;
    private static final int FILE_MAX_SIZE = 512000;

    public Critique findById(@NonNull Long id) {
        Optional<Critique> byId = critiqueRepository.findById(id);
        if (byId.isEmpty()) {
            throw new ValidationException("Critique with id " + id + " does not exist");
        }
        return byId.get();
    }

    public List<Critique> findAll() {
        List<Critique> all = critiqueRepository.findAll();
        if(all.isEmpty()){
            throw new ValidationException("There are no critiques");
        }
        return all;
    }

    public List<Critique> findByCriteria(@NonNull CritiqueDTO critiqueDTO) {
        if(critiqueDTO.requestIsEmpty()){
            throw new BadRequestException("Critique does not have specified parameters");
        }
        List<Critique> all = critiqueCriteriaRepository.findAllByFilters(critiqueDTO);
        if(all.isEmpty()){
            throw new ValidationException("Critiques with specified parameters does not exist");
        }
        return all;
    }

    public List<Critique> findByFilmName(@NonNull String filmName) {
        List<Critique> allByFilm_nameLike = critiqueRepository.findAllByFilm_NameLike(filmName);
        if(allByFilm_nameLike.isEmpty()){
            throw new NotFoundException("At the request \" "+filmName+" \" there was no critiques");
        }
        return allByFilm_nameLike;
    }

    public List<Critique> findByRating(@NonNull double rating) {
        List<Critique> allByRating = critiqueRepository
                .findAllByRating(rating);
        if(allByRating.isEmpty()) {
            throw new NotFoundException("Critiques not found with rating " + rating);
        }
        return allByRating;
    }

    public List<Critique> findAllByCritiqueState(@NonNull CritiqueState critiqueState){
        List<Critique> allByCritiqueState = critiqueRepository.findAllByCritiqueState(critiqueState);
        if(allByCritiqueState.isEmpty()){
            throw new NotFoundException("Critiques not found");
        }
        return allByCritiqueState;
    }


    public List<Critique> findByCritiqueOwnerId(@NonNull Long id) {
        List<Critique> critiquesByCritiqueOwner_id = critiqueRepository.findCritiquesByCritiqueOwner_Id(id);
        if (critiquesByCritiqueOwner_id.isEmpty()) {
            throw new NotFoundException("Critique with critique owner id " + id + " does not exist");
        }
        return critiquesByCritiqueOwner_id;
    }

    public List<Critique> findByCritiqueOwnerUsername(@NonNull String username) {
        List<Critique> critiquesByCritiqueOwnerUsername= critiqueRepository.findAllByCritiqueOwnerUsername(username);
        if (critiquesByCritiqueOwnerUsername.isEmpty()) {
            throw new NotFoundException("Critique with critique owner username " + username + " does not exist");
        }
        return critiquesByCritiqueOwnerUsername;
    }

    public int findQuantityOfCritiquesByCriticId(@NonNull Long id) {
        Optional<Integer> quantityOfCritiquesByCriticId = critiqueRepository.findQuantityOfCritiquesByCriticId(id);
        if (quantityOfCritiquesByCriticId.isEmpty()) {
            return 0;
        }
        return quantityOfCritiquesByCriticId.get();
    }

    public double findSumOfCritiquesByCriticId(@NonNull Long id) {
        Optional<Double> sumOfCritiquesRatingByCriticId = critiqueRepository.findSumOfCritiquesRatingByCriticId(id);
        if (sumOfCritiquesRatingByCriticId.isEmpty()) {
            return 0;
        }
        return sumOfCritiquesRatingByCriticId.get();
    }

    public List<Critique> findByCriticsLastnameAndFirstname(@NonNull String firstname, @NonNull String lastname){
        List<Critique> allByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike
                = critiqueRepository.findAllByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike(lastname, firstname);
        if(allByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike.isEmpty()){
            throw  new NotFoundException("Critiques with specified critic not found ");
        }
        return allByCritiqueOwnerLastnameAndCritiqueOwnerFirstnameLike;
    }

    public List<Critique> findByDateOfAcceptance(@NonNull Date date){
        List<Critique> allByDateOfAcceptance = critiqueRepository.findAllByDateOfAcceptance(date);
        if(allByDateOfAcceptance.isEmpty()){
            throw new NotFoundException("Critiques with specified date not found ");
        }
        return allByDateOfAcceptance;
    }

    public List<Critique> findAllOrderByDateDesc(){
        List<Critique> byOrderByDateOfAcceptanceDesc = critiqueRepository.findByOrderByDateOfAcceptanceDesc();
        if(byOrderByDateOfAcceptanceDesc.isEmpty()){
            throw new NotFoundException("Critiques not found ");
        }
        return byOrderByDateOfAcceptanceDesc;
    }

    public List<Critique>findAllOrderByDateAsc(){
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
    public void updateCritique(@NonNull Long critiqueId, @NonNull CritiqueCreationDTO critiqueCreationDTO) {
        Critique toChange = critiqueRepository.findById(critiqueId).orElseThrow(
                () -> new NotFoundException("Critique with id " + critiqueId + " does not found")
        );

        CritiqueState critiqueState = toChange.getCritiqueState();

        if(!(critiqueState.equals(CritiqueState.SENT_FOR_CORRECTIONS)||
                critiqueState.equals(CritiqueState.IN_PROCESSED)))
        {
            throw new ValidationException("Critique does not have status" + CritiqueState.SENT_FOR_CORRECTIONS.name());
        }

        String text = critiqueCreationDTO.getText();
        String title = critiqueCreationDTO.getTitle();

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


    public List<Critique> findAllToProcess(){
        List<Critique> resInPr = findAllByCritiqueState(CritiqueState.IN_PROCESSED);
        List<Critique> resCor = findAllByCritiqueState(CritiqueState.CORRECTED);
        Comparator<Critique> cmp = Comparator.comparingDouble(obj -> obj.getCritiqueOwner().getCriticRating());
        resInPr.sort(cmp);
        resCor.sort(cmp);
        resCor.addAll(resInPr);
        return resCor;
    }

    public void save(@NonNull Critique critique){
        if(critique.getText().length() > TEXT_LENGTH_MAX || critique.getText().length() < TEXT_LENGTH_MIN){
            throw new ValidationException("Text length must be less than 3000 symbols and greater than 300 symbols");
        }

        if(critique.getTitle().length() > TITLE_LENGTH_MAX || critique.getTitle().length() < TITLE_LENGTH_MIN){
            throw new ValidationException("Title length must be less than 150 symbols and greater than 15 symbols");
        }

        critiqueRepository.save(critique);
    }

    public Critique save(@NonNull CritiqueCreationDTO critiqueCreationDTO){
        if(!critiqueCreationDTO.fieldsAreNotEmpty()){
            throw new ValidationException("Please, fill all fields");
        }
        Film film = filmService.findById(critiqueCreationDTO.getFilmId());
        Critic critic = (Critic) appUserService.findByUsername(authenticationFacade.getAuthentication().getName());
        Critique critique = new Critique(critiqueCreationDTO.getTitle(), critiqueCreationDTO.getText(), film, critic);
        critiqueRepository.save(critique);
        return critique;
    }

    public void deleteById(@NonNull Long id){
        if(!critiqueRepository.existsById(id)){
            throw new NotFoundException("Can not delete critique with id "+id+" . Critique does not exist.");
        }
        critiqueRepository.deleteById(id);

    }

    public boolean checkOwner(@NonNull Long critiqueId, @NonNull String username){
        return findById(critiqueId).getCritiqueOwner().getUsername().equals(username);
    }

    public String readPdf(MultipartFile multipartFile) throws IOException {

        if(multipartFile.getSize() > FILE_MAX_SIZE ){
            throw new ValidationException("File is too big. Max size "+ FILE_MAX_SIZE/1024 + "Kb");
        }

        //transfer
        Path path = Paths.get("files/"+multipartFile.getOriginalFilename());
        multipartFile.transferTo(path);

        //open
        File file = new File("files/"+multipartFile.getOriginalFilename());
        if(!file.exists()){
            throw new IOException("Invalid path to file");
        }
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        return pdfTextStripper.getText(document);
    }



    public boolean isAcceptedOrInProcessed(Long critiqueId){
        Critique critique = findById(critiqueId);
        return critique.getCritiqueState() == CritiqueState.IN_PROCESSED ||
                critique.getCritiqueState() == CritiqueState.ACCEPTED;
    }

    public boolean isAccepted(Long critiqueId){
        Critique critique = findById(critiqueId);
        return critique.getCritiqueState() == CritiqueState.ACCEPTED;
    }
}


