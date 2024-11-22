package com.example.Vendor_Advertisement.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vendor_advertisements")
@Getter
@Setter
public class VendorAdvertisement {

    @Id
    private String advertisementid;
    private String vendorId;
    private String advertisementImageUrl;
}