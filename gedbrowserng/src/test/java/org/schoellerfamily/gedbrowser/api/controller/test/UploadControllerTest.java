package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.schoellerfamily.gedbrowser.api.controller.UploadController;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.loader.GedObjectFileLoader;
import org.schoellerfamily.gedbrowser.api.service.storage.FileSystemStorageService;
import org.schoellerfamily.gedbrowser.api.service.storage.StorageService;
import org.schoellerfamily.gedbrowser.persistence.mongo.gedconvert.GedObjectToGedDocumentMongoConverter;
import org.schoellerfamily.gedbrowser.persistence.mongo.repository.RepositoryManagerMongo;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tools.jackson.databind.ObjectMapper;

/**
 * @author Richard Schoeller
 */
@ExtendWith(MockitoExtension.class)
public class UploadControllerTest {
    /** */
    private InputStream is;
    /** */
    private MockMvc mockMvc;

    /** */
    private StorageService service = mock(FileSystemStorageService.class);

    /** */
    private GedObjectFileLoader loader = mock(GedObjectFileLoader.class);

    /** */
    private GedObjectToGedDocumentMongoConverter toDocConverter = mock(GedObjectToGedDocumentMongoConverter.class);

    /** */
    private RepositoryManagerMongo repositoryManager = mock(RepositoryManagerMongo.class);

    /** */
    private UploadController controller = spy(new UploadController(loader, toDocConverter, repositoryManager, service));

    /** */
    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        is = controller.getClass().getClassLoader()
                .getResourceAsStream("mini-schoeller.ged");
    }

    /**
     * @throws Exception if something goes wrong.
     */
    @Test
    public void testUploadController() throws Exception {
        final MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", "mini-schoeller.ged", "multipart/form-data", is);
        doNothing()
            .when(service).store(mockMultipartFile);
        final ApiHead o =
                ApiHead.builder()
                    .type("type")
                    .string("string that you would never get")
                    .build();
        doReturn(o)
            .when(controller).readOne(anyString());
        final MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/v1/upload")
                    .file(mockMultipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(HttpURLConnection.HTTP_OK))
                .andReturn();
        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(o);
        assertEquals(json, result.getResponse().getContentAsString(),
            "head object doesn't match");
    }
}