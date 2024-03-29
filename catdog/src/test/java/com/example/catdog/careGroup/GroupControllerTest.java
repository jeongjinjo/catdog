package com.example.catdog.careGroup;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    void careGroupAllDelete() throws Exception {
        String requestBody = "{\n" +
                "  \"groupDTO\": {\n" +
                "    \"group_num\": \"21\"\n" +
                "  },\n" +
                "   \"current_member_id\": \"tt\" \n" +
                "}";

        mockMvc.perform(put("/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());;

    }

    @Test
    void groupInMemberOutUpdate() throws Exception {
        int groupNum = 8;
        String loginId = "ninano";
        String deleteTargetMember = "tt";

        mockMvc.perform(put( "/group/" + groupNum + "/" + loginId + "/" + deleteTargetMember))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void groupInPetOutUpdate() throws Exception {
        int groupNum = 1;
        String loginId = "hoho";
        int delTargetPet = 1;

        mockMvc.perform(delete( "/group/" + groupNum + "/" + loginId + "/" + delTargetPet))
                .andExpect(status().isOk())
                .andDo(print());
    }
}