package com.bintoufha.gestionStocks.dto.permission;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PermissionSaveDto {

    private String code;

    private String name;

    private String description;

    private String module;

    private String category;

    private String action; // CREATE, READ, UPDATE, DELETE

}
