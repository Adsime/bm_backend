package com.acc.database.repository;

import com.acc.database.entity.HbnEntity;
import com.acc.database.entity.HbnProblem;
import com.acc.database.entity.HbnUser;
import com.acc.database.specification.GetUserByIdSpec;
import com.acc.database.specification.HqlSpecification;
import com.acc.database.specification.Specification;
import com.acc.models.Problem;
import com.acc.providers.Links;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen.duy.j.khac on 15.02.2017.
 */

// TODO: 15.03.2017 NO AUTO INCREMENT OF PROBLEM
public class ProblemRepository extends AbstractRepository implements Repository<Problem> {
        
    public ProblemRepository() {
        super();
    }

    @Override
    public Problem add(Problem problem) throws EntityNotFoundException, IllegalArgumentException{
        if (problem.getTitle().equals("") || problem.getContent().equals("")) throw new IllegalArgumentException("Feil i registrering av oppgave: \nFyll ut nødvendige felter!");

        //getAuthor() throws EntityNotFoundException
        HbnProblem mappedProblem = new HbnProblem(problem.getPath(), getAuthor(problem.getAuthor()), problem.getTitle());

        try {
             if (problem.getTags() != null) mappedProblem.setTags(super.getHbnTagSet(problem.getTags()));
        }catch (EntityNotFoundException enf){
            //TODO exception message to log file
            throw new EntityNotFoundException("Feil i registrering av oppgave: \nEn eller flere merknader finnes ikke");
        }
        long id = super.addEntity(mappedProblem);

        return new Problem(
                (int) id,
                problem.getAuthor(),
                problem.getTitle(),
                problem.getContent(),
                problem.getPath(),
                problem.getTags()
        );
    }

    @Override
    public boolean update(Problem problem) throws EntityNotFoundException{
        //getAuthor() throws EntityNotFoundException
        HbnProblem mappedProblem = new HbnProblem(problem.getPath(), getAuthor(problem.getAuthor()), problem.getTitle());
        mappedProblem.setId(problem.getId());

        try {
            if (problem.getTags() != null) mappedProblem.setTags(super.getHbnTagSet(problem.getTags()));
        }catch (EntityNotFoundException enf){
            //TODO exception message to log file
           throw new EntityNotFoundException("Feil i oppdatering av oppgave: \nEn eller flere merknader finnes ikke");
        }

        try{
            return super.updateEntity(mappedProblem);
        }catch (EntityNotFoundException enf){
            //TODO exception message to log file
            throw new EntityNotFoundException("Feil i oppdatering av oppgave: \nOppgave med id: " + problem.getId() + " finnes ikke");
        }

    }

    @Override
    public boolean remove(long id) {
        HbnProblem mappedProblem = new HbnProblem();
        mappedProblem.setId(id);

        try{
            return super.removeEntity(mappedProblem);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i sletting av oppgave: \nOppgave med id: " + id + " finnes ikke");
        }
    }

    @Override
    public List<Problem> getQuery(Specification spec) {
        List<HbnEntity> readData;
        try{
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av oppgave: \nEn eller flere oppgaver finnes ikke");
        }

        List<Problem> result = new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnProblem hbnProblem = (HbnProblem) entity;

            Problem problem = new Problem(
                    (int)hbnProblem.getId(),
                    (int)hbnProblem.getUser().getId(),
                    hbnProblem.getTitle(),
                    "",
                    hbnProblem.getPath(),
                    hbnProblem.getTags() != null ? super.toTagList(hbnProblem.getTags()) : new ArrayList<>()
            );

            List<Integer> authorId = new ArrayList<>(problem.getAuthor());
            problem.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            if (!problem.getTags().isEmpty()) problem.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, problem.getTagIdList()));
            result.add(problem);
        }
        return result;
    }

    @Override
    public List<Problem> getMinimalQuery(Specification spec) throws EntityNotFoundException{
        List<HbnEntity> readData;
        try{
            readData = super.queryToDb((HqlSpecification) spec);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i henting av oppgave: \nEn eller flere oppgaver finnes ikke");
        }
        List<Problem> result = new ArrayList<>();

        for (HbnEntity entity : readData){
            HbnProblem hbnProblem = (HbnProblem) entity;

            Problem problem = new Problem();
            problem.setId((int)hbnProblem.getId());
            problem.setTitle(hbnProblem.getTitle());
            problem.setTags(
                    hbnProblem.getTags() != null ? super.toTagList(hbnProblem.getTags()) : new ArrayList<>()
            );

            List<Integer> authorId = new ArrayList<>(problem.getAuthor());
            problem.addLinks(Links.USERS, Links.generateLinks(Links.USER, authorId));
            if (!problem.getTags().isEmpty()) problem.addLinks(Links.TAGS, Links.generateLinks(Links.TAG, problem.getTagIdList()));
            result.add(problem);
        }
        return result;
    }

    private HbnUser getAuthor(long authorId){
        try {
            return (HbnUser) queryToDb(new GetUserByIdSpec(authorId)).get(0);
        }catch (EntityNotFoundException enf){
            throw new EntityNotFoundException("Feil i registrering/oppdatering av oppgave: \nForfatter med id: " + authorId + " finnes ikke");
        }
    }
}

