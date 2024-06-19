package ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @NotBlank(message = "ko de trong")
    private String categoryName;
    @NotBlank(message = "ko de trong")
    @Column(columnDefinition = "TEXT")
    private String description;

    private Boolean status;

    @OneToMany( mappedBy = "category")
    @JsonIgnore
    private List<Product> products;
}
