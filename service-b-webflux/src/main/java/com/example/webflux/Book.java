package com.example.webflux;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yangzq80@gmail.com
 * @date 2020-06-10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

//    @Id
    private Long id;

//    @Column(value = "name")
    private String name;

//    @Column(value = "author")
    private String author;

}
