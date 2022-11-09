package cvut.config.utils;

import com.github.javafaker.Faker;
import cvut.model.*;

import java.util.Date;
import java.util.Random;

public class Generator {

    private static Faker faker = new Faker();
    private static Random random = new Random();


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
        return new AppUser(firstname,lastname,username,password);
    }

    public static Critic generateCritic(){
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String password = faker.phoneNumber().phoneNumber();
        String username = faker.name().username();
        return new Critic(firstname,lastname,username,password);
    }

    public static Critique generateCritique(){
        Critique critique = new Critique();
        Date dateOfAccept = faker.date().birthday();
        String title = faker.book().title();
        String text = faker.book().title()+faker.book().title();
        AppUser admin = generateUser();
        Critic owner = generateCritic();
        critique.setAdmin((Admin) admin);
        critique.setCritiqueOwner(owner);
        critique.setDateOfAcceptance(dateOfAccept);
        critique.setText(text);
        critique.setTitle(title);
        return critique;
    }


    public static RatingVote generateRating(){
        RatingVote ratingVote = new RatingVote();
        ratingVote.setStars(random.nextInt(5));
        ratingVote.setCritique(generateCritique());
        ratingVote.setDate(generateDate());
        return ratingVote;
    }

    public static RatingVote generateRating(Critique critique){
        RatingVote ratingVote = new RatingVote();
        ratingVote.setStars(random.nextInt(5));
        ratingVote.setCritique(critique);
        ratingVote.setDate(generateDate());
        return ratingVote;
    }



}
