package cvut.config.utils;

import com.github.javafaker.Faker;
import cvut.model.*;

import java.util.Date;
import java.util.Random;

public class Generator {

    private static Faker faker = new Faker();
    private static Random random = new Random();


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

    public static Admin generateAdmin(){
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String password = faker.phoneNumber().phoneNumber();
        String username = faker.name().username();
        String email = firstname + lastname +"@gmail.com";
        return new Admin(firstname,lastname,username,password, email);
    }

    public static Film generateFilm(){
        Film film = new Film();
        film.setDescription(faker.music().instrument());
        film.setDateOfRelease(faker.date().birthday());
        film.setDescription(faker.address().lastName());
        film.setName(faker.name().title());
        return film;
    }

    public static Critique generateCritique(){
        Critique critique = new Critique();
        Date dateOfAccept = faker.date().birthday();
        String title = faker.book().title();
        String text = faker.book().title()+faker.book().title();
        Admin admin = generateAdmin();
        Critic owner = generateCritic();
        critique.setAdmin(admin);
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
        ratingVote.setVoteOwner(generateUser());
        return ratingVote;
    }

    public static Critique generateCritique(Film film, Critic critic){
        Critique critique = new Critique();
        Date dateOfAccept = faker.date().birthday();
        String title = faker.book().title();
        String text = faker.book().title()+faker.book().title();
        critique.setCritiqueOwner(critic);
        critique.setDateOfAcceptance(dateOfAccept);
        critique.setText(text);
        critique.setTitle(title);
        critique.setFilm(film);
        return critique;
    }


}