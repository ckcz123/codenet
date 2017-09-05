package codenet.service;

import org.apache.commons.fileupload.FileItem;
import java.io.File;
import java.io.PrintWriter;

/**
 * Created by oc on 2017/9/5.
 */
public class DatasetService {
    public static void processUploadFile(FileItem item, PrintWriter pw, String filePath)
            throws Exception
    {
        String filename = item.getName();
        System.out.println("filename：" + filename);
        int index = filename.lastIndexOf("\\");
        filename = filename.substring(index + 1, filename.length());

        long fileSize = item.getSize();

        if("".equals(filename) && fileSize == 0)
        {
            System.out.println("empty file ...");
            return;
        }

        File pathFile = new File(filePath);
        if(!pathFile.exists())pathFile.mkdirs();
        File uploadFile = new File(filePath + "/" + filename);
        item.write(uploadFile);
        pw.println(filename + " file saved ...");
        pw.println("file size ：" + fileSize + "\r\n");
    }

//    public static void processFormField(FileItem item, PrintWriter pw)
//            throws Exception
//    {
//        String name = item.getFieldName();
//        String value = item.getString();
//        pw.println(name + " : " + value + "\r\n");
//    }
}
