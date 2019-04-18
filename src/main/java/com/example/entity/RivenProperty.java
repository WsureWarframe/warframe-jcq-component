package com.example.entity;

import org.codehaus.jackson.annotate.JsonProperty;

public class RivenProperty {
    /**
     * 紫卡属性项
     *    {
     *       "Property": "多重射击",
     *       "Min": 8.4,
     *       "Max": 10.3,
     *       "MinusMin": -6.8,
     *       "MinusMax": -8.3
     *     },
     */

    @JsonProperty("Property")
    private String Property;
    @JsonProperty("Min")
    private Double Min;
    @JsonProperty("Max")
    private Double Max;
    @JsonProperty("MinusMin")
    private Double MinusMin;
    @JsonProperty("MinusMax")
    private Double MinusMax;

    public String getProperty() {
        return Property;
    }

    public void setProperty(String property) {
        Property = property;
    }

    public Double getMin() {
        return Min;
    }

    public void setMin(Double min) {
        Min = min;
    }

    public Double getMax() {
        return Max;
    }

    public void setMax(Double max) {
        Max = max;
    }

    public Double getMinusMin() {
        return MinusMin;
    }

    public void setMinusMin(Double minusMin) {
        MinusMin = minusMin;
    }

    public Double getMinusMax() {
        return MinusMax;
    }

    public void setMinusMax(Double minusMax) {
        MinusMax = minusMax;
    }
}
