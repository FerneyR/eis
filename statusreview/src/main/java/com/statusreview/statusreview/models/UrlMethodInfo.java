package com.statusreview.statusreview.models;

import org.springframework.http.HttpMethod;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlMethodInfo {
    private String url;
    private HttpMethod httpMethod;
    private String bodyjson;

}
