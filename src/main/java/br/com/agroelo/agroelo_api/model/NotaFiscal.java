package br.com.agroelo.agroelo_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "nota_fiscal",
        uniqueConstraints = @UniqueConstraint(columnNames = {"cooperado_id", "serie", "numero"}))
@Getter
@Setter
@NoArgsConstructor
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false, length = 3)
    private String serie;

    private LocalDateTime dataEmissao;

    @Column(nullable = false , precision = 12, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SituacaoNota situacao = SituacaoNota.RASCUNHO;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cooperado_id", nullable = false)
    private Cooperado cooperado;
}
