import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/19
 * @ClassName: chunkTest
 */
public class chunkTest {

    public static void main(String[] args) throws IOException {
        testChunk();

    }

    /**
     * 将给定的文件分割成多个较小的部分并保存到指定目录中
     * 此方法主要用于处理大文件，通过将其分割成小块，便于后续处理或传输
     *
     * @throws IOException 如果在文件操作过程中发生错误
     */
    public static void testChunk() throws IOException {
        //源文件路径
        File rawFile = new File("C:\\Users\\a1830\\Videos\\崩溃.mp4");
        //分块文件存放的路径
        File ripeFile = new File("C:\\Users\\a1830\\Videos\\chunk\\");
        if (!ripeFile.exists()) {
            ripeFile.mkdir();
        }
        //分片大小 5M
        int chunk = 1024 * 1024 * 30;
        //分片总数
        int chunkNum = (int) Math.ceil(1.0 * rawFile.length() / chunk);
        //流对象读取源文件 向分片文件写入数据 5M
        byte[] bytes = new byte[1024];
        //源文件指针
        RandomAccessFile r = new RandomAccessFile(rawFile, "r");
        //进行分片操作
        for (int i = 0; i < chunkNum; i++) {
            File file = new File(ripeFile.getPath() + "\\" + i);
            //创建文件（文件存在会失败）
            boolean newFile = file.createNewFile();
            RandomAccessFile rw = new RandomAccessFile(file, "rw");
            if (newFile) {
                int len = -1;
                while ((len = r.read(bytes)) != -1) {
                    rw.write(bytes, 0, len);
                    if (file.length() >= chunk) {
                        break;
                    }
                }
            }
            rw.close();
        }
        r.close();
    }




}
