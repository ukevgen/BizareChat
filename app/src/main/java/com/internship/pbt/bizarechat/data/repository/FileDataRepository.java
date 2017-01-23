package com.internship.pbt.bizarechat.data.repository;

import com.internship.pbt.bizarechat.data.net.RetrofitApi;
import com.internship.pbt.bizarechat.data.net.services.FileManagerService;

public class FileDataRepository {

    FileManagerService fileManagerService;

    public FileDataRepository() {
        fileManagerService = RetrofitApi.getRetrofitApi().getFileManagerService();
    }
}
