package walshe.juniemvc.juniemvc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    // read only
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // read only
    @Version
    private Integer version;

    // read only
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    // read only
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
