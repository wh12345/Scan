package com.qiyan.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.imageio.ImageIO;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.qiyan.util.SystemUtil;

public class PdfJpg {
	private static final Logger logger = Logger.getLogger(PdfJpg.class);
	/**
	 * 生成pdf文件
	 * @param map
	 * @return
	 */
	public  String createPDF(Map<String, String> map) {
		String templatePdfUrl = SystemUtil.getConfigByStringKey("IMG.URL")+"pdfTemplate"+File.separator+"Template.pdf";
		String pdfUrl = SystemUtil.getConfigByStringKey("IMG.URL")+"pdf";
		createDirectory(pdfUrl);
		String jdsbh = map.get("JDSBH");
		pdfUrl += File.separator+jdsbh+".pdf";
		PdfReader reader = null;
		AcroFields af = null;
		PdfStamper ps = null;
		ByteArrayOutputStream bos = null;
		FileOutputStream fos = null;	
		//加入pdf内容
		try {
			reader = new PdfReader(templatePdfUrl);
			bos = new ByteArrayOutputStream();
			ps = new PdfStamper(reader,bos);
			af = ps.getAcroFields();			
            //使用中文字体
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);			
			af.addSubstitutionFont(bfChinese);
			System.out.println("daxiao:"+map.size());
			Set<Entry<String, String>> set = map.entrySet();
			Iterator<Entry<String, String>> it = set.iterator();
			Entry<String, String> entry = null;
			while (it.hasNext()) {
				entry = it.next();
				if(entry.getValue()!=null) {
					System.out.println("key:"+entry.getKey()+" "+entry.getValue());
					af.setField(entry.getKey(), entry.getValue());
				}
			}
			//设置不能编辑
			ps.setFormFlattening(true);
		
	    	String imgurl = SystemUtil.getConfigByStringKey("IMG.URL");
			//添加图片1
			String dzyz = imgurl+"dzyz"+File.separator+map.get("DZYZ")+".PNG";
			File dzyzFile = new File(dzyz);
			if(dzyzFile.exists()) {
				int pageNo1 = af.getFieldPositions("DZYZ_af_image").get(0).page;
				Rectangle signRect1 = af.getFieldPositions("DZYZ_af_image").get(0).position;
				float x1 = signRect1.getLeft();
				float y1 = signRect1.getBottom();
				Image image1 = Image.getInstance(dzyz);
				PdfContentByte under1 = ps.getOverContent(pageNo1);
				image1.scaleToFit(signRect1.getWidth(),signRect1.getHeight());;;
				image1.setAbsolutePosition(x1,y1);
				under1.addImage(image1);
			}
			
			//添加图片2
			String zqmj = imgurl+"qm"+File.separator+map.get("ZQMJ")+".PNG";
			File zqmjFile = new File(zqmj);
			if(zqmjFile.exists()) {
				int pageNo2 = af.getFieldPositions("ZQMJ_af_image").get(0).page;
				Rectangle signRect2 = af.getFieldPositions("ZQMJ_af_image").get(0).position;
				float x2 = signRect2.getLeft();
				float y2 = signRect2.getBottom();
				Image image2 = Image.getInstance(zqmj);
				PdfContentByte under2 = ps.getOverContent(pageNo2);
				image2.scaleToFit(signRect2.getWidth(),signRect2.getHeight());
				image2.setAbsolutePosition(x2,y2);
				under2.addImage(image2);
			}
			
			//添加图片3
			String czqm = getCzqmUrl(jdsbh);
			if(czqm!=null) {
				int pageNo3 = af.getFieldPositions("CZQM_af_image").get(0).page;
				Rectangle signRect3 = af.getFieldPositions("CZQM_af_image").get(0).position;
				float x3 = signRect3.getLeft();
				float y3 = signRect3.getBottom();
				Image image3 = Image.getInstance(czqm);
				PdfContentByte under3 = ps.getOverContent(pageNo3);
				image3.scaleToFit(signRect3.getWidth(),signRect3.getHeight());;;
				image3.setAbsolutePosition(x3,y3);
				under3.addImage(image3);					
			}
			//添加水印
			String waterMarkName="韶关交警微信公众号";
			String openid = map.get("OPENID");
			PdfContentByte under;
			Rectangle wmRect =ps.getReader().getPageSizeWithRotation(1);
		    under = ps.getOverContent(1);
            under.saveState();
            // set Transparency
            PdfGState gs = new PdfGState();
            // 设置透明度为0.2
            gs.setFillOpacity(0.2f);
            under.setGState(gs);
            under.restoreState();
            under.beginText();
            under.setFontAndSize(bfChinese, 18);
            under.setTextMatrix(30, 30);
            under.setColorFill(new BaseColor(179,185,181));
        	for (int y = 0; y < 10; y++) {
				for (int x = 0; x < 8; x++) {
					// 水印文字成35度角倾斜
	                under.showTextAligned(Element.ALIGN_LEFT
	                        , waterMarkName, 5+ 260* x, 260*y, 35);	
	                under.showTextAligned(Element.ALIGN_LEFT
	                        , openid,40+ 260* x, 260*y, 35);
	                }
			}
            under.endText();
            under.setLineWidth(1f);
            under.stroke();
            
			ps.close();
			reader.close();
			logger.info("生成电子处罚单PDF文件:jdsbh"+jdsbh+"pdfUrl:"+pdfUrl);
			fos = new FileOutputStream(pdfUrl);
			fos.write(bos.toByteArray());
			fos.flush();
		} catch (IOException|DocumentException e) {
			logger.info("生成pdf文件读取文件异常！",e);
			e.printStackTrace();
		}finally{
			try {
				if(fos!=null) {
					fos.close();					
				}
				if(bos!=null) {
					bos.close();					
				}
				if(reader!=null) {
					reader.close();					
				}
			} catch (IOException e) {
				logger.info("生成pdf文件读取文件异常！",e);
				e.printStackTrace();
			}
		}		
		return pdfUrl;
	}
	
	/**
	 * 下载处罚人签名图片
	 * @param jdsbh
	 * @return
	 */
	private  String getCzqmUrl(String jdsbh) {
		String baseurl = SystemUtil.getConfigByStringKey("IMG.URL")+"czqm";
		createDirectory(baseurl);
		String imgurl = baseurl+File.separator+jdsbh+".png";       
		String neturl = SystemUtil.getConfigByStringKey("IMG.CZQMURL")+"?jdsbh="+jdsbh;
		byte[]   imgByte = SystemUtil.getCzqmImg(neturl);
		if(imgByte!=null) {
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(imgurl);
				fos.write(imgByte);
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return imgurl;			
		}
		return null;
	}
	
	/**
	 * pdf文件转成图片 dpi越大转换后越清晰，相对转换速度越慢
	 * @param PdfFilePath
	 * @param dpi
	 */
	public boolean pdf2Image(String PdfFilePath,String value,int dpi) {
		File file = new File(PdfFilePath);
		PDDocument pdDocument;
		FileOutputStream fos = null;
		try {
			String imgPDFPath = file.getParent();
			int dot = file.getName().lastIndexOf('.');
			String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
			String imgFolderPath =imgPDFPath + File.separator+value;
			if (createDirectory(imgFolderPath)) {
				pdDocument = PDDocument.load(file);
				PDFRenderer renderer = new PDFRenderer(pdDocument);
				/* dpi越大转换后越清晰，相对转换速度越慢 */
				PdfReader reader = new PdfReader(PdfFilePath);
				int pages = reader.getNumberOfPages();
				StringBuffer imgFilePath = null;
				for (int i = 0; i < pages; i++) {
					String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
					imgFilePath = new StringBuffer();
					imgFilePath.append(imgFilePathPrefix);
					imgFilePath.append(".jpg");
					File dstFile = new File(imgFilePath.toString());
					BufferedImage image = renderer.renderImageWithDPI(i, dpi);
					fos =new FileOutputStream( dstFile);
					ImageIO.write(image, "jpg",fos);
				}
				fos.close();
				reader.close();
				pdDocument.close();
				System.out.println("PDF文档转jpg图片成功！");
				return true;
 
			} else {
				System.out.println("PDF文档转jpg图片失败：" + "创建" + imgFolderPath + "失败");
				return false;
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
 
	/**
	 * 创建未存在的目录
	 * @param folder
	 * @return
	 */
	private static boolean createDirectory(String folder) {
		File dir = new File(folder);
		if (dir.exists()) {
			return true;
		} else {
			return dir.mkdirs();
		}
	}
	
	
	/**
	 * 删除所创建的pdf文件和处罚单图片
	 * @param jdsbh
	 */
	public void deleteFile(String jdsbh) {
		String baseUrl = SystemUtil.getConfigByStringKey("IMG.URL");
		String czqmImg =baseUrl+"czqm"+File.separator+jdsbh+".png";
		String pdf = baseUrl+"pdf"+File.separator+jdsbh+".pdf";
		String pdfJpg = baseUrl+"pdf"+File.separator+jdsbh+".jpg";
		System.out.println(czqmImg+" "+pdf+" "+pdfJpg);
		File czqmFile =new File(czqmImg);
		if(czqmFile.isFile()&&czqmFile.exists()) {
			System.out.println("删除车主签名"+czqmFile.delete());
		}
		File pdfFile = new File(pdf);
		if(pdfFile.isFile()&&pdfFile.exists()) {
			System.out.println("删除pdf文件"+pdfFile.delete());
		}
	}
	
	/*public static void main(String[] args) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("FXJGMC", "韶关市公安局交通警察支队高速公路二大队二中队");
		map.put("WSMC", "公安交通管理简易程序处罚决定书");
		map.put("JDSBH", "440291188001981");
		map.put("DSR", "曹前进");
		map.put("DABH", "411416004022");
		map.put("JSZH", "411424198705167537");
		map.put("ZJCX", "A2D");
		map.put("LXFS", "河南省柘城县起台镇北街村红旗二组０９７号");
		map.put("HPHM", "浙A7U999");
		map.put("HPZL", "大型汽车");
		map.put("FZJG", "豫N");
		map.put("WFSJ", "2018年09月20日09时28分");
		map.put("WFDZ", "韶赣高速123公里");
		map.put("WFNR", "驾驶机件不符合技术标准的机动车的");
		map.put("WFXW", "1073");
		map.put("FLTW", "《中华人民共和国道路交通安全法》");
		map.put("WFGD", "第九十条 《法》第21条、第90条 ");
		map.put("FKJE", "0");
		map.put("JKYH", "建行、工商、农信社等");
		map.put("FYDW", "韶关市公安局");
		map.put("SSDW", "韶关市武江区");
		map.put("DYRQ", "2018年10月24日");
		map.put("WFJFS", "0");
		map.put("DZYZ", "440200000000_1");
		map.put("ZQMJ", "100051");
		map.put("OPENID","onx9L1EGtIWj_8GwRUqvbH3_R5zE");
		String pdf =createPDF(map);
		pdf2Image(pdf, "181115", 400);
		//pdf2Image(pdf,  400);
		//deleteFile("440291188001981");		
	}*/
	public static void main(String[] args) {
	   PdfJpg pj = new PdfJpg();
	   pj.pdf2Image("G:\\test\\1111.pdf", "2018", 300);
	}
	
}
