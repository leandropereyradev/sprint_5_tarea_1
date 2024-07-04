package cat.itacademy.barcelonactiva.pereyra.gastonleandro.s05.t01.n01.S05T01N01PereyraGastonLeandro.model.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk_BranchID;

    private String branchName;

    private String branchCountry;
}
