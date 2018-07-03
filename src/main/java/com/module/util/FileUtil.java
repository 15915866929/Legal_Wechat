package com.module.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class.getName());

	/**
	 * 单个文件上传
	 * 
	 * @param byteFile 
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadOneFile(byte[] byteFile, String filePath, String fileName) throws Exception {
		FileOutputStream out = null;
		try {
			File targetFile = new File(filePath);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			out = new FileOutputStream(filePath + fileName);
			out.write(byteFile);
			out.flush();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			//关闭文件流
			closeFileStream(out);
		}
	}
	
	/**
	 * 单个文件上传
	 * 
	 * @param multipartFile 
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadOneFile(MultipartFile multipartFile, String filePath, String fileName) throws Exception {
		FileOutputStream out = null;
		byte[] byteFile = multipartFile.getBytes();
		try {
			File targetFile = new File(filePath);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			out = new FileOutputStream(filePath + fileName);
			out.write(byteFile);
			out.flush();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			//关闭文件流
			closeFileStream(out);
		}
	}

	/**
	 * 下载文件，是向页面输出流，不返回流
	 * 
	 * @param filePath
	 *            文件服务器存储目录
	 * @param downloadName
	 *            下载文件保存的文件名
	 * @param fileName
	 *            服务器存储文件名
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void downloadFile(String filePath, String downloadName, String fileName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// fileName = new java.net.URLDecoder().decode(fileName, "utf-8");
		// downloadName = new java.net.URLDecoder().decode(downloadName,
		// "utf-8");
		String path = filePath + fileName;
		if (!judeFileExists(path)) {
			// 文件不存在就不进行流操作
			throw new UnsupportedEncodingException("文件不存在：" + path);
		}
		String agent = request.getHeader("USER-AGENT");
		if (null != agent && -1 != agent.indexOf("MSIE")) // IE
		{
			downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
		} else if (null != agent && -1 != agent.indexOf("Mozilla")) // Firefox
		{
			downloadName = new String(downloadName.getBytes("UTF-8"), "iso-8859-1");
		} else {
			downloadName = java.net.URLEncoder.encode(downloadName, "UTF-8");
		}

		response.setContentType("application/octet-stream;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + downloadName);
		OutputStream toClient = null;
		InputStream fis = null;
		try {
			// 以流的形式下载文件
			fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			// fis.close();

			toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
			// toClient.close();
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			//关闭文件流
			closeFileStream(fis,toClient);
		}
	}

	/**
	 * 显示图片,是向页面输出流，不返回流
	 * 
	 * @param filePath
	 *            文件服务器存储目录
	 * @param fileName
	 *            服务器存储文件名
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void showImage(String filePath, String fileName, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String path = filePath + fileName;

		if (!judeFileExists(path)) {
			// 文件不存在就不进行流操作
			throw new UnsupportedEncodingException("文件不存在：" + path);
		}
		response.setContentType("image/*");
		// response.setHeader("Content-Disposition", "attachment;filename=" +
		// downloadName);
		InputStream fis=null;
		OutputStream toClient=null;
		try {
			// 以流的形式下载文件
			fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
//			fis.close();

			toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
//			toClient.close();
		} catch (Exception e) {
			throw new Exception(e);
		}finally {
			//关闭文件流
			closeFileStream(fis,toClient);
		}
	}
	
	/**
	 * 播放视频流，是向页面输出流，不返回流（断点流）
	 * @author chc
	 * @date 2017-09-04
	 * @param filePath
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public static void playVideoStream(String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedInputStream bis = null;
        try {
            File file = new File(filePath+fileName);
            if (file.exists()) {
                long p = 0L;
                long toLength = 0L;
                long contentLength = 0L;
                int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
                long fileLength;
                String rangBytes = "";
                fileLength = file.length();

                // get file content
                InputStream ins = new FileInputStream(file);
                bis = new BufferedInputStream(ins);

                // tell the client to allow accept-ranges
                response.reset();
                response.setHeader("Accept-Ranges", "bytes");

                // client requests a file block download start byte
                String range = request.getHeader("Range");
                if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
                    response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
                    rangBytes = range.replaceAll("bytes=", "");
                    if (rangBytes.endsWith("-")) {  // bytes=270000-
                        rangeSwitch = 1;
                        p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
                        contentLength = fileLength - p;  // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
                    } else { // bytes=270000-320000
                        rangeSwitch = 2;
                        String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
                        String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
                        p = Long.parseLong(temp1);
                        toLength = Long.parseLong(temp2);
                        contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
                    }
                } else {
                    contentLength = fileLength;
                }

                // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
                // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
                response.setHeader("Content-Length", new Long(contentLength).toString());

                // 断点开始
                // 响应的格式是:
                // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
                if (rangeSwitch == 1) {
                    String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
                            .append(new Long(fileLength - 1).toString()).append("/")
                            .append(new Long(fileLength).toString()).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else if (rangeSwitch == 2) {
                    String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    bis.skip(p);
                } else {
                    String contentRange = new StringBuffer("bytes ").append("0-")
                            .append(fileLength - 1).append("/")
                            .append(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                }

                /*原始代码
                String fileName = file.getName();
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);*/
                response.setContentType("video/*");//我的代码

                OutputStream out = response.getOutputStream();
                int n = 0;
                long readLength = 0;
                int bsize = 1024;
                byte[] bytes = new byte[bsize];
                if (rangeSwitch == 2) {
                    // 针对 bytes=27000-39000 的请求，从27000开始写数据
                    while (readLength <= contentLength - bsize) {
                        n = bis.read(bytes);
                        readLength += n;
                        out.write(bytes, 0, n);
                    }
                    if (readLength <= contentLength) {
                        n = bis.read(bytes, 0, (int) (contentLength - readLength));
                        out.write(bytes, 0, n);
                    }
                } else {
                    while ((n = bis.read(bytes)) != -1) {
                        out.write(bytes, 0, n);
                    }
                }
                out.flush();
                out.close();
                bis.close();
            } else {
                System.out.println("Error: file not found.");
            }
        } catch (IOException ie) {
            // 忽略 ClientAbortException 之类的异常
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * 批量上传
	 * @author chc
	 * @date 2017-09-11
	 * @param multipartFileList
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadManyFile(List<MultipartFile> multipartFileList, String filePath, String fileName) throws Exception{
		for(MultipartFile multipartFile:multipartFileList){
			uploadOneFile(multipartFile, filePath, fileName);
		}
		
	}
	
	/**
	 * 批量上传
	 * @author chc
	 * @date 2017-09-11
	 * @param byteFileList
	 * @param filePath
	 * @param fileName
	 * @throws Exception
	 */
	public static void uploadManyFileTobyte(List<byte[]> byteFileList, String filePath, String fileName) throws Exception{
		for(byte[] byteFile:byteFileList){
			uploadOneFile(byteFile, filePath, fileName);
		}
		
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (judeFileExists(file)) {
			if (file.delete()) {
				// System.out.println("删除单个文件" + fileName + "成功！");
				logger.info("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				// System.out.println("删除单个文件" + fileName + "失败！");
				logger.warning("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 获取文件后缀名
	 * 
	 * @author chc
	 * @date 2017-08-09
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return "";
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @author chc
	 * @date 2017-08-17
	 * @param filePath
	 *            文件地址（地址+文件名）
	 * @return true:存在，false:不存在
	 */
	public static boolean judeFileExists(String filePath) {
		File file = new File(filePath);
		boolean result;
		// 如果文件路径所对应的文件存在，并且是一个文件
		if (file.exists() && file.isFile()) {
			result = true;
		} else {
			logger.warning("文件不存在：" + filePath);
			result = false;
		}
		return result;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @author chc
	 * @date 2017-08-17
	 * @param file
	 * @return true:存在，false:不存在
	 */
	public static boolean judeFileExists(File file) {
		boolean result;
		// 如果文件路径所对应的文件存在，并且是一个文件
		if (file.exists() && file.isFile()) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	/**
	 * 关闭io流
	 * @author chc
	 * @date 2017-09-11
	 * @param ioStream
	 */
	private static void closeFileStream(Closeable...ioStream){
		for(Closeable cl:ioStream){
			if(cl!=null){
				try {
					cl.close();
				} catch (IOException e) {
				}
			}
		}
		
	}

	/**
	 * 路径分隔符
	 * @return
	 */
	public static String getDelimiter(){
		String delimiter = System.getProperty("file.separator"); //分隔符
		return delimiter;
	}

}
