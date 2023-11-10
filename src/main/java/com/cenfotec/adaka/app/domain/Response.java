package com.cenfotec.adaka.app.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * A generic response entity containing a title and data.
 *
 * @param <T> The type of data contained in the response.
 * @Author Alberto Solano
 * @CreatedDate: October 12th, 2023
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    /**
     * The title of the response.
     */
    private String title;

    /**
     * The data contained in the response.
     */
    private List<T> data;

}
