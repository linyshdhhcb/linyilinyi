import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.Scanner;

public class ImageToCustomAscii {
    // 用户自定义的字符集（可以包括英文、汉字等）
    private static String customChars;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 获取用户输入的字符集
        System.out.print("请输入用于生成字符画的字符集（可包含汉字或英文字符）: ");
        customChars = scanner.nextLine();
        
        try {
            // 读取图片
            BufferedImage image = ImageIO.read(new File("path_to_your_image.png"));
            
            // 缩小图片尺寸
            int newWidth = 100; // 根据需要调整宽度
            int newHeight = (int) (image.getHeight() * (newWidth / (double) image.getWidth()) * 0.55); // 调整比例
            BufferedImage resizedImage = resizeImage(image, newWidth, newHeight);

            // 将图片转为字符画
            String asciiArt = convertToCustomAscii(resizedImage);
            System.out.println(asciiArt);

        } catch (Exception e) {
            System.out.println("出错了：" + e.getMessage());
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

    // 图片转换为自定义字符画
    private static String convertToCustomAscii(BufferedImage image) {
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

                // 根据灰度值选择自定义字符集中的字符
                int charIndex = (int) (grayValue / 255.0 * (customChars.length() - 1));
                asciiArt.append(customChars.charAt(charIndex));
            }
            asciiArt.append("\n"); // 每行结束换行
        }
        return asciiArt.toString();
    }
}
