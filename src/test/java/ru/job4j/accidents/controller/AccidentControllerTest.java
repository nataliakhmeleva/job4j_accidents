package ru.job4j.accidents.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.data.AccidentDataService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class AccidentControllerTest {
    @MockBean
    private AccidentDataService accidentService;
    @Autowired
    private MockMvc mockMvc;


    @Test
    @WithMockUser
    public void whenCreateAccident() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"));
    }

    @Test
    @WithMockUser
    public void whenAccidentGetByIdThenEdit() throws Exception {
        this.mockMvc.perform(get("/editAccident?id={id}", 1))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("error"));
    }

    @Test
    @WithMockUser
    public void whenSaveAccidentThenGetAccidentsList() throws Exception {
        this.mockMvc.perform(post("/saveAccident")
                        .param("id", "1")
                        .param("name", "name")
                        .param("text", "text")
                        .param("address", "address")
                        .param("type.id", "1")
                        .param("rIds", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).save(argument.capture());
        assertThat(argument.getValue().getName()).isEqualTo("name");
    }

    @Test
    @WithMockUser
    public void whenUpdateAccidentThenGetAccidentsList() throws Exception {
        this.mockMvc.perform(post("/updateAccident")
                        .param("id", "1")
                        .param("name", "name1")
                        .param("text", "text")
                        .param("address", "address")
                        .param("type.id", "1")
                        .param("rIds", "1", "2", "3"))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argument.capture());
        assertThat(argument.getValue().getName()).isEqualTo("name1");
    }
}