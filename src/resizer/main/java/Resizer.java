package resizer.main.java;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Resizer {
	public static final File TARGET_FILE = new File(System.getProperty("user.dir"));
	public static final File IO_FILE = new File(TARGET_FILE.toString()+File.separator+"io_file");
	public static void main(String[] args) throws FileNotFoundException, IOException {
		int i = 0;
		File[] infiles = getFiles(IO_FILE);
		File newFile = IO_FILE;
		//检查io_file文件夹是否存在，否则新建
		if(!newFile.exists()&&!newFile.isDirectory()) {
			newFile.mkdir();
			JOptionPane.showMessageDialog(null, "将你的图片放入 "+IO_FILE.toString()+File.separator+" 文件夹, 然后再次运行");
			return;
		}
		for(File file:infiles){
			//取得文件名称
			String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
			//调整图片尺寸并保持，不是图片则跳过
			try {
				BufferedImage img = readImage(file);
				if(img!=null) {
					writeImage(resizeImage(img, 32), fileName+"_32");
					writeImage(resizeImage(img, 128), fileName+"_128");
					i++;
				}
			}catch(IOException ex) {
				continue;
			}
		}
		JOptionPane.showMessageDialog(null, i > 0?"调整了"+i+"张图片，请在"+IO_FILE.toString()+"中查看":IO_FILE.toString()+"中没有图片");
		
	}
	//取得文件
	public static File[] getFiles(File path){
		File[] files = path.listFiles();
		return files;
	}
	//读取文件的图片
	public static BufferedImage readImage(File file) throws FileNotFoundException, IOException {
		BufferedImage image = ImageIO.read(file);
		return image;
	}
	//写入png图片至文件夹
	public static void writeImage(BufferedImage img,String str) throws IOException {
		File file = IO_FILE;
		ImageIO.write(img, "png", new File(file.toString()+File.separator+str+".png"));
	}
	//修改图片的尺寸
	public static BufferedImage resizeImage(BufferedImage bimg,int size){
		Image img = bimg.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		BufferedImage outImg = new BufferedImage(size, size, BufferedImage.TRANSLUCENT);
		outImg.getGraphics().drawImage(img, 0, 0, null);
		return outImg;
	}
}
