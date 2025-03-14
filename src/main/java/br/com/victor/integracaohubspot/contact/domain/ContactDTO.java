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

    public ContactDTO(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Converte o DTO para o formato Map<String, Object>.
     * Pode ser usado diretamente pelo serviço HubSpotContactService.
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("firstname", this.firstName);
        map.put("lastname", this.lastName);
        map.put("email", this.email);
        return map;
    }
}