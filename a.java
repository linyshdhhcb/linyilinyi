import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class a {
    // 简化后的ASCII字符集，根据亮度不同，顺序从暗到亮
    private static String simpleChars = "8#%*+=-:. ";

    public static void main(String[] args) {
        try {
            // 读取图片
            BufferedImage image = ImageIO.read(new File("C:\\Users\\a1830\\Pictures\\漫\\result.png"));

            // 缩小图片尺寸
            int newWidth = 100; // 根据需要设置新的宽度
            double heightRatio = 0.55; // 用户可以调整此值以控制高度缩放比例
            int newHeight = (int) (image.getHeight() * (newWidth / (double) image.getWidth()) * heightRatio);
            BufferedImage resizedImage = resizeImage(image, newWidth, newHeight);

            // 将图片转为简化字符画
            String asciiArt = convertToSimpleAscii(resizedImage);

            // 写入文本文件
            writeAsciiArtToFile(asciiArt, "C:\\Users\\a1830\\Desktop\\临时文件\\3.txt");

            System.out.println("ASCII 艺术已成功写入 output.txt 文件。");

        } catch (IOException e) {
            System.out.println("读取图片时出错：" + e.getMessage());
        } catch (Exception e) {
            System.out.println("出错了：" + e.getMessage());
        }
    }

    private static void writeAsciiArtToFile(String asciiArt, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(asciiArt);
        }
    }

    // 图片尺寸调整
    private static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB); // 保留Alpha通道
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }

    // 图片转换为简化字符画
    private static String convertToSimpleAscii(BufferedImage image) {
        StringBuilder asciiArt = new StringBuilder();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // 获取当前像素的ARGB值
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel, true); // 包含alpha通道

                // 跳过透明像素
                if (color.getAlpha() == 0) {
                    asciiArt.append(" "); // 透明像素对应空格
                    continue;
                }

                // 将颜色转为灰度值
                int grayValue = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                // 根据灰度值选择简化字符集中的字符
                int charIndex = (int) (grayValue / 255.0 * (simpleChars.length() - 1));
                asciiArt.append(simpleChars.charAt(charIndex));
            }
            asciiArt.append("\n"); // 每行结束换行
        }
        return asciiArt.toString();
    }
}
