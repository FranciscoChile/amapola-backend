package com.pancho.demo.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavDTO implements Serializable {
    
    private Long id;
    private String title;
    private String description;

    @Lob
    @Column(length = 5000)
    private String summary;

    private Date published_at;
    private String image_url;

}
