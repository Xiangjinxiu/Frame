package com.xwh.frame.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * File的相关操作.
 * Created by xwh on 2017/11/28.
 */

public class FileUtil {
    /**
     * 创建文件夹
     * 首选SD卡上的目录,具体是指 /sdcard/Android/data/package_name/file/目录名
     *
     * @param uniqueName 目录唯一名
     * @return File（目录路径）
     */
    public static File createFileDir(String uniqueName, Context context) {
        File fileDir;
        /* 外部存储是否可用 */
        boolean externalStorageAvailable = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);
        final String createPath;// 创建路径
        if (externalStorageAvailable) {
            createPath = context.getExternalFilesDir(null).getPath();
        } else {
            createPath = context.getFilesDir().getPath();
        }
        fileDir = new File(createPath + File.separator + uniqueName);
        if (!fileDir.exists()) {// 判断本地文件夹是否存在,否则创建
            fileDir.mkdirs();
        }
        return fileDir;
    }

    /**
     * 根据文件目录名寻找路径
     *
     * @param uniqueName 文件夹名
     * @return 路径
     */
    public static String getFileDir(String uniqueName, Context context) {
        File file;
        /* 外部存储是否可用 */
        boolean externalStorageAvailable = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String createPath;// 寻找路径
        if (externalStorageAvailable) {
            createPath = context.getExternalFilesDir(null).getPath();
        } else {
            createPath = context.getFilesDir().getPath();
        }
        file = new File(createPath + File.separator + uniqueName);
        if (!file.exists()) {// 如果本地文件夹不存在,返回 null
            return null;
        }
        return createPath + File.separator + uniqueName;
    }

    /**
     * 通过递归得到某一路径下所有的目录及其文件
     *
     * @param filePath 文件夹路径
     */
    public static ArrayList<String> getImgFiles(String filePath) {
        File root = new File(filePath);
        ArrayList<String> imageFiles = new ArrayList<String>();
        if (!root.exists()) {
            return null;
        }
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                getImgFiles(file.getAbsolutePath());// 递归调用
                System.out.println("显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath());
            } else {
                String imageFile = file.getAbsolutePath();
                imageFiles.add(imageFile);
                System.out.println("显示" + filePath + "下所有子目录：" + file.getAbsolutePath() + "====" + "文件名：" + file.getName());
            }
        }
        return imageFiles;
    }

    /**
     * 得到某一路径下的文件并删除文件
     *
     * @param path     文件夹路径
     * @param filePath 文件路径
     */
    public static void deleteFile(String path, String filePath) {
        File f = new File(path);
        File file = new File(filePath);
        if (!f.exists()) {
            f.mkdir();
            return;
        } else {
            if (!file.exists()) {
                return;
            }
        }
        file.delete();
        System.out.println("显示" + filePath + "下所有子目录：" + file.getAbsolutePath() + "====" + "文件名：" + file.getName());
    }

    /**
     * 得到某一路径下的文件并删除文件
     *
     * @param filePath 文件路径
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return;
        }
        file.delete();
        System.out.println("显示" + filePath + "下所有子目录：" + file.getAbsolutePath() + "====" + "文件名：" + file.getName());
    }

    /**
     * 通过递归得到某一路径下所有的目录及其文件并删除所有文件
     *
     * @param filePath 文件夹路径
     */
    public static void deleteFiles(String filePath) {
        File root = new File(filePath);
        if (!root.exists()) {
            root.mkdirs();
            return;
        }
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                deleteFiles(file.getAbsolutePath());// 递归调用
                System.out.println("显示" + filePath + "下所有子目录及其文件" + file.getAbsolutePath());
            } else {
                file.delete();
                System.out.println("显示" + filePath + "下所有子目录：" + file.getAbsolutePath() + "====" + "文件名：" + file.getName());
            }
        }
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 移动文件
     *
     * @param srcFileName 源文件完整路径
     * @param destDirName 目的目录完整路径
     * @return 文件移动成功返回true，否则返回false
     */
    public static boolean moveFile(String srcFileName, String destDirName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        File destDir = new File(destDirName);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return srcFile.renameTo(new File(destDirName + File.separator + srcFile.getName()));
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 写入文件
     *
     * @param inputStream 输入流
     * @param file        写入的文件
     */
    public static void writeFile(InputStream inputStream, File file) throws IOException {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getAbsoluteFile());
            byte[] buff = new byte[1024 * 8];
            int flag = 0;
            while ((flag = inputStream.read(buff)) != -1) {
                fos.write(buff, 0, flag);
            }
        } finally {
            inputStream.close();
            fos.close();
        }
    }

}
