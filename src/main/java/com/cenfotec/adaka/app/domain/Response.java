package com.cenfotec.adaka.app.domain;

import java.util.List;


/**
 * A generic response entity containing a title and data.
 *
 * @param <T> The type of data contained in the response.
 * @Author Alberto Solano
 * @CreatedDate: October 12th, 2023
 */
public class Response<T> {

    /**
     * The title of the response.
     */
    private String title;

    /**
     * The data contained in the response.
     */
    private List<T> data;

    /**
     * Constructs a new Response instance.
     *
     * @param title The title of the response.
     * @param data The data to be included in the response.
     */
    public Response(String title, List<T> data) {
        this.title = title;
        this.data = data;
    }

    /**
     * Gets the title of the response.
     *
     * @return The title of the response.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the response.
     *
     * @param title The title of the response.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the data contained in the response.
     *
     * @return The data contained in the response.
     */
    public List<T> getData() {
        return data;
    }

    /**
     * Sets the data contained in the response.
     *
     * @param data The data to be included in the response.
     */
    public void setData(List<T> data) {
        this.data = data;
    }
}
