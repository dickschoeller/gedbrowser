package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;
import org.schoellerfamily.gedbrowser.api.controller.PersonsController;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiPerson;
import org.schoellerfamily.gedbrowser.datamodel.Person;
import org.springframework.security.access.AccessDeniedException;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Unit tests for PersonsController using only public APIs and Spring Boot 4.x dependencies.
 */
class PersonsControllerUnitTest {

    /**
     * Test createDummyLivingPerson returns expected dummy fields.
     */
    @Test
    void testCreateDummyLivingPersonFields() {
        final PersonsController controller =
            mock(PersonsController.class, org.mockito.Mockito.CALLS_REAL_METHODS);
        final ApiPerson dummy = controller.createDummyLivingPerson("ID123");
        assertEquals("ID123", dummy.getString());
        assertEquals("Living", dummy.getIndexName());
        assertEquals("?", dummy.getSurname());
    }


    /**
     * Test shouldHideConfidential returns false when admin.
     */
    @Test
    void testShouldHideConfidentialFalseIfAdmin() {
        final PersonsController controller =
            mock(PersonsController.class, org.mockito.Mockito.CALLS_REAL_METHODS);
        final Person person = mock(Person.class);
        assertEquals(false, controller.shouldHideConfidential(person, true));
    }


    /**
     * Test shouldHideLiving returns false if user.
     */
    @Test
    void testShouldHideLivingFalseIfUser() {
        final PersonsController controller =
            mock(PersonsController.class, org.mockito.Mockito.CALLS_REAL_METHODS);
        final Person person = mock(Person.class);
        assertEquals(false, controller.shouldHideLiving(person, true));
    }


    /**
     * Test create throws AccessDeniedException if not admin.
     */
    @Test
    void testCreateThrowsAccessDenied() {
        final PersonsController controller =
            mock(PersonsController.class, org.mockito.Mockito.CALLS_REAL_METHODS);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ApiPerson person = mock(ApiPerson.class);
        assertThrows(AccessDeniedException.class, () ->
            controller.create(request, "db", person));
    }

    /**
     * Test update throws AccessDeniedException if not admin.
     */
    @Test
    void testUpdateThrowsAccessDenied() {
        final PersonsController controller =
            mock(PersonsController.class, org.mockito.Mockito.CALLS_REAL_METHODS);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final ApiPerson person = mock(ApiPerson.class);
        assertThrows(AccessDeniedException.class, () ->
            controller.update(request, "db", "id", person));
    }

    /**
     * Test delete throws AccessDeniedException if not admin.
     */
    @Test
    void testDeleteThrowsAccessDenied() {
        final PersonsController controller =
            mock(PersonsController.class, org.mockito.Mockito.CALLS_REAL_METHODS);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        assertThrows(AccessDeniedException.class, () ->
            controller.delete(request, "db", "id"));
    }

    // Additional tests for read, update, delete with admin/user can be added
    // with more advanced mocks if needed.
}
