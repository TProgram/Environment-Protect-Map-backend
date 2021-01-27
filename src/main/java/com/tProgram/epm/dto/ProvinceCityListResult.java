package com.tProgram.epm.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProvinceCityListResult {
    List<ProvinceCityItem> ProvinceCityItemList;

    public ProvinceCityListResult() {
        this.ProvinceCityItemList = new ArrayList<>();
    }
}
