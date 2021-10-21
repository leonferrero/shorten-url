package com.demo.shortenurl.controller;

import com.demo.shortenurl.repository.ShortenUrlRepository;
import com.demo.shortenurl.service.ShortenUrlService;
import com.demo.shortenurl.vo.ShortUrl;
import com.demo.shortenurl.vo.ShortUrlGenerateRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ShortenUrlControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("normal url convert test")
    void convertNormalCase() throws Exception {

        // given
        String url = "https://magazine.musinsa.com/?m=magazine&uid=16798";

        // when
        MvcResult mvcResult = mockMvc.perform(
                        post("/url/convert")
                                .content(mapper.writeValueAsString(new ShortUrlGenerateRequest(url)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        // then
        ShortUrl shortUrl = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ShortUrl>() {
        });

        Assertions.assertNotNull(shortUrl);
        Assertions.assertNotNull(shortUrl.getUrl());
        Assertions.assertTrue(shortUrl.getCount() > 0);
    }



    @Test
    @DisplayName("encoded url convert test")
    void convertEncodedUrlCase() throws Exception {

        String[] urls = {
                "https://magazine.musinsa.com/?m=magazine&uid=16798",
                "https%3A%2F%2Fmagazine.musinsa.com%2F%3Fm%3Dmagazine%26uid%3D16798",
                "https%253A%252F%252Fmagazine.musinsa.com%252F%253Fm%253Dmagazine%2526uid%253D16798"
        };

        for (int i = 0; i < urls.length; i++) {
            ShortUrl shortUrl = convert(urls[i]);

            Assertions.assertNotNull(shortUrl);
            Assertions.assertNotNull(shortUrl.getUrl());
            Assertions.assertTrue(shortUrl.getCount() > 0);
        }
    }

    ShortUrl convert(String url) throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                        post("/url/convert")
                                .content(mapper.writeValueAsString(new ShortUrlGenerateRequest(url)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        return mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<ShortUrl>() {
        });
    }

    @ParameterizedTest
    @DisplayName("invalid url convert test")
    @ValueSource(strings = "imInvalidUrl")
    @NullAndEmptySource
    void convertInvalidCase(String invalidUrl) throws Exception {
        // when
        MvcResult mvcResult = mockMvc.perform(
                        post("/url/convert")
                                .content(mapper.writeValueAsString(new ShortUrlGenerateRequest(invalidUrl)))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn();
    }
}