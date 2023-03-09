package ru.yandex.practicum.catsgram.model;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Email;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class User {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String nickname;
    @NotNull
    @PastOrPresent
    private LocalDate birthdate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
