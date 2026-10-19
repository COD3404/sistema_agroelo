package br.com.agroelo.agroelo_api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "solicitacao_assistencia")
@Getter
@Setter
@NoArgsConstructor
public class SolicitacaoAssistencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String assunto;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(nullable = false)
    private LocalDate dataSolicitacao = LocalDate.now();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusSolicitacao situacao = StatusSolicitacao.ABERTA;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cooperado_id", nullable = false)
    private Cooperado cooperado;


}
