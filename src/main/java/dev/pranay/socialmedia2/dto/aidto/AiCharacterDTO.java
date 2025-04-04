package dev.pranay.socialmedia2.dto.aidto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiCharacterDTO {
    private Integer id;
    private String name;
    private String imageUrl;
    private String personality;
    private Boolean isSelected;
}