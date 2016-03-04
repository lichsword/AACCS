package com.at.common;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author lichs_000
 * 
 */
public class AtuFile {

    private static final int DEFAULT_BUFFER_SIZE = 8192;

    private AtuFile() {
    }

    /**
     * ensure a dir exist.
     * 
     * @param dirPath
     * @return
     */
    public static boolean ensureDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            return file.mkdirs();
        } // end if
        return true;
    }

    /**
     * check file exist.
     * 
     * @param filePath
     * @return
     */
    public static boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * If file has exist & is not directory, delete it.
     * 
     * @param path
     *            the file to delete
     * @return true if file deleted.
     */
    public static boolean deleteFile(String path) {
        boolean success = false;
        File file = new File(path);
        if (file.exists() && !file.isDirectory()) {
            file.delete();
            success = true;
        } else {
            success = false;
        }
        return success;
    }

    /**
     * Clean a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     */
    public static void cleanDir(final File dir) {
        deleteDir(dir, false);
    }

    /**
     * Clean a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     * @param filter
     *            the filter to determine which file or directory to delete.
     */
    public static void cleanDir(final File dir, final FilenameFilter filter) {
        deleteDir(dir, false, filter);
    }

    /**
     * Clean a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     * @param filter
     *            the filter to determine which file or directory to delete.
     */
    public static void cleanDir(final File dir, final FileFilter filter) {
        deleteDir(dir, false, filter);
    }

    /**
     * Clean dir but ignore file in parameter ignoreFilePathList.
     * 
     * @param dir
     * @param ignoreFilePathList
     */
    public static void cleanDir(File dir, ArrayList<String> ignoreFilePathList) {
        File[] fileList = null;
        if (dir.exists() && dir.isDirectory()) {
            fileList = dir.listFiles();
            for (File file : fileList) {
                if (ignoreFilePathList.contains(file.getAbsolutePath())) {
                    // do nothing
                    continue;
                } // end if

                if (file.isFile()) {
                    file.delete();
                } // end if
            }
        } // end if
    }

    /**
     * Delete a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     */
    public static void deleteDir(final File dir) {
        deleteDir(dir, true);
    }

    /**
     * Delete a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     * @param filter
     *            the filter to determine which file or directory to delete.
     */
    public static void deleteDir(final File dir, final FileFilter filter) {
        deleteDir(dir, true, filter);
    }

    /**
     * Delete a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     * @param filter
     *            the filter to determine which file or directory to delete.
     */
    public static void deleteDir(final File dir, final FilenameFilter filter) {
        deleteDir(dir, true, filter);
    }

    /**
     * Delete a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     * @param removeDir
     *            true to remove the {@code dir}.
     */
    public static void deleteDir(final File dir, final boolean removeDir) {
        if (dir != null && dir.isDirectory()) {
            final File[] files = dir.listFiles();
            for (final File file : files) {
                if (file.isDirectory()) {
                    deleteDir(file, removeDir);
                } else {
                    file.delete();
                }
            }
            if (removeDir) {
                dir.delete();
            }
        }
    }

    /**
     * Delete a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     * @param removeDir
     *            true to remove the {@code dir}.
     * @param filter
     *            the filter to determine which file or directory to delete.
     */
    public static void deleteDir(final File dir, final boolean removeDir, final FileFilter filter) {
        if (dir != null && dir.isDirectory()) {
            final File[] files = dir.listFiles(filter);
            if (files != null) {
                for (final File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file, removeDir, filter);
                    } else {
                        file.delete();
                    }
                }
            }
            if (removeDir) {
                dir.delete();
            }
        }
    }

    /**
     * Delete a specified directory.
     * 
     * @param dir
     *            the directory to clean.
     * @param removeDir
     *            true to remove the {@code dir}.
     * @param filter
     *            the filter to determine which file or directory to delete.
     */
    public static void deleteDir(final File dir, final boolean removeDir, final FilenameFilter filter) {
        if (dir != null && dir.isDirectory()) {
            final File[] files = dir.listFiles(filter);
            if (files != null) {
                for (final File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file, removeDir, filter);
                    } else {
                        file.delete();
                    }
                }
            }
            if (removeDir) {
                dir.delete();
            }
        }
    }

    /**
     * Write the specified data to an specified file.
     * 
     * @param file
     *            The file to write into.
     * @param data
     *            The data to write. May be null.
     */
    public static final void writeDataToFile(final File file, byte[] data) {
        if (null == file) {
            throw new IllegalArgumentException("file may not be null.");
        }
        if (null == data) {
            data = new byte[0];
        }
        final File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static final void writeDataToFile(final File file, final InputStream stream) {
    	boolean append = false;
    	writeDataToFile(file, stream, append);
    }
    /**
     * Write the specified data to an specified file.
     * 
     * @param file
     *            The file to write into.
     * @param stream
     *            The stream to write.
     */
    public static final void writeDataToFile(final File file, final InputStream stream, boolean append) {
        if (null == file) {
            throw new IllegalArgumentException("file may not be null.");
        }
        if (null == stream) {
            throw new IllegalArgumentException("stream may not be null.");
        }
        final File dir = file.getParentFile();
        if (dir != null && !dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fos = null;
        final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        try {
            fos = new FileOutputStream(file, append);
            int bytesRead = stream.read(buffer);
            while (bytesRead > 0) {
                fos.write(buffer, 0, bytesRead);
                bytesRead = stream.read(buffer);
            }
            fos.close();
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the specified data to an specified file.
     * 
     * @param srcFile
     *            The file to write into.
     * @param destFile if is directory, will copy into this directory as same file name.
     */
    public static final void copyFile(final File srcFile, File destFile) {
        if (null == srcFile) {
            throw new IllegalArgumentException("srcFile may not be null.");
        }
        if (null == destFile) {
            throw new IllegalArgumentException("destFile may not be null.");
        }
        
        if(destFile.isDirectory()){
        	destFile = new File(destFile, srcFile.getName());
        }
        
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead = fis.read(buffer);
            while (bytesRead > 0) {
                fos.write(buffer);
                bytesRead = fis.read(buffer);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            quietClose(fis);
            quietClose(fos);
        }
    }

    public static final void copyStreamToFile(final InputStream inputStream, final File destFile) {
        FileOutputStream fileOutputStream = null;
        try {
            ensureParent(destFile);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            fileOutputStream = new FileOutputStream(destFile);
            final byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int bytesRead = inputStream.read(buffer);
            while (bytesRead > 0) {
                fileOutputStream.write(buffer);
                bytesRead = inputStream.read(buffer);
            }
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            quietClose(inputStream);
            quietClose(fileOutputStream);
        }
    }

    /**
     * Write the specified data to an specified file.
     * 
     * @param file
     *            The file to write into.
     * @param data
     *            The data to write. May be null.
     */
    public static final void moveFile(final File srcFile, final File destFile) {
        if (null == srcFile) {
            throw new IllegalArgumentException("srcFile may not be null.");
        }
        if (null == destFile) {
            throw new IllegalArgumentException("destFile may not be null.");
        }
        srcFile.renameTo(destFile);
    }

    /**
     * Close an {@linkplain Closeable} quietly.
     * 
     * @param closeable
     *            the {@linkplain Closeable} to close.
     */
    public static final void quietClose(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (final IOException e) {
                // Ignore errors.
            }
        }
    }

    public static long getFileSize(final String diskPath) {
        long result = 0;
        File file = new File(diskPath);
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (File child : list) {
                result += getFileSize(child.getAbsolutePath());
            }
        } else {
            result = file.length();
        }
        return result;
    }

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;

    private static final float KB_f = KB;
    private static final float MB_f = MB;
    private static final float GB_f = GB;

    /**
     * long大小转为含单位字符串
     * 
     * @param size
     * @return
     */
    public static String sizeToString(long size) {

        String result = "";

        if (size < KB) {
            result = String.format("%dKB", size);
        } else if (size < MB) {
            float value = (size) / KB_f;
            result = String.format("%3.1fKB", value);
        } else if (size < GB) {
            float value = (size) / MB_f;
            result = String.format("%3.1fMB", value);
        } else {
            float value = (size) / GB_f;
            result = String.format("%3.1fGB", value);
        }

        return result;
    }


    /**
     * Retrieve the main file name.
     * 
     * @param path
     *            the file name.
     * @return the main file name without the extension.
     */
    public static String getFileNameWithoutExtension(final String path) {
        if (AtuText.isEmpty(path)) {
            return null;
        }
        return getFileNameWithoutExtension(new File(path));
    }

    /**
     * Retrieve the main file name.
     * 
     * @param file
     *            the file.
     * @return the main file name without the extension.
     */
    public static String getFileNameWithoutExtension(final File file) {
        if (null == file) {
            return null;
        }
        String fileName = file.getName();
        final int index = fileName.lastIndexOf('.');
        if (index >= 0) {
            fileName = fileName.substring(0, index);
        }
        return fileName;
    }

    /**
     * Retrieve the main file name.
     * 
     * @param path
     *            the file name.
     * @return the extension of the file.
     */
    public static String getExtension(final String path) {
        if (AtuText.isEmpty(path)) {
            return null;
        }
        return getExtension(new File(path));
    }

    /**
     * Retrieve the extension of the file.
     * 
     * @param file
     *            the file.
     * @return the extension of the file.
     */
    public static String getExtension(final File file) {
        if (null == file) {
            return null;
        }
        final String fileName = file.getName();
        final int index = fileName.lastIndexOf('.');
        String extension;
        if (index >= 0) {
            extension = fileName.substring(index + 1);
        } else {
            extension = "";
        }
        return extension;
    }

    public static String getExtensionOfUrl(final String url) {
        if (AtuText.isEmpty(url)) {
            return null;
        } // end if

        final int index = url.lastIndexOf('.');
        String extension;
        if (index >= 0) {
            extension = url.substring(index + 1);
        } else {
            extension = "";
        }
        return extension;
    }

    /**
     * compute the size of one folder
     * 
     * @param dir
     * @return the byte length for the folder
     */
    public static long computeFolderSize(final File dir) {
        if (dir == null) {
            return 0;
        }
        long dirSize = 0;
        final File[] files = dir.listFiles();
        if (null != files) {
            for (int i = 0; i < files.length; i++) {
                final File file = files[i];
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize += file.length();
                    dirSize += computeFolderSize(file);
                }
            }
        }
        return dirSize;
    }

    public static void writeIntoFile(final File file, final String content, final boolean append) {
        if (null != file && !AtuText.isEmpty(content)) {
            ensureParent(file);
            try {
                final FileWriter fileWriter = new FileWriter(file, append);
                fileWriter.write(content);
                fileWriter.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void ensureParent(final File file) {
        if (null != file) {
            final File parentFile = file.getParentFile();
            if (null != parentFile && !parentFile.exists()) {
                parentFile.mkdirs();
            }
        }
    }

    public static String readFile(final File file) {
        final StringBuffer builder = new StringBuffer();
        if (null != file && file.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                final InputStreamReader fileReader = new InputStreamReader(fileInputStream);
                final BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                bufferedReader.close();
                fileReader.close();
                fileInputStream.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    public static String readInputStream(InputStream inputStream) {
        final StringBuilder builder = new StringBuilder();
        if (null != inputStream) {
            try {
                InputStreamReader reader = new InputStreamReader(inputStream);
                final BufferedReader bufferedReader = new BufferedReader(reader);
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line + "\n");
                }
                bufferedReader.close();
                reader.close();
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }
}
