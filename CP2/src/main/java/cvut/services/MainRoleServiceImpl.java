package cvut.services;

import cvut.config.utils.Mapper;
import cvut.exception.NotFoundException;
import cvut.model.Film;
import cvut.model.FilmRole;
import cvut.model.MainRole;
import cvut.model.dto.MainRoleDTO;
import cvut.model.dto.creation.MainRoleCreationDTO;
import cvut.repository.MainRoleCriteriaRepository;
import cvut.repository.MainRoleRepository;
import lombok.RequiredArgsConstructor;
import org.jboss.jandex.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainRoleServiceImpl implements MainRoleService{

    private final MainRoleRepository mainRoleRepository;
    private final MainRoleCriteriaRepository mainRoleSearchCriteria;
    private final Mapper mapper;


    public List<MainRole> findAll(){
        return mainRoleRepository.findAll();
    }

    public void save(@NonNull MainRole mainRole){
        mainRoleRepository.save(mainRole);
    }

    public void deleteById(@NonNull Long mainRoleId){
        boolean ex = mainRoleRepository.existsById(mainRoleId);
        if(!ex){
            throw new NotFoundException("Main role with id "+mainRoleId+" does not exist");
        }
        mainRoleRepository.deleteById(mainRoleId);
    }

    public MainRole findById(@NonNull Long id){
        return mainRoleRepository.findById(id).orElseThrow(
                ()->new NotFoundException("Main role with id "+ id+ " does not exist")
        );
    }

    public List<MainRole> findByFilmRole(@NonNull FilmRole filmRole){
        List<MainRole> allByFilmRole = mainRoleRepository.findAllByFilmRole(filmRole);
        if(allByFilmRole.isEmpty()){
            throw new NotFoundException("Main role with role "+ filmRole + " does not exist");
        }
        return allByFilmRole;
    }

    public List<MainRole> findAllByCriteria(MainRoleCreationDTO criteria){
        return mainRoleSearchCriteria.findAllByFilters(criteria);
    }

    @Transactional
    @Override
    public void update(@NonNull Long mainRoleId, @NonNull MainRoleCreationDTO mainRoleCreationDTO) {
        MainRole mainRole = findById(mainRoleId);

        String firstname = mainRoleCreationDTO.getFirstname();
        String lastname = mainRoleCreationDTO.getLastname();
        FilmRole filmRole = FilmRole.fromDBName(mainRoleCreationDTO.getFilmRole());
        List<Film> filmList = mapper.toFilms(mainRoleCreationDTO.getFilms());

        if(firstname != null &&
                !mainRole.getFirstname().equals(firstname)){
            mainRole.setFirstname(firstname);
        }if(lastname != null &&
                !mainRole.getLastname().equals(lastname)){
            mainRole.setLastname(lastname);
        }if(!mainRole.getFilmRole().equals(filmRole)){
            mainRole.setFilmRole(filmRole);
        }if(filmList != null &&
                !mainRole.getFilmList().equals(filmList)){
            mainRole.setFilmList(filmList);
        }
    }
}
