package cvut.model;

import lombok.Getter;

import java.util.Locale;

public enum FilmRole {
    ACTOR("Actor"),
    PRODUCER("Producer"),
    OPERATOR("Operator"),
    SCREENWRITER("Screenwriter");

    @Getter
    private final String role;

    FilmRole(String role) {
        this.role = role;
    }

    public static FilmRole fromDBName(String filmRole) {
        FilmRole[] values = FilmRole.values();
        for (FilmRole filmR : values) {
            if (filmR
                    .role
                    .toLowerCase(Locale.ROOT)
                    .matches(filmRole.toLowerCase(Locale.ROOT))) {
                return filmR;
            }
        }
        throw new IllegalArgumentException("Can not find " + filmRole);
    }
}
