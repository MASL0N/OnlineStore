package OnlineStoreWemalpa.com.OnlineStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "color")
@Data
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    private Long colorId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "hex_code")
    private String hexCode;

    @Column(name = "clothing_type")
    private String clothingType;
}

