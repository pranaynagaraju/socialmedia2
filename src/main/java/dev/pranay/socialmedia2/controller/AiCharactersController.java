package dev.pranay.socialmedia2.controller;

import dev.pranay.socialmedia2.dto.aidto.AiCharacterDTO;
import dev.pranay.socialmedia2.service.aiservice.AiCharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ai-characters")
public class AiCharactersController {

    private final AiCharacterService aiCharacterService;

    public AiCharactersController(AiCharacterService aiCharacterService) {
        this.aiCharacterService = aiCharacterService;
    }

    @GetMapping("get-ai-characters")
    public ResponseEntity<List<AiCharacterDTO>> getAiCharacters()
    {
        List<AiCharacterDTO> AiCharactersList = aiCharacterService.getAllAiCharacters();
        return new ResponseEntity<>(AiCharactersList, HttpStatus.OK);
    }

    @PostMapping("set-ai-characters")
    public ResponseEntity<String> setAiCharacters(@RequestParam("value") String selectedAiCharacters)
    {
        aiCharacterService.setAllAiCharacters(selectedAiCharacters);
        return new ResponseEntity<>(!selectedAiCharacters.equals("0") ?
                "The Ai bots will comment on your posts from now":"The bots are removed now", HttpStatus.OK);
    }


}
