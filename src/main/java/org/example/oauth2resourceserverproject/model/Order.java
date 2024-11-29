package org.example.oauth2resourceserverproject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "tb_order")
@Data
public class Order implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product")
    private String product;

    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDate expectedDate;

    @Column(name = "buyer")
    private String buyer;

    @Column(name = "seller")
    private String seller;
}
