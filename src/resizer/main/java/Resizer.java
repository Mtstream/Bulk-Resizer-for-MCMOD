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
		//���io_file�ļ����Ƿ���ڣ������½�
		if(!newFile.exists()&&!newFile.isDirectory()) {
			newFile.mkdir();
			JOptionPane.showMessageDialog(null, "�����ͼƬ���� "+IO_FILE.toString()+File.separator+" �ļ���, Ȼ���ٴ�����");
			return;
		}
		for(File file:infiles){
			//ȡ���ļ�����
			String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
			//����ͼƬ�ߴ粢���֣�����ͼƬ������
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
		JOptionPane.showMessageDialog(null, i > 0?"������"+i+"��ͼƬ������"+IO_FILE.toString()+"�в鿴":IO_FILE.toString()+"��û��ͼƬ");
		
	}
	//ȡ���ļ�
	public static File[] getFiles(File path){
		File[] files = path.listFiles();
		return files;
	}
	//��ȡ�ļ���ͼƬ
	public static BufferedImage readImage(File file) throws FileNotFoundException, IOException {
		BufferedImage image = ImageIO.read(file);
		return image;
	}
	//д��pngͼƬ���ļ���
	public static void writeImage(BufferedImage img,String str) throws IOException {
		File file = IO_FILE;
		ImageIO.write(img, "png", new File(file.toString()+File.separator+str+".png"));
	}
	//�޸�ͼƬ�ĳߴ�
	public static BufferedImage resizeImage(BufferedImage bimg,int size){
		Image img = bimg.getScaledInstance(size, size, Image.SCALE_DEFAULT);
		BufferedImage outImg = new BufferedImage(size, size, BufferedImage.TRANSLUCENT);
		outImg.getGraphics().drawImage(img, 0, 0, null);
		return outImg;
	}
}
