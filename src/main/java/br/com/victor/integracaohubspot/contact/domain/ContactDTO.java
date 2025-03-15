package br.com.victor.integracaohubspot.contact.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.HashMap;
import java.util.Map;

public class ContactDTO {

    private final String firstName;

    private final String lastName;

    @Email(message = "E-mail deve ser válido")
    @NotEmpty(message = "O e-mail não pode estar vazio")
    private final String email;

    private final String phone;

    public ContactDTO(String firstName, String lastName, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstname", this.firstName);
        map.put("lastname", this.lastName);
        map.put("email", this.email);
        map.put("phone", this.phone);
        return map;
    }
}