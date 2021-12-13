package nz.codehq.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Device")
public class Device {

    @Id
    @Column(name = "deviceid")
    private String deviceId;

    @Column(name = "latitude")
    private float latitude;

    @Column(name = "longitude")
    private float longitude;

    @JsonManagedReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "device", cascade=CascadeType.ALL)
    @JsonProperty("data")
    private List<Weather> listWeather = new ArrayList<>();
}
