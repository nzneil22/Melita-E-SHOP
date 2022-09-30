package com.melita_task.api.models;

import com.melita_task.contract.ClientStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.nonNull;


@Data
@Entity
@Builder
@Table(name="clients")
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    private static final long serialVersionUID = 8977477093018286915L;

    @Id
    @Type(type = "uuid-char")
    @Column(columnDefinition = "char(36)")
    private final UUID id = UUID.randomUUID();

    @Valid
    @NotNull
    @Embedded
    private FullName fullName;

    @Valid
    @NotNull
    @Embedded
    private InstallationAddress installationAddress;

    private ClientStatus status = ClientStatus.ACTIVE;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "client", fetch = FetchType.LAZY)
    private List<Order> orders;

    public void updateClient(final FullNameUpdate fullName){
        if(nonNull(fullName.getFirstName()))this.fullName.setFirstName(fullName.getFirstName());
        if(nonNull(fullName.getMiddleName()))this.fullName.setMiddleName(fullName.getMiddleName());
        if(nonNull(fullName.getLastName()))this.fullName.setLastName(fullName.getLastName());
    }

    public void updateClient(final InstallationAddressUpdate installationAddress){
        if(nonNull(installationAddress.getIsland()))this.installationAddress.setIsland(installationAddress.getIsland());
        if(nonNull(installationAddress.getTown()))this.installationAddress.setTown(installationAddress.getTown());
        if(nonNull(installationAddress.getStreet()))this.installationAddress.setStreet(installationAddress.getStreet());
        if(nonNull(installationAddress.getBuilding()))this.installationAddress.setBuilding(installationAddress.getBuilding());
    }

    @Override
    public String toString(){
        return id.toString();
    }


    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Client)) return false;
        final Client other = (Client) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$fullName = this.getFullName();
        final Object other$fullName = other.getFullName();
        if (!Objects.equals(this$fullName, other$fullName)) return false;
        final Object this$installationAddress = this.getInstallationAddress();
        final Object other$installationAddress = other.getInstallationAddress();
        if (!Objects.equals(this$installationAddress, other$installationAddress))
            return false;
        final Object this$status = this.getStatus();
        final Object other$status = other.getStatus();
        return Objects.equals(this$status, other$status);
//        final Object this$orders = this.getOrders();
//        final Object other$orders = other.getOrders();
//        if (this$orders == null ? other$orders != null : !this$orders.equals(other$orders)) return false;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Client;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $fullName = this.getFullName();
        result = result * PRIME + ($fullName == null ? 43 : $fullName.hashCode());
        final Object $installationAddress = this.getInstallationAddress();
        result = result * PRIME + ($installationAddress == null ? 43 : $installationAddress.hashCode());
        final Object $status = this.getStatus();
        result = result * PRIME + ($status == null ? 43 : $status.hashCode());
//        final Object $orders = this.getOrders();
//        result = result * PRIME + ($orders == null ? 43 : $orders.hashCode());
        return result;
    }
}
