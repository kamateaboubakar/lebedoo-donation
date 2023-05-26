package com.freewan.lebeboo.common.storage;

import com.freewan.lebeboo.common.http.ApiResponseCode;
import com.freewan.lebeboo.common.http.ApiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import static com.freewan.lebeboo.common.storage.FileStorageUtils.getFileUrlByFilename;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileUploadApiResponse extends ApiResponse {
    private String filename;
    private String url;

    public FileUploadApiResponse(String filename) {
        super(ApiResponseCode.SUCCESS, "File uploaded with success");
        this.filename = filename;
        this.url = getFileUrlByFilename(filename);
    }
}
