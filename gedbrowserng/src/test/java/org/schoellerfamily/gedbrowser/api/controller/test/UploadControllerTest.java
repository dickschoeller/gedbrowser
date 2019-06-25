package org.schoellerfamily.gedbrowser.api.controller.test;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.net.HttpURLConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.schoellerfamily.gedbrowser.api.controller.UploadController;
import org.schoellerfamily.gedbrowser.api.datamodel.ApiHead;
import org.schoellerfamily.gedbrowser.api.service.storage.StorageService;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Richard Schoeller
 */
@RunWith(MockitoJUnitRunner.class)
public class UploadControllerTest {
    /** */
    private InputStream is;
    /** */
    private MockMvc mockMvc;

    /** */
    @Spy
    @InjectMocks
    private UploadController controller = new UploadController();

    /** */
    @Mock
    private StorageService service;

    /** */
    @Before
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
        Mockito.doNothing().when(service).store(mockMultipartFile);
        final ApiHead o =
                new ApiHead("type", "string that you would never get");
        Mockito.doReturn(o).when(controller).readOne("mini-schoeller");
        final MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders
                    .fileUpload("/v1/upload")
                    .file(mockMultipartFile)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers
                        .status()
                        .is(HttpURLConnection.HTTP_OK))
                .andReturn();
        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(o);
        assertEquals("head object doesn't match",
                json, result.getResponse().getContentAsString());
    }
}
