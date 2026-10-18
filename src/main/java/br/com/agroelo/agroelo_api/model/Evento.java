package br.com.agroelo.agroelo_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "evento")
@Getter
@Setter
@NoArgsConstructor
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataEvento;

    @Column(nullable = false, length = 100)
    private String local;

    @Column(nullable = false)
    private Integer vagas;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoEvento situacao = SituacaoEvento.AGENDADO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cooperativa_id", nullable = false)
    private Cooperativa cooperativa;

}
