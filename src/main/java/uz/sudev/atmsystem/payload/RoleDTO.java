package uz.sudev.atmsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import uz.sudev.atmsystem.entity.enums.PermissionName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Component
public class RoleDTO {
    @NotBlank
    private String name;
    private String description;
    @NotEmpty
    private List<PermissionName> permissionIds;
}
