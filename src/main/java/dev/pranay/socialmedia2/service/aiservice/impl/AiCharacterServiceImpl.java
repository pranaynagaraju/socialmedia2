package dev.pranay.socialmedia2.service.aiservice.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pranay.socialmedia2.dto.aidto.AiCharacterDTO;
import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.entity.ai.AiCharacter;
import dev.pranay.socialmedia2.entity.ai.UserAiCharacter;
import dev.pranay.socialmedia2.repo.airepos.AiCharacterRepository;
import dev.pranay.socialmedia2.repo.airepos.UserAiCharacterRepository;
import dev.pranay.socialmedia2.service.PostService;
import dev.pranay.socialmedia2.service.aiservice.AiCharacterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class AiCharacterServiceImpl implements AiCharacterService {

    private final WebClient webClient;

    private String aiPrompt = "You are a %s. Your job is to comment on social media posts in a way that reflects your personality. Generate a short and engaging comment for the following post make it a single comment and use 30 characters and no need for bold or italic make it simple: '%s'";

    private final PostService postService;
    private AiCharacterRepository aiCharacterRepository;
    private UserAiCharacterRepository userAiCharacterRepository;

    public AiCharacterServiceImpl(WebClient webClient, @Lazy PostService postService, AiCharacterRepository aiCharacterRepository, UserAiCharacterRepository userAiCharacterRepository) {
        this.webClient = webClient;
        this.postService = postService;
        this.aiCharacterRepository = aiCharacterRepository;
        this.userAiCharacterRepository = userAiCharacterRepository;
    }

    @Override
    public List<AiCharacterDTO> getAllAiCharacters() {
        List<AiCharacter> aiCharactersList = aiCharacterRepository.findAll();
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<UserAiCharacter> userAiCharacterList = userAiCharacterRepository.findByUserId(profile.getUserId());
        List<Integer> selectedAiCharacterIds =  userAiCharacterList.stream().map(i->i.getAiCharacter().getId()).toList();
        List<AiCharacterDTO> aiCharacterDTOList = new ArrayList<>();
        for(AiCharacter aiCharacter: aiCharactersList)
        {    
            AiCharacterDTO aiCharacterDTO = new AiCharacterDTO();
            aiCharacterDTO.setId(aiCharacter.getId());
            aiCharacterDTO.setName(aiCharacter.getName());
            aiCharacterDTO.setImageUrl(aiCharacter.getImageUrl());
            aiCharacterDTO.setPersonality(aiCharacter.getPersonality());
            aiCharacterDTO.setIsSelected(selectedAiCharacterIds.contains(aiCharacter.getId()));
            aiCharacterDTOList.add(aiCharacterDTO);
        }
        return aiCharacterDTOList;
    }


    @Override
    public void setAllAiCharacters(String selectedAiCharacters) {
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userAiCharacterRepository.deleteAllByUserId(profile.getUserId());
        List<Integer> selectedAiCharactersIdList = Arrays
                .stream(selectedAiCharacters.split(","))
                .map(Integer::valueOf).toList();
        List<AiCharacter> aiCharactersList = aiCharacterRepository.findAll();
        List<Integer> aiCharactersIdList = aiCharactersList
                .stream().map(AiCharacter::getId).toList();

        for(Integer id : selectedAiCharactersIdList)
        {
            List<UserAiCharacter> userAiCharacterList =new ArrayList<>();
            if(aiCharactersIdList.contains(id))
            {
                UserAiCharacter userAiCharacter = new UserAiCharacter();
                userAiCharacter.setUserId(profile.getUserId());
                userAiCharacter.setAiCharacter(aiCharactersList.stream()
                        .filter(aiChar->aiChar.getId()
                                .equals(id))
                        .toList()
                        .getFirst());
                userAiCharacterList.add(userAiCharacter);
            }
            userAiCharacterRepository.saveAll(userAiCharacterList);
        }

    }

    public String generatePromptRequestBody(String personality, String postText) {
        String formattedPrompt = String.format(aiPrompt, personality, postText);
        String requestBody = """
        {
          "contents": [
            {
              "parts": [
                { "text": "%s" }
              ]
            }
          ]
        }
        """.formatted(formattedPrompt);
        return requestBody;
    }

    @Override
    public String generateAiComments(int postId,String postText,Profile profile) {
        log.info("Started generateAiComments");
        List<UserAiCharacter> userAiCharacterList = userAiCharacterRepository.findByUserId(profile.getUserId());

        userAiCharacterList.
                forEach(
                        i->{
                            log.info("Getting the Ai Comments");
                           String requestBody= generatePromptRequestBody(i.getAiCharacter().getPersonality(),postText);
                            String response = webClient.post()
                                    .uri("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=AIzaSyBSydHUMYB1ZmZ6h_6Y87NohXeOfOqiavc") // Add API URL here
                                    .header("Content-Type", "application/json")
                                    .bodyValue(requestBody)
                                    .retrieve()
                                    .bodyToMono(String.class)
                                    .block(); // Make the call synchronous
                            log.info("Ai Comment: {}",extractCommentFromJson(response));
                            System.out.println("AI Comment: " + extractCommentFromJson(response));
                            postService.addAiComment(postId,extractCommentFromJson(response),i.getAiCharacter());
                        }
                );
        log.info("Ended generateAiComments");
        return null;
    }


    private String extractCommentFromJson(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);

            // Navigate through the response JSON structure
            return rootNode.path("candidates")
                    .path(0) // First candidate
                    .path("content")
                    .path("parts")
                    .path(0) // First part
                    .path("text") // Get the AI-generated text
                    .asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to extract AI response.";
        }
    }

}
