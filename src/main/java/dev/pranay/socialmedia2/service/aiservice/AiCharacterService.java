package dev.pranay.socialmedia2.service.aiservice;
import java.util.*;
import dev.pranay.socialmedia2.dto.aidto.AiCharacterDTO;
import dev.pranay.socialmedia2.entity.Profile;


public interface AiCharacterService {
    List<AiCharacterDTO> getAllAiCharacters();

    void setAllAiCharacters(String setAllAiCharacters);

    String generateAiComments(int postId, String postText, Profile profile);
}
