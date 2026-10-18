package br.com.agroelo.agroelo_api.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "cooperativa")
@Getter
@Setter
@NoArgsConstructor
public class Cooperativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String razaoSocial;

    @Column(length = 150)
    private String nomeFantasia;

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(length = 20)
    private String telefone;

    @Column(length = 150)
    private String email;

    @Column(nullable = false, updatable = false)
    private LocalDate dataCadastro = LocalDate.now();
    }

