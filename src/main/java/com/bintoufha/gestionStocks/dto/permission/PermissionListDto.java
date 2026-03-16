package com.bintoufha.gestionStocks.dto.permission;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PermissionListDto {

    private String code;

    private String name;

    private String description;

    private String action; // CREATE, READ, UPDATE, DELETE

}
