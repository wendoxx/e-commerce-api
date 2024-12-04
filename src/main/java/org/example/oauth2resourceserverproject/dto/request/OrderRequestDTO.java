package org.example.oauth2resourceserverproject.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class OrderRequestDTO {
    private Long id;
    private List<String> products;
    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDate expectedDate;
    private String buyer;
    private String seller;
}
