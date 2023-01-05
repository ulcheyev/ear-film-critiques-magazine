package cvut.controllers;

import cvut.config.utils.Generator;
import cvut.exception.NotFoundException;
import cvut.model.Critique;
import cvut.model.Remarks;
import cvut.model.dto.creation.RemarksCreationDTO;
import cvut.services.CritiqueServiceImpl;
import cvut.services.RemarksServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



public class RemarksControllerTest extends TestHelper{

    @Autowired
    private RemarksServiceImpl remarksService;

    @Autowired
    private CritiqueServiceImpl critiqueService;

    @Test
    public void roleUserHasNotAccess() throws Exception {
        mockMvc.perform(get("/api/system/remarks")
                        .with(pepaUser())).andExpect(status().isPreconditionFailed());
    }

    @Test
    public void addRemarks() throws Exception {

        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        critiqueService.save(critique);

        RemarksCreationDTO remarksCreationDTO = new RemarksCreationDTO();
        remarksCreationDTO.setContent("ear ear ear predelat");

        mockMvc.perform(post("/api/system/critiques/"+critique.getId())
                        .content(toJson(remarksCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(pepaAdmin()))
                .andExpect(status().isOk());

        List<Remarks> allByCritiqueId = remarksService.findAllByCritiqueId(critique.getId());
        assertFalse(allByCritiqueId.isEmpty());
    }

    @Test
    public void removeRemarks() throws Exception {
        Critique critique = Generator.generateCritique(1500);
        critique.setTitle(Generator.generateString("Lol", 10));
        critiqueService.save(critique);

        RemarksCreationDTO remarksCreationDTO = new RemarksCreationDTO();
        remarksCreationDTO.setContent("ear ear ear predelat");

        mockMvc.perform(post("/api/system/critiques/"+critique.getId())
                        .content(toJson(remarksCreationDTO))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(pepaAdmin()))
                .andExpect(status().isOk());

        List<Remarks> allByCritiqueId = remarksService.findAllByCritiqueId(critique.getId());
        assertFalse(allByCritiqueId.isEmpty());

        mockMvc.perform(delete("/api/system/remarks/"+allByCritiqueId.get(0).getId())
                        .with(pepaAdmin()))
                .andExpect(status().isOk());

        assertThrows(NotFoundException.class, () -> remarksService.findAllByCritiqueId(critique.getId()));
    }

}
