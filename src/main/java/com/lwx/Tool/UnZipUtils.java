package com.lwx.Tool;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: Roy
 * @date: 2021/6/7 23:05
 * @desc:
 */
public class UnZipUtils {
    /**
     * @param source   原始文件路径
     * @param dest     解压路径
     * @param password 解压文件密码(可以为空)
     */
    public static final String OperationSuccessful = "OperationSuccessful"; //解压成功
    public static final String PasswordISrequired = "PasswordISrequired";  //需要密码
    public static final String WrongPassword = "WrongPassword";  //密码错误
    public static String unZip(String source, String dest, String password) {

        try {
            File zipFile = new File(source);
            ZipFile zFile = new ZipFile(zipFile);
            zFile.setFileNameCharset("GBK");
            File destDir = new File(dest);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            if (zFile.isEncrypted()) {
                zFile.setPassword(password.toCharArray());
            }
            File destFile = new File(dest);
            if(destFile.exists()){
                for(String lists:destFile.list()){
                    new File(lists).delete();
                }
                destFile.delete();
            }
            destFile.mkdir();
            zFile.extractAll(dest);
            List<FileHeader> headerList = zFile.getFileHeaders();
            List<File> extractedFileList = new ArrayList<>();
            for (FileHeader fileHeader : headerList) {
                if (!fileHeader.isDirectory()) {
                    extractedFileList.add(new File(destDir, fileHeader.getFileName()));
                }
            }
            File[] extractedFiles = new File[extractedFileList.size()];
            extractedFileList.toArray(extractedFiles);
            return OperationSuccessful;
        } catch (ZipException e) {
            if(password.length() == 0){
                return PasswordISrequired;
            }else{
                return WrongPassword;
            }
        }
    }
}
