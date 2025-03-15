package br.com.victor.integracaohubspot.contact.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ContactDTOTest {

    @Test
    void testToMapWithAllFields() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = "123456789";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email, phone);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithEmptyFirstName() {
        String firstName = "";
        String lastName = "Smith";
        String email = "jane.smith@example.com";
        String phone = "123456789";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email, phone);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithNullFirstName() {
        String firstName = null;
        String lastName = "Brown";
        String email = "michael.brown@example.com";
        String phone = "123456789";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email, phone);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertNull(result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithEmptyLastName() {
        String firstName = "Emily";
        String lastName = "";
        String email = "emily.jones@example.com";
        String phone = "123456789";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email, phone);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithNullLastName() {
        String firstName = "Sophia";
        String lastName = null;
        String email = "sophia.lee@example.com";
        String phone = "123456789";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email, phone);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertNull(result.get("lastname"));
        assertEquals(email, result.get("email"));
    }

    @Test
    void testToMapWithNullEmail() {
        String firstName = "Daniel";
        String lastName = "Taylor";
        String email = null;
        String phone = "123456789";
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email, phone);

        Map<String, Object> result = contactDTO.toMap();

        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertNull(result.get("email"));
    }

    @Test
    void testToMapWithNullPhone() {
        String firstName = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String phone = null;
        ContactDTO contactDTO = new ContactDTO(firstName, lastName, email, phone);

        Map<String, Object> result = contactDTO.toMap();
        assertNotNull(result);
        assertEquals(4, result.size());
        assertEquals(firstName, result.get("firstname"));
        assertEquals(lastName, result.get("lastname"));
        assertEquals(email, result.get("email"));

    }
}