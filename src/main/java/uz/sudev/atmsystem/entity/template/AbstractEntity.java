package uz.sudev.atmsystem.entity.template;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import uz.sudev.atmsystem.entity.User;

import javax.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
@Data
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;
    @Column(nullable = false,updatable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
    @LastModifiedBy
    @ManyToOne(fetch = FetchType.LAZY)
    private User updatedBy;
}
