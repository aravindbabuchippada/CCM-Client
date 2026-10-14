package com.ccm.client.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientPojo {

    private Integer id;

    private String clientName;

    @Builder.Default
    private Integer active = 1;

    private String createdBy;

    private String modifiedBy;
}
