package OnlineStoreWemalpa.com.OnlineStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "material")
@Data
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "clothing_type")
    private String clothingType;

    @Column(name = "description")
    private String description;
}
