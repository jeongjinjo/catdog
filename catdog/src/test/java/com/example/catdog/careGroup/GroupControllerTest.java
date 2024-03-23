package com.example.catdog.careGroup;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GroupControllerTest {
    @Autowired private MockMvc mockMvc;

    // CareGroup careGroup, List<String> memberId, List<Integer> petNum, String currentMemberId
//    {
//        "groupDTO": {
//        "group_name": "TDD"
//    },
//        "member_id": ["1", "2", "3"],
//        "pet_num": ["dog", "cat", "bird"],
//        "current_member_id": "tt"
//    }
    @Test
    void careGroupAndTargetInsert() throws Exception {

        String requestBody = "{\n" +
                "  \"groupDTO\": {\n" +
                "    \"group_name\": \"Sample Group\"\n" +
                "  },\n" +
                "  \"member_id\": [\"tt\", \"yyy\", \"hoho\",\"ninano\"],\n" +
                "  \"pet_num\": [\"8\", \"9\", \"10\"],\n" +
                "   \"current_member_id\": \"tt\" \n" +
                "}";


        mockMvc.perform(post("/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is4xxClientError())
                .andDo(print());
    }
}