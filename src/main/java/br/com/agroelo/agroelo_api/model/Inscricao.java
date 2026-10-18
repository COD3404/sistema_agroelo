package br.com.agroelo.agroelo_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inscricao",
        uniqueConstraints = @UniqueConstraint(columnNames = {"evento_id", "cooperado_id"}))
@Getter
@Setter
@NoArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataInscricao = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoInscricao situacao = SituacaoInscricao.INSCRITO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cooperado_id", nullable = false)
    private Cooperado cooperado;
}
