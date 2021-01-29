package ua.mainacademy.model;

import lombok.*;

import java.util.HashMap;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {

    private String name;
    private String code;
    private String url;
    private String imageUrl;
    private String group;
    private HashMap specifications;

}
