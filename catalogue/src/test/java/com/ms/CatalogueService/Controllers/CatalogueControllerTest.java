package com.ms.CatalogueService.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.CatalogueService.Entities.Catalogue;
import com.ms.CatalogueService.Services.CatalogueService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;


@ExtendWith(SpringExtension.class)
@WebMvcTest(CatalogueController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CatalogueControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    CatalogueService catalogueService;

    @Test
    @Order(1)
    void testGetAllProducts() throws Exception {
        List<Catalogue> catalogueList = Arrays.asList(
                new Catalogue(1,"powder","goodproduct")
                ,new Catalogue(2,"cream","niceCream"));
        RequestBuilder req;

        when(catalogueService.getAllProducts()).thenReturn(catalogueList);
        req= MockMvcRequestBuilders
                .get("/catalogue/allproducts")
                .accept(MediaType.APPLICATION_JSON);
        String expectedResult="[{id:1,productName:powder,productDescription:goodproduct},{id:2,productName:cream,productDescription:niceCream}]";
        mockMvc.perform(req)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult))
                .andReturn();
    }

    @Test
    @Order(2)
    void testGetProductById() throws Exception {
        List<Catalogue> catalogueList = Arrays.asList(
                new Catalogue(1,"powder","goodproduct"));
        RequestBuilder req;

        when(catalogueService.getProduct(1)).thenReturn(catalogueList.get(0));
        req= MockMvcRequestBuilders
                .get("/catalogue/products/1")
                .accept(MediaType.APPLICATION_JSON);
        String expectedResult="{id:1,productName:powder,productDescription:goodproduct}";
        mockMvc.perform(req)
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult))
                .andReturn();
    }

    @Test
    @Order(3)
    void testAddProduct() throws Exception {

        Catalogue newProduct= new Catalogue(3,"perfume","good perfume");
        RequestBuilder req;
        when(catalogueService.addNewProduct(any(Catalogue.class))).thenReturn(newProduct);

        req= MockMvcRequestBuilders
                .post("/catalogue/products")
                .content(objectMapper.writeValueAsString(newProduct))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(req)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}
