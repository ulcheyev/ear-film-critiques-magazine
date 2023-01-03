package cvut.config.utils;

import com.github.javafaker.Faker;
import cvut.model.*;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.Random;

public class Generator {

    public static Faker faker = new Faker();
    public static Random random = new Random();


    //Generatory v1
    public static Date generateDate(){
        return faker.date().birthday();
    }

    public static Long generateId(){
        return random.nextLong(10L);
    }

    public static AppUser generateUser(){
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String password = faker.phoneNumber().phoneNumber();
        String username = faker.name().username();
        String email = firstname + lastname +"@gmail.com";
        return new AppUser(firstname,lastname,username,password, email);
    }

    public static Critic generateCritic(){
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String password = faker.phoneNumber().phoneNumber();
        String username = faker.name().username();
        String email = firstname + lastname +"@gmail.com";
        return new Critic(firstname,lastname,username,password, email);
    }


    public static Film generateFilm(){
        Film film = new Film();
        film.setDescription(faker.music().instrument());
        film.setDateOfRelease(faker.date().birthday());
        film.setDescription(faker.address().lastName());
        film.setName(faker.name().title());
        return film;
    }


    public static Critique generateCritique(Integer textLength){
        Critique critique = new Critique();
        Date dateOfAccept = faker.date().birthday();
        String title = faker.book().title();
        String text = StringUtils.repeat("B", textLength);
//        Admin admin = generateAdmin();
        Critic owner = generateCritic();
//        critique.setAdmin(admin);
        critique.setCritiqueOwner(owner);
        critique.setDateOfAcceptance(dateOfAccept);
        critique.setText(text);
        critique.setTitle(title);
        critique.setFilm(generateFilm());
        return critique;
    }


    public static RatingVote generateRating(Critique critique){
        RatingVote ratingVote = new RatingVote();
        ratingVote.setStars(random.nextInt(5));
        ratingVote.setCritique(critique);
        ratingVote.setDate(generateDate());
        ratingVote.setVoteOwner(generateUser());
        return ratingVote;
    }

    public static String generateString(){
        String std = faker.name().firstName()+faker.book();
        return std;
    }


    //Generatory v2
    public static RatingVote generateRating(Critique critique, AppUser appUser){
        RatingVote ratingVote = new RatingVote();
        ratingVote.setStars(random.nextInt(5));
        ratingVote.setCritique(critique);
        ratingVote.setDate(generateDate());
        ratingVote.setVoteOwner(appUser);
        return ratingVote;
    }

    public static String generateString(String toRepeat, int textLength){
        return StringUtils.repeat(toRepeat, textLength);
    }

    public static Critique generateCritique(Film film, Critic critic, Integer textLength){
        Critique critique = new Critique();
        Date dateOfAccept = faker.date().birthday();
        String title = faker.book().title();
        String text = StringUtils.repeat("B", textLength);
        critique.setCritiqueOwner(critic);
        critique.setDateOfAcceptance(dateOfAccept);
        critique.setText(text);
        critique.setTitle(title);
        critique.setFilm(film);
        return critique;
    }

    public static Critique generateCritique(CritiqueState state, Integer textLength){
        Critique critique = new Critique();
        Date dateOfAccept = faker.date().birthday();
        String title = faker.book().title();
        String text = StringUtils.repeat("B", textLength);
//        Admin admin = generateAdmin();
        Critic owner = generateCritic();
//        critique.setAdmin(admin);
        critique.setCritiqueOwner(owner);
        critique.setDateOfAcceptance(dateOfAccept);
        critique.setText(text);
        critique.setTitle(title);
        critique.setFilm(generateFilm());
        critique.setCritiqueState(state);
        return critique;
    }

    public static Comment generateComment(String text, Critique critique, AppUser appUser){
        Comment comment = new Comment();
        comment.setText(text);
        comment.setDateOfPublic(new Date());
        comment.setCritique(critique);
        comment.setAppUser(appUser);
        return comment;
    }

    public static Comment generateComment(){
        Comment comment = new Comment();
        comment.setText(generateString("S", 100));
        comment.setDateOfPublic(new Date());
        comment.setCritique(generateCritique(200));
        comment.setAppUser(generateUser());
        return comment;
    }


}