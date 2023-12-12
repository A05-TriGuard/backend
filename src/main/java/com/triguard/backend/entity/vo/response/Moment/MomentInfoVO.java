package com.triguard.backend.entity.vo.response.Moment;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MomentInfoVO {
    Integer id;
    Integer accountId;
    String profile;
    String username;
    String date;
    String content;
    String images;
    String video;
    @JsonAlias("class")
    String classification;
    String category;
    Integer commentCount;
    Integer likeCount;
    Integer favoriteCount;
    Boolean isLike;
    Boolean isFavorite;
    Boolean isFollow;
}
