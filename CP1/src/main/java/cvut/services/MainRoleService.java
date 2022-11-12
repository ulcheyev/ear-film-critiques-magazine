package cvut.services;

import cvut.exception.NotFoundException;
import cvut.model.Film;
import cvut.model.FilmRole;
import cvut.model.MainRole;
import cvut.repository.FilmRepository;
import cvut.repository.MainRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MainRoleService {

    private final MainRoleRepository mainRoleRepository;

    @Autowired

    public MainRoleService(MainRoleRepository mainRoleRepository) {
        this.mainRoleRepository = mainRoleRepository;
    }


    public List<MainRole> getAll(){
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
        return mainRoleRepository.findAllByFilmRole(filmRole).orElseThrow(
                ()->new NotFoundException("Main role with role "+ filmRole + " does not exist")
        );
    }
}
